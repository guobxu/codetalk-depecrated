package me.codetalk.flow.solv.exception;

/**
 * SOLV服务异常
 * @author guobxu
 *
 */
public class SolvServiceException extends Exception {

	public SolvServiceException(String msg, Exception ex) {
		super(msg, ex);
	}
	
	public SolvServiceException(Exception ex) {
		super(ex);
	}
	
	public SolvServiceException(String msg) {
		super(msg);
	}
	
}


