package adminaction;

import adminservice.AdminArticleService;
import adminservice.AdminUserService;
import com.opensymphony.xwork2.ActionSupport;
import domain.MyArticle;
import domain.MyUser;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by sangzhenya on 2016/4/5.
 */
public class UserAction extends ActionSupport {
    private int page;

    private int ids;

    private static final int pageSize = 15;

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();

    public String getUsers(){
        List<MyUser> myUsers = AdminUserService.getUser(page - 1, pageSize);
        long counts = AdminUserService.getUserCounts();
        httpSession.setAttribute("myUsers",myUsers);
        long pages = counts / pageSize;
        if (counts % pageSize != 0){
            pages = pages + 1;
        }
        httpSession.setAttribute("pages",pages);
        httpSession.setAttribute("indexPage",page);
        return SUCCESS;
    }

    public String deleteItem(){
        if (ids != -1){
            AdminUserService.deleteItem(ids);
            page = (int)httpSession.getAttribute("indexPage");
            List<MyUser> myUsers = AdminUserService.getUser(page-1, pageSize);
            long counts = AdminUserService.getUserCounts();
            httpSession.setAttribute("adminArticle",myUsers);
            long pages = counts / pageSize;
            if (counts % pageSize != 0){
                pages = pages + 1;
            }
            httpSession.setAttribute("pages",pages);
            httpSession.setAttribute("indexPage",page);
        }
        return SUCCESS;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }
}
