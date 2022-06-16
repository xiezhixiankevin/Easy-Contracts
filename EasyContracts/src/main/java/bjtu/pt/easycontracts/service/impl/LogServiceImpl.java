package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.LogMapper;
import bjtu.pt.easycontracts.pojo.table.Log;
import bjtu.pt.easycontracts.pojo.table.LogExample;
import bjtu.pt.easycontracts.service.LogService;
import bjtu.pt.easycontracts.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService
{
    @Autowired
    private LogMapper logMapper;

    @Autowired
    private UserService userService;

    @Override
    public int addLog(Log log)
    {
        return logMapper.insert(log);
    }

    @Override
    public int deleteLog(Log startLog, Log endLog)
    {
        LogExample logExample = new LogExample();
        LogExample.Criteria criteria = logExample.createCriteria();

        criteria.andTimeBetween(startLog.getTime() , endLog.getTime());
        return logMapper.deleteByExample(logExample);
    }

    @Override
    public List<Log> listLogByTime(Log startLog, Log endLog)
    {
        LogExample logExample = new LogExample();
        LogExample.Criteria criteria = logExample.createCriteria();

        criteria.andTimeBetween(startLog.getTime() , endLog.getTime());
        return logMapper.selectByExample(logExample);
    }

    @Override
    public List<Log> listLog(Integer contractId,Integer userId,Integer pn)
    {
        LogExample logExample = new LogExample();
        LogExample.Criteria criteria = logExample.createCriteria();
        if (contractId !=null){
            criteria.andContractidEqualTo(contractId);
        }
        if (userId !=null){
            criteria.andUseridEqualTo(userId);
        }
        PageHelper.startPage(pn,5); //每页显示5个数据
        List<Log> logList = logMapper.selectByExampleWithBLOBs(logExample);
        for (Log log : logList) {
            setLog(log);
        }
        return logList;
    }

    @Override
    public List<Log> listLogByUserId(int userId)
    {
        LogExample logExample = new LogExample();
        logExample.createCriteria().andUseridEqualTo(userId);
        return logMapper.selectByExample(logExample);
    }

    @Override
    public void setLog(Log log) {
        Integer operateType = log.getOperatetype();
        String username = userService.getUserById(log.getUserid()).getUsername();
        String time = log.getTime().toString();
        String operateTypeStr;

        switch (operateType)
        {
            case 1 :
                operateTypeStr = "COUNTERSIGN_LOG";
                break;
            case 2 :
                operateTypeStr = "FINALIZE_LOG";
                break;
            case 3 :
                operateTypeStr = "EXAM_LOG";
                break;
            case 4 :
                operateTypeStr = "SIGN_LOG";
                break;
            case 5 :
                operateTypeStr = "DRAFT_LOG";
                break;
            case 6 :
                operateTypeStr = "ASSIGN_PERMISSION_LOG";
                break;
            case 7 :
                operateTypeStr = "ADD_CUSTOMER_LOG";
                break;
            case 8 :
                operateTypeStr = "MODIFY_CUSTOMER_LOG";
                break;
            case 9 :
                operateTypeStr = "DELETE_CUSTOMER_LOG";
                break;
            case 10 :
                operateTypeStr = "MODIFY_USER_LOG";
                break;
            case 11 :
                operateTypeStr = "DELETE_USER_LOG";
                break;
            case 12 :
                operateTypeStr = "MODIFY_OWN_LOG";
                break;
            case 13 :
                operateTypeStr = "DELETE_CONTRACT_LOG";
                break;
            default:
                operateTypeStr = "Error";
        }

        /* 赋值 */
        log.setUsername(username);
        log.setOperatetypeStr(operateTypeStr);
        log.setTimeStr(time);
    }

    /**
     * 添加会签,定稿,审批,签订,起草日志,删除合同
     */
    @Override
    public void addType1Log(int userId, int contractId, int type) {
        String typeStr = "";

        switch (type){
            case 1:
                typeStr = "会签";
                break;
            case 2:
                typeStr = "定稿";
                break;
            case 3:
                typeStr = "审批";
                break;
            case 4:
                typeStr = "签订";
                break;
            case 5:
                typeStr = "起草";
                break;
            case 13:
                typeStr = "删除";
                break;
            default:
                return;
        }
        String content = "用户id为" + userId + "的用户完成了对id为" + contractId + "的合同的" + typeStr;
        Log log = new Log(userId,contractId,type,content);
        addLog(log);
    }

    /**
     * 添加客户,修改客户,删除客户
     */
    @Override
    public void addType2Log(String customerName, int userId, int type) {
        String typeStr = "";

        switch (type){
            case 7:
                typeStr = "添加";
                break;
            case 8:
                typeStr = "修改";
                break;
            case 9:
                typeStr = "删除";
                break;
            default:
                return;
        }
        String content = "用户id为" + userId + "的用户完成了对" + customerName + "客户的" + typeStr;
        Log log = new Log(userId,null,type,content);
        addLog(log);
    }

    /**
     * 修改用户,删除用户,分配权限
     * @param userId1 被操作者
     * @param userId2 操作者
     */
    @Override
    public void addType3Log(int userId1, int userId2, int type) {
        String typeStr = "";

        switch (type){
            case 10:
                typeStr = "修改";
                break;
            case 11:
                typeStr = "删除";
                break;
            case 6:
                typeStr = "权限变动";
                break;
            default:
                return;
        }
        String content = "用户id为" + userId2 + "的用户完成了对id为" + userId1 + "的客户的" + typeStr;
        Log log = new Log(userId2,null,type,content);
        addLog(log);
    }
}
