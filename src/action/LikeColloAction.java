package action;

import com.opensymphony.xwork2.ActionSupport;
import domain.MyUser;
import org.apache.struts2.ServletActionContext;
import service.LikeColloService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by sangzhenya on 2016/4/1.
 */
public class LikeColloAction extends ActionSupport {
    private int article_id;
    private int comment_id;

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();

    private int getUserId(){
        MyUser myUser = (MyUser) httpSession.getAttribute("currentUser");
        return myUser.getUser_id();
    }

    public void like(){
        LikeColloService.like(getUserId(),article_id);
    }
    public void disLike(){
        LikeColloService.dislike(getUserId(),article_id);
    }
    public void collo(){
        LikeColloService.collo(getUserId(),article_id);
    }

    public void comment(){
        LikeColloService.comment(getUserId(),comment_id);
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
}
