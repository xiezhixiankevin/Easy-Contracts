package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.ContractFileService;
import bjtu.pt.easycontracts.utils.Global;
import bjtu.pt.easycontracts.utils.ReturnObject;
import bjtu.pt.easycontracts.utils.TimeUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import bjtu.pt.easycontracts.service.ContractProcessService;
import bjtu.pt.easycontracts.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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
    private ContractService contractService;

    @Autowired
    private ContractProcessService contractProcessService;

    @Autowired
    private ContractFileService contractFileService;
    /*
    * 返回满足条件的合同list，
    * */
    @GetMapping("/list/{pn}")
    @ResponseBody
    public ReturnObject<PageInfo> listContracts(Contract contract, @PathVariable("pn")Integer pn){

        return null;
    }

    /*
     * 返回满足条件的合同list，以json的格式
     * */
    @GetMapping("/list_needAssign/{pn}")
    @ResponseBody
    public ReturnObject<PageInfo> listContractsNeedAssign(Contract contract, @PathVariable("pn")Integer pn){
        List<Contract> contractList = contractService.getNeedAllocationContracts(contract, pn);
        PageInfo pageInfo = new PageInfo(contractList,5);
        ReturnObject<PageInfo> result = new ReturnObject<>(Global.SUCCESS,pageInfo);
        return result;
    }

    //tome界面展示当前任务
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
    public String draftContract(MultipartFile upload, Contract contract, HttpSession session) throws FileNotFoundException {

        User user = (User)session.getAttribute("nowUser");
        //设置起草人id
        contract.setDrafterid(user.getUserid());
        //将前端传来的时间字符串转成date并填入
        contract.setBegintime(TimeUtil.strToDate(contract.getBeginTimeStr()));
        contract.setEndtime(TimeUtil.strToDate(contract.getEndTimeStr()));
        //其他设置
        contract.setType(Global.WAITING);
        //1.先保存合同
        int id = contractService.addContract(contract);
        //2.保存附件
        ContractAttachment contractAttachment = new ContractAttachment();
        contractAttachment.setContractid(id);
        contractAttachment.setUploadtime(new Date());
        contractAttachment.setType("普通文件");
        contractFileService.addContractFile(contractAttachment,upload);
        //3.设置定稿人为起草人
        ContractProcess contractProcess = new ContractProcess();
        contractProcess.setContractid(id);
        contractProcess.setTime(new Date());
        contractProcess.setType(FINALIZE);
        contractProcess.setState(NOT_COME);
        contractProcess.setUserid(user.getUserid());
        contractProcessService.insertProcess(contractProcess);

        return "info/success";
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

    @GetMapping("/distribute")
    @ResponseBody
    public ReturnObject<List<Contract>> listConTractToDistribute(){
        List<Contract> contractList = contractService.getNeedAllocationContracts();
        ReturnObject<List<Contract>> returnObject = new ReturnObject<>( SUCCESS,contractList);
        return returnObject;
    }

    /*
    * 为合同分配人员
    * */
    @PostMapping("/assignUser")
    @ResponseBody
    public String assignUser(@RequestParam("contractId")Integer contractId,
                             @RequestBody Map<Integer,List<Integer>> userMap){

        return String.valueOf(contractProcessService.assignUsers(contractId,userMap));
    }

}
