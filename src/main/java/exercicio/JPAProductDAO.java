package exercicio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

public class JPAProductDAO implements ProductDAO {
	public long insert(Product product) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = MyEntityManagerFactory.createSession();
			tx = em.getTransaction();
			tx.begin();

			em.persist(product);

			tx.commit();

			return product.getId();
		} catch (RuntimeException e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (RuntimeException he) {
				}
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public void update(Product product) throws ProductNotFoundException {
		EntityManager em = null;
		EntityTransaction tx = null;
		Product databaseProduct = null;

		try {
			em = MyEntityManagerFactory.createSession();
			tx = em.getTransaction();
			tx.begin();

			databaseProduct = em.find(Product.class, product.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (databaseProduct == null) {
				tx.rollback();
				throw new ProductNotFoundException("Produto nao encontrado");
			}

			em.merge(product);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (RuntimeException he) {
				}
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public void delete(long id) throws ProductNotFoundException {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = MyEntityManagerFactory.createSession();
			tx = em.getTransaction();
			tx.begin();

			Product product = em.find(Product.class, Long.valueOf(id), LockModeType.PESSIMISTIC_WRITE);

			if (product == null) {
				tx.rollback();
				throw new ProductNotFoundException("Produto nao encontrado");
			}

			em.remove(product);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (RuntimeException he) {
				}
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public Product findById(long id) throws ProductNotFoundException {
		EntityManager em = null;

		try {
			em = MyEntityManagerFactory.createSession();

			Product product = em.find(Product.class, id);

			if (product == null) {
				throw new ProductNotFoundException("Produto nao encontrado");
			}

			return product;
		} finally {
			em.close();
		}
	}

	public List<Product> findAll() {
		EntityManager em = null;

		try {
			em = MyEntityManagerFactory.createSession();

			@SuppressWarnings("unchecked")
			List<Product> products = em.createQuery("select p from Product p order by p.id").getResultList();

			return products;
		} finally {
			em.close();
		}
	}
}
