package adminservice;

import admindomain.CommentCount;
import admindomain.ReadCount;
import domain.*;
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
public class AdminArticleService {
    public static void add(String title, String author, String content,
                           String abstracts, int classification, Date date){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MyArticle myArticle = new MyArticle();
            myArticle.setAuthor(author);
            myArticle.setTitle(title);
            myArticle.setContent(content);
            myArticle.setAbstracts(abstracts);
            myArticle.setMyClassification(session.get(MyClassification.class,classification));
            myArticle.setPub_date(date);

            session.save(myArticle);

            MyLog.Log("AdminArticleService","success");

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

    public static List<MyArticle> getArticle(int pageNo, int pageSize){
        Session session = null;
        Transaction tx = null;
        List<MyArticle> myArticles;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            myArticles = session.createQuery("from MyArticle order by article_id desc ")
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults(pageSize)
                    .list();

//            MyLog.Log("AdminArticleService","success");

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return myArticles;
    }

    public static long getArticleCounts(){
        Session session = null;
        Transaction tx = null;
        Number counts;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            counts = (Number) session.createQuery("select count(*) from MyArticle").uniqueResult();

//            MyLog.Log("AdminArticleService","success");

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return counts.longValue();
    }

    public static void deleteItem(int id) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            List<ArticleLike> articleLikes = session.createQuery("from ArticleLike a where a.myArticle.article_id = :id").setInteger("id", id).list();
            if (articleLikes != null) {
                for (ArticleLike article : articleLikes) {
                    session.delete(article);
                }
            }

            List<ArticleDisLike> articleDisLikes = session.createQuery("from ArticleDisLike d where d.myArticle.article_id = :id ").setInteger("id", id).list();
            if (articleDisLikes != null) {
                for (ArticleDisLike articleDisLike : articleDisLikes) {
                    session.delete(articleDisLike);
                }
            }

            List<MyCollocation> myCollocations = session.createQuery("from MyCollocation d where d.myArticle.article_id = :id ").setInteger("id", id).list();
            if (myCollocations != null) {
                for (MyCollocation myCollocation : myCollocations) {
                    session.delete(myCollocation);
                }
            }

            List<ReadCount> readCounts = session.createQuery("from ReadCount d where d.myArticle.article_id = :id ").setInteger("id", id).list();
            if (readCounts != null) {
                for (ReadCount readCount : readCounts) {
                    session.delete(readCount);
                }
            }

            List<MyComment> comments = session.createQuery("from MyComment d where d.myArticle.article_id = :id ").setInteger("id", id).list();
            if (comments != null) {
                for (MyComment comment : comments) {
                    List<CommentCount> commentCounts = session.createQuery("from CommentCount d where d.myComment.comment_id = :id ").setInteger("id", comment.getComment_id()).list();
                    for (CommentCount commentCount : commentCounts) {
                        session.delete(commentCount);
                    }
                    session.delete(comment);
                }
            }

            session.delete(session.get(MyArticle.class, id));

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

    public static MyArticle getArticleMod(int ids){
        Session session = null;
        Transaction tx = null;
        MyArticle myArticle = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

           myArticle = session.get(MyArticle.class, ids);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        if (myArticle != null)
            return myArticle;
        return null;
    }


    public static void updateMyArticle(int id, String title, String author, String content,
                                      String abstracts, int classification, Date date){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MyArticle myArticle1 = session.get(MyArticle.class,id);
            myArticle1.setAuthor(author);
            myArticle1.setTitle(title);
            myArticle1.setContent(content);
            myArticle1.setAbstracts(abstracts);
            myArticle1.setMyClassification(session.get(MyClassification.class,classification));
            myArticle1.setPub_date(date);

            session.update(myArticle1);

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
