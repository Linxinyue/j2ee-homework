package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sangzhenya on 2016/3/29.
 */
@Entity
@Table(name = "myclassification")
public class MyClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classification_id;
    private String classification_name;
    private String classification_desc;

    public String getClassification_name() {
        return classification_name;
    }

    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }


    @OneToMany(targetEntity = MyArticle.class)
    @JoinColumn(name = "classification_id" ,referencedColumnName = "classification_id")
    private Set<MyArticle> myArticles = new HashSet<>();

    public Set<MyArticle> getMyArticles() {
        return myArticles;
    }

    public void setMyArticles(Set<MyArticle> myArticles) {
        this.myArticles = myArticles;
    }

    public MyClassification() {
    }

    public MyClassification(String classification_name, String classification_desc) {
        this.classification_name = classification_name;
        this.classification_desc = classification_desc;
    }

    public int getClassification_id() {
        return classification_id;
    }

    public void setClassification_id(int classification_id) {
        this.classification_id = classification_id;
    }

    public String getClassification_desc() {
        return classification_desc;
    }

    public void setClassification_desc(String classification_desc) {
        this.classification_desc = classification_desc;
    }
}
