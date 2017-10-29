package me.codetalk.flow.fnd.exception;

public class FndServiceException extends Exception {

	public FndServiceException(String msg, Exception ex) {
		super(msg, ex);
	}
	
	public FndServiceException(Exception ex) {
		super(ex);
	}
	
	public FndServiceException(String msg) {
		super(msg);
	}
	
}
