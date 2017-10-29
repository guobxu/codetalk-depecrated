
import util from '@/assets/js/util'

var KEY_ACCESS_TOKEN = "__access_token"	// access token key
var KEY_USER_LOGIN = "__user_login"	// user login key

export default {

	isLoggedIn() {
		var accessToken = this.getAccessToken()

		return accessToken != ""
	},

	setLoginInfo(login, accessToken) {
		util.setCookie(KEY_USER_LOGIN, login)
		util.setCookie(KEY_ACCESS_TOKEN, accessToken)
	},

	// 设置user login
	setUserLogin(login) {
		util.setCookie(KEY_USER_LOGIN, login)
	},

	// 获取user login
	getUserLogin() {
		return util.getCookie(KEY_USER_LOGIN)
	},

	// 设置access token
	setAccessToken(token) {
		util.setCookie(KEY_ACCESS_TOKEN, token)
	},

	// 获取access token
	getAccessToken() {
		return util.getCookie(KEY_ACCESS_TOKEN)
	},

	logout() {
		util.setCookie(KEY_ACCESS_TOKEN, "")
	}

}
