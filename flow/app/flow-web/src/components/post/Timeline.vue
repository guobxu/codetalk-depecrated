<template>
	<div class="wrapper-timeline container">
    <div is="post-item" v-for="post in posts" :key="post.post_id" :post="post">
    </div>
  </div>
</template>

<script>
import PostItem from './PostItem'
import auth from '@/assets/js/auth'

export default {
  name: "timeline",
  components: {
    'post-item': PostItem
  },
  data: function() {
    return {
      posts: []
    }
  },
  mounted: function () {
      var params = {
          user_login: auth.getUserLogin(),
          access_token: auth.getAccessToken(), 
          user_login_get: auth.getUserLogin(),
          show_img: 1,
          begin: 0, 
          count: 20
      }
      var url = "/ssc/pofo/post/list"
      this.$http.get(url, {params: params, responseType: 'json'}).then(response => {
          var rt = response.body;

          if(rt.ret_code  == 2) {
              console.log(rt.ret_msg)
          } else {
              var rtData = rt.ret_data
              console.log(rtData)

              this.posts = rtData
          }
      }, response => {
          console.log("Error - Get url: " + url + ", response: " + response.status)
      })
  },
  methods: {
    
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
div.wrapper-timeline
  background white
  padding 0 12px
  border-radius 5px
</style>
