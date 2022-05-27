package bjtu.pt.easycontracts;





import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static bjtu.pt.easycontracts.utils.Global.WAITING;


@SpringBootTest
class EasyContractsApplicationTests {


    @Test
    void contextLoads() {
        String regex = "[1-9]{8,12}@[a-zA-Z]{2,4}.com";
        String sss = "123456789145@BJtU.com";
        System.out.println("---------------------");
        System.out.println(sss.matches(regex));
        System.out.println("---------------------");
    }
}
