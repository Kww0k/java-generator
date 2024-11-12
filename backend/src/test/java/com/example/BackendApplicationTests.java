package com.example;

import com.example.service.GenerateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.constants.OrmConstants.MYBATIS_PLUS;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    GenerateService generateService;

    @Test
    void contextLoads() {
        String sql = """
                create table event_test_table
                (
                    `id` int(11) NOT NULL AUTO_INCREMENT,
                    title       varchar(255)              null comment '标题',
                    preview     varchar(255)              null comment '封面',
                    start_time  datetime                  null comment '开始时间',
                    end_time    datetime                  null comment '结束时间',
                    location    varchar(255)              null comment '举办地',
                    view        int          default 0    null comment '浏览量',
                    content     mediumtext                null comment '内容',
                    examine     varchar(255) default 'no' not null,
                    create_by   int                       null,
                    create_time datetime                  null,
                    update_by   int                       null,
                    update_time datetime                  null,
                    del_flag    int          default 0    null,
                    PRIMARY KEY (`id`) USING BTREE
                );
                """;
        generateService.generate(sql, MYBATIS_PLUS);
    }

}
