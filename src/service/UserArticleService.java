package service;

import domain.MyUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangzhenya on 2016/3/30.
 */
public class UserArticleService {

    public static List<Object[]> findByPage(int pageNo, int pageSize,int classId, MyUser myUser){
//        System.out.println(myUser.getUser_id());
        Session session = null;
        Transaction tx = null;
        List<Object[]> myArticles;

        List<Object[]> articles = new ArrayList<>();

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            if (classId != 0){
                myArticles = session.createQuery("" +
                        "select a.article_id, a.title,a.abstracts, a.article_pic,a.author,a.pub_date " +
                        "from MyArticle a " +
                        "where a.myClassification.classification_id = :classId " +
                        "order by article_id desc ")
                        .setInteger("classId",classId)
                        .setFirstResult(pageNo*pageSize)
                        .setMaxResults(pageSize)
                        .list();
            }else {
                myArticles = session.createQuery("" +
                        "select a.article_id, a.title,a.abstracts, a.article_pic,a.author,a.pub_date " +
                        "from MyArticle a " +
                        "order by article_id desc ")
                        .setFirstResult(pageNo*pageSize)
                        .setMaxResults(pageSize)
                        .list();
            }

            for (int i = 0; i < myArticles.size(); i++) {
                Object[] objects = new Object[13];
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

                List<Object> obj4 = session.createQuery("from ArticleLike a " +
                        "where a.myUser.user_id = :userId " +
                        "and a.myArticle.article_id = :article_id")
                        .setInteger("userId",myUser.getUser_id())
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .list();

                List<Object> obj5 = session.createQuery("from ArticleDisLike a " +
                        "where a.myUser.user_id = :userId " +
                        "and a.myArticle.article_id = :article_id")
                        .setInteger("userId",myUser.getUser_id())
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .list();

                List<Object> obj6 = session.createQuery("from MyCollocation a " +
                        "where a.myUser.user_id = :userId " +
                        "and a.myArticle.article_id = :article_id")
                        .setInteger("userId",myUser.getUser_id())
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .list();

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
                if (obj4 != null && obj4.size() != 0){
                    objects[10] = 1;
                }else {
                    objects[10] = 0;
                }
                if (obj5 != null && obj5.size() != 0){
                    objects[11] = 1;
                }else {
                    objects[11] = 0;
                }
                if (obj6 != null && obj6.size() != 0){
                    objects[12] = 1;
                }else {
                    objects[12] = 0;
                }

                articles.add(objects);
            /*    if (obj0 != null)
                    System.out.println(obj0.toString());
                if (obj1 != null)
                    System.out.println(obj1.toString());
                if (obj2 != null)
                    System.out.println(obj2.toString());
                if (obj3 != null)
                    System.out.println(obj3.toString());
                System.out.println("-----------------------------");*/

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


        /*for (Object[] objs:articles){
            System.out.println("0::"+objs[0].toString());
            System.out.println("1::"+objs[1].toString());
            System.out.println("2::"+objs[2].toString());
            System.out.println("3::"+objs[3].toString());
            System.out.println("4::"+objs[4].toString());
            System.out.println("5::"+objs[5].toString());
            System.out.println("6::"+objs[6].toString());
            System.out.println("7::"+objs[7].toString());
            System.out.println("8::"+objs[8].toString());
            System.out.println("9::"+objs[9].toString());
            System.out.println("10::"+objs[10].toString());
            System.out.println("11::"+objs[11].toString());
            System.out.println("12::"+objs[12].toString());
            System.out.println("----------------------------------------");
        }*/
        return articles;
    }
}
