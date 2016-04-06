package adminservice;

import domain.MyArticle;
import domain.MyUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.MyLog;

import java.util.List;

/**
 * Created by sangzhenya on 2016/4/5.
 */
public class AdminUserService {
    public static List<MyUser> getUser(int pageNo, int pageSize){
        Session session = null;
        Transaction tx = null;
        List<MyUser> myUsers;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            myUsers = session.createQuery("from MyUser order by user_id desc ")
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults(pageSize)
                    .list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return myUsers;
    }

    public static long getUserCounts(){
        Session session = null;
        Transaction tx = null;
        Number counts;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            counts = (Number) session.createQuery("select count(*) from MyUser ").uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        MyLog.Log("AdminCommentService",counts.toString());
        return counts.longValue();
    }

    public static void deleteItem(int id){
        //XINTODO
        /**
         *  等待user表中添加了isdelete选项后，添加该方法，设置isdelete的值为1
         */
    }
}
