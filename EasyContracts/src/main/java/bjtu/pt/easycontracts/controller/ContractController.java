package bjtu.pt.easycontracts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <Description> ContractController
 *
 * @author 26802
 * @version 1.0
 * @ClassName ContractController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */
@RequestMapping("/contract")
@Controller
public class ContractController {

    /*
    * 返回满足条件的合同list，以json的格式
    * */
    @GetMapping("/{title}/{id}/{status}")
    @ResponseBody
    public String listContracts(@PathVariable(value = "title",required = false)String title,
                                @PathVariable(value = "id",required = false)String id,
                                @PathVariable(value = "status",required = false)String status){

        return null;
    }


}
