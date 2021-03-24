package be.helha.crowdfunding.exceptions;

public class DuplicateElementException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateElementException(String message) {
		super(message);
	}

}
