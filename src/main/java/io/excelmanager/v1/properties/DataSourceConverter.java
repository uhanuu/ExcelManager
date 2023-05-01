package io.excelmanager.v1.properties;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;



@Slf4j
public class DataSourceConverter {

    private static Map<String,Object> mapKeyToValue = new HashMap<>();
    private static int[] intArrayValues;

    public DataSourceConverter(List<String> attributeKey, List<Character> attributeValue) {
        converter(attributeKey,attributeValue);
    }
    private void converter(List<String> attributeKey, List<Character> attributeValue){
        if (attributeKey.size() != attributeValue.size())
            throw new NoSuchElementException("attribute key-value의 개수를 맞춰주세요");

        intArrayValues = new int[attributeKey.size()];
        for (int i = 0; i < attributeKey.size(); i++){
            mapKeyToValue.put(attributeKey.get(i), i);
            intArrayValues[i] = attributeValue.get(i) - 65;
        }
        log.info("map={}",mapKeyToValue);
        log.info("int array={}",intArrayValues);
    }
    public static Map<String, Object> getMapKeyToValue() {
        return mapKeyToValue;
    }
    public static int[] getIntArrayValues() {return intArrayValues;}
}
