package action;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.deploy.net.HttpRequest;
import domain.Media;
import org.apache.struts2.ServletActionContext;
import service.MediaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by sangzhenya on 2016/3/29.
 */
public class MoreAction extends ActionSupport{
    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();

    public String about(){
        Media media = MediaService.getMedia(1);
        httpSession.setAttribute("media",media);
//        System.out.println(media.getConent());
        return SUCCESS;
    }
    public String contact(){
        Media media = MediaService.getMedia(2);
        httpSession.setAttribute("media",media);
        return SUCCESS;
    }
    public String agreenment(){
        Media media = MediaService.getMedia(3);
        httpSession.setAttribute("media",media);
        return SUCCESS;
    }
    public String complaint(){
        Media media = MediaService.getMedia(4);
        httpSession.setAttribute("media",media);
        return SUCCESS;
    }
    public String cooperate(){
        Media media = MediaService.getMedia(5);
        httpSession.setAttribute("media",media);
        return SUCCESS;
    }

}
