package service;

import domain.Media;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 * Created by sangzhenya on 2016/4/1.
 */
public class MediaService {
    public static Media getMedia(int id){
        Session session = null;
        Transaction tx = null;

        Media media;

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            media = session.get(Media.class,id);
            if (media == null)
                media = session.get(Media.class,1);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        return media;
    }
}
