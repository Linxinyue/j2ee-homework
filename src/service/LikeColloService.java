package service;

import domain.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

/**
 * Created by sangzhenya on 2016/4/1.
 */
public class LikeColloService {
    public static void like(int user_id, int article_id){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            List<ArticleLike> articleLikes = session.createQuery("from ArticleLike a " +
                    "where a.myArticle.article_id = :article_id " +
                    "and a.myUser.user_id = :user_id")
                    .setInteger("article_id",article_id)
                    .setInteger("user_id",user_id)
                    .list();

            if (articleLikes == null || articleLikes.size() == 0){
                ArticleLike articleLike = new ArticleLike();
                articleLike.setMyArticle(session.get(MyArticle.class,article_id));
                articleLike.setMyUser(session.get(MyUser.class,user_id));
                session.save(articleLike);
                System.out.println("yicunchu");
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
    public static void dislike(int user_id, int article_id){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            List<ArticleDisLike> articleDisLikes = session.createQuery("from ArticleDisLike a " +
                    "where a.myArticle.article_id = :article_id " +
                    "and a.myUser.user_id = :user_id")
                    .setInteger("article_id",article_id)
                    .setInteger("user_id",user_id)
                    .list();

            if (articleDisLikes == null || articleDisLikes.size() == 0){
                ArticleDisLike articleDisLike = new ArticleDisLike();
                articleDisLike.setMyArticle(session.get(MyArticle.class,article_id));
                articleDisLike.setMyUser(session.get(MyUser.class,user_id));
                session.save(articleDisLike);
//                System.out.println("yicunchu");
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

    public static void collo(int user_id, int article_id){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            List<MyCollocation> myCollocations = session.createQuery("from MyCollocation a " +
                    "where a.myArticle.article_id = :article_id " +
                    "and a.myUser.user_id = :user_id")
                    .setInteger("article_id",article_id)
                    .setInteger("user_id",user_id)
                    .list();

            if (myCollocations == null || myCollocations.size() == 0){
                MyCollocation myCollocation = new MyCollocation();
                myCollocation.setMyArticle(session.get(MyArticle.class,article_id));
                myCollocation.setMyUser(session.get(MyUser.class,user_id));
                session.save(myCollocation);
//                System.out.println("yicunchu");
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

    public static void comment(int user_id, int comment_id){


        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            List<CommentLike> commentLikes = session.createQuery("from CommentLike cl " +
                    "where cl.myUser.user_id = :userId " +
                    "and cl.myComment.comment_id = :commentId")
                    .setInteger("userId",user_id)
                    .setInteger("commentId",comment_id)
                    .list();

            if (commentLikes == null || commentLikes.size() == 0){
                CommentLike commentLike = new CommentLike();
                commentLike.setMyComment(session.get(MyComment.class,comment_id));
                commentLike.setMyUser(session.get(MyUser.class,user_id));
                session.save(commentLike);

                System.out.println(comment_id+":::::"+user_id);
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

}
