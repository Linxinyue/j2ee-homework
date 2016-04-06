package service;

import domain.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

/**
 * Created by sangzhenya on 2016/3/30.
 */
public class PageService {

    /**
     * page页面获取文章的信息
     *
     * @param id
     * @return
     */
    public MyArticle getArticle(int id) {
        Session session = null;
        Transaction tx = null;
        MyArticle article = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            article = (MyArticle) session.createQuery("from MyArticle where article_id = :id")
                    .setInteger("id", id)
                    .uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return article;
    }

    /**
     * 获取文章所属的分类
     *
     * @param id
     * @return
     */
    public MyClassification getClassName(int id) {
        Session session = null;
        Transaction tx = null;
        MyClassification myClassification = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            myClassification = (MyClassification) session.createQuery("select c from MyArticle a, MyClassification c " +
                    "where a.article_id = :id and " +
                    "a.myClassification.classification_id = c.classification_id")
                    .setInteger("id", id)
                    .uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return myClassification;
    }


    /**
     * 取消订阅
     * @param userId
     * @param classId
     */
    public static void unSub(int userId, int classId) {
        Session session = null;
        Transaction tx = null;
        List<MySubscribe> mySubscribes;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            mySubscribes = session.createQuery("" +
                    "from MySubscribe ms " +
                    "where ms.myUser.user_id = :userId " +
                    "and ms.myClassification.classification_id = :classId")
                    .setInteger("userId", userId)
                    .setInteger("classId", classId)
                    .list();
            if (mySubscribes != null && mySubscribes.size() != 0) {
                for (MySubscribe mySubscribe : mySubscribes) {
                    session.delete(mySubscribe);
                }
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
    }

    /**
     * 添加订阅
     * @param userId
     * @param classId
     */
    public static void sub(int userId, int classId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MySubscribe mySubscribe = new MySubscribe();
            mySubscribe.setMyClassification(session.get(MyClassification.class,classId));
            mySubscribe.setMyUser(session.get(MyUser.class,userId));
            session.save(mySubscribe);

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

    public static void putComment(int userId, int articleId, String content){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MyComment myComment = new MyComment();
            myComment.setMyArticle(session.get(MyArticle.class,articleId));
            myComment.setMyUser(session.get(MyUser.class,userId));
            myComment.setContent(content);
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
