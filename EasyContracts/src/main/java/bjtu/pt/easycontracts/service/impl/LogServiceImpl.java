package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.LogMapper;
import bjtu.pt.easycontracts.pojo.table.Log;
import bjtu.pt.easycontracts.pojo.table.LogExample;
import bjtu.pt.easycontracts.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService
{
    @Autowired
    private LogMapper logMapper;

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
    public List<Log> listLog()
    {
        LogExample logExample = new LogExample();
        return logMapper.selectByExample(logExample);
    }

    @Override
    public List<Log> listLogByUserId(int userId)
    {
        LogExample logExample = new LogExample();
        logExample.createCriteria().andUseridEqualTo(userId);
        return logMapper.selectByExample(logExample);
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
