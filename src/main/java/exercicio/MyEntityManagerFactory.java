package exercicio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MyEntityManagerFactory {
	private static MyEntityManagerFactory factory = null;
	private EntityManagerFactory entityManagerFactory = null;

	private MyEntityManagerFactory() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("exercicio");
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println(">>>>>>>>>> Mensagem de erro: " + e.getMessage());
		}
	}

	public static EntityManager createSession() {
		if (factory == null) {
			factory = new MyEntityManagerFactory();
		}

		return factory.entityManagerFactory.createEntityManager();
	}

	public static void closeEntityManagerFactory() {
		if (factory == null) {
			return;
		}

		if (factory.entityManagerFactory == null) {
			return;
		}

		factory.entityManagerFactory.close();
	}
}