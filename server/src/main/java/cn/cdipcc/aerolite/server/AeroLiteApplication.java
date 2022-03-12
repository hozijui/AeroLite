package cn.cdipcc.aerolite.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.cdipcc.aerolite.server.dao")
public class AeroLiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AeroLiteApplication.class, args);
    }

}
