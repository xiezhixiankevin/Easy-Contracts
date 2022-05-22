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

    private String email;

    private String codeValue;

    public Code(String email, String codeValue) {
        this.email = email;
        this.codeValue = codeValue;
    }

    public Code() {
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
