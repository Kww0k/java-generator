package com.example.util;

import com.example.constants.OrmConstants;
import com.example.entity.OrmType;
import com.example.entity.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class EntityGenerator {

    @Value("${generator.packageName}")
    private String packageName;

    public void generateEntity(TableInfo tableInfo, String type) {
        // 根据类型获取文件模板
        List<OrmType> list = getOrmList(type);

        // 依次生成
        for (OrmType ormType : list) {
            generateCode(tableInfo, ormType);
        }
    }

    private List<OrmType> getOrmList(String type) {
        List<OrmType> list = new ArrayList<>();
        // 默认添加 Controller
        list.add(new OrmType("vm/java/controller.java.vm", "backend/src/main/java/com/example/controller/", "Controller"));

        switch (type) {
            case OrmConstants.SPRING_DATA_JPA:
                list.add(new OrmType("vm/java/jpa/entity.java.vm", "backend/src/main/java/com/example/entity/", ""));
                list.add(new OrmType("vm/java/jpa/service.java.vm", "backend/src/main/java/com/example/service/", "Service"));
                list.add(new OrmType("vm/java/jpa/serviceImpl.java.vm", "backend/src/main/java/com/example/service/impl/", "ServiceImpl"));
                list.add(new OrmType("vm/java/jpa/repository.java.vm", "backend/src/main/java/com/example/repository/", "Repository"));
                break;

            case OrmConstants.MYBATIS:
                list.add(new OrmType("vm/java/mybatis/entity.java.vm", "backend/src/main/java/com/example/entity/", ""));
                list.add(new OrmType("vm/java/mybatis/service.java.vm", "backend/src/main/java/com/example/service/", "Service"));
                list.add(new OrmType("vm/java/mybatis/serviceImpl.java.vm", "backend/src/main/java/com/example/service/impl/", "ServiceImpl"));
                list.add(new OrmType("vm/java/mybatis/mapper.java.vm", "backend/src/main/java/com/example/mapper/", "Mapper"));
                break;

            case OrmConstants.MYBATIS_FLEX:
                list.add(new OrmType("vm/java/flex/entity.java.vm", "backend/src/main/java/com/example/entity/", ""));
                list.add(new OrmType("vm/java/flex/service.java.vm", "backend/src/main/java/com/example/service/", "Service"));
                list.add(new OrmType("vm/java/flex/serviceImpl.java.vm", "backend/src/main/java/com/example/service/impl/", "ServiceImpl"));
                list.add(new OrmType("vm/java/flex/mapper.java.vm", "backend/src/main/java/com/example/mapper/", "Mapper"));
                break;

            case OrmConstants.MYBATIS_PLUS:
                list.add(new OrmType("vm/java/plus/entity.java.vm", "backend/src/main/java/com/example/entity/", ""));
                list.add(new OrmType("vm/java/plus/service.java.vm", "backend/src/main/java/com/example/service/", "Service"));
                list.add(new OrmType("vm/java/plus/serviceImpl.java.vm", "backend/src/main/java/com/example/service/impl/", "ServiceImpl"));
                list.add(new OrmType("vm/java/plus/mapper.java.vm", "backend/src/main/java/com/example/mapper/", "Mapper"));
                break;

            default:
                throw new RuntimeException("类型不支持");
        }

        return list;
    }


    private void generateCode(TableInfo tableInfo, OrmType ormType) {
        // 创建Velocity引擎
        Properties properties = new Properties();
        properties.setProperty("resource.loaders", "class");
        properties.setProperty("resource.loader.class.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");


        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
        // 获取模板
        Template template = velocityEngine.getTemplate(ormType.getSource(), "UTF-8");

        // 创建上下文
        Context context = new org.apache.velocity.VelocityContext();
        context.put("tableInfo", tableInfo);
        context.put("packageName", packageName);

        // 生成代码
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        // 将生成的代码写入文件
        String fileName = tableInfo.getName() + ormType.getPackageName() + ".java"; // 文件名为类名
        String directoryPath = ormType.getTarget();
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // 创建多层目录
            if (!created) {
                log.error("目录创建失败: {}", directoryPath);
                return; // 如果目录创建失败，终止后续操作
            }
        }

        try (FileWriter fileWriter = new FileWriter(directoryPath + fileName)) {
            fileWriter.write(writer.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
