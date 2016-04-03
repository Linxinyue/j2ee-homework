package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
	private static SessionFactory factory = null;
	private static Session session = null;
	static {
		factory = new Configuration().configure().buildSessionFactory();

	}

	public static Session getSession() {
		if (factory != null)
			return factory.openSession();
		factory = new Configuration().configure().buildSessionFactory();
		return factory.openSession();
	}

	public static void closeSession() {
		if (session != null && session.isOpen())
			session.close();
	}
}
