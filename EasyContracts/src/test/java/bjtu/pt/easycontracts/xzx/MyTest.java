package bjtu.pt.easycontracts.xzx;

import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <Description> MyTest
 *
 * @author 26802
 * @version 1.0
 * @ClassName MyTest
 * @taskId
 * @see bjtu.pt.easycontracts.xzx
 */

public class MyTest {

    @Autowired
    ContractProcessMapper contractProcessMapper;

    @Test
    public void test(){
        if (contractProcessMapper == null) {
            System.out.println("NULL!-------------------------------------------------");
        }
    }

}
