package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.service.ContractService;
import bjtu.pt.easycontracts.utils.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import bjtu.pt.easycontracts.service.ContractService;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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

    @Autowired
    ContractService contractService;

    /*
     * 返回满足条件的合同list，以json的格式
     * */
    @GetMapping("/list")
    @ResponseBody
    public ReturnObject<Map<Integer, List<Contract>>> listContracts(@RequestParam(value = "title",required = false)String title,
                                                                    @RequestParam(value = "id",required = false)String id,
                                                                    @RequestParam(value = "status",required = false)String status){

        return null;
    }

    /*
    *起草合同按钮映射到此方法
    * */
    @PostMapping("/draft")
    public @ResponseBody String draftContract(MultipartFile upload, Contract contract) throws FileNotFoundException {

        return null;
    }

    /*
     *会签合同按钮映射到此方法
     * */
    @PostMapping("/countersign")
    public String countersignContract(@RequestParam("contractId")Integer contractId,
                                                    @RequestParam("userId")Integer userId,
                                                    @RequestParam("opinion")String opinion){

        return null;
    }

    /*
     *定稿合同按钮映射到此方法
     * */
    @PostMapping("/finalize")
    public @ResponseBody String finalizeContract(MultipartFile upload, Contract contract) throws FileNotFoundException  {

        return null;
    }

    /*
     *审批合同按钮映射到此方法
     * */
    @PostMapping("/examine/{ifPass}")
    public String examineContract(@RequestParam("contractId")Integer contractId,
                                  @RequestParam("userId")Integer userId,
                                  @RequestParam("opinion")String opinion,
                                  @PathVariable("ifPass")Integer ifPass){

        return null;
    }

    /*
     *签订合同按钮映射到此方法
     * */
    @PostMapping("/sign")
    public String signContract(@RequestParam("contractId")Integer contractId,
                                      @RequestParam("userId")Integer userId,
                                      @RequestParam("opinion")String opinion){

        return null;
    }

    @PostMapping("/crate")
    public String crateContract(Contract contract){
        int i = contractService.addContract(contract);
        if(i>0){
            return "Success";
        }
        else{
            return "Error";
        }
    }


}
