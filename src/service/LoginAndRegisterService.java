package service;

import domain.MyUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

/**
 * Created by sangzhenya on 2016/3/30.
 */
public class LoginAndRegisterService {
    public static MyUser loginNow(String name, String pass){
        Session session = null;
        Transaction tx = null;
        List<MyUser> myUsers = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            myUsers = session.createQuery("from MyUser u where u.name = :name and u.password = :password")
                    .setString("name",name)
                    .setString("password",pass)
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
        if(myUsers != null && myUsers.size() != 0){
            return myUsers.get(0);
        }
        return null;
    }

    public static void register(String name, String password){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            MyUser myUser = new MyUser();
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

    }

    public static String getCheck(String name){
        Session session = null;
        Transaction tx = null;
        List<MyUser> myUsers;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            myUsers = session.createQuery("from MyUser " +
                    "where name = :name ")
                    .setString("name", name)
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
        if (myUsers != null && myUsers.size() > 0 ){
//            System.out.println(name+":::::"+myUsers.size());
            return "find";
        }else{
            return "not";
        }
    }
}
