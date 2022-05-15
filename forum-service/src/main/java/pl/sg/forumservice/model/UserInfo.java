package pl.sg.forumservice.model;

public class UserInfo {

    private String userName;

    public UserInfo(String userName) {
        this.userName = userName;
    }

    public UserInfo() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
