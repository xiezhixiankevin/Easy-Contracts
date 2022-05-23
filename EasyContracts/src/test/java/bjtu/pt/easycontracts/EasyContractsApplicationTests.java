package bjtu.pt.easycontracts;

import bjtu.pt.easycontracts.mapper.RoleRightMapper;
import bjtu.pt.easycontracts.pojo.table.Rights;
import bjtu.pt.easycontracts.pojo.table.RoleRightExample;
import bjtu.pt.easycontracts.service.RightsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EasyContractsApplicationTests {
    @Autowired
    private RightsService rightsService;
    @Autowired
    private RoleRightMapper roleRightMapper;
    @Test
    void contextLoads() {
        List<Rights> rightsList=new ArrayList<>();
        Rights rights=new Rights();
        rights.setRightid(5);
        rightsList.add(rights);

        RoleRightExample roleRightExample = new RoleRightExample();
        roleRightExample.createCriteria().andUseridEqualTo(1);//类似于where后面的内容
        roleRightMapper.deleteByExample(roleRightExample);


        //rightsService.allocationRights(1,rightsList);
    }

}
