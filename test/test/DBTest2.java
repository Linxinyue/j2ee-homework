package test;

import admindomain.*;
import domain.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sangz on 4/1/2016.
 */
public class DBTest2 {
    @Test
    public void save2mycomment() {
        List<String> comments = new ArrayList<>();
        comments.add("致敬王勇平！既履行了职责又守住了做人的底线！不明白为什么当时那么多人指责他？难道就听不出来那些话的意思吗？");
        comments.add("在中国要做好新闻发言人，好难的c");
        comments.add("在中国要做好新闻发言人，好难的c");
        comments.add("房子能拉动的装修家具灯具钢筋汽车石材家电等生意，别尼玛在那手插双腰在那喊这个国家房子怎么怎么有希望，有你这样的喷子有希望才怪！现在租房都头疼了");
        comments.add("平均1干万的房子3百多套1天抢光纯属炒作。");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 3, 4);
        Date date = calendar.getTime();

        for (int i = 1; i < 134; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                int commentIndex = (int) (1 + Math.random() * 5);
//                System.out.println(commentIndex);
                MyComment myComment = new MyComment("comment" + i + ":::" + comments.get(commentIndex - 1), date);
                myComment.setMyUser(session.get(MyUser.class, 1));
                myComment.setMyArticle(session.get(MyArticle.class, 1));
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

    @Test
    public void getCommentsCounts(){
        int articleId = 1;
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
        System.out.print(commentCount);
//        return commentCount.intValue();
    }




    @Test
    public void testLike(){
        Session session = null;
        Transaction tx = null;
        int article_id = 1878;
        int user_id = 1;
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
    @Test
    public void testGetClassId(){
        Session session = null;
        Transaction tx = null;
        MyClassification myClassification = null;
        int id = 1878;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            myClassification = (MyClassification) session.createQuery("select c from MyArticle a, MyClassification c " +
                    "where a.article_id = :id and " +
                    "a.myClassification.classification_id = c.classification_id")
                    .setInteger("id",id)
                    .uniqueResult();
            System.out.println(myClassification.getClassification_name()+"---------");
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

    @Test
    public void testDelete(){
        Session session = null;
        Transaction tx = null;
        List<MySubscribe> mySubscribes;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            mySubscribes = session.createQuery("" +
                    "from MySubscribe ms " +
                    "where ms.myUser.user_id = :userId " +
                    "and ms.myClassification.classification_id = :classId")
                    .setInteger("userId",1)
                    .setInteger("classId",8)
                    .list();
            if (mySubscribes != null && mySubscribes.size() != 0){
                for (MySubscribe mySubscribe : mySubscribes){
                    session.delete(mySubscribe);
                }
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
    }


    @Test
    public void save2myadmin(){
        Session session = null;
        Transaction tx = null;
        List<MySubscribe> mySubscribes;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MyAdmin myAdmin = new MyAdmin("xinyue@xinyue.com","xinyue","");
            session.save(myAdmin);

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

    @Test
    public void setDate(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(date);
    }

    @Test
    public  void register(){
        String name = "xixi";
        String password = "haha";
        Session session = null;
        Transaction tx = null;
        MyUser myUser = new MyUser();
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            myUser.setName(name);
            myUser.setPassword(password);
            session.save(myUser);

            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        System.out.println(myUser.getUser_id());
    }

    @Test
    public  void getCount(){
        Session session = null;
        Transaction tx = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,3,1);
        Date date0 = calendar.getTime();
        calendar.set(2016,4,1);
        Date date1 = calendar.getTime();
        List<RequestCount> requestCounts;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            requestCounts = session.createQuery("" +
                    "from RequestCount " +
                    "where request_date between :start and :end")
                    .setDate("start",date0)
                    .setDate("end",date1)
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
        System.out.println("date0:"+date0);
        System.out.println("date1:"+date1);

       /* for (RequestCount req:
                requestCounts) {
            System.out.println("-----------------------------");
            System.out.println(req.getRequest_date());
        }*/
        System.out.println(requestCounts.size());
    }
    @Test
    public  void save2request(){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Calendar calendar = Calendar.getInstance();

            for (int i = 0; i < 244; i++) {
                int day = (int)(Math.random()*7+25);
                calendar.set(2016,2,day);
                Date date = calendar.getTime();

                RequestCount requestCount = new RequestCount();
                int classid = (int)(Math.random()*14+1);
                requestCount.setMyClassification(session.get(MyClassification.class,1));
                requestCount.setRequest_date(date);
                session.save(requestCount);

                RegisterCount registerCount = new RegisterCount();
                int userId = (int)(Math.random()*100+1);
                registerCount.setMyUser(session.get(MyUser.class,userId));
                registerCount.setRegister_date(date);
                session.save(registerCount);

                LoginCount loginCount = new LoginCount();
                loginCount.setLogin_date(date);
                loginCount.setMyUser(session.get(MyUser.class,userId));
                session.save(loginCount);

                ReadCount readCount = new ReadCount();
                readCount.setRead_date(date);
                int articleId = (int)(Math.random()*1010+1);
                readCount.setMyArticle(session.get(MyArticle.class,articleId));

                CommentCount commentCount = new CommentCount();
                commentCount.setLogin_date(date);
                commentCount.setMyComment(session.get(MyComment.class,userId));
                session.save(commentCount);

                System.out.println(date);
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
    }

    @Test
    public  void getCount2(){
        Session session = null;
        Transaction tx = null;
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-7);
        Date date0 = calendar.getTime();
        Number number;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            number = (Number) session.createQuery("select count(*) " +
                    "from RequestCount " +
                    "where request_date between :start and :end")
                    .setDate("start",date0)
                    .setDate("end",date1)
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
        System.out.println("date0:"+date0);
        System.out.println("date1:"+date1);

        System.out.println(number.intValue());
    }

    @Test
    public void getWeekDay(){
        Session session = null;
        Transaction tx = null;
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();

        List<Object> numbers = new ArrayList<>();
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            for (int i = 0; i < 7; i++){
                calendar.set(year,month, day- i);
                Date date0 = calendar.getTime();
                Object tempNumber = session.createQuery("select count(request_date) " +
                        "from RequestCount " +
                        "where request_date between :start and :end " +
                        "group by request_date")
                        .setDate("start",date0)
                        .setDate("end",date0)
                        .uniqueResult();
                if (tempNumber != null){
                    numbers.add(tempNumber);
                    System.out.println(tempNumber.toString());
                }else {
                    numbers.add(0);
                }
                System.out.println(date0);
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
    }

    @Test
    public void testDeleteArticle(){
        Session session = null;
        Transaction tx = null;
        int id = 101;

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            List<ArticleLike> articleLikes = session.createQuery("from ArticleLike a where a.myArticle.article_id = :id").setInteger("id", id).list();

            if (articleLikes != null){
                for (ArticleLike article:articleLikes){
                    session.delete(article);
                }
            }

            List<ArticleDisLike> articleDisLikes = session.createQuery("from ArticleDisLike d where d.myArticle.article_id = :id ").setInteger("id", id).list();

            if (articleDisLikes != null){
                for (ArticleDisLike articleDisLike:articleDisLikes){
                    session.delete(articleDisLike);
                }
            }


            List<MyCollocation> myCollocations = session.createQuery("from MyCollocation d where d.myArticle.article_id = :id ").setInteger("id", id).list();

            if (myCollocations != null){
                for (MyCollocation myCollocation:myCollocations){
                    session.delete(myCollocation);
                }
            }

            List<ReadCount> readCounts = session.createQuery("from ReadCount d where d.myArticle.article_id = :id ").setInteger("id", id).list();

            if (readCounts != null){
                for (ReadCount readCount:readCounts){
                    session.delete(readCount);
                }
            }

            List<MyComment> comments = session.createQuery("from MyComment d where d.myArticle.article_id = :id ").setInteger("id", id).list();

            if (comments != null){
                for (MyComment comment:comments){
                    session.delete(comment);
                }
            }

            session.delete(session.get(MyArticle.class,id));
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

    @Test
    public void testDelteUser(){
        Session session = null;
        Transaction tx = null;

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

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
