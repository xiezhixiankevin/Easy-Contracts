package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractAttachment;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.*;
import bjtu.pt.easycontracts.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <Description> SkipController
 *  负责单纯的页面跳转，即无需处理数据的直接跳转，需要处理数据的跳转不在此类中声明
 * @author 26802
 * @version 1.0
 * @ClassName SkipController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */

@Controller
public class SkipController {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private RightsService rightsService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ContractFileService contractFileService;
    @Autowired
    private ContractProcessService contractProcessService;

    //跳转到注册页面，
    @GetMapping("/toRegister")
    public String toRegister(){
        return "loginup";
    }

    //跳转到登录页面
    @GetMapping("/toLogin")
    public String toLogin(){
        return "loginin";
    }

    //跳转到首页
    @GetMapping("/")
    public String toIndex(){
        return "index";
    }

    //跳转到me页面
    @GetMapping("/toMe")
    public String toMe(){
        return "me";
    }

    //跳转到select用户页面
    @GetMapping("/toSelectUser")
    public String toSelectUser(){
        return "user/select_users";
    }

    //跳转到create用户页面
    @GetMapping("/toCreateUser")
    public String toCreateUser(){
        return "user/create_users";
    }

    //跳转到modify用户页面
    @GetMapping("/toModifyUser")
    public String toModifyUser(){
        return "user/modify_users";
    }

    //跳转到selectContract页面
    @GetMapping("/toSelectContract")
    public String toCounterSign(Model model){
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        return "contract/selectContract";
    }

    //toAssignContract
    //跳转到给合同进行人员分配页面
    @GetMapping("/toAssignContract/{contractId}")
    public String toAssignContract(@PathVariable("contractId")Integer contractId,Model model){
        Contract contract = contractService.getContractOfNeedAssign(contractId);
        model.addAttribute("contract",contract);
        return "contract/assign_contract";
    }

    //跳转到查询需要分配合同页面
    @GetMapping("/toselectCNeedAssign")
    public String toselectCNeedAssign(Model model){
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        return "contract/select_contract_needAllocation";
    }

    //跳转到权限分配页面
    @GetMapping("/toRights/{username}")
    public String toRights(@PathVariable("username")String username, Model model,HttpSession session){
        User nowUser = (User)session.getAttribute("nowUser");
        if (!nowUser.ifHasRight(Global.PERMISSION_ASSIGN_PERMISSIONS)){
            return "error/noRight";
        }
        User userToAssign = userService.getUserByUserName(username);
        userToAssign.setUserRights(rightsService.listRights(userToAssign.getUserid()));
        model.addAttribute("userToAssign",userToAssign);
        return "permission/assign_permissions";
    }

    //跳转到起草合同页面如果
    @GetMapping("/toDraft")
    public String toDraft(HttpSession session,Model model){
        User nowUser = (User)session.getAttribute("nowUser");
        if (!nowUser.ifHasRight(Global.PERMISSION_DRAFT_CONTRACT)){
            return "error/noRight";
        }
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        return "contract/draft";
    }

    //跳转到合同会签页面
    @GetMapping("/toCounterSign/{contractId}")
    public String toCounterSign(@PathVariable("contractId")Integer contractId,
                                Model model){
        Contract toCountersignContract = contractService.getContractById(contractId);
        //目前只能上传一个文件，后续考虑多个文件
        List<ContractAttachment> fileList = contractFileService.getContractFileListOfContract(contractId);

        contractService.setContract(toCountersignContract);
        model.addAttribute("contractObject", toCountersignContract);
        if (!fileList.isEmpty()){
            model.addAttribute("file",fileList.get(0));
        }else {
            ContractAttachment contractAttachment = new ContractAttachment();
            model.addAttribute("file",contractAttachment);
        }
        return "contract/countersign";
    }

    //跳转到合同定稿页面
    @GetMapping("/toFinalize/{contractId}")
    public String toFinalize(@PathVariable("contractId")Integer contractId,
                                Model model){
        Contract toCountersignContract = contractService.getContractById(contractId);
        List<ContractAttachment> fileList = contractFileService.getContractFileListOfContract(contractId);
        contractService.setContract(toCountersignContract); //设置时间等信息

        if (!fileList.isEmpty()){
            model.addAttribute("file",fileList.get(0));
        }else {
            ContractAttachment contractAttachment = new ContractAttachment();
            model.addAttribute("file",contractAttachment);
        }

        model.addAttribute("contractObject", toCountersignContract);
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        if (toCountersignContract.getFailuretimes() >0){
            model.addAttribute("exam",true); //标识是会签定稿，还是审批定稿
            model.addAttribute("counterSignOpinion",contractProcessService.getExamOpinion(contractId));//审批意见
        }else {
            model.addAttribute("finalize",true); //标识是会签定稿，还是审批定稿
            model.addAttribute("counterSignOpinion",contractProcessService.getCounterSignOpinion(contractId));//会签意见
        }
        return "contract/finalize";
    }


    @GetMapping("/toExamine/{contractId}")
    public String toExamine(@PathVariable("contractId")Integer contractId, Model model) {
        Contract toCountersignContract = contractService.getContractById(contractId);
        contractService.setContract(toCountersignContract);
        model.addAttribute("contractObject", toCountersignContract);
        return "contract/examine";
    }

    //跳转到搜索客户页面
    @GetMapping("/toSelectCustomer")
    public String toSelectCustomer(){
        return "customer/select_customers";
    }

    @GetMapping("/toSign/{contractId}")
    public String toSign(@PathVariable("contractId")Integer contractId,
                         Model model){
        Contract toSignContract = contractService.getContractById(contractId);
        contractService.setContract(toSignContract);
        model.addAttribute("contractObject", toSignContract);
        return "sign";
    }


    @GetMapping("/toAddCustomer")
    public String toAddCustomer() {return "customer/create_customers";}
}
