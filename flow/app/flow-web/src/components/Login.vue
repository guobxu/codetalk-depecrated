<template>
	<div class="wrapper-login container">
	    <div class="row">
	      <input class="u-full-width" :class="{'error': errors.has('user_name')}"
	        type="text" placeholder="User name"
	        v-validate="'required'" data-vv-validate-on="input" data-vv-delay="1000"
	        name="user_name" v-model="user_name"></input>
	      <span v-show="errors.has('user_name')" class="msg-error">{{ errors.first('user_name') }}</span>
	    </div>
	    <div class="row">
	      <input class="u-full-width" :class="{'error': errors.has('passwd_str')}"
	        type="password" placeholder="Password"
	        v-validate="'required|min:6'" data-vv-validate-on="input" data-vv-delay="1000"
	        name="passwd_str" v-model="passwd_str"></input>
	      <span v-show="errors.has('passwd_str')" class="msg-error">{{ errors.first('passwd_str') }}</span>
	    </div>
	    
	    <input class="button-primary" type="submit" value="Login" @click="submit"
	          :disabled="errors.any() || !user_name || !passwd_str || processing">
      <span v-if="ret_err" class="msg-error">{{ ret_msg }}</span> <!--  -->
	  </div>
</template>

<script>
import { CRYPTO_CONST, sha256, cipher, decipher } from '@/assets/js/crypto'

import util from '@/assets/js/util'
import auth from '@/assets/js/auth'
import mesg from '@/assets/js/mesg'

export default {
  name: "login",
  data: function() {
    return {
      user_name: "",
      passwd_str: "",
      processing: false,
      ret_err: false,
      ret_msg: ""
    }
  },
  methods: {
    submit: function() {
      this.processing = true
      this.ret_err = false

      var salted = CRYPTO_CONST.PWD_SALT1 + this.passwd_str + CRYPTO_CONST.PWD_SALT2
      var clientKey = util.random32()

      var loginAuthStr = cipher(clientKey + CRYPTO_CONST.SOURCE_TYPE_WEB + "/" + this.user_name, sha256(salted).substring(0, 32))

      var postData = {
        user_name: this.user_name, 
        login_auth_str: loginAuthStr
      }

      this.$http.post("/ssc/auth/login", postData, {responseType: 'json'}).then(response => {
        var rtData = response.body;

        if(rtData.ret_code == 2) {
          this.ret_err = true
          this.ret_msg = rtData.ret_msg
        } else {  // ret_code == 1
          var userLogin = rtData.ret_data.user_login, 
              authRet = rtData.ret_data.auth_ret_str
          var clearText = decipher(authRet, clientKey)

          var tokenRet = clearText.substring(0, 36), userNameRet = clearText.substring(36)
          if(postData.user_name != userNameRet) {
            this.ret_err = true
          } else {
            auth.setLoginInfo(userLogin, tokenRet)
            
            window.location.href = "/"
          }
        }

        this.processing = false;
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
.wrapper-login
  form
    width: 100%;
</style>
