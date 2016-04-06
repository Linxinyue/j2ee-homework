package domain;

import javax.persistence.*;

/**
 * Created by sangzhenya on 2016/3/28.
 */
@Entity
@Table(name = "mysubscribe")
public class MySubscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscibe_id;
//    private int classification_id;

    @ManyToOne(targetEntity = MyClassification.class)
    @JoinColumn(name = "classification_id" ,referencedColumnName = "classification_id")
    private MyClassification myClassification;

    @ManyToOne(targetEntity = MyUser.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private MyUser myUser;

    public MyClassification getMyClassification() {
        return myClassification;
    }

    public void setMyClassification(MyClassification myClassification) {
        this.myClassification = myClassification;
    }

    public int getSubscibe_id() {
        return subscibe_id;
    }

    public void setSubscibe_id(int subscibe_id) {
        this.subscibe_id = subscibe_id;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public MySubscribe() {
    }
}
