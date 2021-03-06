package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.*;
import bjtu.pt.easycontracts.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    public String toIndex(HttpSession session, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
                if (cookie.getName().equals("id")) {
                    System.out.println("SUCCESS READ SAVED INFO");
                    String userId = cookie.getValue();
                    User user = userService.getUserById(Integer.parseInt(userId));
                    List<Rights> rights = rightsService.listRights(user.getUserid());
                    user.setUserRights(rights);
                    session.setAttribute("nowUser", user);
                    return "me";
                }
            }
        }
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
    @GetMapping("/toModifyUser/{userId}")
    public String toModifyUser(@PathVariable("userId")Integer userId, Model model){
        User selectedUser = userService.getUserById(userId);
        model.addAttribute("selectedUser", selectedUser);
        return "user/modify_users";
    }

    //跳转到selectContract页面
    @GetMapping("/toSelectContract")
    public String toCounterSign(Model model){
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        return "contract/selectContract";
    }

    //跳转到contractInfo页面
    @GetMapping("/toShowContract/{id}")
    public String toShowContract(@PathVariable("id")Integer id,Model model){
        Contract contract = contractService.getContractById(id);

        //目前只能上传一个文件，后续考虑多个文件
        List<ContractAttachment> fileList = contractFileService.getContractFileListOfContract(id);

        if (!fileList.isEmpty()){
            model.addAttribute("file",fileList.get(0));
        }else {
            ContractAttachment contractAttachment = new ContractAttachment();
            model.addAttribute("file",contractAttachment);
        }

        model.addAttribute("contractObject",contract);
        return "contract/contractInfo";
    }

    //跳转到selectAllContract页面
    @GetMapping("/toSelectAllContract")
    public String toSelectAllContract(Model model){
        int d=0,cs=0,f=0,e=0,s=0,w=0;
        List<Contract> contractList = contractService.getAllContract();
        for(int i=0;i<contractList.size();i++){
            switch (contractList.get(i).getType()){
                case 1:d++;break;
                case 2:cs++;break;
                case 3:f++;break;
                case 4:e++;break;
                case 5:s++;break;
                case 6:w++;break;
            }
        }
        model.addAttribute("WAITING",d);
        model.addAttribute("COUNTERSIGNING",cs);
        model.addAttribute("FINALIZING",f);
        model.addAttribute("EXAMMING",e);
        model.addAttribute("SIGNING",s);
        model.addAttribute("FINISH",w);
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        return "contract/selectAllContract";
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
            model.addAttribute("examOpinion",contractProcessService.getExamOpinion(contractId));//审批意见
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

        //目前只能上传一个文件，后续考虑多个文件
        List<ContractAttachment> fileList = contractFileService.getContractFileListOfContract(contractId);

        if (!fileList.isEmpty()){
            model.addAttribute("file",fileList.get(0));
        }else {
            ContractAttachment contractAttachment = new ContractAttachment();
            model.addAttribute("file",contractAttachment);
        }

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

        //目前只能上传一个文件，后续考虑多个文件
        List<ContractAttachment> fileList = contractFileService.getContractFileListOfContract(contractId);

        if (!fileList.isEmpty()){
            model.addAttribute("file",fileList.get(0));
        }else {
            ContractAttachment contractAttachment = new ContractAttachment();
            model.addAttribute("file",contractAttachment);
        }

        model.addAttribute("contractObject", toSignContract);
        return "sign";
    }


    @GetMapping("/toAddCustomer")
    public String toAddCustomer() {return "customer/create_customers";}

    @GetMapping("/toModifyCustomer/{customerId}")
    public String toModifyCustomer(@PathVariable("customerId")Integer customerId,
                                   Model model) {
        Customer customer = customerService.selectById(customerId);
        model.addAttribute("customerObject", customer);
        return "customer/modify_customers";
    }

    @GetMapping("/toLog")
    public String toLog(Model model) {

        List<User> userList = userService.listUser();
        List<Contract> contractList = contractService.listContractSelective();

        model.addAttribute("userList",userList);
        model.addAttribute("contractList",contractList);
        return "log";
    }

}
