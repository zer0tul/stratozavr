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
        this.lastName = this.fullRussName.split(" ")[0];
        this.firstName = this.fullRussName.split(" ")[1];
        this.middleName = this.fullRussName.split(" ")[2];
        this.sAMAccountName = "TEST";
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
