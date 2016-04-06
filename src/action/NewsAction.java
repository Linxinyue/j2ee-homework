package action;

import com.opensymphony.xwork2.ActionSupport;
import domain.MyUser;
import org.apache.struts2.ServletActionContext;
import service.MyArticleService;
import service.MyClassifcationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by sangzhenya on 2016/3/29.
 */
public class NewsAction extends ActionSupport{
    private int page = 1;

    private  int pageSize = 15;
    private int pageNo = 0;
    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();

    public String index(){
        getClassArticle("index",-1);
        return SUCCESS;
    }

    public String remen(){
        getClassArticle("remen",1);
        return SUCCESS;
    }
    public String shehui(){
        getClassArticle("shehui",2);
        return SUCCESS;
    }
    public String yule(){
       getClassArticle("yule",3);
        return SUCCESS;
    }
    public String keji(){
        getClassArticle("keji",4);
        return SUCCESS;
    }
    public String tiyu(){
        getClassArticle("tiyu",5);
        return SUCCESS;
    }
    public String caijing(){
        getClassArticle("caijing",6);
        return SUCCESS;
    }
    public String junshi(){
        getClassArticle("junshi",7);
        return SUCCESS;
    }
    public String guoji(){
        getClassArticle("guoji",8);
        return SUCCESS;
    }
    public String shishang(){
        getClassArticle("shishang",9);
        return SUCCESS;
    }
    public String tansuo(){
        getClassArticle("tansuo",10);
        return SUCCESS;
    }
    public String meiwen(){
       getClassArticle("meiwen",11);
        return SUCCESS;
    }
    public String lishi(){
        getClassArticle("lishi",12);
        return SUCCESS;
    }
    public String gushi(){
        getClassArticle("gushi",13);
        return SUCCESS;
    }
    public String youxi(){
        getClassArticle("youxi",14);
        return SUCCESS;
    }



    private void getClassArticle(String className,int classId){
        if (page > 0){
            pageNo = page-1;
        }
        //分类名称
        httpSession.setAttribute("className",className);
        //当前页码
        httpSession.setAttribute("currentPage",page);
        //总页面数量
        int pageCounts = MyArticleService.pageCounts(pageSize,classId);
        httpSession.setAttribute("pageCounts",pageCounts);
        //设置分类和用户名称
        List<Object[]> articles;
        List<Object> myClassifications;
        List<Object[]> realComments;
        if (httpSession.getAttribute("currentUser")!=null){
            MyUser myUser = (MyUser) httpSession.getAttribute("currentUser");
            articles = MyArticleService.findByPageClass(pageNo,pageSize,classId,myUser);
            myClassifications = MyClassifcationService.getUserClasses(myUser);
            realComments = MyArticleService.getBestComment(myUser.getUser_id());
        }else{
            articles = MyArticleService.findByPageClass(pageNo,pageSize,classId,null);
            myClassifications = MyClassifcationService.getUserClasses(null);
            realComments = MyArticleService.getBestComment(-1);
        }
        httpSession.setAttribute("articles", articles);
        httpSession.setAttribute("myClassifications",myClassifications);
        //时事新闻
        List<Object[]> realTitles = MyArticleService.getRealTitles();
        httpSession.setAttribute("realTitles",realTitles);
        //最佳评论
        httpSession.setAttribute("realComments", realComments);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
