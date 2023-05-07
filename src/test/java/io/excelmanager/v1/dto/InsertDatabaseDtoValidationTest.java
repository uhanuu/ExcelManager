package io.excelmanager.v1.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class InsertDatabaseDtoValidationTest {

    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        InsertDatabaseDto insertDatabaseDto = new InsertDatabaseDto(
                " ","A,AZ","sstring","1test"
        );

        Set<ConstraintViolation<InsertDatabaseDto>> violations = validator.validate(insertDatabaseDto);
        for (ConstraintViolation<InsertDatabaseDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
    }

}