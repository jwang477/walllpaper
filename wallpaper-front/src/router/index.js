import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manger'
import Home from "@/components/Home";
import User from "@/components/User";
import Favorite from "@/components/Favorite";
import Login from "@/views/Login";
import Upload from "@/components/Upload";
import Register from "@/views/Register";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    redirect: '/home',
    children: [
      {
        name: 'home',
        path: 'home',
        component: Home
      }
      ,
      {
        path: '/user',
        name: 'user',
        component: User
      },
      {
        path: '/favorite',
        name: 'favorite',
        component: Favorite
      },
      {
        path: '/upload',
        name: 'upload',
        component: Upload
      },
      {
        path: '/register',
        name: 'register',
        component: Register
      },
      {
        path: '/login',
        name: 'login',
        component: Login
      }
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
