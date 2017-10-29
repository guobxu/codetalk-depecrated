package me.codetalk.flow.pofo.exception;

/**
 * POFO服务异常
 * @author guobxu
 *
 */
public class PofoServiceException extends Exception {

	public PofoServiceException(String msg, Exception ex) {
		super(msg, ex);
	}
	
	public PofoServiceException(Exception ex) {
		super(ex);
	}
	
	public PofoServiceException(String msg) {
		super(msg);
	}
	
}


