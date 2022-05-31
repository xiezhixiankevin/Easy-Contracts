package bjtu.pt.easycontracts;




import bjtu.pt.easycontracts.mapper.CustomerMapper;
import bjtu.pt.easycontracts.pojo.table.Customer;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.utils.Global;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bjtu.pt.easycontracts.utils.Global.*;


@SpringBootTest
class EasyContractsApplicationTests {
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void contextLoads() {
        Customer customer = new Customer();
        System.out.println("_________TEST____________________________");
        int i = customerMapper.insert(customer);
        System.out.println(i+"_________TEST____________________________");
       
    }
}
