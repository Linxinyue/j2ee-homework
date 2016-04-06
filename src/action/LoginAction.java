package action;

import com.opensymphony.xwork2.ActionSupport;
import domain.MyUser;
import org.apache.struts2.ServletActionContext;
import service.LoginAndRegisterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sangzhenya on 2016/3/26.
 */
public class LoginAction extends ActionSupport{
    private String realPath;
    private String name;
    private String password;
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession httpSession = request.getSession();

    public void login(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        if(name!=null&&password!=null){
            MyUser myUser  = LoginAndRegisterService.loginNow(name,password);
            if(myUser!=null){
                httpSession.setAttribute("currentUser",myUser);
                httpSession.setAttribute("realPath",realPath);
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
        }
    }

    public String logout(){
        httpSession.invalidate();
        return "loginOut";
    }

    public String prePage(){
        System.out.println(httpSession.getAttribute("realPath").toString());
        return "prePage";
    }


    public void register(){
        LoginAndRegisterService.register(name,password);
    }
    public String toregister(){
        return "register";
    }
    public void checkName() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(LoginAndRegisterService.getCheck(name));
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

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
}
