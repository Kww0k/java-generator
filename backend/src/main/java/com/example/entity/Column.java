package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Column {
    /** 列名称 */
    private String name;

    /** 列描述 */
    private String comment;

    /** JAVA类型 */
    private String javaType;

}