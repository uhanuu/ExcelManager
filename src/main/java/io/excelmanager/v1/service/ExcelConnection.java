package io.excelmanager.v1.service;


import io.excelmanager.v1.properties.DataSourceConverter;
import io.excelmanager.v1.properties.ExcelDataSourceProperties;
import io.excelmanager.v1.properties.ExcelFileReaderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelConnection {
    private final ExcelDataSourceProperties excelDataSourceProperties;
    private final ExcelFileReaderProperties excelFileReaderProperties;
    private Connection getConnection() {

        String dbUrl = excelDataSourceProperties.getUrl();
        String username = excelDataSourceProperties.getUsername();
        String password = excelDataSourceProperties.getPassword();
        String driverClassName = excelDataSourceProperties.getDriverClassName();
        log.info("dbUrl={}",dbUrl);
        log.info("username={}",username);
        log.info("password={}",password);
        log.info("driverClassName={}",driverClassName);

        Connection conn = null;

        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e){
            e.getMessage();
            log.error("연결되지 않았습니다.");
        }
        try {
            conn = DriverManager.getConnection(dbUrl,username,password);
            log.info("연결에 성공했습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

     public void excute(List<Map<String, Object>> list) throws IOException {
        Connection  conn  = null;
        PreparedStatement pstmt = null;
        String query = setInitialQuery();
        Map<String, Object> mapKeyToValue = DataSourceConverter.getMapKeyToValue();
         List<String> attributeType = excelFileReaderProperties.getAttributeType();
         List<String> key = excelFileReaderProperties.getAttributeKey();
        log.info("총 라인 수={}",list.size());



        try {
            conn = getConnection();	//데이터베이스 연결
            pstmt = conn.prepareStatement(query);	//쿼리 설정

            for(int i=1; i<list.size(); i++) {      //매개변수로 받아온 ArrayList 의 길이만큼 반복한다.

                //읽어온 각 셀들이 자신이 생성해준 table 제약조건과 일치하지 않을 경우 SqlException이 발생한다.
                //그러한 조건이 발생하면 continue 를 해주는 부분을 추가해주면 된다.
                if(list.get(i).isEmpty()) continue;	//행에 값이 없을 경우에 그 행을 제외하고 진행

                for (int j = 0; j < mapKeyToValue.size(); j++){

                    String excelValue = (String) list.get(i).get(key.get(j));
                    switch (attributeType.get(j).toLowerCase()) {
                        case "string": pstmt.setString(j+1,excelValue);
                        break;
                        case "int" : pstmt.setInt(j+1,Integer.parseInt(excelValue));
                        break;
                        case "long": pstmt.setLong(j+1, Long.parseLong(excelValue));
                        break;
                    }
                }

                //update query 실행
                pstmt.executeUpdate();
            }
            log.info("insert를 완료했습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace();}
            if(conn  != null) try { conn.close();  } catch (SQLException e) { e.printStackTrace();}
        } // DB 연결에 사용한 객체와 Query수행을 위해 사용한 객체를 닫는다.
    }

    public String setInitialQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");

        List<String> attributes = excelFileReaderProperties.getAttributeKey();
        String databaseTableName = excelFileReaderProperties.getDatabaseTableName();

        sb.append(databaseTableName+" (");
        for (String attribute : attributes){
            sb.append(attribute+",");
        }
        //마지막 , 지우기
        sb.deleteCharAt(sb.length() -1);
        sb.append(") values (");

        for (int i = 0; i < attributes.size(); i++){
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() -1);
        sb.append(")");

        return String.valueOf(sb);
    }
}
