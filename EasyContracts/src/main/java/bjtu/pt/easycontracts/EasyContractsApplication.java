package bjtu.pt.easycontracts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"bjtu.pt.easycontracts.mapper"})
public class EasyContractsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyContractsApplication.class, args);
    }

}
