package com.example.service.impl;

import com.example.constants.OrmConstants;
import com.example.entity.TableInfo;
import com.example.service.GenerateService;
import com.example.util.EntityGenerator;
import com.example.util.SqlParseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("generatorService")
@RequiredArgsConstructor
public class GenerateServiceImpl implements GenerateService {

    private final SqlParseUtils sqlParseUtils;

    private final EntityGenerator entityGenerator;

    @Override
    public void generate(String sql, String ormType) {
        if (!OrmConstants.MYBATIS_PLUS.equals(ormType))
            return; // 目前只有mybatis-plus的模板
        // 解析sql
        TableInfo tableInfo = sqlParseUtils.parseCreateTableSql(sql);

        // 生成文件
        entityGenerator.generateEntity(tableInfo, ormType);
    }
}
