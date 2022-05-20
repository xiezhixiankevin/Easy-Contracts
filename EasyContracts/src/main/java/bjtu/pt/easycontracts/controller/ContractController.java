package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.Contract;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

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
    @GetMapping("/list/{title}/{id}/{status}")
    @ResponseBody
    public String listContracts(@PathVariable(value = "title",required = false)String title,
                                @PathVariable(value = "id",required = false)String id,
                                @PathVariable(value = "status",required = false)String status){

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



}
