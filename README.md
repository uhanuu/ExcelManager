# 엑셀 데이터 추출하기

공공데이터포털에서 사용하면 유용할 Excel파일들이 있어, 데이터를 추출해서 JSON으로 넘겨주거나 DB에 저장하면 유용할거 같아서 개발하기 시작했다. 

<img width="1438" alt="image" src="https://user-images.githubusercontent.com/110734817/235599797-6cfd6dfc-68b5-46f6-99e2-b87dc7c7967f.png">
> Reference: https://www.data.go.kr/index.do

## Spring @ConfigurationProperties을 이용해서 apllication.properties 설정 정보 가져오기
<img width="808" alt="image" src="https://user-images.githubusercontent.com/110734817/235601044-3278e134-4267-4f73-863e-3f3d42c445d9.png">

| 값 | 의미 | 기본값 |
|:---:|:---:|:---:|
| `url` | DB connection을 위한 옵션 | `NOT NULL` |
| `username` | DB connection을 위한 옵션 | `NOT NULL` |
| `password` | DB connection을 위한 옵션 | `NOT NULL` |
| `driverClassName` | DB connection을 위한 옵션 | `NOT NULL` |

| 값 | 의미 | 기본값 |
|:---:|:---:|:---:|
| `attributeKey` | DB테이블의 column 이름을 순서대로 작성 | `NOT NULL` |
| `attributeValue` | Excel파일에서 읽어올 column 이름을 테이블 명과 일치하게 작성 | `NOT NULL` |
| `attributeType` | DB colums에 데이터 타입으로 JPA에 명시할 때 사용하는 데이터 타입으로 작성(대소문자 구별X) | `NOT NULL` |
| `path` | Excel파일 경로 | `NOT NULL` |
| `fileName` | Excel파일 이름 | `NOT NULL` |
| `databaseTableName` | DB connection을 위한 옵션 | `NOT NULL` |

실행 결과
<img width="1127" alt="image" src="https://user-images.githubusercontent.com/110734817/235604724-c5d91a97-17e2-461c-b9c0-a2288ada85a4.png">
<img width="792" alt="image" src="https://user-images.githubusercontent.com/110734817/235604929-e81687fa-6ed9-4d4f-bbd6-a3fb190f5e9c.png">
+ 위에 Excel파일 사진과, application.properties 사진을 참고해서 보면 이해가 될 것이다.

## 주의점
![image](https://user-images.githubusercontent.com/110734817/235668352-d7001f39-96c2-4746-8005-76f02f1f1f74.png)

application.properties 안에 database-table-name 정보를 확인 후 해당 테이블이 있으면 DROP TABLE 이후 CREATE TABLE을 하기로 지금은 설정되어 있다.

## yml에서 한글 데이터가 깨진다면 밑에 사진을 참고하자
<img src="https://user-images.githubusercontent.com/110734817/235599126-f77664ea-9df0-4dfc-b4fc-09aea5a7aa54.png" width="700" height="500"/>
우측하단에 Transparent native-to-ascii  conversion 클릭해주자

## 개선 요구사항
1. 예외처리
2. 내 코드는 "대학원", "대학원대학" 조건은 제외되도록 코드를 작성했는데 좀더 다른 조건을 걸 수 있도록 유연하게 설계
3. (4)번 조건으로 인해 데이터가 비어있는 부분이 있다. -> DB에 insert 할 때는 체크했지만 JSON 처리할 때 empty check하기
4. POST요청으로 -> JSON 내려줄 수 있게 처리해주기
5. Java bean validation 으로 제약조건 설정해주기 -> NOT NULL 사용안할 때 있을 수 있으니까 필수값 수정하기
(Javax validation에서 조금 기능 더있는 하이버네이트 validation 사용하기 (duration으로 연결시간 설정할 때)
6. spring에 jpa.hibernate.ddl-auto 옵션 처럼 원하는 설정 정보에 따라서 table drop, update 요구사항 처리하기
