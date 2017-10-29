
var KEY_LOCALE = "__locale"
var DEFAULT_LOCALE = "zh_CN"

export default {

	// generate random 32 length key
	random32 () {
		var key = ""
		var hex = "0123456789ABCDEF"

		for (var i = 0; i < 32; i++) {
		    key += hex.charAt(Math.floor(Math.random() * 16));
		}

		return key;
	},

	millisToDate(timeMillis) {
		var interval = Date.now() - timeMillis
      	var seconds = parseInt(interval / 1000)
      	if(seconds < 60) return seconds + "秒"

      	var mins = parseInt(seconds / 60)
      	if(mins < 60) return mins + "分"

      	var hours = parseInt( mins / 60 )
		if(hours < 24) return hours + "时"

		var date = new Date(timeMillis), now = new Date()
		if(date.getFullYear() == now.getFullYear()) {
			return date.getMonth() + "月" + date.getDate() + "日"
		} else {
			return date.getFullYear() + "年" + date.getMonth() + "月" + date.getDate() + "日"
		}
  	},

  	// cookie funcs
	getCookie(key) {
		var name = key + "=";

	    var ca = document.cookie.split(';');
	    for(var i = 0; i <ca.length; i++) {
	        var c = ca[i].trim();

	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length,c.length);
	        }
	    }

	    return "";
	},

	setCookie(key, val) {
		document.cookie = key + "=" + val
	},

	// 读取locale, 如果为空, 则设置默认
	getLocale() {
		var locale = this.getCookie(KEY_LOCALE)

		return locale ? locale : DEFAULT_LOCALE
	},

	setLocale(locale) {
		this.setCookie(KEY_LOCALE, locale)
	}

}
