package bjtu.pt.easycontracts;




import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.mapper.CustomerMapper;
import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.ContractProcessService;
import bjtu.pt.easycontracts.service.ContractService;
import bjtu.pt.easycontracts.service.EmailService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bjtu.pt.easycontracts.utils.Global.*;


@SpringBootTest
class EasyContractsApplicationTests {
    @Autowired
    ContractProcessService contractProcessService;

    @Autowired
    ContractService contractService;
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @Autowired
    private ContractProcessMapper contractProcessMapper;
    @Test
    void contextLoads() {
        System.out.println("_________TEST____________________________");


        System.out.println("_________TEST____________________________");
       
    }
}
