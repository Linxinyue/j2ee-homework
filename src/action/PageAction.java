package action;

import com.opensymphony.xwork2.ActionSupport;
import domain.MyArticle;
import domain.MyClassification;
import domain.MyUser;
import org.apache.struts2.ServletActionContext;
import service.CommentsService;
import service.MyClassifcationService;
import service.PageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by sangzhenya on 2016/3/30.
 */
public class PageAction extends ActionSupport{
    private int id;
    private int page;
    private int classId;
    private String commentConent;

    private static final int pageSize = 10;
    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();
    private PageService pageService = new PageService();

    /**
     * 获取当前用户id，如果不存在返回 -1
     * @return
     */
    private int getUserId(){
        if (httpSession.getAttribute("currentUser") != null){
            MyUser myUser = (MyUser) httpSession.getAttribute("currentUser");
            return myUser.getUser_id();
        }
        return -1;
    }

    private int getArticleId(){
        if (httpSession.getAttribute("articleid") != null){
            return (int) httpSession.getAttribute("articleid");
        }
        return -1;
    }


    /**
     * 获取当前页面文章信息的方法
     * @return
     * @throws Exception
     */
    public String indexId() throws Exception {

        //设置当前文章的id
        httpSession.setAttribute("articleid",id);

        //设置当前的文章的具体信息
        MyArticle myArticle = pageService.getArticle(id);
        httpSession.setAttribute("myArticle",myArticle);

        //设置当前的类的名字
        MyClassification myClassification = pageService.getClassName(id);
        httpSession.setAttribute("myClassification",myClassification);

        //首次获取评论的信息
        List<Object[]> comments = CommentsService.getComments(0,pageSize,id,getUserId());
        httpSession.setAttribute("comments",comments);

        //设置网页评论的数量信息
        int commentCunts = CommentsService.getCommentsCounts(id);
        httpSession.setAttribute("commentCount", commentCunts);
        if(commentCunts % 10 != 0)
            httpSession.setAttribute("totalCommentPage",commentCunts/10+1);
        else
            httpSession.setAttribute("totalCommentPage",commentCunts/10);

        //设置当前评论的页面位置
        httpSession.setAttribute("currentCommentPage",1);

        //检查是否订阅
        if (httpSession.getAttribute("currentUser")!=null) {
            String isSubClass = MyClassifcationService.isSubClass((MyUser) httpSession.getAttribute("currentUser"),myClassification);
            httpSession.setAttribute("isSubClass",isSubClass);
        }
        return SUCCESS;
    }

    /**
     * 通过ajax获取页面的方法
     * @return 评论信息
     */
    public String comment(){
        List<Object[]> comments = CommentsService.getComments(page-1,pageSize,getArticleId(),getUserId());
        httpSession.setAttribute("comments",comments);
        httpSession.setAttribute("currentCommentPage",page);
        return "comment";
    }

    public void putComment(){
        pageService.putComment(getUserId(),getArticleId(),commentConent);
    }


    /**
     * 取消订阅
     */
    public void unSub(){
        PageService.unSub(getUserId(),classId);
    }

    /**
     * 订阅
     */
    public void sub(){
        PageService.sub(getUserId(),classId);
    }

    /**************get set方法******************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCommentConent() {
        return commentConent;
    }

    public void setCommentConent(String commentConent) {
        this.commentConent = commentConent;
    }
}
