package io.excelmanager.v2.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExcelFileData is a Querydsl query type for ExcelFileData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExcelFileData extends EntityPathBase<ExcelFileData> {

    private static final long serialVersionUID = -1312930187L;

    public static final QExcelFileData excelFileData = new QExcelFileData("excelFileData");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tableName = createString("tableName");

    public QExcelFileData(String variable) {
        super(ExcelFileData.class, forVariable(variable));
    }

    public QExcelFileData(Path<? extends ExcelFileData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExcelFileData(PathMetadata metadata) {
        super(ExcelFileData.class, metadata);
    }

}

