import { useUserStore } from '@/stores/userStore'

// 路由守卫
export const setupRouteGuards = (router: any) => {
  router.beforeEach(async (to: any, from: any, next: any) => {
    const userStore = useUserStore()
    
    // 白名单路径（无需登录即可访问）
    const whiteList = ['/login', '/register', '/']
    
    // 如果在白名单中，直接访问
    if (whiteList.includes(to.path)) {
      next()
      return
    }
    
    // 检查用户是否已登录
    if (!userStore.isAuthenticated) {
      try {
        // 尝试获取用户信息
        await userStore.fetchCurrentUser()
        // 登录成功后重定向到对应角色的仪表盘
        const primaryRole = userStore.user?.roles?.[0] || 'STUDENT'
        switch (primaryRole) {
          case 'TEACHER':
            if (to.path === '/' || to.path === '/dashboard') {
              next('/teacher/dashboard')
              return
            }
            break
          case 'ADMIN':
            if (to.path === '/' || to.path === '/dashboard') {
              next('/admin/dashboard')
              return
            }
            break
          default:
            if (to.path === '/' || to.path === '/dashboard') {
              next('/student/dashboard')
              return
            }
        }
        next()
      } catch (error) {
        // 获取用户信息失败，跳转到登录页
        next('/login')
        return
      }
    } else {
      // 用户已登录，检查是否有访问权限
      next()
    }
  })
}