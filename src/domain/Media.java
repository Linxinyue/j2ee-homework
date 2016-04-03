package domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by sangzhenya on 2016/3/31.
 */
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int media_id;

    @Lob
    @Type(type = "text")
    @Basic(fetch = FetchType.LAZY)
    private String conent;

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public String getConent() {
        return conent;
    }

    public void setConent(String conent) {
        this.conent = conent;
    }
}
