package br.com.jhonatan.carrosprospects.services.exceptions;

public class IOPipedriveException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IOPipedriveException(String msg) {
		super(msg);
	}
	
	public IOPipedriveException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
