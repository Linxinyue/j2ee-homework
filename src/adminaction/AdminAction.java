package adminaction;

import adminservice.AdminArticleService;
import adminservice.AdminCommentService;
import adminservice.AdminService;
import adminservice.AdminUserService;
import com.opensymphony.xwork2.ActionSupport;
import domain.MyArticle;
import domain.MyComment;
import domain.MyUser;
import org.apache.struts2.ServletActionContext;
import util.MyLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by sangzhenya on 2016/4/3.
 */
public class AdminAction extends ActionSupport {
    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();

    private int ids;

    public String index() {
//        System.out.println("AdminAction:" + "here is 0");
        if (checkAdmin()) {
            long currentCount = AdminService.getCurrentCount();
            long dayCount = AdminService.getDayCount();
            long weekCount = AdminService.getWeekCount();
            long totalCount = AdminService.getTotalCount();
            List<Object> weekDay = AdminService.getWeekDay();

//            System.out.println(weekDay.size());

            httpSession.setAttribute("currentCount", currentCount);
            httpSession.setAttribute("dayCount", dayCount);
            httpSession.setAttribute("weekCount", weekCount);
            httpSession.setAttribute("totalCount", totalCount);
            httpSession.setAttribute("weekDay", weekDay);
            httpSession.setAttribute("currentIndex", 1);
//            System.out.println("AdminAction:" + "here is ");
            return "index";
        }
        return ERROR;
    }

    public String article() {
        if (checkAdmin()) {
            httpSession.setAttribute("currentIndex", 2);
            return "article";
        }
        return ERROR;
    }

    public String user() {
        if (checkAdmin()) {
            httpSession.setAttribute("currentIndex", 9);
            return "user";
        }
        return ERROR;
    }

    public String comment() {
        if (checkAdmin()) {
            System.out.println("AdminAction:6");
            httpSession.setAttribute("currentIndex", 6);
            return "comment";
        }
        return ERROR;
    }

    public String articleAdd() {
        if (checkAdmin()) {
            httpSession.setAttribute("currentIndex", 3);
            return "articleAdd";
        }
        return ERROR;
    }

    public String commentAdd() {
        if (checkAdmin()) {
            httpSession.setAttribute("currentIndex", 7);
            return "commentAdd";
        }
        return ERROR;
    }

    public String articleDel() {
        if (checkAdmin()) {
            httpSession.removeAttribute("myComments");
            httpSession.removeAttribute("myUsers");
            httpSession.setAttribute("currentIndex", 5);
            List<MyArticle> myArticles = AdminArticleService.getArticle(0,15);
            long counts = AdminArticleService.getArticleCounts();
            httpSession.setAttribute("adminArticle",myArticles);
            long pages = counts / 15;
            if (counts % 15 != 0){
                pages = pages + 1;
            }
            httpSession.setAttribute("pages",pages);
            httpSession.setAttribute("indexPage", 1);

            return "delete";
        }
        return ERROR;
    }

    public String commentDel() {
        if (checkAdmin()) {
            httpSession.removeAttribute("adminArticle");
            httpSession.removeAttribute("myUsers");
            httpSession.setAttribute("currentIndex", 8);
            List<Object[]> myComments = AdminCommentService.getComments(0,15);
            long counts = AdminCommentService.getCommentCounts();
            httpSession.setAttribute("myComments",myComments);
            long pages = counts / 15;
            if (counts % 15 != 0){
                pages = pages + 1;
            }
            httpSession.setAttribute("pages",pages);
            httpSession.setAttribute("indexPage", 1);
            return "delete";
        }
        return ERROR;
    }

    public String userDel() {
        if (checkAdmin()) {
            httpSession.removeAttribute("adminArticle");
            httpSession.removeAttribute("myComments");
            httpSession.setAttribute("currentIndex", 10);
            List<MyUser> myUsers = AdminUserService.getUser(0,15);
            long counts = AdminUserService.getUserCounts();
            httpSession.setAttribute("myUsers",myUsers);
            long pages = counts / 15;
            if (counts % 15 != 0){
                pages = pages + 1;
            }
            httpSession.setAttribute("pages",pages);
            httpSession.setAttribute("indexPage", 1);

            return "delete";
        }
        return ERROR;
    }

    public String articleMod(){
        if (checkAdmin()) {
            httpSession.setAttribute("currentIndex", 4);
            return "articleMod";
        }
        return ERROR;
    }

    private boolean checkAdmin() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        if (httpSession.getAttribute("currentAdmin") != null) {
            try {
                response.getWriter().write("<script>location.href='admin/admin_index'</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }
}
