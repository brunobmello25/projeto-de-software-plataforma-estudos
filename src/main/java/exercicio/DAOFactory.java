package exercicio;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DAOFactory {
	private static ResourceBundle prop;

	static {
		try {
			prop = ResourceBundle.getBundle("dao");
		} catch (MissingResourceException e) {
			System.out.println("Aquivo dao.properties não encontrado.");
			throw new RuntimeException(e);
		}
	}

	public static <T> T getDAO(Class<T> classType) {
		T dao = null;
		String className = null;

		try {
			className = prop.getString(classType.getSimpleName());
			dao = (T) Class.forName(className).newInstance();
		} catch (InstantiationException e) { // erros quando tentamos instanciar algo nao instanciavel, exemplo: Interfaces
			System.out.println("Nao foi possivel criar um objeto do tipo " + className);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) { // exemplo: construtor privado
			System.out.println("Nao foi possivel criar um objeto do tipo " + className);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) { // classe inexistente no projeto
			System.out.println("Classe " + className + " nao encontrada");
			throw new RuntimeException(e);
		} catch (MissingResourceException e) { // dao.properties não encontrado ou a chave não foi encontrada
			System.out.println("Chave " + classType + " nao encontrada em dao.properties");
			throw new RuntimeException(e);
		}

		return dao;
	}
}
