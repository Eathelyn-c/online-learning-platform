<template>
  <div class="dashboard-layout">
    <!-- 顶部导航栏 -->
    <header class="top-navbar">
      <div class="navbar-left">
        <button class="menu-toggle" @click="toggleSidebar">
          <span class="bar"></span>
          <span class="bar"></span>
          <span class="bar"></span>
        </button>
        <div class="breadcrumb">
          <span v-for="(item, index) in breadcrumbs" :key="index">
            {{ item }}
            <span v-if="index < breadcrumbs.length - 1" class="separator">/</span>
          </span>
        </div>
      </div>
      <div class="navbar-right">
        <div class="user-info" @click="toggleUserMenu">
          <img :src="userAvatar" alt="User Avatar" class="avatar" />
          <span class="username">{{ username }}</span>
        </div>
        <div v-show="showUserMenu" class="user-menu">
          <button @click="handleProfile">个人资料</button>
          <button @click="handleLogout">退出登录</button>
        </div>
      </div>
    </header>

    <!-- 左侧边栏 -->
    <aside class="sidebar" :class="{ collapsed: isSidebarCollapsed }">
      <div class="sidebar-logo">
        <h2 v-if="!isSidebarCollapsed">学习平台</h2>
      </div>
      <nav class="sidebar-nav">
        <ul>
          <li v-for="item in displayedMenuItems" :key="item.name"
              :class="{ active: isActive(item.path) }"
              @click="navigateTo(item.path)">
            <i class="menu-icon">{{ item.icon }}</i>
            <span class="menu-text" v-if="!isSidebarCollapsed">{{ item.name }}</span>
          </li>
        </ul>
      </nav>
    </aside>

    <!-- 主内容区域 -->
    <main class="main-content" :class="{ 'sidebar-collapsed': isSidebarCollapsed }">
      <slot></slot>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { logout } from '@/services/authService'
import { getMenuItemsByRole } from '@/config/menuConfig'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 用户信息
const username = computed(() => userStore.user?.nickname || userStore.user?.username || '用户')
const userAvatar = ref('https://via.placeholder.com/40') // 占位图

// 侧边栏状态
const isSidebarCollapsed = ref(false)
const showUserMenu = ref(false)

// 面包屑导航
const breadcrumbs = ref<string[]>(['首页'])

// 根据用户角色计算菜单项
const displayedMenuItems = computed(() => {
  if (!userStore.user || !userStore.user.roles || userStore.user.roles.length === 0) {
    return getMenuItemsByRole('STUDENT')
  }
  
  // 获取用户的主要角色（第一个角色）
  const primaryRole = userStore.user.roles[0]
  return getMenuItemsByRole(primaryRole)
})

// 切换侧边栏
const toggleSidebar = () => {
  isSidebarCollapsed.value = !isSidebarCollapsed.value
}

// 切换用户菜单
const toggleUserMenu = () => {
  showUserMenu.value = !showUserMenu.value
}

// 检查当前路径是否激活
const isActive = (path: string) => {
  return route.path === path
}

// 导航到指定路径
const navigateTo = (path: string) => {
  // 特殊处理仪表盘路径
  if (path === '/dashboard') {
    const primaryRole = userStore.user?.roles?.[0] || 'STUDENT'
    switch (primaryRole) {
      case 'TEACHER':
        router.push('/teacher/dashboard')
        return
      case 'ADMIN':
        router.push('/admin/dashboard')
        return
      default:
        router.push('/student/dashboard')
        return
    }
  }
  
  router.push(path)
}

// 个人资料
const handleProfile = () => {
  router.push('/profile')
  showUserMenu.value = false
}

// 退出登录
const handleLogout = async () => {
  try {
    await logout()
    userStore.clearUser()
  } catch (error) {
    console.error('退出登录时发生错误:', error)
  } finally {
    // 跳转到登录页
    router.push('/login')
  }
}

// 点击其他地方关闭用户菜单
const handleClickOutside = (event: MouseEvent) => {
  const userMenu = document.querySelector('.user-menu')
  const userInfo = document.querySelector('.user-info')

  if (userMenu && userInfo &&
      !userMenu.contains(event.target as Node) &&
      !userInfo.contains(event.target as Node)) {
    showUserMenu.value = false
  }
}

// 更新面包屑
const updateBreadcrumbs = () => {
  // 根据当前路由更新面包屑
  switch(route.path) {
    case '/':
    case '/dashboard':
    case '/student/dashboard':
      breadcrumbs.value = ['首页', '仪表板']
      break
    case '/student/courses':
      breadcrumbs.value = ['首页', '我的课程']
      break
    case '/student/learning':
      breadcrumbs.value = ['首页', '学习进度']
      break
    case '/student/exams':
      breadcrumbs.value = ['首页', '考试中心']
      break
    case '/student/community':
      breadcrumbs.value = ['首页', '社区交流']
      break
    case '/teacher/dashboard':
      breadcrumbs.value = ['首页', '教师仪表板']
      break
    case '/teacher/courses':
      breadcrumbs.value = ['首页', '课程管理']
      break
    case '/teacher/questions':
      breadcrumbs.value = ['首页', '题库管理']
      break
    case '/teacher/papers':
      breadcrumbs.value = ['首页', '试卷管理']
      break
    case '/teacher/students':
      breadcrumbs.value = ['首页', '学生管理']
      break
    case '/teacher/resources':
      breadcrumbs.value = ['首页', '资源管理']
      break
    case '/admin/dashboard':
      breadcrumbs.value = ['首页', '管理员仪表板']
      break
    case '/admin/users':
      breadcrumbs.value = ['首页', '用户管理']
      break
    case '/admin/courses':
      breadcrumbs.value = ['首页', '课程管理']
      break
    case '/admin/community':
      breadcrumbs.value = ['首页', '社区管理']
      break
    case '/admin/settings':
      breadcrumbs.value = ['首页', '系统设置']
      break
    case '/analytics':
      breadcrumbs.value = ['首页', '统计分析']
      break
    case '/profile':
      breadcrumbs.value = ['首页', '个人资料']
      break
    default:
      breadcrumbs.value = ['首页']
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
  updateBreadcrumbs()
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})

// 监听路由变化
router.afterEach(() => {
  updateBreadcrumbs()
  showUserMenu.value = false
})
</script>

<style scoped>
.dashboard-layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.top-navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}

.navbar-left {
  display: flex;
  align-items: center;
}

.menu-toggle {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  background: transparent;
  border: none;
  cursor: pointer;
  margin-right: 20px;
}

.bar {
  width: 20px;
  height: 2px;
  background-color: #333;
  margin: 2px 0;
  transition: 0.3s;
}

.breadcrumb {
  font-size: 14px;
  color: #666;
}

.separator {
  margin: 0 8px;
  color: #ccc;
}

.navbar-right {
  position: relative;
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.username {
  font-size: 14px;
  color: #333;
}

.user-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background: white;
  border-radius: 4px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 10px 0;
  min-width: 120px;
  z-index: 1001;
}

.user-menu button {
  display: block;
  width: 100%;
  text-align: left;
  padding: 8px 16px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 14px;
  color: #333;
}

.user-menu button:hover {
  background-color: #f5f5f5;
}

.sidebar {
  position: fixed;
  top: 60px;
  left: 0;
  bottom: 0;
  width: 220px;
  background-color: #2c3e50;
  color: white;
  transition: all 0.3s ease;
  z-index: 999;
  overflow-y: auto;
}

.sidebar.collapsed {
  width: 60px;
}

.sidebar-logo {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.sidebar-logo h2 {
  margin: 0;
  font-size: 18px;
}

.sidebar-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar-nav li {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.sidebar-nav li:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.sidebar-nav li.active {
  background-color: #3498db;
}

.menu-icon {
  font-size: 18px;
  margin-right: 15px;
  width: 20px;
  text-align: center;
}

.sidebar.collapsed .menu-text {
  display: none;
}

.sidebar.collapsed .menu-icon {
  margin-right: 0;
  font-size: 20px;
}

.main-content {
  flex: 1;
  margin-top: 60px;
  margin-left: 220px;
  padding: 20px;
  transition: margin-left 0.3s ease;
}

.main-content.sidebar-collapsed {
  margin-left: 60px;
}

@media (max-width: 768px) {
  .sidebar {
    width: 60px;
  }

  .sidebar:not(.collapsed) .menu-text {
    display: none;
  }

  .main-content {
    margin-left: 60px;
  }
}
</style>