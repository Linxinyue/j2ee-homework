package adminservice;

import admindomain.*;
import domain.MyArticle;
import domain.MyClassification;
import domain.MyComment;
import domain.MyUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sangzhenya on 2016/4/4.
 */
public class AdminCountService {

    /**
     * 登陆记录
     * @param myUser
     */
    public static void loginRecord(MyUser myUser){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            LoginCount loginCount = new LoginCount();
            loginCount.setMyUser(myUser);
            loginCount.setLogin_date(Calendar.getInstance().getTime());
            session.save(loginCount);

            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
    }

    /**
     * 注册记录
     * @param myUser
     */
    public static void registerRecord(MyUser myUser){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            RegisterCount registerCount = new RegisterCount();
            registerCount.setMyUser(myUser);
            registerCount.setRegister_date(Calendar.getInstance().getTime());
            session.save(registerCount);

            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
    }

    /**
     * 访问数量
     * @param myClassification
     */
    public static void requestRecord(MyClassification myClassification){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            RequestCount requestCount = new RequestCount();
            requestCount.setMyClassification(myClassification);
            requestCount.setRequest_date(Calendar.getInstance().getTime());
            session.save(requestCount);

            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
    }

    /**
     * 阅读数量
     * @param myArticle
     */
    public static void readRecord(MyArticle myArticle){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            ReadCount readCount = new ReadCount();
            readCount.setMyArticle(myArticle);
            readCount.setRead_date(Calendar.getInstance().getTime());
            session.save(readCount);

            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
    }

    public static void commentCount(MyComment myComment){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            CommentCount commentCount = new CommentCount();
            commentCount.setMyComment(myComment);
            commentCount.setLogin_date(Calendar.getInstance().getTime());
            session.save(commentCount);

            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
    }
}
