package bjtu.pt.easycontracts;




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
    UserService userService;

    @Test
    void contextLoads() {

        Map<Integer, List<User>> temp = new HashMap<>();
        temp = userService.getUserListByRightsForAssignContract();
        System.out.println("_________TEST____________________________");
        System.out.println(temp.get(PERMISSION_SIGN_CONTRACT).size());
        System.out.println("_________TEST____________________________");
       
    }
}
