<template>
    <div class="wrapper-user container">
    <div class="row">
      <label for="user-login">User Login</label>
      <input class="u-full-width" id="user-login" type="text" disabled="disabled" v-model="user_login"></input>
    </div>
    <div class="row">
      <label for="user-name">Name</label>
      <input class="u-full-width" :class="{'error': errors.has('user_name')}" id="user-name"
        type="text" placeholder="Please input your name..."
        v-validate="'required'" data-vv-validate-on="input" data-vv-delay="1000"
        name="user_name" v-model="user_name"></input>
      <span v-show="errors.has('user_name')" class="msg-error">{{ errors.first('user_name') }}</span>
    </div>

    <div class="row">
      <label for="user-mail">Email</label>
      <input class="u-full-width" id="user-mail" type="text" disabled="disabled" v-model="user_mail"></input>
      <span v-show="errors.has('user_mail')" class="msg-error">{{ errors.first('user_mail') }}</span>
    </div>
    
    <div class="row">
      <label for="user-profile">Profile</label>
      <input type="hidden" v-model="user_profile" name="user-profile">
      <a href="javascript:void(0)" class="action-upload" @click="toUploadProfile">
          <div class="wrapper-profile" :style="profileBgStyle">
            <span class="fa fa-camera"></span>
            <span>更新头像</span>
          </div>
          <input id="profile-file" type="file" name="profile-file" @change="onFileChange" accept="image/*">
      </a>
      <span v-if="upload_ret_error" class="msg-error">{{ upload_ret_msg }}</span>
    </div>

    <input class="button-primary" type="submit" value="Submit" @click="submit"
          :disabled="errors.any() || !user_name || !user_mail || processing || uploading">
    <span v-if="ret_error" class="msg-error">{{ ret_msg }}</span> <!--  -->
  </div>
</template>

<script>
import auth from '@/assets/js/auth'

export default {
  	name: "user-info",
	data: function () {
    	return {
            user_login: "",
        	user_name: "", 
            user_mail: "",
            user_profile: "",
            profileBgStyle: { background: '#ddd' },
            ret_error: false,
            ret_msg: "",
            processing: false,
            uploading: false,
            upload_ret_error: false,
            upload_ret_msg: ""
    	}
	},

    mounted: function () {
        var params = {
            user_login: auth.getUserLogin(),
            access_token: auth.getAccessToken()
        }
        var url = "/ssc/fnd/user"
        this.$http.get(url, {params: params, responseType: 'json'}).then(response => {
            var rt = response.body;

            if(rt.ret_code  == 2) {
                console.log(rt.ret_msg)
            } else {
                var rtData = rt.ret_data

                this.user_login = rtData.user_login
                this.user_name = rtData.user_name
                this.user_mail = rtData.user_mail
                this.user_profile = rtData.user_profile

                if(this.user_profile) {
                    this.profileBgStyle = { backgroundImage : "url('" + this.user_profile + "')" }
                }
            }
        }, response => {
            console.log("Error - Get url: " + url + ", response: " + response.status)
        })
    },

	methods: {

        toUploadProfile: function() {
            var profileInput = document.getElementById("profile-file")

            if( !this.uploading ) profileInput.click()
        },

        onFileChange: function(event) {
            var files = event.target.files
            if(!files || files.length == 0) return

            this.uploading = true
            this.upload_ret_error = false

            var uploadFile = files[0]
            var formData = new FormData()
            formData.append("file", uploadFile)
            formData.append("user_login", auth.getUserLogin())
            formData.append("access_token", auth.getAccessToken())

            this.$http.post('/ssc/fnd/file/upload', formData, {responseType: 'json'}).then(response => {
                var rt = response.body;

                if(rt.ret_code == 2) {
                    this.upload_ret_error = true
                    this.upload_ret_msg = rt.ret_msg
                } else {
                    this.user_profile = rt.ret_data.file_url
                    this.profileBgStyle = { backgroundImage : "url('" + rt.ret_data.file_url + "')" }
                }

                this.uploading = false;
            }, response => {
                console.log(response.status + ": " + response.statusText)

                this.uploading = false
                this.upload_ret_error = true
                this.upload_ret_msg = "网络异常, 请稍后重试..."
            })

        },

        submit: function() {
            this.processing = true
            this.ret_error = false

            var postData = {
                user_login: auth.getUserLogin(),
                access_token: auth.getAccessToken(),
                user_name: this.user_name,
                user_profile: this.user_profile
            }

            this.$http.post("/ssc/fnd/user/update", postData, {responseType: 'json'}).then(response => {
                var rt = response.body;

                if(rt.ret_code == 2) {
                  this.ret_error = true
                  this.ret_msg = rt.ret_msg
                } else {  // ret_code == 1
                  console.log("Success: update user.")
                }

                this.processing = false;
            }, response => {
                console.log(response.status + ": " + response.statusText)

                this.processing = false;
            })
        }

    }
}
</script>

<style scoped lang="stylus">
.action-upload
    position relative
    display inline-block
.action-upload, .wrapper-profile 
    border-radius 50%
    height 150px
    width 150px
    background-size cover
    background-position center
    background-blend-mode multiply
    vertical-align middle
    text-align center
    color transparent
    transition all .3s ease
    text-decoration none
    &:hover
        background-color rgba(0,0,0,.5)
        z-index 10000
        color #fff
        transition all .3s ease
        text-decoration none
    input#profile-file
        position absolute
        top -10000px

.wrapper-profile span 
    display inline-block
    padding-top 4.5em
    padding-bottom 4.5em
input.button-primary
    margin-top 32px
</style>