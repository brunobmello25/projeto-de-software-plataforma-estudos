package exercicio;

import java.util.List;
import corejava.Console;

public class Main {
	public static void main(String[] args) {
		String name;
		double price;
		Product aProduct;

		ProductDAO productDAO = DAOFactory.getDAO(ProductDAO.class);

		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um produto");
			System.out.println("2. Alterar um produto");
			System.out.println("3. Remover um produto");
			System.out.println("4. Listar todos os produtos");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' +
					"Digite um número entre 1 e 5:");

			switch (opcao) {
				case 1: {
					name = Console.readLine('\n' + "Informe o nome do produto: ");
					price = Console.readDouble("Informe o preço do produto: ");

					aProduct = new Product(name, price);

					productDAO.insert(aProduct);

					System.out.println('\n' + "Produto número " +
							aProduct.getId() + " incluído com sucesso!");

					break;
				}

				case 2: {
					int resposta = Console.readInt('\n' +
							"Digite o número do produto que você deseja alterar: ");

					try {
						aProduct = productDAO.findById(resposta);
					} catch (ProductNotFoundException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println('\n' +
							"NÃÂºmero = " + aProduct.getId() +
							"    Nome = " + aProduct.getName() +
							"    PreÃÂ§o = " + aProduct.getPrice());

					System.out.println('\n' + "O que vocÃÂª deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. preÃÂ§o");

					int opcaoAlteracao = Console.readInt('\n' +
							"Digite um nÃÂºmero de 1 a 2:");

					switch (opcaoAlteracao) {
						case 1:
							String novoNome = Console.readLine("Digite o novo nome: ");

							aProduct.setName(novoNome);

							try {
								productDAO.update(aProduct);

								System.out.println('\n' +
										"Alteração de nome efetuada com sucesso!");
							} catch (ProductNotFoundException e) {
								System.out.println('\n' + e.getMessage());
							}

							break;

						case 2:
							double newPrice = Console.readDouble("Digite o novo preço: ");

							aProduct.setPrice(newPrice);

							try {
								productDAO.update(aProduct);

								System.out.println('\n' +
										"Alteração de preço efetuada " +
										"com sucesso!");
							} catch (ProductNotFoundException e) {
								System.out.println('\n' + e.getMessage());
							}

							break;

						default:
							System.out.println('\n'
									+ "OpÃÂÃÂÃÂÃÂ§ÃÂÃÂÃÂÃÂ£o invÃÂÃÂÃÂÃÂ¡lida!");
					}

					break;
				}

				case 3: {
					int resposta = Console.readInt('\n' +
							"Digite o nÃÂºmero do produto que vocÃÂª deseja remover: ");

					try {
						aProduct = productDAO.findById(resposta);
					} catch (ProductNotFoundException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println('\n' +
							"nÃºmero = " + aProduct.getId() +
							"    Nome = " + aProduct.getName());

					String resp = Console.readLine('\n' +
							"Confirma a remoÃ§Ã£o do produto?");

					if (resp.equals("s")) {
						try {
							productDAO.delete(aProduct.getId());
							System.out.println('\n' +
									"Produto removido com sucesso!");
						} catch (ProductNotFoundException e) {
							System.out.println('\n' + e.getMessage());
						}
					} else {
						System.out.println('\n' +
								"Produto não removido.");
					}

					break;
				}

				case 4: {
					List<Product> products = productDAO.findAll();

					for (Product product : products) {
						System.out.println('\n' +
								"Id = " + product.getId() +
								"  Nome = " + product.getName() +
								"  Preço = " + product.getPrice());
					}

					break;
				}

				case 5: {
					continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
