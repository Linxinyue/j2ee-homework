package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sangzhenya on 2016/3/26.
 */
@Entity
@Table(name = "myuser")
public class MyUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String name;
    private String password;

    @OneToOne( fetch = FetchType.LAZY,targetEntity = UserInformation.class)
    @PrimaryKeyJoinColumn
    private UserInformation userInformation;

    @OneToMany( fetch = FetchType.LAZY,targetEntity = MySubscribe.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private Set<MySubscribe> mySubscribe = new HashSet<>(0);

    public Set<MySubscribe> getMySubscribe() {
        return mySubscribe;
    }

    public void setMySubscribe(Set<MySubscribe> mySubscribe) {
        this.mySubscribe = mySubscribe;
    }

    @OneToMany( fetch = FetchType.LAZY,targetEntity = MyComment.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private Set<MyComment> myComments = new HashSet<>();

    public Set<MyComment> getMyComments() {
        return myComments;
    }

    public void setMyComments(Set<MyComment> myComments) {
        this.myComments = myComments;
    }

    @OneToMany( fetch = FetchType.LAZY,targetEntity = ArticleLike.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private Set<ArticleLike> articleLikes = new HashSet<>();



    public Set<ArticleLike> getArticleLikes() {
        return articleLikes;
    }

    public void setArticleLikes(Set<ArticleLike> articleLikes) {
        this.articleLikes = articleLikes;
    }

    @OneToMany( fetch = FetchType.LAZY,targetEntity = ArticleDisLike.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private Set<ArticleDisLike> articleDisLikes = new HashSet<>();

    public Set<ArticleDisLike> getArticleDisLikes() {
        return articleDisLikes;
    }

    public void setArticleDisLikes(Set<ArticleDisLike> articleDisLikes) {
        this.articleDisLikes = articleDisLikes;
    }

    @OneToMany( fetch = FetchType.LAZY,targetEntity = CommentLike.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private Set<CommentLike> commentLikes = new HashSet<>();

    public Set<CommentLike> getCommentLikes() {
        return commentLikes;
    }

    public void setCommentLikes(Set<CommentLike> commentLikes) {
        this.commentLikes = commentLikes;
    }

    @OneToMany( fetch = FetchType.LAZY,targetEntity = MyFollow.class)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private Set<MyFollow>myFollows = new HashSet<>();

    public Set<MyFollow> getMyFollows() {
        return myFollows;
    }

    public void setMyFollows(Set<MyFollow> myFollows) {
        this.myFollows = myFollows;
    }

    @OneToMany( fetch = FetchType.LAZY,targetEntity = MyFollow.class)
    @JoinColumn(name = "follow_id" ,referencedColumnName = "user_id")
    private Set<MyFollow> followWhos = new HashSet<>();

    public Set<MyFollow> getFollowWhos() {
        return followWhos;
    }

    public void setFollowWhos(Set<MyFollow> followWhos) {
        this.followWhos = followWhos;
    }

    public MyUser() {
    }

    public MyUser(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }
}
