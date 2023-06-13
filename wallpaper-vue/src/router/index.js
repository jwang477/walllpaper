import Vue from 'vue'
import VueRouter from 'vue-router'
import Manger from '../views/Manger.vue'
import Home from "@/components/Home";
import User from "@/components/User";
import Category from "@/components/Category";
import Login from "@/views/Login";
import Wall from "@/components/Wall";
import WallDetail from "@/components/WallDetail";
import WallAudit from "@/components/WallAudit";
import CommentAudit from "@/components/CommentAudit";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'manger',
        component: Manger,
        redirect: '/home',
        children: [
            {
                name: 'home',
                path: 'home',
                component: Home
            }, {
                name: 'user',
                path: 'user',
                component: User
            }, {
                name: 'category',
                path: 'category',
                component: Category
            }, {
                name: 'wall',
                path: 'wall',
                component: Wall
            }
            , {
                name: 'wallDetail',
                path: 'wallDetail',
                component: WallDetail
            },
            , {
                name: 'wallAudit',
                path: 'wallAudit',
                component: WallAudit
            }, {
                name: 'commentAudit',
                path: 'commentAudit',
                component: CommentAudit
            }
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: Login,

    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
