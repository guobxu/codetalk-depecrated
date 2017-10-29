<template>
	<div class="wrapper-post">
    <div class="wrapper-user-profile" :style="profileBgStyle">
      <template v-else>
        <div class="wrapper-dummy-profile"></div>
      </template>
    </div>
    <div class="wrapper-post-content">
      <div class="wrapper-meta row">
        <span class="post-user-name">{{ post.user_name }}</span>
        <a class="post-user-link" href="#">@{{ post.user_login }}</a>
        <span class="post-date">{{ postDate }}</span>
      </div>
      <div class="wrapper-text row">
        <p>{{ post.post_content }}</p>
      </div>
      <div class="wrapper-stats row">
        <i class="action action-comment stat fa fa-comment-o four columns" aria-hidden="true">{{ post.post_comments }}</i>
        <i class="action action-repost stat fa fa-retweet four columns" aria-hidden="true">{{ post.post_refers }}</i>
        <i class="action action-like stat fa fa-thumbs-o-up four columns" aria-hidden="true">{{ post.post_likes }}</i>
      </div>
    </div>
  </div>
</template>

<script>

import util from "@/assets/js/util"

export default {
  name: "post-item",
  props: {
    post: {
      type: Object
    }
  },
  computed: {
    postDate: function() {
      return util.millisToDate(this.post.create_date)
    },
    profileBgStyle: function() {
      if( this.post.user_profile ) {
        return { backgroundImage : "url('" + this.post.user_profile + "')" }
      } else {
        return { background: '#ddd' }
      }
    }
  },
  methods: {
    
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
mainColor = #33C3F0

div.wrapper-post 
  padding 20px 0
  border-bottom 1px solid #eee
  div.wrapper-user-profile
    border-radius 50%
    height 75px
    width 75px
    background-size cover
    background-position center
    background-blend-mode multiply
    vertical-align middle
    text-align center
    color transparent
    transition all .3s ease
    float left
  div.wrapper-post-content
    width auto
    overflow hidden
    .wrapper-meta
      padding 3px 0
      span.post-date
        margin-left 9px
    .wrapper-text
      padding 6px 0
  div.wrapper-stats
    .action 
      color #999
      &:before 
        padding-right 6px
      &:hover 
        cursor pointer
        color mainColor
  &:after
    display table
    clear both
    content ""
</style>
