<template>
  <div class="wrapper-signup container">
    <div class="row">
      <input class="u-full-width" :class="{'error': errors.has('login_name')}"
        type="text" placeholder="Login name"
        v-validate="'required|alpha_dash|min:6'" data-vv-validate-on="input" data-vv-delay="1000"
        name="login_name" v-model="login_name"></input>
      <span v-show="errors.has('login_name')" class="msg-error">{{ errors.first('login_name') }}</span>
    </div>
    <div class="row">
      <input class="u-full-width" :class="{'error': errors.has('user_mail')}"
        type="text" placeholder="Email"
        v-validate="'required|email'" data-vv-validate-on="input" data-vv-delay="1000"
        name="user_mail" v-model="user_mail"></input>
      <span v-show="errors.has('user_mail')" class="msg-error">{{ errors.first('user_mail') }}</span>
    </div>
    <div class="row">
      <input class="u-full-width" :class="{'error': errors.has('passwd_str')}"
        type="password" placeholder="Password"
        v-validate="'required|min:6'" data-vv-validate-on="input" data-vv-delay="1000"
        name="passwd_str" v-model="passwd_str"></input>
      <span v-show="errors.has('passwd_str')" class="msg-error">{{ errors.first('passwd_str') }}</span>
    </div>
    
    <input class="button-primary" type="submit" value="Signup" @click="submit" 
          :disabled="errors.any() || !login_name || !user_mail || !passwd_str || processing">
  </div>
</template>

<script>
import { CRYPTO_CONST, sha256, cipher, decipher } from '@/assets/js/crypto'

import util from '@/assets/js/util'

export default {
  name: "signup",
  data: function() {
    return {
      login_name: "", 
      user_mail: "",
      passwd_str: "",
      processing: false
    }
  },
  methods: {
    submit: function() {
      this.processing = true;

      var salted = CRYPTO_CONST.PWD_SALT1 + this.passwd_str + CRYPTO_CONST.PWD_SALT2
      var pwdCiphered = cipher(sha256(salted).substring(0, 32), CRYPTO_CONST.AES_PWD_KEY)

      var postData = {
        login_name: this.login_name,
        user_mail: this.user_mail, 
        passwd_str: pwdCiphered
      }

      this.$http.post("/ssc/auth/signup", postData, {responseType: 'json'}).then(response => {
        var rtData = response.body;

        console.log("ret_code: " + rtData.ret_code);
        console.log("ret_msg: " + rtData.ret_msg);

        if(rtData.ret_code == 1) {
          this.$router.push("/")
        }

        this.processing = false;
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
.wrapper-signup
  width: 100%;

</style>
