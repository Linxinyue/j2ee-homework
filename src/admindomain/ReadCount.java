package admindomain;

import domain.MyArticle;
import domain.MyUser;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sangzhenya on 2016/4/4.
 */
@Entity
@Table(name = "read_count")
public class ReadCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int read_count_id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date read_date;

    @ManyToOne(targetEntity = MyArticle.class)
    @JoinColumn(name = "article_id" ,referencedColumnName = "article_id")
    private MyArticle myArticle;

    public int getRead_count_id() {
        return read_count_id;
    }

    public void setRead_count_id(int read_count_id) {
        this.read_count_id = read_count_id;
    }

    public Date getRead_date() {
        return read_date;
    }

    public void setRead_date(Date read_date) {
        this.read_date = read_date;
    }

    public MyArticle getMyArticle() {
        return myArticle;
    }

    public void setMyArticle(MyArticle myArticle) {
        this.myArticle = myArticle;
    }
}
