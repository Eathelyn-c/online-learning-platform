import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DashboardView from '../views/DashboardView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: { layout: 'banner' }
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashboardView,
      meta: { layout: 'dashboard' }
    },
    {
      path: '/courses',
      name: 'courses',
      component: () => import('../views/CoursesView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
      meta: { layout: 'banner' }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue'),
      meta: { layout: 'banner' }
    },
  ],
})

export default router