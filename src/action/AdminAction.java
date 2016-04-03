package action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by sangzhenya on 2016/4/3.
 */
public class AdminAction extends ActionSupport {
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    public String index(){
        return "index";
    }
    public String article(){
        return "article";
    }
    public String user(){
        return "user";
    }
    public String comment(){
        return "comment";
    }

    public String articleAdd(){
        return "articleAdd";
    }
    public String commentAdd(){
        return "commentAdd";
    }

    public String articleDel(){
        return "delete";
    }
    public String commentDel(){
        return "delete";
    }
    public String userDel(){
        return "delete";
    }
}
