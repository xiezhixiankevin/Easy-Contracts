package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Log;

import java.util.List;

/**
 * <Description> LogService
 *
 * @author 26802
 * @version 1.0
 * @ClassName LogService
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */
public interface LogService {

    //新增日志
    int addLog(Log log);

    //删除指定时间的日志
    int deleteLog(Log startLog,Log endLog);

    //查询日志
    //1.查询指定时间的日志
    int listLogByTime(Log startLog,Log endLog);
    //2.列出所有日志(最新的排在最前面)
    List<Log> listLog();
    //3.查询某个人的操作日志
    List<Log> listLogByUserId(int userId);

}
