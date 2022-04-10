package exercicio;

import java.util.List;

public interface ProdutoDAO {
	long inclui(Product umProduto);

	void altera(Product umProduto)
			throws ProdutoNaoEncontradoException;

	void exclui(long id)
			throws ProdutoNaoEncontradoException;

	Product recuperaUmProduto(long numero)
			throws ProdutoNaoEncontradoException;

	List<Product> recuperaProdutos();
}