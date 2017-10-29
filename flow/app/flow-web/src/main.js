import Vue from 'vue'

import VueRouter from 'vue-router'
Vue.use(VueRouter)

import VueResource from 'vue-resource'
Vue.use(VueResource)

import VeeValidate, { Validator } from 'vee-validate'
Vue.use(VeeValidate, {
  locale: 'zh_CN'
})

import messages from 'vee-validate/dist/locale/zh_CN'
Validator.addLocale(messages)

import { dictionary } from './assets/locale/dictionary'
Validator.updateDictionary(dictionary)

import '@/assets/skeleton/skeleton.css'
import '@/assets/font-awesome/css/font-awesome.min.css'
import '@/assets/css/custom.css'

import auth from '@/assets/js/auth'
import App from '@/components/App'
import Signup from '@/components/Signup'
import Home from '@/components/Home'
import Login from '@/components/Login'
import Timeline from '@/components/post/Timeline'
import UserUpdate from '@/components/fnd/UserUpdate'

const router = new VueRouter({
  mode: 'hash',
  base: __dirname,
  routes: [
  	{ path: '/signup', component: Signup },
    { path: '/login', component: Login },
    { path: '/timeline', component: Timeline },
    { path: '/user/update', component: UserUpdate },
    { path: '/', component: Home },
  ]
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
