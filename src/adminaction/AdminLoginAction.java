package adminaction;

import admindomain.MyAdmin;
import adminservice.AdminLoginService;
import com.opensymphony.xwork2.ActionSupport;
import domain.MyUser;
import org.apache.struts2.ServletActionContext;
import service.LoginAndRegisterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sangzhenya on 2016/4/4.
 */
public class AdminLoginAction extends ActionSupport{
    private String adminName;
    private String adminPassword;
    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();


    public void login() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        if (httpSession.getAttribute("currentAdmin")!=null){
            try {
                response.getWriter().write("<script>location.href='admin/admin_index'</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ;
        }
        if(adminName!=null&&adminPassword!=null){
            MyAdmin myAdmin  = AdminLoginService.loginNow(adminName,adminPassword);
            if(myAdmin!=null){
                httpSession.setAttribute("currentAdmin",myAdmin);
                try {
                    response.getWriter().write("success");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    response.getWriter().write("error");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            try {
                response.getWriter().write("<script>location.href='admin/admin_login'</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
