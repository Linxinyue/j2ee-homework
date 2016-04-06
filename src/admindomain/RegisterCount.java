package admindomain;

import domain.MyUser;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sangzhenya on 2016/4/4.
 */
@Entity
@Table(name = "register_count")
public class RegisterCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int register_count_id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date register_date;

    @ManyToOne(targetEntity = MyUser.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private MyUser myUser;

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public int getRegister_count_id() {
        return register_count_id;
    }

    public void setRegister_count_id(int register_count_id) {
        this.register_count_id = register_count_id;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }
}
