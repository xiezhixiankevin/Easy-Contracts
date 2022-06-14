package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.Log;
import bjtu.pt.easycontracts.service.LogService;
import bjtu.pt.easycontracts.utils.Global;
import bjtu.pt.easycontracts.utils.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <Description> LogController
 *
 * @author 26802
 * @version 1.0
 * @ClassName LogController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/listLog/{pn}")
    @ResponseBody
    public ReturnObject<PageInfo> listLog(@RequestParam(value = "contractId",required = false)Integer contractId,
                                          @RequestParam(value = "userId",required = false)Integer userId,
                                          @PathVariable("pn")Integer pn){

        List<Log> logList = logService.listLog(contractId, userId, pn);
        PageInfo pageInfo = new PageInfo(logList,5);
        ReturnObject<PageInfo> result = new ReturnObject<>(Global.SUCCESS,pageInfo);
        return result;

    }



}
