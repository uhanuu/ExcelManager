package io.excelmanager.v2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ExcelFileData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableName;
}
