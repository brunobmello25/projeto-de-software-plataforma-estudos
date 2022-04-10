package exercicio;

import java.util.List;
import corejava.Console;

public class Principal {
	public static void main(String[] args) {
		String name;
		double price;
		Product aProduct;

		ProdutoDAO productDAO = FabricaDeDAOs.getDAO(ProdutoDAO.class);

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

					productDAO.inclui(aProduct);

					System.out.println('\n' + "Produto número " +
							aProduct.getId() + " incluído com sucesso!");

					break;
				}

				case 2: {
					int resposta = Console.readInt('\n' +
							"Digite o número do produto que você deseja alterar: ");

					try {
						aProduct = productDAO.recuperaUmProduto(resposta);
					} catch (ProdutoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println('\n' +
							"Número = " + aProduct.getId() +
							"    Nome = " + aProduct.getName() +
							"    Preço = " + aProduct.getPrice());

					System.out.println('\n' + "O que você deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. preço");

					int opcaoAlteracao = Console.readInt('\n' +
							"Digite um número de 1 a 2:");

					switch (opcaoAlteracao) {
						case 1:
							String novoNome = Console.readLine("Digite o novo nome: ");

							aProduct.setName(novoNome);

							try {
								productDAO.altera(aProduct);

								System.out.println('\n' +
										"AlteraÃ¯Â¿Â½Ã¯Â¿Â½o de nome efetuada com sucesso!");
							} catch (ProdutoNaoEncontradoException e) {
								System.out.println('\n' + e.getMessage());
							}

							break;

						case 2:
							double newPrice = Console.readDouble("Digite o novo preço: ");

							aProduct.setPrice(newPrice);

							try {
								productDAO.altera(aProduct);

								System.out.println('\n' +
										"Alteração de preço efetuada " +
										"com sucesso!");
							} catch (ProdutoNaoEncontradoException e) {
								System.out.println('\n' + e.getMessage());
							}

							break;

						default:
							System.out.println('\n' + "OpÃÂ§ÃÂ£o invÃÂ¡lida!");
					}

					break;
				}

				case 3: {
					int resposta = Console.readInt('\n' +
							"Digite o número do produto que você deseja remover: ");

					try {
						aProduct = productDAO.recuperaUmProduto(resposta);
					} catch (ProdutoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}

					System.out.println('\n' +
							"número = " + aProduct.getId() +
							"    Nome = " + aProduct.getName());

					String resp = Console.readLine('\n' +
							"Confirma a remoção do produto?");

					if (resp.equals("s")) {
						try {
							productDAO.exclui(aProduct.getId());
							System.out.println('\n' +
									"Produto removido com sucesso!");
						} catch (ProdutoNaoEncontradoException e) {
							System.out.println('\n' + e.getMessage());
						}
					} else {
						System.out.println('\n' +
								"Produto não removido.");
					}

					break;
				}

				case 4: {
					List<Product> products = productDAO.recuperaProdutos();

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
