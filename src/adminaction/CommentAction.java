package adminaction;

import adminservice.AdminArticleService;
import adminservice.AdminCommentService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import util.MyLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sangzhenya on 2016/4/5.
 */
public class CommentAction extends ActionSupport {
    private int ids = -1;
    private int page = -1;

    private int userId;
    private String date;
    private String content;

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();

    private final static int pageSize =  15;

    private Calendar calendar = Calendar.getInstance();


    public String getComment(){
        List<Object[]> myComments = AdminCommentService.getComments(page-1, pageSize);
        long counts = AdminCommentService.getCommentCounts();
        httpSession.setAttribute("myComments",myComments);
        long pages = counts / 15;
        if (counts % 15 != 0){
            pages = pages + 1;
        }
        httpSession.setAttribute("pages",pages);
        httpSession.setAttribute("indexPage", page);
        MyLog.Log("CommentAction",pages);
        return SUCCESS;
    }


    public String delete(){
        if(ids != -1){
            AdminCommentService.deleteItem(ids);
            page = (int) httpSession.getAttribute("indexPage");
            List<Object[]> myComments = AdminCommentService.getComments(page - 1, pageSize);
            long counts = AdminCommentService.getCommentCounts();
            httpSession.setAttribute("myComments",myComments);
            long pages = counts / pageSize;
            if (counts % pageSize != 0){
                pages = pages + 1;
            }
            httpSession.setAttribute("pages",pages);
            httpSession.setAttribute("indexPage",page);
        }
        return SUCCESS;
    }

    public void getArticleTtitle(){
        if (ids != -1){
            String articleTitle = AdminCommentService.getArticleTitle(ids);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            if (articleTitle != null){
                try {
                    response.getWriter().write(articleTitle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    response.getWriter().write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getUserName(){
        if (ids != -1){
            String userName = AdminCommentService.getUserName(ids);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            if (userName != null){
                try {
                    response.getWriter().write(userName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    response.getWriter().write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void add(){
        Date mydate;
        try {
            String[] dates = date.split("/");
            calendar.set(Integer.parseInt(dates[2]),Integer.parseInt(dates[1]) - 1
                    ,Integer.parseInt(dates[0]));
            mydate = calendar.getTime();
        }catch (Exception ex){
            ex.printStackTrace();
            mydate = calendar.getTime();
        }
        AdminCommentService.addCommnet(ids,userId,mydate,content);
    }


    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
