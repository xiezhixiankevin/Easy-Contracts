package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.ContractFileService;
import bjtu.pt.easycontracts.service.RightsService;
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

import java.util.ArrayList;
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

    @Autowired
    private RightsService rightsService;
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
        List<Boolean> rights = new ArrayList<>();
        if (rightsService.ifUserHasRights(userID,Global.PERMISSION_ASSIGN_COUNTERSIGN)!=null){
            rights.add(true);
        }else {
            rights.add(false);
        }
        if (rightsService.ifUserHasRights(userID,Global.PERMISSION_ASSIGN_APPROVE)!=null){
            rights.add(true);
        }else {
            rights.add(false);
        }
        if (rightsService.ifUserHasRights(userID,Global.PERMISSION_ASSIGN_SIGN)!=null){
            rights.add(true);
        }else {
            rights.add(false);
        }
        Map<Integer, List<Contract>> integerListMap = contractProcessService.listConTractUserNeedDeal(userID,rights);
        ReturnObject<Map<Integer, List<Contract>>> returnObject = new ReturnObject<>( SUCCESS,integerListMap);
        return returnObject;
    }

    @GetMapping("/select_contractTodeal/{pn}")
    @ResponseBody
    public ReturnObject<PageInfo> select_contractTodeal(Contract contract, Integer userID,@PathVariable("pn")Integer pn){
        List<Contract> contractList = contractService.listContractUserNeedDeal(contract, userID, pn);
        PageInfo pageInfo = new PageInfo(contractList,5);
        ReturnObject<PageInfo> result = new ReturnObject<>(Global.SUCCESS,pageInfo);
        return result;
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
     *定稿合同按钮映射到此方法
     * */
    @PostMapping("/finalize")
    public String finalizeContract(MultipartFile upload, Contract contract, HttpSession session) throws FileNotFoundException  {
        //将前端传来的时间字符串转成date并填入
        contract.setBegintime(TimeUtil.strToDate(contract.getBeginTimeStr()));
        contract.setEndtime(TimeUtil.strToDate(contract.getEndTimeStr()));
        //其他设置
        contract.setType(Global.EXAMMING);
        //1.修改合同基本信息
        contractService.updateContract(contract.getContractid(),contract);
        //2.更新起草人对应的process状态
        User user = (User)session.getAttribute("nowUser");
        contractProcessService.finalize(contract.getContractid(),user.getUserid()); //定稿
        //2.修改合同附件信息
        if (upload.getOriginalFilename() == null){
            return "info/success_finalize";
        }
        if (upload.getOriginalFilename().length()!=0){
            //1.判断是否本来有附件
            List<ContractAttachment> listOfContract = contractFileService.getContractFileListOfContract(contract.getContractid());
            if (!listOfContract.isEmpty()){
                //先删除后上传
                for (ContractAttachment contractAttachment : listOfContract){
                    contractFileService.deleteContractFile(contractAttachment);
                }
            }
            //2.上传新附件
            ContractAttachment contractAttachment = new ContractAttachment();
            contractAttachment.setContractid(contract.getContractid());
            contractAttachment.setUploadtime(new Date());
            contractAttachment.setType("普通文件");
            contractFileService.addContractFile(contractAttachment,upload);
        }
        return "info/success_finalize";
    }

    /*
     *会签合同按钮映射到此方法
     * */
    @PostMapping("/countersign")
    @ResponseBody
    public String countersignContract(Integer contractId, Integer userId, String opinion){
        // TODO: 在下面这个方法中注释了群发邮件的方法，因为会报错
        contractService.countersignContract(contractId,userId,opinion);
        return INFO_SUCCESS;
    }



    /*
     *审批合同按钮映射到此方法
     * */
    @PostMapping("/examine") // ifPass
    @ResponseBody
    public String examineContract(Integer contractId, Integer userId, String opinion, Integer ifPass){
        boolean ifPassBoolean = false;
        if (ifPass == 1)
        {
            ifPassBoolean = true;
        }

        int code = contractService.examineContract(contractId, userId, opinion, ifPassBoolean);
        if (code == SUCCESS)
            return "SUCCESS";
        else
            return "审批失败，后端错误";
    }

    /*
     *签订合同按钮映射到此方法
     * */
    @PostMapping("/sign")
    @ResponseBody
    public String signContract(@RequestParam("contractId")Integer contractId,
                                      @RequestParam("userId")Integer userId,
                                      @RequestParam("opinion")String opinion){
        if(contractService.signContract(contractId,userId,opinion)>0){
            return INFO_SUCCESS;
        }
        return INFO_ERROR;
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
