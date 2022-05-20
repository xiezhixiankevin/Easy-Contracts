package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.pojo.logic.Code;
import bjtu.pt.easycontracts.service.CodeService;
import bjtu.pt.easycontracts.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <Description> CodeServiceImpl
 *
 * @author 26802
 * @version 1.0
 * @ClassName CodeServiceImpl
 * @taskId
 * @see bjtu.pt.easycontracts.service.impl
 */
@Service
public class CodeServiceImpl implements CodeService {

    private final int REGISTER = 1; //注册
    private final int FIND = 0; //找回

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int sendCode(String username, String email,int type) {
        if (type == REGISTER){
            return sendCodeREGISTER(username,email);
        }else if (type == FIND){
            return sendCodeFIND(username,email);
        }
        return Global.FAIL;
    }

    @Override
    public int checkCode(Code code) {
        return 0;
    }

    //发送注册验证码
    private int sendCodeREGISTER(String username,String email){
        return 0;
    }

    //发送找回密码验证码
    private int sendCodeFIND(String username,String email){
        return 0;
    }
}
