package admindomain;

import domain.MyUser;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sangzhenya on 2016/4/4.
 */
@Entity
@Table(name = "login_count")
public class LoginCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int login_count_id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date login_date;

    @ManyToOne(targetEntity = MyUser.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private MyUser myUser;

    public int getLogin_count_id() {
        return login_count_id;
    }

    public void setLogin_count_id(int login_count_id) {
        this.login_count_id = login_count_id;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Date login_date) {
        this.login_date = login_date;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }
}
