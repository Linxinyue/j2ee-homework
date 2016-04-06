package admindomain;

import domain.MyComment;
import domain.MyUser;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sangzhenya on 2016/4/4.
 */
@Entity
@Table(name = "comment_count")
public class CommentCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_count_id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date login_date;

    @ManyToOne(targetEntity = MyComment.class)
    @JoinColumn(name = "comment_id" ,referencedColumnName = "comment_id")
    private MyComment myComment;

    public int getComment_count_id() {
        return comment_count_id;
    }

    public void setComment_count_id(int comment_count_id) {
        this.comment_count_id = comment_count_id;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Date login_date) {
        this.login_date = login_date;
    }

    public MyComment getMyComment() {
        return myComment;
    }

    public void setMyComment(MyComment myComment) {
        this.myComment = myComment;
    }
}
