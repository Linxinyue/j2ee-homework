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
@Table(name = "myarticle")
public class MyArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int article_id;
    private String title;
    private String article_pic;

    @Lob
    @Type(type="text")
    @Basic(fetch = FetchType.LAZY)
    private String abstracts;

    @Lob
    @Type(type="text")
    @Basic(fetch = FetchType.LAZY)
    private String content;

    private String author;
    @Column
    @Temporal(TemporalType.DATE)
    private Date pub_date;

    @ManyToOne(targetEntity = MyClassification.class)
    @JoinColumn(name = "classification_id" ,referencedColumnName = "classification_id")
    private MyClassification myClassification;

    @OneToMany(targetEntity = MyComment.class)
    @JoinColumn(name = "article_id" ,referencedColumnName = "article_id")
    private Set<MyComment> myComments = new HashSet<>();


    @OneToMany( fetch = FetchType.LAZY,targetEntity = ArticleLike.class)
    @JoinColumn(name = "article_id" ,referencedColumnName = "article_id")
    private Set<ArticleLike> articleLikes = new HashSet<>();


    public MyArticle() {
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
    }

    public MyClassification getMyClassification() {
        return myClassification;
    }

    public void setMyClassification(MyClassification myClassification) {
        this.myClassification = myClassification;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAbstracts() {
        return abstracts;
    }
    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getArticle_pic() {
        return article_pic;
    }

    public void setArticle_pic(String article_pic) {
        this.article_pic = article_pic;
    }

    public Set<MyComment> getMyComments() {
        return myComments;
    }

    public void setMyComments(Set<MyComment> myComments) {
        this.myComments = myComments;
    }

    public Set<ArticleLike> getArticleLikes() {
        return articleLikes;
    }

    public void setArticleLikes(Set<ArticleLike> articleLikes) {
        this.articleLikes = articleLikes;
    }

}
