package adminservice;

import admindomain.CommentCount;
import domain.CommentLike;
import domain.MyArticle;
import domain.MyComment;
import domain.MyUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.MyLog;

import java.util.Date;
import java.util.List;

/**
 * Created by sangzhenya on 2016/4/5.
 */
public class AdminCommentService {
    public static List<Object[]> getComments(int pageNo, int pageSize){
        Session session = null;
        Transaction tx = null;
        List<Object[]> objects;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            objects = session.createQuery(" " +
                    "select mc.comment_id, mc.content, my.name, ma.title, mc.coment_date, ma.article_id, my.user_id " +
                    "from MyComment mc, MyUser my, MyArticle ma " +
                    "where mc.myArticle.article_id = ma.article_id " +
                    "and mc.myUser.user_id = my.user_id " +
                    "order by comment_id desc ")
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
        return objects;
    }

    public static long getCommentCounts(){
        Session session = null;
        Transaction tx = null;
        Number counts;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            counts = (Number) session.createQuery("select count(*) from MyComment ").uniqueResult();

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
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            List<CommentLike> commentLikes = session.createQuery("from CommentLike where myComment.comment_id = :id").setInteger("id",id).list();
            for(CommentLike commentLike:commentLikes){
                session.delete(commentLike);
            }

            List<CommentCount> commentCounts = session.createQuery("from CommentCount where myComment.comment_id = :id").setInteger("id", id).list();
            for (CommentCount commentCount:commentCounts){
                session.delete(commentCount);
            }

            session.delete(session.get(MyComment.class,id));

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }


    public static String getArticleTitle(int id){
        Session session = null;
        Transaction tx = null;
        String artitlceTitle;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            try {
                artitlceTitle = session.get(MyArticle.class,id).getTitle();
            } catch (Exception e) {
//                e.printStackTrace();
                artitlceTitle = " ";
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return artitlceTitle;
    }

    public static String getUserName(int id){
        Session session = null;
        Transaction tx = null;
        String userName;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            try {
                userName = session.get(MyUser.class,id).getName();
            } catch (Exception e) {
//                e.printStackTrace();
                userName = " ";
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return userName;
    }

    public static void addCommnet(int articleId, int userId, Date date, String conent){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MyComment myComment = new MyComment();

            myComment.setComent_date(date);
            myComment.setContent(conent);
            myComment.setMyUser(session.get(MyUser.class,userId));
            myComment.setMyArticle(session.get(MyArticle.class,articleId));

            session.save(myComment);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
