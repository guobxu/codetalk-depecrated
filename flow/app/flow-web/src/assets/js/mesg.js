
import util from '@/assets/js/util'

export default {

	get(key) {
		var locale = util.getLocale()

		var localeMap = MESSAGES[locale]
		if( !localeMap ) return key

		var val = localeMap[key]
		if( !val ) return key
		
		return val
	}

}


const MESSAGES = {
	zh_CN: {
		"login_auth_error": "登录失败，用户名或者密码错误！"
	}
}


















