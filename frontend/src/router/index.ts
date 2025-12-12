import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import StudentDashboardView from '../views/student/StudentDashboardView.vue'

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
      component: StudentDashboardView,
      meta: { layout: 'dashboard' }
    },
    {
      path: '/student/dashboard',
      name: 'student-dashboard',
      component: () => import('../views/student/StudentDashboardView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/student/courses',
      name: 'student-courses',
      component: () => import('../views/student/StudentCoursesView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/student/learning',
      name: 'student-learning',
      component: () => import('../views/student/StudentLearningView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/student/exams',
      name: 'student-exams',
      component: () => import('../views/student/StudentExamsView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/student/community',
      name: 'student-community',
      component: () => import('../views/student/StudentCommunityView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/teacher/dashboard',
      name: 'teacher-dashboard',
      component: () => import('../views/teacher/TeacherDashboardView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/teacher/courses',
      name: 'teacher-courses',
      component: () => import('../views/teacher/TeacherCoursesView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/teacher/questions',
      name: 'teacher-questions',
      component: () => import('../views/teacher/TeacherQuestionsView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/teacher/papers',
      name: 'teacher-papers',
      component: () => import('../views/teacher/TeacherPapersView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/teacher/students',
      name: 'teacher-students',
      component: () => import('../views/teacher/TeacherStudentsView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/teacher/resources',
      name: 'teacher-resources',
      component: () => import('../views/teacher/TeacherResourcesView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/admin/dashboard',
      name: 'admin-dashboard',
      component: () => import('../views/admin/AdminDashboardView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/admin/users',
      name: 'admin-users',
      component: () => import('../views/admin/AdminUsersView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/admin/courses',
      name: 'admin-courses',
      component: () => import('../views/admin/AdminCoursesView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/admin/community',
      name: 'admin-community',
      component: () => import('../views/admin/AdminCommunityView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/admin/settings',
      name: 'admin-settings',
      component: () => import('../views/admin/AdminSettingsView.vue'),
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
    // 其他路由
    {
      path: '/analytics',
      name: 'analytics',
      component: () => import('../views/AnalyticsView.vue'),
      meta: { layout: 'dashboard' }
    },
    {
      path: '/unauthorized',
      name: 'unauthorized',
      component: () => import('../views/UnauthorizedView.vue'),
      meta: { layout: 'dashboard' }
    }
  ],
})

export default router