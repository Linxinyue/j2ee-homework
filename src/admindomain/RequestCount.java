package admindomain;

import domain.MyClassification;
import domain.MyUser;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sangzhenya on 2016/4/4.
 */
@Entity
@Table(name = "request_count")
public class RequestCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int request_count_id;
    @Column
    @Temporal(TemporalType.DATE)
    private Date request_date;

    @ManyToOne(targetEntity = MyClassification.class)
    @JoinColumn(name = "classification_id" ,referencedColumnName = "classification_id")
    private MyClassification myClassification;

    public int getRequest_count_id() {
        return request_count_id;
    }

    public void setRequest_count_id(int request_count_id) {
        this.request_count_id = request_count_id;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public MyClassification getMyClassification() {
        return myClassification;
    }

    public void setMyClassification(MyClassification myClassification) {
        this.myClassification = myClassification;
    }
}
