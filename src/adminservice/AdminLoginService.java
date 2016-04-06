package adminservice;

import admindomain.MyAdmin;
import domain.MyUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

/**
 * Created by sangzhenya on 2016/4/4.
 */
public class AdminLoginService {
    public static MyAdmin loginNow(String name, String password){
        Session session = null;
        Transaction tx = null;
        List<MyAdmin> myAdmins = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            myAdmins = session.createQuery("from MyAdmin u where u.name = :name and u.password = :password")
                    .setString("name",name)
                    .setString("password",password)
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
        if(myAdmins != null && myAdmins.size() != 0){
            return myAdmins.get(0);
        }
        return null;
    }
}
