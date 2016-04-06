package domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sangzhenya on 2016/3/29.
 */
@Entity
@Table(name = "mycomment")
public class MyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;

    @Lob
    @Type(type="text")
    @Basic(fetch = FetchType.LAZY)
    private String content;
    private Date coment_date;

    @ManyToOne(targetEntity = MyArticle.class)
    @JoinColumn(name = "article_id" ,referencedColumnName = "article_id")
    private MyArticle myArticle;

    @ManyToOne(targetEntity = MyUser.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private MyUser myUser;

    @OneToMany( fetch = FetchType.LAZY,targetEntity = CommentLike.class)
    @JoinColumn(name = "comment_id" ,referencedColumnName = "comment_id")
    private Set<CommentLike> commentLikes = new HashSet<>();

    public MyComment() {
    }

    public MyComment( String content, Date coment_date) {
        this.content = content;
        this.coment_date = coment_date;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getComent_date() {
        return coment_date;
    }

    public void setComent_date(Date coment_date) {
        this.coment_date = coment_date;
    }

    public MyArticle getMyArticle() {
        return myArticle;
    }

    public void setMyArticle(MyArticle myArticle) {
        this.myArticle = myArticle;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public Set<CommentLike> getCommentLikes() {
        return commentLikes;
    }

    public void setCommentLikes(Set<CommentLike> commentLikes) {
        this.commentLikes = commentLikes;
    }
}
