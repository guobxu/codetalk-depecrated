package me.codetalk.flow.proxy.impl;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.codetalk.param.checker.ParamCheckResult;
import me.codetalk.flow.proxy.BaseProxy;
import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.service.ISolvService;

@RestController
public class SolvProxy extends BaseProxy {

	private static Logger LOGGER = LoggerFactory.getLogger(SolvProxy.class);
	
	@Autowired
	private ISolvService solvService;
	
	@RequestMapping(value = "/ssc/solv/**", method = RequestMethod.POST)
	public Callable<String> doPost(@RequestBody Map<String, Object> data, HttpServletRequest request) {
		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				String uri = request.getRequestURI();
				ParamCheckResult checkRt = paramChecker.checkPost(uri, data);
				
				if(!checkRt.isValid()) return errorWithMsg(checkRt.getErrMsg());
				
				try {
		            return solvService.doPost(uri, data);
		        } catch(Exception ex) {
		        	LOGGER.error(ex.getMessage(), ex);
		    		
		    		return errorWithKey("sys_exception_msg");
		    	}
			}
    		
    	};
	}
	
	@RequestMapping(value = "/ssc/solv/**", method = RequestMethod.GET)
    public Callable<String> doGet(HttpServletRequest request) {
    	return new Callable<String>() {

			@Override
			public String call() throws Exception {
				String uri = request.getRequestURI();
				Map<String, String[]> params = request.getParameterMap();
				
				ParamCheckResult checkRt = paramChecker.checkGet(uri, params);
				if(!checkRt.isValid()) return errorWithMsg(checkRt.getErrMsg());
				
				try {
		            return solvService.doGet(uri, params);
		        } catch(Exception ex) {
		        	LOGGER.error(ex.getMessage(), ex);
		    		
		    		return errorWithKey("sys_exception_msg");
		    	}
			}
    		
    	};
    }
	
    
}







