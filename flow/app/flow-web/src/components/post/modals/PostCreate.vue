<template>
  <transition name="modal">
    <div class="modal-mask modal-post-create">
      <div class="modal-wrapper">
        <div class="modal-container">

          <div class="modal-header">
            <span>Write Post</span>
            <i class='fa fa-close action action-close' @click="$emit('close')"></i>
          </div>

          <div class="modal-body">
            <textarea v-model="post_content" placeholder="Write your post..."></textarea>
          </div>

          <div class="modal-footer">
            <span v-if="ret_error" class="msg-error">{{ ret_msg }}</span>
            <button class="modal-default-button" @click="createPost" :disabled="!post_content.trim() || processing">
            Submit
            </button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>
<script>

import auth from '@/assets/js/auth'

export default {
  name: "post-create", 
  data: function() {
    return {
      post_content: "",
      processing: false, 
      ret_error: false,
      ret_msg: ""
    }
  },
  methods: {
    createPost: function() {
      this.processing = true
      this.ret_error = false

      var postData = {
        user_login: auth.getUserLogin(),
        access_token: auth.getAccessToken(),
        post_content: this.post_content
      }

      this.$http.post("/ssc/pofo/post/create", postData, {responseType: 'json'}).then(response => {
        var rtData = response.body;

        if(rtData.ret_code == 2) {
          this.ret_error = true
          this.ret_msg = rtData.ret_msg
        } else {  // ret_code == 1
          this.$emit('close')
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
mainColor = #33C3F0

.modal-post-create
  .modal-container 
    width 600px
    margin-top 200px    
  .modal-header
    border-bottom 1px solid #ddd
    span 
      font-weight bold
      color mainColor
    .action
      float right
      color #999
      &:hover 
        color mainColor
        cursor pointer
  .modal-body 
    textarea
      width 100%
      height 150px
      resize none
  .modal-footer
    &:after
      content ""
      clear both
      display table
    button.modal-default-button
      margin-bottom 0
</style>
