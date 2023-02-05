package clone_project.stagram.DTO;

public class LoginDTO {
    private String idOrEmail;
    private String pw;

    public String getIdOrEmail() {
        return idOrEmail;
    }

    public void setIdOrEmail(String idOrEmail) {
        this.idOrEmail = idOrEmail;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
