package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;

import java.util.List;
import java.util.Map;

/**
 * <Description> ContractProcessService
 *
 * @author 26802
 * @version 1.0
 * @ClassName ContractProcessService
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */
public interface ContractProcessService {

    //根据用户id查出当前他需要处理的所有合同
    /**
     * @return 返回值说明：Integer表示哪一类操作，比如需要他会签，还是审批
     *
     * */
    //right[0] [1] [2]分别代表分配会签权，分配审批权，分配签订权
    Map<Integer, List<Contract>> listConTractUserNeedDeal(int userId,List<Boolean> rights);//zxc

    //修改合同流程信息(旧，保留旧的是因为有些地方用到了此方法，懒得改了)
    int updateProcess(int userId, int contractId, ContractProcess contractProcess);//wj

    //修改合同流程信息(新,新增了一个type参数，才能唯一确定一条记录)
    int updateProcess(int userId, int contractId, int type,ContractProcess contractProcess);//xzx

    //添加合同流程信息
    int insertProcess(ContractProcess contractProcess); //zxc

    // 添加人员
    int addUser(Integer contractid,Integer userid,Integer type);

    //一次性为合同分配多个人员
    int assignUsers(Integer contractId,Map<Integer,List<Integer>> users);//xzx

    //查看某一合同是否已全部分配
    boolean ifAssignAll(Integer contractId);//xzx

    //定稿合同
    void finalize(int contractId,int userId);//xzx

    //获取格式化的会签意见
    String getCounterSignOpinion(int contractId);//xzx
    //获取格式化的审批意见
    String getExamOpinion(int contractId);//xzx

    //发邮件提醒没有处理合同的人
    void sendEmailRemind (Contract contract);
}
