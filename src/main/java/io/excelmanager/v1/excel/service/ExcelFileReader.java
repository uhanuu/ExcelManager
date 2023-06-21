package io.excelmanager.v1.excel.service;

import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static io.excelmanager.v1.properties.DataSourceConverter.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelFileReader {

    private final ExcelFileReaderProperties excelFileReaderProperties;


    public List<Map<String, Object>> readExcel() {
        String path = excelFileReaderProperties.getPath();
        String fileName = excelFileReaderProperties.getFileName();

        List<Map<String, Object>> list = new ArrayList<>();
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

    private static void getSheet(Workbook workbook, int sheets, List<Map<String, Object>> list) {
        for (int i = 0; i < sheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int rows = sheet.getLastRowNum();
            getRow(sheet, rows, list);
        }
    }

    private static void getRow(Sheet sheet, int rows, List<Map<String, Object>> list) {
        for (int i = 0; i <= rows; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                int cells = row.getPhysicalNumberOfCells();
                list.add(getCell(row, cells));
            }
        }
    }

    private static Map<String, Object> getCell(Row row, int cells) {

        int[] cellFilter = getIntArrayValues();
        Map<String, Object> map = new HashMap<>();
        String[] tmp = getMapKeyToValue()
                        .keySet().toArray(new String[getMapKeyToValue().size()]);
        String[] columns = new String[getMapKeyToValue().size()];
        Map<String, Object> mapKeyToValue = getMapKeyToValue();

        //순서 맞춰주기
        for (int i = 0; i < mapKeyToValue.size(); i++){
            columns[(int) mapKeyToValue.get(tmp[i])] = tmp[i];
        }

        int i = 0;
        for (int j = 0; j < cellFilter.length; j++) {

            Cell cell = row.getCell(cellFilter[i]);
            i++;
            if (cell != null) {
                switch (cell.getCellType()) {
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
}
