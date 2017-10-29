package me.codetalk.param.checker.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.param.checker.IParamChecker;
import me.codetalk.param.checker.ParamCheckResult;
import me.codetalk.param.type.EmailParam;
import me.codetalk.param.type.IntEnumParam;
import me.codetalk.param.type.IntParam;
import me.codetalk.param.type.LongParam;
import me.codetalk.param.type.Param;
import me.codetalk.param.type.RegexParam;
import me.codetalk.param.type.StringListParam;
import me.codetalk.param.type.StringParam;

/**
 * 前端参数检测, 使用示例:
	ParamCheckResult rt = paramChecker.checkPost(uri, data);
	if(!rt.isValid()) {
		return errorWithMsg(rt.getErrMsg());
	}
 *
 * @author guobxu
 *
 */
@Component("paramChecker")
public class ParamCheckerImpl implements IParamChecker {

	@Autowired
	private KeyedMessages km;
	
	// 映射
	static Map<String, Param[]> URI_PARAM_MAPPING = new HashMap<String, Param[]>();
	
	private static Param PARAM_USER_LOGIN = new StringParam("user_login");
	private static Param PARAM_ACCESS_TOKEN = new StringParam("access_token");
	
	static Param PARAM_BEGIN = new IntParam("begin", true, 0);	// 分页开始
	static Param PARAM_COUNT = new IntParam("count", true, 1);	// count分页条数
	
	static {
		/*************************** Auth ***************************/
		// 用户注册
		URI_PARAM_MAPPING.put("/ssc/auth/signup", new Param[] {
				new RegexParam("login_name", true, "[A-Za-z0-9][\\w-]{5,19}"),
				new EmailParam("user_mail", true), 
				new StringParam("passwd_str", true),
				new IntParam("position_type", true)});
		
		// 用户注册
		URI_PARAM_MAPPING.put("/ssc/auth/login", new Param[] {
				new StringParam("user_name", true),
				new StringParam("login_auth_str", true), 
				new IntEnumParam("remember_me", false, new Integer[] {0, 1})});
		
		/*************************** Fnd ***************************/
		
		// 修改用户信息
		URI_PARAM_MAPPING.put("/ssc/fnd/file/upload", new Param[] {
			PARAM_USER_LOGIN,
			PARAM_ACCESS_TOKEN
		});
		
		// 获取用户信息
		URI_PARAM_MAPPING.put("/ssc/fnd/user", new Param[] {
				PARAM_USER_LOGIN,
				PARAM_ACCESS_TOKEN,
				new StringParam("user_login_get", false)
		});
		
		// 修改用户信息
		URI_PARAM_MAPPING.put("/ssc/fnd/user/update", new Param[] {
			PARAM_USER_LOGIN,
			PARAM_ACCESS_TOKEN,
			new StringParam("user_name", true),
			new StringParam("user_profile", true, true) // 允许为""
		});
		
		// 标签分组 TODO
//		URI_PARAM_MAPPING.put("/ssc/fnd/tags", new Param[] {
//			new IntParam("position_type", true)
//		});
		
		
		/*************************** Pofo ***************************/

//		q=xx // 查询关键字，可选
//				user_login_get="xx" // 用户登录名，可选
//				show_img=0 // 是否显示图片 0 不显示 1 显示
//				begin=0 // 起止
//				count=20 // 条数

		// 帖子列表 TODO
//		URI_PARAM_MAPPING.put("/ssc/pofo/post/list", new Param[] {
//				PARAM_USER_LOGIN,
//				PARAM_ACCESS_TOKEN,
//				new StringParam("q", false),
//				new StringParam("user_login_get", false),
//				new IntEnumParam("show_img", true, new Integer[] {0, 1}),
//				PARAM_BEGIN,
//				PARAM_COUNT
//		});
		
		// 发表帖子
		URI_PARAM_MAPPING.put("/ssc/pofo/post/create", new Param[] {
				PARAM_USER_LOGIN,
				PARAM_ACCESS_TOKEN,
				new StringParam("post_content", false, true),
				new LongParam("post_id_refer", false, 1L),
				new LongParam("comment_id_refer", false, 1L),
				new StringListParam("post_imgs", false),
				new StringListParam("notify_users", false),
		});
		
	}
	
	@Override
	public ParamCheckResult checkPost(String uri, Map<String, Object> params) {
		Param[] paramArr = URI_PARAM_MAPPING.get(uri);
		if(paramArr == null || paramArr.length == 0) return ParamCheckResult.VALID;
		
		for(Param param : paramArr) {
			Object val = params.get(param.getName());
			if(!param.isValid(val)) {
				return ParamCheckResult.invalidWithMsg(km.getWithParams("common_err_param", "paramName", param.getName()));
			}
		}
		
		return ParamCheckResult.VALID;
	}

	@Override
	public ParamCheckResult checkGet(String uri, Map<String, String[]> params) {
		Param[] paramArr = URI_PARAM_MAPPING.get(uri);
		if(paramArr == null || paramArr.length == 0) return ParamCheckResult.VALID;
		
		for(Param param : paramArr) {
			String[] valArr = params.get(param.getName());
			
			Object paramObj = null;
			if(valArr == null) {
				paramObj = null;
			} else if(param.isList()) {
				paramObj = Arrays.asList(valArr);
			} else {
				paramObj = valArr[0];
			}
			
			if(!param.isValid(paramObj)) {
				return ParamCheckResult.invalidWithMsg(km.getWithParams("common_err_param", "paramName", param.getName()));
			}
		}
		
		return ParamCheckResult.VALID;
	}

}

















