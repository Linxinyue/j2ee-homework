package domain;

import javax.persistence.*;

/**
 * Created by sangzhenya on 2016/3/29.
 */
@Entity
@Table(name = "mycollocation")
public class MyCollocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int collocation_id;

    @ManyToOne(targetEntity = MyArticle.class)
    @JoinColumn(name = "article_id" ,referencedColumnName = "article_id")
    private MyArticle myArticle;

    @ManyToOne(targetEntity = MyUser.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private MyUser myUser;

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public MyArticle getMyArticle() {
        return myArticle;
    }

    public void setMyArticle(MyArticle myArticle) {
        this.myArticle = myArticle;
    }


    public int getCollocation_id() {
        return collocation_id;
    }

    public void setCollocation_id(int collocation_id) {
        this.collocation_id = collocation_id;
    }
}
