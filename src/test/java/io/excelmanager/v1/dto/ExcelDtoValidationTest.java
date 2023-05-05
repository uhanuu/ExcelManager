package io.excelmanager.v1.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExcelDtoValidationTest {

    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ExcelDto excelDto = new ExcelDto(
                " ","A,AZ","sstring","1test"
        );

        Set<ConstraintViolation<ExcelDto>> violations = validator.validate(excelDto);
        for (ConstraintViolation<ExcelDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
    }

}