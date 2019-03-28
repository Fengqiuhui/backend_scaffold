package com.maoniunet;

import com.maoniunet.entity.UserEntity;
import com.maoniunet.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
public class MaoniuApplication {

    @Resource
    private UserService userService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MaoniuApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {

        jdbcTemplate.execute("create table user (" +
                "id int not null primary key, " +
                "username varchar(50) not null default ''," +
                "created_date datetime," +
                "last_modified_date datetime," +
                "is_del bit(1) not null default 0)");

        for (int i = 1; i <= 100; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId((long) i);
            userEntity.setUsername(RandomString.make());
            userService.insert(userEntity);
        }
    }

}
