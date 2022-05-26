package bjtu.pt.easycontracts;



import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static bjtu.pt.easycontracts.utils.Global.WAITING;


@SpringBootTest
class EasyContractsApplicationTests {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractProcessMapper contractProcessMapper;
    @Test
    void contextLoads() {

    }
}
