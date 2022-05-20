package bjtu.pt.easycontracts.service;

/**
 * <Description> EmailService
 *
 * @author 26802
 * @version 1.0
 * @ClassName EmailService
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */
public interface EmailService {

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

}
