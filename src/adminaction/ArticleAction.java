package adminaction;

import adminservice.AdminArticleService;
import com.opensymphony.xwork2.ActionSupport;
import domain.MyArticle;
import org.apache.struts2.ServletActionContext;
import util.MyLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sangzhenya on 2016/4/5.
 */
public class ArticleAction extends ActionSupport{
    private String title;
    private String author;
    private String date;
    private String abstracts;
    private String content;
    private int classification;

    private int ids = -1;

    private int page;

    private static final int pageSize = 15;

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpSession httpSession = request.getSession();

    private Calendar calendar = Calendar.getInstance();

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
        AdminArticleService.add(title,author,content,abstracts,classification,mydate);
    }

    public String getArticle(){
        List<MyArticle> myArticles = AdminArticleService.getArticle(page - 1, pageSize);
        long counts = AdminArticleService.getArticleCounts();
        httpSession.setAttribute("adminArticle",myArticles);
        long pages = counts / pageSize;
        if (counts % pageSize != 0){
            pages = pages + 1;
        }
        httpSession.setAttribute("pages",pages);
        httpSession.setAttribute("indexPage",page);
        return SUCCESS;
    }

    public String delete(){
        if(ids != -1){
            AdminArticleService.deleteItem(ids);
            page = (int) httpSession.getAttribute("indexPage");
            List<MyArticle> myArticles = AdminArticleService.getArticle(page - 1, pageSize);
            long counts = AdminArticleService.getArticleCounts();
            httpSession.setAttribute("adminArticle",myArticles);
            long pages = counts / pageSize;
            if (counts % pageSize != 0){
                pages = pages + 1;
            }
            httpSession.setAttribute("pages",pages);
            httpSession.setAttribute("indexPage",page);
        }
        return SUCCESS;
    }


    public String getModArticle(){
        if ( ids != -1){
            MyArticle myArticle = AdminArticleService.getArticleMod(ids);
            MyLog.Log("ArticleAction",myArticle.getPub_date());
            httpSession.setAttribute("modArticle",myArticle);
        }
        return "modifi";
    }

    public void update(){
        Date mydate;
        try {
            String[] dates = date.split("-");
            calendar.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1
                    ,Integer.parseInt(dates[2]));
            mydate = calendar.getTime();
        }catch (Exception ex){
            ex.printStackTrace();
            mydate = calendar.getTime();
        }
        AdminArticleService.updateMyArticle(ids,title,author,content,abstracts,classification,mydate);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
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
