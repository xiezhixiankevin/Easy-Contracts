package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.LogMapper;
import bjtu.pt.easycontracts.pojo.table.Log;
import bjtu.pt.easycontracts.pojo.table.LogExample;
import bjtu.pt.easycontracts.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
