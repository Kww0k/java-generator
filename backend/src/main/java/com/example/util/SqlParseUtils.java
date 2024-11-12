package com.example.util;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.example.entity.Column;
import com.example.entity.TableInfo;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SqlParseUtils {

    private final SqlTypeUtils sqlTypeUtils;

    private final TimeUtil timeUtil;

    @Value("${generator.author}")
    private String author;

    public TableInfo parseCreateTableSql(String sql) {
        // 解析 SQL 语句
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        // 只允许输入一句
        if (statementList.size() > 1)
            return null;
        for (SQLStatement statement : statementList) {
            if (statement instanceof MySqlCreateTableStatement createTableStatement) {
                // 获取表名
                String tableName = createTableStatement.getName().toString();

                List<Column> pkColumns = new ArrayList<>();
                List<Column> columns = new ArrayList<>();

                // 获取列定义
                createTableStatement.getColumnDefinitions().forEach(columnDefinition -> {
                    String columnName = columnDefinition.getName().toString().replaceAll("`", "");
                    String javaType = sqlTypeUtils.mapSqlTypeToJavaType(columnDefinition.getDataType().toString());
                    String comment = null;
                    if (columnDefinition.getComment() != null) {
                        comment = columnDefinition.getComment().toString();
                        // 去除''
                        if (comment.startsWith("'") && comment.endsWith("'")) {
                            comment = comment.substring(1, comment.length() - 1);
                        }
                    }

                    Column column = new Column(columnName, comment, javaType);
                    if (columnDefinition.isPrimaryKey())
                        pkColumns.add(column);
                    else
                        columns.add(column);
                });
                return new TableInfo(tableName,
                        convertToCamelCase(tableName),
                        pkColumns,
                        columns,
                        author,
                        timeUtil.getLocalTime());
            }
        }
        return null;
    }

    private String convertToCamelCase(String tableName) {
        String[] parts = tableName.split("_");
        StringBuilder camelCaseName = new StringBuilder();

        for (String part : parts) {
            // 每个部分的首字母大写，后续字母小写
            camelCaseName.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1).toLowerCase());
        }

        return camelCaseName.toString();
    }
}
