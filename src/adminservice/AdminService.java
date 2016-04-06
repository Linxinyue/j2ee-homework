package adminservice;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sangzhenya on 2016/4/4.
 */
public class AdminService {
    public static long getTotalCount() {
        Session session = null;
        Transaction tx = null;
        Number number;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            number = (Number) session.createQuery("select count(*) from RequestCount ")
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
        return number.longValue();
    }

    public static long getWeekCount() {
        Session session = null;
        Transaction tx = null;
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-6);
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
        return number.longValue();
    }

    public static long getDayCount() {
        Session session = null;
        Transaction tx = null;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        Number number;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            number = (Number) session.createQuery("select count(*) " +
                    "from RequestCount " +
                    "where request_date between :start and :end")
                    .setDate("start",date)
                    .setDate("end",date)
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
        return number.longValue();
    }

    public static long getCurrentCount() {
        return (long) (Math.random() * 12 + 123);
    }

    public static List<Object> getWeekDay(){
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
//                    System.out.println(tempNumber.toString());
                }else {
                    numbers.add(0);
                }
//                System.out.println(date0);
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
        return numbers;
    }
}
