package service;

import adminservice.AdminCountService;
import domain.Media;
import domain.MyClassification;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 * Created by sangzhenya on 2016/4/1.
 */
public class MediaService {

    /**
     * 获取更多的内容
     * @param id
     * @return
     */
    public static Media getMedia(int id){
        Session session = null;
        Transaction tx = null;

        Media media;
        MyClassification myClassification = new MyClassification();

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            media = session.get(Media.class,id);
            if (media == null)
                media = session.get(Media.class,1);

            myClassification = session.get(MyClassification.class,1);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        AdminCountService.requestRecord(myClassification);
        return media;
    }
}
