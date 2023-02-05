package clone_project.stagram.DTO;

import java.util.Date;

public class UserDTO {
    private Long user_no;
    private String email;
    private String id;
    private String pw;
    private String name;
    private String bio;
    private Date regDate;

    public Long getUser_no() {
        return user_no;
    }

    public void setUser_no(Long user_no) {
        this.user_no = user_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
}
