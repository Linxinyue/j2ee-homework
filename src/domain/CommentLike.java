package domain;

import javax.persistence.*;

/**
 * Created by sangzhenya on 2016/3/29.
 */
@Entity
@Table(name = "commentlike")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentlike_id;

    @ManyToOne(targetEntity = MyComment.class)
    @JoinColumn(name = "comment_id" ,referencedColumnName = "comment_id")
    private MyComment myComment;

    @ManyToOne(targetEntity = MyUser.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private MyUser myUser;

    public int getCommentlike_id() {
        return commentlike_id;
    }

    public void setCommentlike_id(int commentlike_id) {
        this.commentlike_id = commentlike_id;
    }

    public MyComment getMyComment() {
        return myComment;
    }

    public void setMyComment(MyComment myComment) {
        this.myComment = myComment;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }
}
