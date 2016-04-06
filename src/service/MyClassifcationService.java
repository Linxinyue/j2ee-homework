package service;

import domain.MyClassification;
import domain.MySubscribe;
import domain.MyUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by sangzhenya on 2016/3/29.
 */
public class MyClassifcationService {

    /**
     * 获取当前的栏目信息
     * @param myUser
     * @return
     */
    public static List<Object> getUserClasses(MyUser myUser){
        Session session = null;
        Transaction tx = null;
        List<MyClassification> myClassifications = null;
        List<Object> classTitleDesc = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            if(myUser ==null){
                classTitleDesc = session.createQuery("select c.classification_name from MyClassification c ").list();
            }else {
                classTitleDesc = session.createQuery("select c.classification_name from MyUser u, MyClassification c, MySubscribe s " +
                        "where c.classification_id = s.myClassification.classification_id " +
                        "and u.user_id = s.myUser.user_id " +
                        "and u.user_id = :id")
                        .setInteger("id",myUser.getUser_id())
                        .list();
            }
            myClassifications = session.createQuery("from MyClassification ").list();
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        for (MyClassification myClassification:myClassifications){
            boolean isFind = false;
            for (Object objects:classTitleDesc){
                if (myClassification.getClassification_name().equals(objects)){
                    isFind = true;
                    break;
                }
            }
            if (!isFind){
                classTitleDesc.add(myClassification.getClassification_name());
            }
        }
//        System.out.println("NyClassifcatinService:"+classTitleDesc.size());
        return classTitleDesc;
    }

    /**
     * 判断是否是用户订阅的类
     * @param myUser
     * @param myClassification
     * @return
     */

    public static String  isSubClass(MyUser myUser, MyClassification myClassification){
        Session session = null;
        Transaction tx = null;
        List<MySubscribe> mySubscribes;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            mySubscribes  =  session.createQuery("from MySubscribe ms " +
                    "where ms.myClassification.classification_id = :classId " +
                    "and ms.myUser.user_id = :userId")
                    .setInteger("classId",myClassification.getClassification_id())
                    .setInteger("userId",myUser.getUser_id())
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
        if (mySubscribes != null && mySubscribes.size() > 0){
            return "true";
        }
        return "false";
    }
}
