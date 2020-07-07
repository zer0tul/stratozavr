package stratozavr;

import com.imperva.ddc.core.query.ConnectionResponse;
import com.imperva.ddc.core.query.FieldType;
import com.imperva.ddc.core.query.ObjectType;
import com.imperva.ddc.service.DirectoryConnectorService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.imperva.ddc.service.DirectoryConnectorService.resolveDistinguishedName;

public class UserAccount {
    private Long code;
    private String fullRussName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String sAMAccountName;
    private ImageView resultImg;

    public UserAccount(String fullRussName) {
        this.fullRussName = fullRussName;
        this.code = Long.valueOf(0);
        this.lastName = this.fullRussName.split(" ")[0];
        this.firstName = this.fullRussName.split(" ")[1];
        this.middleName = this.fullRussName.split(" ")[2];
        this.sAMAccountName = genSAMAccountName(firstName, middleName, lastName);
       // ConnectionResponse connectionResponse = DirectoryConnectorService.authenticate(Connection.getEndpoint());
        String urlImg = "/iconNoCheck.png";
        if (Connection.getEndpoint() != null) {
            String DN = resolveDistinguishedName(this.sAMAccountName, FieldType.LOGON_NAME, ObjectType.USER, Connection.getEndpoint());

            if (DN == null) urlImg = "/iconCheck.png";
            else urlImg = "/iconFalse.png";
        }
        this.resultImg = new ImageView(new Image(this.getClass().getResourceAsStream(urlImg)));
        System.out.println(Connection.getEndpoint());
    }

    public void resetUserAccount(String fullRussName) {
        this.fullRussName = fullRussName;
        this.lastName = this.fullRussName.split(" ")[0];
        this.firstName = this.fullRussName.split(" ")[1];
        this.middleName = this.fullRussName.split(" ")[2];
        this.sAMAccountName = genSAMAccountName(firstName, middleName, lastName);
        String urlImg = "/iconNoCheck.png";
        if (Connection.getEndpoint() != null) {
            String DN = resolveDistinguishedName(this.sAMAccountName, FieldType.LOGON_NAME, ObjectType.USER, Connection.getEndpoint());

            if (DN == null) urlImg = "/iconCheck.png";
            else urlImg = "/iconFalse.png";
        }
        this.resultImg = new ImageView(new Image(this.getClass().getResourceAsStream(urlImg)));
    }

    public String genSAMAccountName(String firstName, String middleName, String lastName) {
        String str = firstName.substring(0, 1).toLowerCase() + "." + middleName.substring(0,1).toLowerCase() + "." + lastName.toLowerCase();
        return translit(str);
    }

    public String translit(String str) {
        String rusLit = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.";
        String[] entLit = {"a", "b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch","","y","","e","yu","ya","."};
        String newString="";
        for (int i = 0; i < str.length(); i++) {
            newString = newString + entLit[rusLit.indexOf(str.charAt(i))];
        }
        System.out.println(newString);

        return newString;
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

    public void setSAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

   public String getSAMAccountName() {
        return sAMAccountName;
    }

    public ImageView getResultImg() {
        return resultImg;
    }

    public void setResultImg(ImageView resultImg) {
        this.resultImg = resultImg;
    }
}
