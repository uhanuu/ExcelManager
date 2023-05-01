package io.excelmanager.v1.service;

import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class ExcelFileReader {
    private final ExcelFileReaderProperties excelFileReaderProperties;

    public ExcelFileReader(ExcelFileReaderProperties excelFileReaderProperties) {
        this.excelFileReaderProperties = excelFileReaderProperties;
    }

    public List<Map<Object, Object>> readExcel() {
        String path = excelFileReaderProperties.getPath();
        String fileName = excelFileReaderProperties.getFileName();

        List<Map<Object, Object>> list = new ArrayList<>();
        if (path == null || fileName == null) {
            return list;
        }

        FileInputStream fis = null;
        File excel = new File(path + fileName);

        try {
            fis = new FileInputStream(excel);
            Workbook workbook = null;
            if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else if (fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            }

            if (workbook != null) {
                int sheets = workbook.getNumberOfSheets();
                getSheet(workbook, sheets, list);
            }
        } catch (IOException e) {
            //예외처리 해주기
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    private static void getSheet(Workbook workbook, int sheets, List<Map<Object, Object>> list) {
        for (int i = 0; i < sheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int rows = sheet.getLastRowNum();
            getRow(sheet, rows, list);
        }
    }

    private static void getRow(Sheet sheet, int rows, List<Map<Object, Object>> list) {
        for (int i = 0; i <= rows; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                int cells = row.getPhysicalNumberOfCells();
                list.add(getCell(row, cells));
            }
        }
    }

    private static Map<Object, Object> getCell(Row row, int cells) {
        //colums -> ExcelFileProperties 을 이용해서 넣어주기
        String[] columns = { "type", "university_id", "name", "branch", "domain"};
        Map<Object, Object> map = new HashMap<>();
        int[] cellFilter = getCellIndex(columns);
        int i = 0;
        for (int j = 0; j < columns.length; j++) {

            Cell cell = row.getCell(cellFilter[i]);
            i++;
            if (cell != null) {
                String strValue = cell.getStringCellValue();
                if (strValue.contains("대학원") || strValue.contains("대학원대학")){
                    break;
                }
                switch (cell.getCellType()) {
                    //다형성을 이용해서 변경 변경가능성 염두
                    case BLANK:
                        map.put(columns[j], "");
                        break;
                    case STRING:
                        map.put(columns[j], cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            map.put(columns[j], cell.getDateCellValue());
                        } else {
                            map.put(columns[j], cell.getNumericCellValue());
                        }
                        break;
                    case ERROR:
                        map.put(columns[j], cell.getErrorCellValue());
                        break;
                    default:
                        map.put(columns[j], "");
                        break;
                }
            }
        }

        return map;
    }
    private static int[] getCellIndex(String[] type){
        int[] filterIndex = new int[type.length];
        Map<String, Character> filter = Map.of(
                //enum 으로 수정해서 변경가능성 염두하기
                "type",'A',
                "university_id", 'B',
                "name", 'C',
                "branch",'D',
                "domain", 'R'

        );
        for (int i = 0; i < type.length; i++){
            filterIndex[i] = filter.get(type[i]) - 65;
        }
        return filterIndex;
    }
}