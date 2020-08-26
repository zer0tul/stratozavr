package stratozavr;

import com.imperva.ddc.core.query.ConnectionResponse;
import com.imperva.ddc.core.query.FieldType;
import com.imperva.ddc.core.query.ObjectType;
import com.imperva.ddc.service.DirectoryConnectorService;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import static com.imperva.ddc.service.DirectoryConnectorService.resolveDistinguishedName;

public class UserAccount {
    @CsvBindByName(column = "Code")
    @CsvBindByPosition(position = 0)
    private Long code;
    @CsvBindByName(column = "fullRussName")
    @CsvBindByPosition(position = 1)
    private String fullRussName;
    @CsvBindByName(column = "firstName")
    @CsvBindByPosition(position = 3)
    private String firstName;
    @CsvBindByName(column = "middleName")
    @CsvBindByPosition(position = 4)
    private String middleName;
    @CsvBindByName(column = "lastName")
    @CsvBindByPosition(position = 2)
    private String lastName;
    @CsvBindByName(column = "sAMAccountName")
    @CsvBindByPosition(position = 5)
    private String sAMAccountName;
    @CsvIgnore
    private ImageView resultImg;
    @CsvBindByName(column = "dateBirth")
    @CsvBindByPosition(position = 6)
    private String dateBirth;
    @CsvBindByName(column = "groupName")
    @CsvBindByPosition(position = 7)
    private String groupName;
    @CsvBindByName(column = "faculty")
    @CsvBindByPosition(position = 8)
    private String faculty;

    public UserAccount(String fullRussName) {
        this.fullRussName = fullRussName;
        this.code = Long.valueOf(0);
        this.lastName = this.fullRussName.split(" ")[0];
        this.firstName = this.fullRussName.split(" ")[1];
        if (this.fullRussName.split(" ").length == 2) this.middleName = "";
        else this.middleName = this.fullRussName.split(" ")[2];
        this.sAMAccountName = genSAMAccountName(firstName, middleName, lastName);
       // ConnectionResponse connectionResponse = DirectoryConnectorService.authenticate(Connection.getEndpoint());
        String urlImg = "/iconNoCheck.png";
        InitialLdapContext context = ActiveDirectoryNew.getContext();
        if (context != null) {
            ActiveDirectoryNew.User user = ActiveDirectoryNew.getUser(this.sAMAccountName, context);
            //System.out.println(user);
            if (user == null) urlImg = "/iconCheck.png";
            else urlImg = "/iconFalse.png";
        }
        this.resultImg = new ImageView(new Image(this.getClass().getResourceAsStream(urlImg)));
        //System.out.println(context);
    }

    public UserAccount(String lastName, String firstName, String middleName, long code, String dateBirth, String groupName, String faculty) {
        this.lastName = lastName;
        this.firstName = firstName;
        if (middleName == null) {
            this.fullRussName = lastName + " " + firstName;
            this.middleName = "";
        }
        else {
            this.fullRussName = lastName + " " + firstName + " " + middleName;
            this.middleName = middleName;
        }
        this.code = code;
        this.dateBirth = dateBirth;
        this.groupName = groupName;
        this.faculty = faculty;
        this.sAMAccountName = genSAMAccountName(this.firstName, this.middleName, this.lastName);
        String urlImg = "/iconNoCheck.png";
        InitialLdapContext context = ActiveDirectoryNew.getContext();
        if (context != null) {
            ActiveDirectoryNew.User user = ActiveDirectoryNew.getUser(this.sAMAccountName, context);
            //System.out.println(user);
            if (user == null) urlImg = "/iconCheck.png";
            else urlImg = "/iconFalse.png";
        }
        this.resultImg = new ImageView(new Image(this.getClass().getResourceAsStream(urlImg)));
    }

    public void resetUserAccount(String fullRussName) {
        this.fullRussName = fullRussName;
        this.lastName = this.fullRussName.split(" ")[0];
        this.firstName = this.fullRussName.split(" ")[1];
        this.middleName = this.fullRussName.split(" ")[2];
        this.sAMAccountName = genSAMAccountName(firstName, middleName, lastName);
        String urlImg = "/iconNoCheck.png";
        InitialLdapContext context = ActiveDirectoryNew.getContext();
        if (context != null) {
            ActiveDirectoryNew.User user = null;
            try {
                user = ActiveDirectoryNew.getUser(this.sAMAccountName, context);
            }
            catch (Exception e) {}
           // System.out.println(user);
            if (user == null) urlImg = "/iconCheck.png";
            else urlImg = "/iconFalse.png";
        }
        this.resultImg = new ImageView(new Image(this.getClass().getResourceAsStream(urlImg)));
    }

    public  void resetSAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
        String urlImg = "/iconNoCheck.png";
        InitialLdapContext context = ActiveDirectoryNew.getContext();
        if (context != null) {
            ActiveDirectoryNew.User user = null;
            try {
                user = ActiveDirectoryNew.getUser(this.sAMAccountName, context);
            }
            catch (Exception e) {}
            // System.out.println(user);
            if (user == null) urlImg = "/iconCheck.png";
            else urlImg = "/iconFalse.png";
        }
        this.resultImg = new ImageView(new Image(this.getClass().getResourceAsStream(urlImg)));
    }

    public String genSAMAccountName(String firstName, String middleName, String lastName) {
        firstName = translit(firstName.toLowerCase());
        lastName = translit(lastName.toLowerCase());
        if (!middleName.equals("")) middleName = translit(middleName.toLowerCase());
        String str = "";
        if (middleName.equals("")) str =  firstName.substring(0, 1) + "." + lastName;
        else str = firstName.substring(0, 1) + "." + middleName.substring(0,1) + "." + lastName;
        return str;
    }

    public String translit(String str) {
        String rusLit = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.- ";
        String[] entLit = {"a", "b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch","","y","","e","yu","ya",".","-","."};
        String newString="";
        for (int i = 0; i < str.length(); i++) {
            newString = newString + entLit[rusLit.indexOf(str.charAt(i))];
        }
       // System.out.println(newString);

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

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
