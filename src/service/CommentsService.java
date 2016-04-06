package service;

import domain.CommentLike;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangz on 4/1/2016.
 */
public class CommentsService {
    public static List<Object[]> getComments(int pageNo, int pageSize, int articleId, int userId){
        Session session = null;
        Transaction tx = null;
        List<Object[]> commentRela = new ArrayList<>();

        List<Object[]> comments = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            comments = session.createQuery("select u.user_id, c.comment_id, c.content, c.coment_date, u.name " +
                    "from MyComment c, MyUser u " +
                    "where c.myArticle.article_id = :article_id " +
                    "and c.myUser.user_id = u.user_id " +
                    "order by c.comment_id desc ")
                    .setInteger("article_id",articleId)
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults(pageSize)
                    .list();
            if(comments != null){
                for (int i = 0; i < comments.size() ; i++) {
                    Object[] objects = new Object[7];
                    Object object = session.createQuery("select count(cl.myComment.comment_id) " +
                            "from CommentLike cl " +
                            "where cl.myComment.comment_id = :comment_id " +
                            "group by cl.myComment.comment_id")
                            .setInteger("comment_id",Integer.parseInt(comments.get(i)[1].toString()))
                            .uniqueResult();



                    objects[0] = comments.get(i)[0];
                    objects[1] = comments.get(i)[1];
                    objects[2] = comments.get(i)[2];
                    objects[3] = comments.get(i)[3];
                    objects[4] = comments.get(i)[4];
                    if (object != null)
                        objects[5] = object;
                    else
                        objects[5] = 0;

                    if (userId != -1){
                        List<CommentLike> commentLikes =  session.createQuery("from CommentLike cl " +
                                "where cl.myUser.user_id = :userId " +
                                "and cl.myComment.comment_id = :commentId")
                                .setInteger("userId",userId)
                                .setInteger("commentId",Integer.parseInt(comments.get(i)[1].toString()))
                                .list();
                        if (commentLikes != null && commentLikes.size() > 0)
                            objects[6] = 1;
                        else
                            objects[6] = 0;
                    }

                    commentRela.add(objects);
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
        /*for (Object[] object:commentRela){
            System.out.println(object[0].toString());
            System.out.println(object[1].toString());
            System.out.println(object[2].toString());
            System.out.println(object[3].toString());
            System.out.println(object[4].toString());
            System.out.println(object[5].toString());
            System.out.println("-------------------------------");
        }*/
        return commentRela;
    }

    public static int getCommentsCounts(int articleId){
        Session session = null;
        Transaction tx = null;

        Number commentCount;

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            commentCount = (Number)session.createQuery("" +
                    "select count(c.myArticle.id) " +
                    "from MyComment c " +
                    "where c.myArticle.article_id = :article_id " +
                    "group by c.myArticle.id")
                    .setInteger("article_id",articleId)
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
        if (commentCount != null)
            return commentCount.intValue();
        return 0;
    }


}
