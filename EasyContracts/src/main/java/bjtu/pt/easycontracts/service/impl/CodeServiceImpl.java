package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.pojo.logic.Code;
import bjtu.pt.easycontracts.service.CodeService;
import bjtu.pt.easycontracts.service.EmailService;
import bjtu.pt.easycontracts.utils.Global;
import org.junit.internal.runners.statements.Fail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EmailService emailService;

    @Override
    public int sendCode( String email,int type) {
        if (type == Global.REGISTER){
            return sendCodeREGISTER(email);
        }else if (type == Global.FIND){
            return sendCodeFIND(email);
        }
        return Global.FAIL;
    }

    @Override
    public int checkCode(Code code,int type) {
        String realCode = getCode(code.getUsername(), type);
        if (realCode.equals(code.getCodeValue())){
            return Global.SUCCESS;
        }
        return Global.FAIL;
    }

    @Override
    public int getSendTimes(String email, int type) {
        String key = email + "_";
        if (type == Global.REGISTER){
            key += "register_times";
        }else {
            key += "find_times";
        }
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null){
            return 0;
        }

        return Integer.parseInt((String) result);
    }

    @Override
    public String getCode(String email,int type) {
        String key = email + "_";
        if (type == Global.REGISTER){
            key += "register_code";
        }else {
            key += "find_code";
        }
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null){
            return "";
        }
        return (String) result;
    }

    //发送注册验证码
    private int sendCodeREGISTER(String email){
        int times = getSendTimes(email,Global.REGISTER);
        if (times <= 5){ //每个邮箱每1小时只能发送5次
            long codeL = System.nanoTime();
            //先生成6位验证码
            String codeStr = Long.toString(codeL);
            codeStr = codeStr.substring(codeStr.length() - 8, codeStr.length() - 2);
            //存入redis
            String key_code = email + "_register_code";
            String key_times = email + "_register_times";
            redisTemplate.opsForValue().set(key_code,codeStr,60*5,TimeUnit.SECONDS);//验证码有效时间是5分钟
            redisTemplate.opsForValue().set(key_times,String.valueOf(times+1),1, TimeUnit.HOURS);//更新最近验证码发送时间
            //发送到用户邮箱
            String content = "你好，欢迎使用EasyContract,验证码是:"+ codeStr +",有效时间5分钟";
            emailService.sendSimpleMail(email,"EasyContract注册验证码",content);
            return Global.SUCCESS;
        }
        return Global.FAIL;
    }

    //发送找回密码验证码
    private int sendCodeFIND(String email){
        int times = getSendTimes(email,Global.FIND);
        if (times <= 5){ //每个用户每1小时只能发送5次
            long codeL = System.nanoTime();
            //先生成6位验证码
            String codeStr = Long.toString(codeL);
            codeStr = codeStr.substring(codeStr.length() - 8, codeStr.length() - 2);
            //存入redis
            String key_code = email + "_find_code";
            String key_times = email + "_find_times";
            redisTemplate.opsForValue().set(key_code,codeStr,60*5,TimeUnit.SECONDS);//验证码有效时间是5分钟
            redisTemplate.opsForValue().set(key_times,String.valueOf(times+1),1, TimeUnit.HOURS);//更新最近验证码发送时间
            //发送到用户邮箱
            String content = "你好，欢迎使用EasyContract,验证码是:"+ codeStr +",有效时间5分钟";
            emailService.sendSimpleMail(email,"EasyContract找回密码验证码",content);
            return Global.SUCCESS;
        }
        return Global.FAIL;
    }
}
