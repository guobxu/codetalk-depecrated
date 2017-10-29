<template>
  <div id="app">
    <!-- nav -->
    <div class="wrapper-nav">
      <ul class="nav container">
        <li>
          <router-link to="/">Home</router-link>
          <router-link v-if="loggedIn" to="/timeline">Timeline</router-link>
        </li>
        <li class="pull-right" v-if="loggedIn">
          <ul class="sub-nav">
            <li>
              <a href="javascript:void(0);" v-if="loggedIn" @click="showUserPopover = !showUserPopover">
                {{ userLogin }}
              </a>
              <popover-user v-if="showUserPopover" v-bind:userLogin="userLogin" @logout="logout"></popover-user>
            </li>
            <li>
              <a v-if="loggedIn" @click="showModal = true" href="javascript:void(0);">
                <i class="fa fa-pencil action action-to-post"></i>
              </a>
            </li>
          </ul>          
        </li>
        <li class="pull-right" v-if="loggedIn">
          
        </li>
        <li class="pull-right" v-if="!loggedIn">
          <router-link v-if="!loggedIn" to="/login">Log in</router-link>
          <router-link v-if="!loggedIn" to="/signup">Sign up</router-link>
        </li>
      </ul>
    </div>
    <!-- content -->
    <div class="wrapper-content">
      <template v-if="$route.matched.length"> <!-- 匹配某个router-link -->
        <router-view></router-view>
      </template>
      <template v-else><!-- 不匹配任何router-link -->
        <p>You are logged {{ loggedIn ? 'in' : 'out' }}</p>
      </template>
    </div>
    <component v-if="showModal" @close="showModal = false" :is="modalView"></component>
  </div>
</template>

<script>
import auth from '@/assets/js/auth'
import PostCreate from '@/components/post/modals/PostCreate'
import UserPopover from '@/components/fnd/popover/UserPopover'

export default {
  name: "app",
  components: {
    'post-create': PostCreate,
    'popover-user': UserPopover
  },
  data: function() {
    return {
      loggedIn: auth.isLoggedIn(), 
      userLogin: auth.getUserLogin(),
      showModal: false,
      modalView: "post-create",
      showUserPopover: false
    }
  },

  methods: {

    logout: function() {
      auth.logout()

      this.loggedIn = false
    },

  }
}
</script>