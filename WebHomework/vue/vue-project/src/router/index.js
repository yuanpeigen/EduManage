import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from '../views/LoginPage.vue'
import RegisterPage from '../views/RegisterPage.vue'
import HomePage from '../views/HomePage.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Manager',
    component: HomePage,
  },
  {
    // 重定向到主页
    path: '/',
    name: 'Login',
    component: LoginPage,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginPage,

  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterPage
  },
]

const router = new VueRouter({
  routes
})

export default router
