package test;

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
    public void save2emdia(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Media media = new Media();
            media.setConent("<p>\n" +
                    "            《今日头条》支持蜘蛛协议（Robots Exclusion Protocol）“ToutiaoSpider”，同时，我们尊重所有的网络媒体，如媒体不希望内容被《今日头条》推荐，请及时邮件至\n" +
                    "\n" +
                    "            <a href=\"mailto:bd@toutiao.com\">bd@toutiao.com</a>\n" +
                    "\n" +
                    "            邮箱，或在网站页面中根据拒绝蜘蛛协议（Robots Exclusion Protocol）加注拒绝收录的标记，我们将对有异议的内容采取断开链接的做法。\n" +
                    "        </p>\n" +
                    "        <p></p>\n" +
                    "        <h2>no-transform协议</h2>\n" +
                    "        <p>转码支持的no-transform协议为如下两种形式：</p>\n" +
                    "        <p>1、HTTP Response中显示声明 Cache-control为no-transform；</p>\n" +
                    "        <p>2、meta标签中显示声明Cache-control为no-transform,格式为：</p>\n" +
                    "        <p><img src=\"http://p2.pstatp.com/large/1013/6493117447\">\n" +
                    "        </p>\n" +
                    "        <p>如果第三方网站不希望页面被今日头条客户端转码，可在页面中添加此协议，当用户进入时，会直接跳转至原网页。</p>\n" +
                    "\n" +
                    "        <p></p>\n" +
                    "        <h2>预加载技术</h2>\n" +
                    "\n" +
                    "        <p>今日头条为了让用户获得更好的体验，使用预加载技术极致提升用户打开文章的速度，使用户进入文章时几乎不用等待，实现“秒开”体验。</p>\n" +
                    "\n" +
                    "        <p>所谓预加载，是指用户在打开页面前，会预先加载文章的html、css、javascript这几部分内容。一些浏览器厂商为提高网页访问速度也同样使用此技术。比如：搜狗高速浏览器，其宣称的“智能预取，速度革命”，就是如此。</p>\n" +
                    "\n" +
                    "        <p>预加载技术特点：</p>\n" +
                    "\n" +
                    "        <p>1. 预加载只加载文本代码（html、css和javascript），不预加载图片。</p>\n" +
                    "        <p>2. 预加载不执行代码（javascript），不影响下游网站的流量统计。</p>\n" +
                    "        <p>3. 广告不进行预加载。 </p>\n");
            session.save(media);

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
}
