package exercicio;

import java.util.List;
import corejava.Console;

public class Main {
	static ProductDAO productDAO = DAOFactory.getDAO(ProductDAO.class);

	public static void main(String[] args) {

		boolean loop = true;
		while (loop) {
			int option = getOption();

			System.out.println();

			if (option == 1)
				insertProduct();
			else if (option == 2)
				updatedProduct();
			else if (option == 3)
				deleteProduct();
			else if (option == 4)
				listProducts();
			else if (option == 5)
				loop = false;
			else
				System.out.println("\nOpcao invalida!");
		}

		System.out.println("\nAte a proxima!");
	}

	private static int getOption() {
		System.out.println("\nO que voce deseja fazer?\n");
		System.out.println("1. Cadastrar um produto");
		System.out.println("2. Alterar um produto");
		System.out.println("3. Remover um produto");
		System.out.println("4. Listar todos os produtos");
		System.out.println("5. Sair");

		int option = Console.readInt("\nDigite um numero entre 1 e 5:");

		return option;
	}

	private static void insertProduct() {
		String name = Console.readLine('\n' + "Informe o nome do produto: ");
		double price = Console.readDouble("Informe o preco do produto: ");

		Product product = new Product(name, price);

		productDAO.insert(product);

		System.out.println("\nProduto numero" + product.getId() + " incluido com sucesso!");
	}

	private static void updatedProduct() {
		int idOfProductToUpdate = Console.readInt("Digite o numero do produto que voce deseja alterar: ");
		Product productToUpdate;

		try {
			productToUpdate = productDAO.findById(idOfProductToUpdate);
		} catch (ProductNotFoundException e) {
			System.out.println('\n' + e.getMessage());
			return;
		}

		System.out.println(productToUpdate);

		System.out.println("O que voce deseja alterar?\n");
		System.out.println("1. Nome");
		System.out.println("2. preco");

		int optionToUpdate = Console.readInt("\nDigite um numero de 1 a 2:");

		if (optionToUpdate == 1) {
			String newName = Console.readLine("Digite o novo nome: ");

			productToUpdate.setName(newName);
		} else if (optionToUpdate == 2) {
			double newPrice = Console.readDouble("Digite o novo preco: ");

			productToUpdate.setPrice(newPrice);
		} else {
			System.out.println("Opção inválida!");
			return;
		}

		try {
			productDAO.update(productToUpdate);

			System.out.println("\nProduto atualizado com sucesso!");
		} catch (ObjectStateObsoleteException e) {
			String message = "\nA operacao nao foi efetuada: os dados que voce tentou salvar foram modificados por outro usuario";
			System.out.println(message);
		} catch (ProductNotFoundException e) {
			System.out.println('\n' + e.getMessage());
			return;
		}

	}

	private static void deleteProduct() {
		int idOfProductToDelete = Console.readInt("Digite o numero do produto que voce deseja remover: ");
		Product productToDelete;

		try {
			productToDelete = productDAO.findById(idOfProductToDelete);
		} catch (ProductNotFoundException e) {
			System.out.println('\n' + e.getMessage());
			return;
		}

		System.out.println(productToDelete);

		String confirmation = Console.readLine("Confirma a remocao do produto? (s/n)");

		if (confirmation.toLowerCase().equals("s")) {
			try {
				productDAO.delete(productToDelete.getId());

				System.out.println('\n' + "Produto removido com sucesso!");
			} catch (ProductNotFoundException e) {
				System.out.println('\n' + e.getMessage());
			}
		} else {
			System.out.println("\nProduto não removido.");
		}
	}

	private static void listProducts() {
		List<Product> products = productDAO.findAll();

		System.out.println("\n--- Produtos ---");
		for (Product product : products) {
			System.out.println(product);
		}
	}
}
