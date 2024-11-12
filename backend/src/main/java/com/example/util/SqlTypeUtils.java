package com.example.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SqlTypeUtils {

    private static final Map<String, String> sqlToJavaTypeMap = new HashMap<>();

    static {
        sqlToJavaTypeMap.put("int", "Integer");
        sqlToJavaTypeMap.put("tinyint", "Byte");
        sqlToJavaTypeMap.put("smallint", "Short");
        sqlToJavaTypeMap.put("mediumint", "Integer");
        sqlToJavaTypeMap.put("bigint", "Long");
        sqlToJavaTypeMap.put("float", "Float");
        sqlToJavaTypeMap.put("double", "Double");
        sqlToJavaTypeMap.put("decimal", "BigDecimal");
        sqlToJavaTypeMap.put("varchar", "String");
        sqlToJavaTypeMap.put("char", "String");
        sqlToJavaTypeMap.put("text", "String");
        sqlToJavaTypeMap.put("mediumtext", "String");
        sqlToJavaTypeMap.put("longtext", "String");
        sqlToJavaTypeMap.put("date", "Date");
        sqlToJavaTypeMap.put("datetime", "Date");
        sqlToJavaTypeMap.put("timestamp", "Date");
        sqlToJavaTypeMap.put("time", "Time");
        sqlToJavaTypeMap.put("boolean", "Boolean");
        sqlToJavaTypeMap.put("bit", "Boolean");
        // 可以根据需要添加更多类型
    }

    public String mapSqlTypeToJavaType(String sqlType) {
        // 使用正则表达式去掉括号中的内容
        Pattern pattern = Pattern.compile("([a-zA-Z]+)(\\(.*?\\))?");
        Matcher matcher = pattern.matcher(sqlType);
        if (matcher.find()) {
            String typeWithoutLength = matcher.group(1);
            return sqlToJavaTypeMap.getOrDefault(typeWithoutLength.toLowerCase(), "Object");
        }
        return "Object"; // 默认返回 Object
    }
}
