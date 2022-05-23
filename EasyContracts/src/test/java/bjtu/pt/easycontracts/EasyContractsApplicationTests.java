package bjtu.pt.easycontracts;

import bjtu.pt.easycontracts.mapper.CustomerMapper;
import bjtu.pt.easycontracts.mapper.UserMapper;
import bjtu.pt.easycontracts.pojo.table.Customer;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.pojo.table.UserExample;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EasyContractsApplicationTests {

    @Autowired
    private CustomerMapper customerMapper;
    @Test
    void contextLoads() {

        customerMapper.deleteByPrimaryKey(1);
    }

}
