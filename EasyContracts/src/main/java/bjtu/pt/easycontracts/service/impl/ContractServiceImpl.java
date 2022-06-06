package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.mapper.CustomerMapper;
import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.EmailService;
import bjtu.pt.easycontracts.service.UserService;

import bjtu.pt.easycontracts.service.ContractService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.HashMap;
import java.util.List;
import static bjtu.pt.easycontracts.utils.Global.*;

import bjtu.pt.easycontracts.utils.Global;

@Service
public class ContractServiceImpl implements ContractService
{
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractProcessMapper contractProcessMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ContractProcessServiceImpl contractProcessService;

    @Override
    public List<Contract> listContractSelective(Contract contract,int pn)
    {
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        List<Contract> contractList;

        /* 添加查询条件 */
        if (contract.getCustomerid() != null)
            criteria.andCustomeridEqualTo(contract.getCustomerid());
        if (contract.getContractname() != null)
            criteria.andContractnameLike('%' + contract.getContractname() + '%');
        if (contract.getOnlyWhich() != 0)
            criteria.andTypeEqualTo(contract.getOnlyWhich());

        PageHelper.startPage(pn,5); //每页显示5个数据
        contractList = contractMapper.selectByExample(contractExample);

        for (Contract each : contractList) {
            setContract(each);
        }

        if (contract.isIfBeginFirst()){
            contractList.sort(new Comparator<Contract>() {
                @Override
                public int compare(Contract o1, Contract o2) {
                    return o1.getBegintime().compareTo(o2.getBegintime());
                }
            });
        }
        if (contract.isIfEndFirst()){
            contractList.sort(new Comparator<Contract>() {
                @Override
                public int compare(Contract o1, Contract o2) {
                    return o1.getEndtime().compareTo(o2.getEndtime());
                }
            });
        }

        return contractList;

    }

    @Override
    public Contract getContractById(int contractId) {
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        setContract(contract);
        return contract;
    }

    @Override
    public int addContract(Contract contract) {
        contractMapper.insert(contract);
        return contract.getContractid();
    }

    @Override
    public int deleteContract(int id)
    {
        int deleteLine = 0; // 记录删除的行数
        ContractExample contractExample = new ContractExample();
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        contractExample.createCriteria().andContractidEqualTo(id);
        contractProcessExample.createCriteria().andContractidEqualTo(id);
        deleteLine += contractMapper.deleteByExample(contractExample);
        deleteLine += contractProcessMapper.deleteByExample(contractProcessExample);


        return deleteLine;
    }

    @Override
    public int updateContract(int id, Contract newContract) {
        newContract.setContractid(id);
        contractMapper.updateByPrimaryKeySelective(newContract);
        return SUCCESS;
    }

    @Override
    public int countersignContract(int contractId, int userId, String opinion)
    {
        int updateLine = 0;
        int notFinishedCount = 0; // 判断还有多少会签人员没有完成会签操作

        ContractExample contractExample = new ContractExample();
        ContractProcessExample contractProcessExample = new ContractProcessExample();

        /* 添加contract表的检索条件 */
        contractExample.createCriteria().andContractidEqualTo(contractId);

        /* 添加contractprocess表的检索条件 */
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);

        /* 找到对应的记录 */
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        List<ContractProcess> contractProcessList = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample);

        /* 遍历修改contractProcess记录,正在会签状态的改为通过会签状态并将会签内容输入进去 */
        for (ContractProcess contractProcess : contractProcessList)
        {
            ContractProcessExample contractProcessExample1 = new ContractProcessExample();// 专门用来更新一条相应记录
            ContractProcessExample.Criteria criteria = contractProcessExample1.createCriteria();

            /* 更新检索条件 */
            criteria.andContractidEqualTo(contractProcess.getContractid());
            criteria.andUseridEqualTo(contractProcess.getUserid());
            criteria.andTypeEqualTo(contractProcess.getType());

            /* 如果是合同对应会签阶段的记录，如果是对应的会签人员则修改状态并写入内容，如果是别的会签人员则看是否已完成会签内容 */
            if (contractProcess.getType() == Global.COUNTERSIGN)
            {
                if (contractProcess.getUserid() != userId)
                {
                    if (contractProcess.getContent() == null)
                        notFinishedCount++;
                }
                else
                {
                    Date date = new Date();
                    contractProcess.setState(Global.PASS);
                    contractProcess.setContent(opinion);
                    contractProcess.setTime(date);
                }
            }

            /* 更新数据库对应记录内容 */
            updateLine += contractProcessMapper.updateByExampleWithBLOBs(contractProcess , contractProcessExample1);
        }

        /* 判断是否所有会签人员都已完成会签，如果都完成，则再次遍历，找到定稿阶段的记录，更改状态 */
        if (notFinishedCount == 0)
        {
            for (ContractProcess contractProcess : contractProcessList)
            {
                ContractProcessExample contractProcessExample1 = new ContractProcessExample();// 专门用来更新一条相应记录
                ContractProcessExample.Criteria criteria = contractProcessExample1.createCriteria();

                /* 更新检索条件 */
                criteria.andContractidEqualTo(contractProcess.getContractid());
                criteria.andUseridEqualTo(contractProcess.getUserid());
                criteria.andTypeEqualTo(contractProcess.getType());

                if (contractProcess.getType() == Global.FINALIZE)
                {
                    contractProcess.setState(Global.DOING);
                    updateLine += contractProcessMapper.updateByExampleWithBLOBs(contractProcess , contractProcessExample1);
                }
            }

            /* 修改contract记录状态为正在定稿状态 */
            contract.setType(3);
            updateLine += contractMapper.updateByPrimaryKey(contract);
        }

        /* 将该操作员已完成会签操作的信息发送给所有操作员 */
//        List<User> usersAboutContract = userService.getUsersAboutContract(contractId);
//        for (User user : usersAboutContract)
//        {
//            emailService.sendSimpleMail(user.getEmail() , "Contract Countersign" , "The operator with id " + userId + "has finished the countersign" +
//                    " on contract with id " + contractId + ", remember to check!");
//        }

        return updateLine;
    }

    @Override
    public int examineContract(int contractId, int userId, String opinion, boolean ifPass) {
        ContractProcess contractProcess = new ContractProcess();// 存储需要修改的contractProcess记录
        ContractProcessExample contractProcessExample=new ContractProcessExample();
        ContractProcessExample contractProcessExample1 = new ContractProcessExample();// 用于获取将要更新的contractProcess记录
        Contract contract = contractMapper.selectByPrimaryKey(contractId); // 对应的合同记录
        int failureTimes = contract.getFailuretimes(); // 记录在此次操作之前已经失败了几次
        String content; // 该内容是此次的审批内容格式化之后真正要存储在数据库中的内容
        java.util.Date date = new java.util.Date(); // 此次操作的时间

        /* 获取将要修改的contractProcess记录 */
        contractProcessExample1.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(EXAM).andUseridEqualTo(userId);
        List<ContractProcess> contractProcesses = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample1);
        if (contractProcesses.size() == 1) {
            contractProcess = contractProcesses.get(0);
        }
        else {
            return FAIL;
        }
        content = contractProcess.getContent();
        if (content == null)
            content = "";

        /* 如果这是第一次审批，就不必加上换行符，否则在内容中先加上换行符，再格式化内容 */
        if (failureTimes != 0)
        {
            content = content + '\n';
        }

        /* 格式化字符串内容 */
        content = content + date.toString();//先加入时间
        content = content + " (第" + Integer.toString(failureTimes + 1) + "次审批)"; // 加入审批次数
        if (ifPass)
        {
            content = content + "(通过): ";
        }
        else
        {
            content = content + "(否决): ";
        }
        content = content + opinion; // 最后加上内容

        //Date
        contractProcess.setTime(date);
        contractProcess.setType(EXAM);
        contractProcess.setContractid(contractId);
        contractProcess.setContent(content);
        contractProcess.setUserid(userId);
        if(ifPass==true){
            contractProcess.setState(PASS);
        }else{
            contractProcess.setState(VETO);
        }


        contractProcessService.updateProcess(userId,contractId,EXAM,contractProcess);

        //检查
        contractProcessExample.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(EXAM);
        List <ContractProcess> contractProcessList = contractProcessMapper.selectByExample(contractProcessExample);
        int flag1=0;//记录操作次数
        int flag2=0;//记录PASS次数
        for(int i=0;i<contractProcessList.size();i++){
            if(contractProcessList.get(i).getState()==PASS  ||contractProcessList.get(i).getState()== VETO)
            {
                flag1++;
                if(contractProcessList.get(i).getState()==PASS){
                    flag2++;
                }
            }
        }
        if(flag2==contractProcessList.size()){
            contract.setType(SIGNING);//进入下一阶段签订
            ContractExample contractExample=new ContractExample();
            contractExample.createCriteria().andContractidEqualTo(contractId);
            contractMapper.updateByExampleWithBLOBs(contract,contractExample);

            /* 将process表中签订部分state设为1 */
            ContractProcessExample contractProcessExample2 = new ContractProcessExample();
            contractProcessExample2.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(SIGN);
            List<ContractProcess> contractProcessList1 = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample2);
            for (ContractProcess process : contractProcessList1)
            {
                ContractProcessExample contractProcessExample3 = new ContractProcessExample();
                contractProcessExample3.createCriteria().andContractidEqualTo(process.getContractid()).andUseridEqualTo(process.getUserid()).andTypeEqualTo(process.getType());
                process.setState(DOING);
                contractProcessMapper.updateByExampleWithBLOBs(process , contractProcessExample3);
            }

            return SUCCESS;
        }else if(flag1==contractProcessList.size()){
            //将合同进程表里数据 status改成0
            ContractProcessExample contractProcessExample2=new ContractProcessExample();
            contractProcessExample2.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(EXAM);
            List <ContractProcess> contractProcessList2 = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample2);
            for(int k=0;k<contractProcessList2.size();k++){
                ContractProcess contractProcess1 = contractProcessList2.get(k);
                contractProcess1.setState(NOT_COME);
                contractProcessService.updateProcess(contractProcess1.getUserid(),contractProcess1.getContractid(),EXAM,contractProcess1);
            }
            contract.setType(FINALIZING);//重新回到定稿阶段
            contract.setFailuretimes(contract.getFailuretimes()+1);
            ContractExample contractExample=new ContractExample();
            contractExample.createCriteria().andContractidEqualTo(contractId);
            contractMapper.updateByExampleWithBLOBs(contract,contractExample);

            /* 将contractProcess表中的该合同的定稿阶段的状态设置为正在进行中1 */
            ContractProcessExample contractProcessExample3 = new ContractProcessExample();
            ContractProcessExample.Criteria contractProcessExample3Criteria = contractProcessExample3.createCriteria();
            contractProcessExample3Criteria.andContractidEqualTo(contractId).andTypeEqualTo(FINALIZE);
            List<ContractProcess> contractProcessList1 = contractProcessMapper.selectByExample(contractProcessExample3);
            for (ContractProcess process : contractProcessList1) {
                ContractProcessExample contractProcessExample4 = new ContractProcessExample();
                ContractProcessExample.Criteria contractProcessExample4Criteria = contractProcessExample4.createCriteria();
                contractProcessExample4Criteria.andContractidEqualTo(contractId).andUseridEqualTo(process.getUserid()).andTypeEqualTo(process.getType());
                process.setState(DOING);
                contractProcessMapper.updateByExampleWithBLOBs(process , contractProcessExample4);
            }
            return SUCCESS;
        }

        /* 将该操作员已完成审批操作的信息发送给所有操作员 */
        List<User> usersAboutContract = userService.getUsersAboutContract(contractId);
        for (User user : usersAboutContract)
        {
            emailService.sendSimpleMail(user.getEmail() , "Contract Examine" , "The operator with id " + userId + "has finished the examine" +
                    " on contract with id " + contractId + ", remember to check!");
        }

        return SUCCESS;
    }

    @Override
    public int signContract(int contractId, int userId, String opinion)
    {
        int updateLine = 0; // 记录更改的记录数，作为返回值
        ContractExample contractExample = new ContractExample(); // 查找contract表对应的合同记录
        ContractProcessExample contractProcessExample = new ContractProcessExample(); // 查找该合同的所有签订阶段的记录
        ContractProcessExample.Criteria criteria = contractProcessExample.createCriteria();
        boolean isAllFinished = true; // 用于判断是否所有的签订人员都已完成了签订

        /* 添加contract表的检索条件 */
        contractExample.createCriteria().andContractidEqualTo(contractId);

        /* 添加contractProcess表的检索条件 */
        criteria.andContractidEqualTo(contractId).andTypeEqualTo(SIGN);

        /* 找到对应的记录 */
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        List<ContractProcess> contractProcessList = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample);

        /* 遍历所有该合同的签订记录，判断是否所有的人员都已完成签订 */
        for (ContractProcess contractProcess : contractProcessList)
        {
            /* 如果是本次签订的人员本身，则更新记录 */
            if (contractProcess.getUserid() == userId)
            {
                ContractProcessExample contractProcessExampleThisUser = new ContractProcessExample();
                ContractProcess contractProcess1;
                Date date = new Date();
                contractProcessExampleThisUser.createCriteria().andContractidEqualTo(contractId).andUseridEqualTo(userId).andTypeEqualTo(SIGN);
                List<ContractProcess> contractProcessList1 = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExampleThisUser);
                if (contractProcessList1.size() == 1)
                {
                    contractProcess1 = contractProcessList1.get(0);
                    contractProcess1.setContent(opinion);
                    contractProcess1.setTime(date);
                    contractProcess1.setState(PASS);
                    contractProcessMapper.updateByExampleWithBLOBs(contractProcess1 , contractProcessExampleThisUser);
                    updateLine++;
                }
                else
                {
                    return  FAIL;
                }
            }
            else
            {
                if (contractProcess.getState() != PASS)
                {
                    isAllFinished = false;
                }
            }
        }

        /* 如果所有的签订人员已完成签订，则将contract对应记录的状态更改 */
        if (isAllFinished)
        {
            contract.setType(FINISH);
            contractMapper.updateByPrimaryKey(contract);
        }

        /* 将该操作员已完成签订操作的信息发送给所有操作员 */
        List<User> usersAboutContract = userService.getUsersAboutContract(contractId);
        for (User user : usersAboutContract)
        {
            emailService.sendSimpleMail(user.getEmail() , "Contract Sign" , "The operator with id " + userId + "has finished the sign" +
                    " on contract with id " + contractId + ", remember to check!");
        }

        return updateLine;
    }

    @Override
    public Map<Integer, Boolean> getNeedAllocationOfContract(int contractId) {
        ContractProcessExample contractProcessExample=new ContractProcessExample();
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);
        List <ContractProcess> contractProcessList = contractProcessMapper.selectByExample(contractProcessExample);
        int flag1=0;int flag2=0;int flag3=0;int flag4=0;//对应四种类型
        for(int i=0;i<contractProcessList.size();i++){
            if(contractProcessList.get(i).getType()==COUNTERSIGN){
                flag1=1;
            }
            if(contractProcessList.get(i).getType()==FINALIZE){
                flag2=1;
            }
            if(contractProcessList.get(i).getType()==EXAM){
                flag3=1;
            }
            if(contractProcessList.get(i).getType()==SIGN){
                flag4=1;
            }
        }

        Map<Integer, Boolean> getNeedAllocationOfContractMap=new HashMap<Integer, Boolean>();
        if(flag1==0)
            getNeedAllocationOfContractMap.put(COUNTERSIGN,true);
        else
            getNeedAllocationOfContractMap.put(COUNTERSIGN,false);
        if(flag2==0)
            getNeedAllocationOfContractMap.put(FINALIZE,true);
        else
            getNeedAllocationOfContractMap.put(FINALIZE,false);
        if(flag3==0)
            getNeedAllocationOfContractMap.put(EXAM,true);
        else
            getNeedAllocationOfContractMap.put(EXAM,false);
        if(flag4==0)
            getNeedAllocationOfContractMap.put(SIGN,true);
        else
            getNeedAllocationOfContractMap.put(SIGN,false);
        return getNeedAllocationOfContractMap;
    }

    @Override
    public Contract getContractOfNeedAssign(int contractId) {
        Contract contract = getContractById(contractId);
        Map<Integer, Boolean> map = getNeedAllocationOfContract(contractId);
        if (!map.get(COUNTERSIGN)){
            contract.setNeedAllocationProcess(COUNTERSIGN-1,false);
        }
        if (!map.get(FINALIZE)){
            contract.setNeedAllocationProcess(FINALIZE-1,false);
        }
        if (!map.get(EXAM)){
            contract.setNeedAllocationProcess(EXAM-1,false);
        }
        if (!map.get(SIGN)){
            contract.setNeedAllocationProcess(SIGN-1,false);
        }
        setContract(contract);
        return contract;
    }

    @Override
    public List<Contract> getNeedAllocationContracts() {
        // 第一步--找出所有未分配所有权限党的合同(即contractType = WAITING)
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andTypeEqualTo(WAITING);
        List<Contract> contractList = contractMapper.selectByExample(contractExample);

        for(int i=0;i<contractList.size();i++){
            // 第二步  获取对应合同的权限分配表(contract process)
            ContractProcessExample contractProcessExample = new ContractProcessExample();
            Contract contract = contractList.get(i);
            setContract(contract);
            contractProcessExample.createCriteria().andContractidEqualTo(contract.getContractid());
            List<ContractProcess> contractProcessList = contractProcessMapper.selectByExample(contractProcessExample);

            for(int j=0;j<contractProcessList.size();j++){
                // 根据查到的权限刷新合同需要分配的权限
                contract.setNeedAllocationProcess(contractProcessList.get(j).getType()-1,false);
            }
        }
        return contractList;
    }

    @Override
    public List<Contract> getNeedAllocationContracts(Contract _contract,Integer pn) {
        // 第一步--找出所有未分配所有权限的合同(即contractType = WAITING)
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andTypeEqualTo(WAITING);
        if (_contract.getContractname()!=null){
            criteria.andContractnameLike("%"+_contract.getContractname()+"%");
        }
        if (_contract.getContent()!=null){
            criteria.andContractnameLike("%"+_contract.getContent()+"%");
        }
        if (_contract.getCustomerid()!=null){
            criteria.andCustomeridEqualTo(_contract.getCustomerid());
        }
        PageHelper.startPage(pn,5); //每页显示5个数据
        List<Contract> contractList = contractMapper.selectByExample(contractExample);

        for(int i=0;i<contractList.size();i++){
            // 第二步  获取对应合同的权限分配表(contract process)
            ContractProcessExample contractProcessExample = new ContractProcessExample();
            Contract contract = contractList.get(i);
            setContract(contract); //初始化一些用于显示的信息
            contractProcessExample.createCriteria().andContractidEqualTo(contract.getContractid());
            List<ContractProcess> contractProcessList = contractProcessMapper.selectByExample(contractProcessExample);

            for(int j=0;j<contractProcessList.size();j++){
                // 根据查到的权限刷新合同需要分配的权限
                contract.setNeedAllocationProcess(contractProcessList.get(j).getType()-1,false);
            }
        }
        if (_contract.isIfBeginFirst()){
            contractList.sort(new Comparator<Contract>() {
                @Override
                public int compare(Contract o1, Contract o2) {
                    return o1.getBegintime().compareTo(o2.getBegintime());
                }
            });
        }
        if (_contract.isIfEndFirst()){
            contractList.sort(new Comparator<Contract>() {
                @Override
                public int compare(Contract o1, Contract o2) {
                    return o1.getEndtime().compareTo(o2.getEndtime());
                }
            });
        }
        return contractList;
    }

    @Override
    public List<Contract> listContractUserNeedDeal(Contract contract, Integer userID, Integer pn) {
        //1.查出用户参与的且state=DOING的所有合同id
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        contractProcessExample.createCriteria().andUseridEqualTo(userID).andStateEqualTo(DOING);
        List<ContractProcess> contractProcesses = contractProcessMapper.selectByExample(contractProcessExample);
        //2.这些所有的合同过程都是该用户当前需要处理的，根据前面的筛选需求查出对应的合同
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        //条件填充
        if (contract.getContractname()!=null){
            criteria.andContractnameLike("%"+contract.getContractname()+"%");
        }
        if (contract.getCustomerid()!=null){
            criteria.andCustomeridEqualTo(contract.getCustomerid());
        }
        if (contract.getOnlyWhich()!=0){
            criteria.andTypeEqualTo(contract.getOnlyWhich()); //只查出某一类，会签，定稿，审批，签订
        }
        //只查出属于改用户的
        List<Integer> contractIdList = new ArrayList<>();
        for (ContractProcess contractProcess : contractProcesses) {
            contractIdList.add(contractProcess.getContractid());
        }
        criteria.andContractidIn(contractIdList);
        PageHelper.startPage(pn,5); //每页显示5个数据
        List<Contract> contractList = contractMapper.selectByExample(contractExample);
        for (Contract each : contractList) {
            setContract(each);
        }

        if (contract.isIfBeginFirst()){
            contractList.sort(new Comparator<Contract>() {
                @Override
                public int compare(Contract o1, Contract o2) {
                    return o1.getBegintime().compareTo(o2.getBegintime());
                }
            });
        }
        if (contract.isIfEndFirst()){
            contractList.sort(new Comparator<Contract>() {
                @Override
                public int compare(Contract o1, Contract o2) {
                    return o1.getEndtime().compareTo(o2.getEndtime());
                }
            });
        }

        return contractList;
    }

    @Override
    //此方法用来给Contract设置一些初始化的信息，如把Date转成字符串
    public void setContract(Contract contract){
        //设置时间为字符串
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        contract.setBeginTimeStr(simpleDateFormat.format(contract.getBegintime()));
        contract.setEndTimeStr(simpleDateFormat.format(contract.getEndtime()));
        //设置客户名
        Customer customer = customerMapper.selectByPrimaryKey(contract.getCustomerid());
        contract.setCustomerName(customer.getCustomername());
    }
}
