package io.excelmanager.v2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class InsertDatabaseDto {

    @NotBlank(message = "table에 저장할 column 이름을 순서대로 입력해주세요. 공백일 수 없습니다.")
    @NotBlank
    private String attributeKey;

    @Pattern(regexp = "^[A-Z](,[A-Z])*$", message = "대문자 A~Z까지만 작성 가능하며 ,을 기준으로 공백없이 작성해주세요")
    @NotEmpty(message = " Excel File에서 저장할 column을 입력해주세요.")
    private String attributeValue;

    @Pattern(regexp = "^(?i)(string|int|long)(,(string|int|long))*$", message = "String,int등 데이터 타입을 입력해주세요 ,을 기준으로 공백없어야 하며 대소문자는 구분하지 않습니다.")
    @NotEmpty(message = "table에 저장할 Type을 입력해주세요.")
    private String attributeType;

    @Pattern(regexp = "^[^0-9]*$", message = "숫자는 입력하실 수 없습니다.")
    @NotEmpty(message = "table 이름을 입력해주세요.")
    private String databaseTableName;

    public InsertDatabaseDto(String attributeKey, String attributeValue, String attributeType, String databaseTableName) {
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
        this.attributeType = attributeType;
        this.databaseTableName = databaseTableName;
    }
}
