package domain;

import javax.persistence.*;

/**
 * Created by sangzhenya on 2016/3/29.
 */
@Entity
@Table(name = "articlelike")
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articlelike_id;

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

    public int getArticlelike_id() {
        return articlelike_id;
    }

    public void setArticlelike_id(int articlelike_id) {
        this.articlelike_id = articlelike_id;
    }
}
