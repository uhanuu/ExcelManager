공공데이터포털에서 사용하면 유용할 Excel파일들이 있어, 데이터를 추출해서 DB에 저장하면 유용할거 같아서 개발하기 시작했다.

<img width="1438" alt="image" src="https://user-images.githubusercontent.com/110734817/235599797-6cfd6dfc-68b5-46f6-99e2-b87dc7c7967f.png">

> Reference: https://www.data.go.kr/index.do

## Spring @ConfigurationProperties을 이용해서 apllication.properties 설정 정보 가져오기
![image](https://github.com/uhanuu/ExcelManager/assets/110734817/e0a11cc2-1cae-49f7-b81d-ac95fe285f7f)



| 값 | 의미 |
|:---:|:---:|
| `url` | DB connection을 위한 옵션 |
| `username` | DB connection을 위한 옵션 |
| `password` | DB connection을 위한 옵션 |
| `driverClassName` | DB connection을 위한 옵션 |

| 값 | 의미 | 기본값 |
|:---:|:---:|:---:|
| `attributeKey` | DB테이블의 column 이름을 순서대로 작성 | `NULL` |
| `attributeValue` | Excel파일에서 읽어올 column 이름(Excel은 A~Z넘을 수 있다.) 테이블 명과 일치하게 작성 | `NULL` |
| `attributeType` | DB colums에 데이터 타입으로 JPA에 명시할 때 사용하는 데이터 타입으로 작성(대소문자 구별X) | `NULL` |
| `path` | Excel파일 경로 | `NULL` |
| `fileName` | Excel파일 이름 | `NULL` |
| `databaseTableName` | DB connection을 위한 옵션 | `NULL` |
| `mode` | 테이블이 없을 경우 생성해주거나 반대로 삭제하는 옵션(대소문자 구별X) | `NONE` |

실행 결과
<img width="1127" alt="image" src="https://user-images.githubusercontent.com/110734817/235604724-c5d91a97-17e2-461c-b9c0-a2288ada85a4.png">
<img width="694" alt="image" src="https://github.com/uhanuu/ExcelManager/assets/110734817/3fabf574-7690-492e-8b3d-f38570730c9e">
+ 위에 Excel파일 사진과, application.properties 사진을 참고해서 보면 이해가 될 것이다.
추가로 select 가능하게 추가 했다.

#### 복수개 field를 받고 싶은 경우 RequestParam으로 List로 받게된다.
<img width="848" alt="image" src="https://github.com/uhanuu/ExcelManager/assets/110734817/db494746-9ff4-43f4-a244-a2dc1990133d">

#### 사용하더라도 pk랑 사용하지 단일 field 검색은 사용하지 않을거 같아서 따로 뺐다.
<img width="720" alt="image" src="https://github.com/uhanuu/ExcelManager/assets/110734817/3cf301b4-82e4-4e31-b84e-549c2f8cd9ac">



## 주의점
![image](https://user-images.githubusercontent.com/110734817/235668352-d7001f39-96c2-4746-8005-76f02f1f1f74.png)

application.properties에서 mode를 create로 설정시 database-table-name 정보를 확인 후 해당 테이블이 있으면 DROP TABLE 이후 CREATE TABLE 쿼리가 실행된다.

## yml에서 한글 데이터가 깨진다면 밑에 사진을 참고하자
<img src="https://user-images.githubusercontent.com/110734817/235599126-f77664ea-9df0-4dfc-b4fc-09aea5a7aa54.png" width="700" height="500"/>
우측하단에 Transparent native-to-ascii  conversion 클릭해주자

## 개선 요구사항
MySQL을 기준으로 만들었는데 쿼리가 별로 없긴한데 다양한 DBMS에서 사용가능하게 추가해주기
