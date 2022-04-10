package exercicio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

public class JPAProductDAO implements ProductDAO {
	public long insert(Product aProduct) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = MyEntityManagerFactory.createSession();
			tx = em.getTransaction();
			tx.begin();

			em.persist(aProduct);

			tx.commit();

			return aProduct.getId();
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

	public void update(Product aProduct) throws ProductNotFoundException {
		EntityManager em = null;
		EntityTransaction tx = null;
		Product product = null;
		try {
			em = MyEntityManagerFactory.createSession();
			tx = em.getTransaction();
			tx.begin();

			product = em.find(Product.class, aProduct.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (product == null) {
				tx.rollback();
				throw new ProductNotFoundException("Produto não encontrado");
			}

			em.merge(aProduct);
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
				throw new ProductNotFoundException("Produto não encontrado");
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

			Product aProduct = em.find(Product.class, id);

			if (aProduct == null) {
				throw new ProductNotFoundException("Produto não encontrado");
			}

			return aProduct;
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
