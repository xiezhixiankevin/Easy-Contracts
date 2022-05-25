package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.utils.Global;
import bjtu.pt.easycontracts.utils.ReturnObject;
import bjtu.pt.easycontracts.utils.TimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import bjtu.pt.easycontracts.service.ContractProcessService;
import bjtu.pt.easycontracts.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import static bjtu.pt.easycontracts.utils.Global.*;

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
    @Autowired
    ContractProcessService contractProcessService;
    /*
    * 返回满足条件的合同list，以json的格式
    * */
    @GetMapping("/list")
    @ResponseBody
    public ReturnObject<List<Contract>> listContracts(@RequestParam(value = "title",required = false)String title,
                                                                   @RequestParam(value = "id",required = false)String id,
                                                                   @RequestParam(value = "status",required = false)String status){

        return null;
    }

    @GetMapping("/todeal")
    @ResponseBody
    public ReturnObject<Map<Integer, List<Contract>>> listConTractToDeal(Integer userID){
        Map<Integer, List<Contract>> integerListMap = contractProcessService.listConTractUserNeedDeal(userID);
        ReturnObject<Map<Integer, List<Contract>>> returnObject = new ReturnObject<>( SUCCESS,integerListMap);
        return returnObject;
    }
    /*
    *起草合同按钮映射到此方法
    * */
    @PostMapping("/draft")
    public @ResponseBody String draftContract(MultipartFile upload, Contract contract, HttpSession session) throws FileNotFoundException {

        User user = (User)session.getAttribute("nowUser");
        //设置起草人id
        contract.setDrafterid(user.getUserid());
        //将前端传来的时间字符串转成date并填入
        contract.setBegintime(TimeUtil.strToDate(contract.getBeginTimeStr()));
        contract.setEndtime(TimeUtil.strToDate(contract.getEndTimeStr()));


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
        if(i==SUCCESS){
            return "Success";
        }
        else{
            return "Error";
        }
    }


}
