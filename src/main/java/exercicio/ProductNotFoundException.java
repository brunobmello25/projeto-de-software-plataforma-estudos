package exercicio;

public class ProductNotFoundException extends Exception {
	private final static long serialVersionUID = 1;

	private int errorCode;

	public ProductNotFoundException(String msg) {
		super(msg);
	}

	public ProductNotFoundException(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}