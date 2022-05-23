package bjtu.pt.easycontracts.pojo.table;

import java.util.*;

public class User {
    private Integer userid;

    private String username;

    private String userdescription;

    private String password;

    private String email;

    //以下是非数据库属性
    private List<Rights> userRights = new ArrayList<>();

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserdescription() {
        return userdescription;
    }

    public void setUserdescription(String userdescription) {
        this.userdescription = userdescription == null ? null : userdescription.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**/
    public List<Rights> getUserRights() {
        return userRights;
    }

    public void setUserRights(List<Rights> userRights) {
        this.userRights = userRights;
    }

    public boolean ifHasRight(Integer rightId){
        if (userRights == null){
            return false;
        }
        for(Rights rights:userRights){
            if (rights.getRightid() == rightId){
                return true;
            }
        }
        return false;
    }

}