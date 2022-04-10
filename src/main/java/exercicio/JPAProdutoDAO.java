package exercicio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

public class JPAProdutoDAO implements ProdutoDAO {
	public long inclui(Product umProduto) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();

			em.persist(umProduto);

			tx.commit();

			return umProduto.getId();
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

	public void altera(Product umProduto) throws ProdutoNaoEncontradoException {
		EntityManager em = null;
		EntityTransaction tx = null;
		Product produto = null;
		try {
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();

			// lock desse registro na tabela de produto pra impedir que
			// ele seja deletado por outro usuario
			produto = em.find(Product.class, umProduto.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (produto == null) {
				tx.rollback();
				throw new ProdutoNaoEncontradoException("Produto não encontrado");
			}

			em.merge(umProduto);
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

	public void exclui(long numero) throws ProdutoNaoEncontradoException {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();

			Product produto = em.find(Product.class, Long.valueOf(numero), LockModeType.PESSIMISTIC_WRITE);

			if (produto == null) {
				tx.rollback();
				throw new ProdutoNaoEncontradoException("Produto não encontrado");
			}

			em.remove(produto);
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

	public Product recuperaUmProduto(long numero) throws ProdutoNaoEncontradoException {
		EntityManager em = null;

		try {
			em = FabricaDeEntityManager.criarSessao();

			Product umProduto = em.find(Product.class, numero);

			// Características no método find():
			// 1. É genérico: não requer um cast.
			// 2. Retorna null caso a linha não seja encontrada no banco.

			if (umProduto == null) {
				throw new ProdutoNaoEncontradoException("Produto não encontrado");
			}

			return umProduto;
		} finally {
			em.close();
		}
	}

	public List<Product> recuperaProdutos() {
		EntityManager em = null;

		try {
			em = FabricaDeEntityManager.criarSessao();

			@SuppressWarnings("unchecked")
			List<Product> produtos = em.createQuery("select p from Produto p order by p.id").getResultList();

			return produtos;
		} finally {
			em.close();
		}
	}
}
