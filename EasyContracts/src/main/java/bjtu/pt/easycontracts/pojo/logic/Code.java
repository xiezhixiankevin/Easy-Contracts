package bjtu.pt.easycontracts.pojo.logic;

/**
 * <Description> Code
 *  验证码
 * @author 26802
 * @version 1.0
 * @ClassName Code
 * @taskId
 * @see bjtu.pt.easycontracts.pojo.logic
 */
public class Code {

    private String username;
    private String codeValue;

    public Code(String username, String codeValue) {
        this.username = username;
        this.codeValue = codeValue;
    }

    public Code() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
