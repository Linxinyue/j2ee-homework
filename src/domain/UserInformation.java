package domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sangzhenya on 2016/3/28.
 */
@Entity
@Table(name = "userinformation")
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int information_id;
    private String email;
    private String sex;
    private Date birthday;
    private String address;
    private String own_word;


    public UserInformation() {
    }

    public UserInformation(String email, String sex, Date birthday, String address, String own_word) {
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
        this.own_word = own_word;
    }

    public int getInformation_id() {
        return information_id;
    }

    public void setInformation_id(int information_id) {
        this.information_id = information_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwn_word() {
        return own_word;
    }

    public void setOwn_word(String own_word) {
        this.own_word = own_word;
    }
}
