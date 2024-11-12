package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TableInfo {

    /** 表名称 */
    private String tableName;

    /** 类名称 */
    private String name;

    /** 主键字段列表 */
    private List<Column> pkColumns;

    /** 其它字段列表 */
    private List<Column> columns;

    /** 作者 */
    private String author;

    /** 创建时间 */
    private String createTime;
}
