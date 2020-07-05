package stratozavr;

public class UserAccount {
    private Long code;
    private String fullRussName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String sAMAccountName;

    public UserAccount(String fullRussName) {
        this.fullRussName = fullRussName;
        this.code = Long.valueOf(0);
    }
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getFullRussName() {
        return fullRussName;
    }

    public void setFullRussName(String fullRussName) {
        this.fullRussName = fullRussName;
    }
}
