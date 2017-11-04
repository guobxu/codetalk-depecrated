package me.codetalk.flow.proxy.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import me.codetalk.param.checker.ParamCheckResult;
import me.codetalk.flow.auth.service.IAuthService;
import me.codetalk.flow.fnd.service.IFndService;
import me.codetalk.flow.proxy.BaseProxy;
import me.codetalk.storage.exception.FileStorageException;
import me.codetalk.storage.service.IStorageService;
import me.codetalk.storage.service.StorageConstants;

@RestController
@ConfigurationProperties("upload")
public class FndProxy extends BaseProxy {

	private static Logger LOGGER = LoggerFactory.getLogger(FndProxy.class);
	
	@Autowired
	private IFndService fndService;
	
	@Autowired
	private IAuthService authService;
	
	@Autowired
	private IStorageService storeService;
	
	private String fileServer;
	
	@RequestMapping(value = "/flow/fnd/user/**", method = RequestMethod.POST)
	public Callable<String> doPost(@RequestBody Map<String, Object> data, HttpServletRequest request) {
		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				String uri = request.getRequestURI();
				ParamCheckResult checkRt = paramChecker.checkPost(uri, data);
				
				if(!checkRt.isValid()) return errorWithMsg(checkRt.getErrMsg());
				
				try {
		            return fndService.doPost(uri, data);
		        } catch(Exception ex) {
		        	LOGGER.error(ex.getMessage(), ex);
		    		
		    		return errorWithKey("sys_exception_msg");
		    	}
			}
    		
    	};
	}
	
	@RequestMapping(value = "/flow/fnd/**", method = RequestMethod.GET)
    public Callable<String> doGet(HttpServletRequest request) {
    	return new Callable<String>() {

			@Override
			public String call() throws Exception {
				String uri = request.getRequestURI();
				Map<String, String[]> params = request.getParameterMap();
				
				ParamCheckResult checkRt = paramChecker.checkGet(uri, params);
				if(!checkRt.isValid()) return errorWithMsg(checkRt.getErrMsg());
				
				try {
		            return fndService.doGet(uri, params);
		        } catch(Exception ex) {
		        	LOGGER.error(ex.getMessage(), ex);
		    		
		    		return errorWithKey("sys_exception_msg");
		    	}
			}
    		
    	};
    }
	
	@RequestMapping(value = "/flow/fnd/file/upload", method = RequestMethod.POST)
	public Callable<String> uploadFile(@RequestParam("file") MultipartFile file, 
										@RequestParam(value="user_login", defaultValue="") String userLogin, 
										@RequestParam(value="access_token",defaultValue="") String accessToken,
										HttpServletRequest request) {
		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				String uri = request.getRequestURI();
				
				// param check
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("user_login", userLogin);
				params.put("access_token", accessToken);
				ParamCheckResult checkRt = paramChecker.checkPost(uri, params);
				if(!checkRt.isValid()) return errorWithMsg(checkRt.getErrMsg());
				
				// login check
//				String userLogin = data.get("user_login").toString(),
//						accessToken = data.get("access_token").toString();
				if(!authService.isLoggedIn(userLogin, accessToken)) {
					return errorWithKey("auth_err_nologin");
				}
				
				// do upload
				try {
					byte[] data = file.getBytes();
	    			
	    			// meta info
	    			Map<String, String> metas = new HashMap<String, String>();
	    	    	
	    			String name = file.getOriginalFilename(), type = file.getContentType();
	    	    	metas.put(StorageConstants.META_KEY_FILENAME, name);
	    	    	metas.put(StorageConstants.META_KEY_FILELENGTH, String.valueOf(data.length));
	    	    	metas.put(StorageConstants.META_KEY_CONTENT_TYPE, type);
	    			
	    			String serverFile = storeService.store(data, metas);
	    			if(serverFile == null) {
	    				return errorWithKey("fdfs_upload_error");
	    			}
	    			
	    			Map<String, Object> rtData = new HashMap<String, Object>();
	    			rtData.put("file_url", fileServer + serverFile);
	    			
					return successWithData(rtData);
		    	} catch(FileStorageException ex) {
		    		ex.printStackTrace();
		    		
		    		return errorWithKey("fdfs_upload_error");
		    	} catch(Exception ex) {
		    		ex.printStackTrace();
		    		
		    		return errorWithKey("proxy_exception_msg");
		    	}
			}
			
		};
	}
	
	public String getFileServer() {
		return fileServer;
	}

	public void setFileServer(String fileServer) {
		this.fileServer = fileServer;
	}
	
}
