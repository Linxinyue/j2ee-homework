package service;

import domain.CommentLike;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangzhenya on 2016/3/29.
 */
public class MyArticleService {

    /**
     * index页面获取文章数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static List<Object[]> findByPage(int pageNo, int pageSize){
        Session session = null;
        Transaction tx = null;
        List<Object[]> myArticles;

        List<Object[]> articles = new ArrayList<>();

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            myArticles = session.createQuery("select a.article_id, a.title,a.abstracts, a.article_pic,a.author,a.pub_date from MyArticle a order by article_id desc ")
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults(pageSize)
                    .list();

            for (int i = 0; i < myArticles.size(); i++) {
                Object[] objects = new Object[10];
                Object obj0 = session.createQuery("select count(c.myArticle.article_id) " +
                        "from MyComment c " +
                        "where c.myArticle.article_id =:article_id " +
                        "group by c.myArticle.article_id ")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj1 = session.createQuery("select count(a.myArticle.article_id) from ArticleLike a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj2 = session.createQuery("select count(a.myArticle.article_id) from ArticleDisLike a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj3 = session.createQuery("select count(a.myArticle.article_id) from MyCollocation a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                objects[0] = myArticles.get(i)[0];
                objects[1] = myArticles.get(i)[1];
                objects[2] = myArticles.get(i)[2];
                objects[3] = myArticles.get(i)[3];
                objects[4] = myArticles.get(i)[4];
                objects[5] = myArticles.get(i)[5];

                if (obj0 != null){
                    objects[6] = obj0;
                }else {
                    objects[6] = 0;
                }

                if (obj0 != null){
                    objects[6] = obj0;
                }else {
                    objects[6] = 0;
                }

                if (obj1 != null){
                    objects[7] = obj1;
                }else {
                    objects[7] = 0;
                }

                if (obj2 != null){
                    objects[8] = obj2;
                }else {
                    objects[8] = 0;
                }

                if (obj3 != null){
                    objects[9] = obj3;
                }else {
                    objects[9] = 0;
                }
                articles.add(objects);
            }
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        return articles;
    }

    /**
     * 获取总的页面数量
     * @param pageSize
     * @return
     */
    public static int pageCounts(int pageSize){
        Session session = null;
        Transaction tx = null;
        Number pages = 0;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            pages = (Number)session.createQuery("select max(a.article_id) from MyArticle a").uniqueResult();
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        int page = pages.intValue();
        if (page%pageSize==0)
            return page/pageSize;
        return page/pageSize+1;
    }


    /**************下面是非index页面获取各种数据的函数*****************/

    /**
     * 获取使用类编号，获取文章信息
     * @param pageNo
     * @param pageSize
     * @param classid
     * @return
     */
    public static List<Object[]> findByPageClass(int pageNo, int pageSize,int classid){
        Session session = null;
        Transaction tx = null;
        List<Object[]> myArticles;

        List<Object[]> articles = new ArrayList<>();

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            myArticles = session.createQuery("select a.article_id, a.title,a.abstracts, a.article_pic,a.author,a.pub_date " +
                    "from MyArticle a " +
                    "where a.myClassification.classification_id = :classId " +
                    "order by article_id desc ")
                    .setInteger("classId",classid)
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults(pageSize)
                    .list();

            for (int i = 0; i < myArticles.size(); i++) {
                Object[] objects = new Object[10];
                Object obj0 = session.createQuery("select count(c.myArticle.article_id) " +
                        "from MyComment c " +
                        "where c.myArticle.article_id =:article_id " +
                        "group by c.myArticle.article_id ")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj1 = session.createQuery("select count(a.myArticle.article_id) from ArticleLike a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj2 = session.createQuery("select count(a.myArticle.article_id) from ArticleDisLike a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj3 = session.createQuery("select count(a.myArticle.article_id) from MyCollocation a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                objects[0] = myArticles.get(i)[0];
                objects[1] = myArticles.get(i)[1];
                objects[2] = myArticles.get(i)[2];
                objects[3] = myArticles.get(i)[3];
                objects[4] = myArticles.get(i)[4];
                objects[5] = myArticles.get(i)[5];

                if (obj0 != null){
                    objects[6] = obj0;
                }else {
                    objects[6] = 0;
                }

                if (obj0 != null){
                    objects[6] = obj0;
                }else {
                    objects[6] = 0;
                }

                if (obj1 != null){
                    objects[7] = obj1;
                }else {
                    objects[7] = 0;
                }

                if (obj2 != null){
                    objects[8] = obj2;
                }else {
                    objects[8] = 0;
                }

                if (obj3 != null){
                    objects[9] = obj3;
                }else {
                    objects[9] = 0;
                }
                articles.add(objects);
            }
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        return articles;
    }

    /**
     * 使用类编号获取文章评论数量
     * @param pageNo
     * @param pageSize
     * @param classid
     * @return
     */
    public List<Long> commentCountsClass(int pageNo, int pageSize, int classid){
        Session session = null;
        Transaction tx = null;
        List<Long> longs = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            longs = session.createQuery(" select count(a.article_id) " +
                    "from MyArticle a, MyComment c " +
                    "where a.article_id = c.myArticle.article_id and a.myClassification.classification_id = :classid " +
                    "group by a.article_id")
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults(pageSize)
                    .setInteger("classid",classid)
                    .list();

            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        return longs;
    }

    /**
     * 使用类编号获取当前栏目的页面总数量
     * @param pageSize
     * @param classid
     * @return
     */
    public int pageCounts(int pageSize, int classid){
        Session session = null;
        Transaction tx = null;
        Number pages = 0;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            pages = (Number)session.createQuery("select count(a.article_id) from MyArticle a where a.myClassification.classification_id = :classid")
                        .setInteger("classid",classid)
                        .uniqueResult();
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        int page = pages.intValue();
//        System.out.println(page);
        return page/pageSize+1;
    }


    /******************公共方法**********************/

    /**
     * 通用的获取实时热点
     * @return
     */
    public static List<Object[]> getRealTitles(){
        Session session = null;
        Transaction tx = null;
        List<Object[]> atrticleTitles = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            atrticleTitles = session.createQuery("select a.title, a.article_id from MyArticle a order by article_id desc")
                    .setMaxResults(5)
                    .list();
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        return atrticleTitles;
    }

    /**
     *获取点赞最多的评论
     * @return
     */
    public static List<Object[]> getBestComment(int userId){
        Session session = null;
        Transaction tx = null;
        List<Object[]> numbers = null;
        List<Object[]> commentRelation = new ArrayList<>();

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            numbers = session.createQuery("select cl.myComment.comment_id, count(cl.myComment.comment_id)" +
                    "from CommentLike cl " +
                    "group by cl.myComment.comment_id order by count(cl.myComment.comment_id) desc")
                    .setMaxResults(3)
                    .list();

            for (int i = 0; i < 3; i++){
                Object[] objects = (Object[]) session.createQuery("select a.article_id, a.title, u.name,  c.content,  a.author,  c.comment_id, c.comment_id " +
                        "from MyUser u, MyArticle a, MyComment c " +
                        "where c.myUser.id = u.user_id and c.myArticle.article_id = a.article_id and c.comment_id = :commentId")
                        .setInteger("commentId",Integer.parseInt(numbers.get(i)[0].toString()))
                        .uniqueResult();
                objects[4] = numbers.get(i)[1];
                if (userId != -1){
                    CommentLike commentLike = (CommentLike) session.createQuery("from CommentLike cl " +
                            "where cl.myUser.user_id = :userId " +
                            "and cl.myComment.comment_id = :commentId")
                            .setInteger("userId",userId)
                            .setInteger("commentId",Integer.parseInt(numbers.get(i)[0].toString()))
                            .uniqueResult();
                    if (commentLike != null)
                        objects[6] = 1;
                    else
                        objects[6] = 0;
//                    System.out.println(userId+":::::"+numbers.get(i)[0].toString()+"::::"+objects[5].toString());
                }
                commentRelation.add(objects);
            }
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        return commentRelation;
    }


}
