package exercicio;

import java.util.List;

public interface ProductDAO {
	long insert(Product product);

	void update(Product product)
			throws ProductNotFoundException, ObjectStateObsoleteException;

	void delete(long id)
			throws ProductNotFoundException;

	Product findById(long id)
			throws ProductNotFoundException;

	List<Product> findAll();
}