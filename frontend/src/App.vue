<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { ref, onMounted } from 'vue'
import { isAuthenticated } from '@/services/authService'
import { useRoute } from 'vue-router'

const isLoggedIn = ref(false)
const $route = useRoute()

onMounted(() => {
  isLoggedIn.value = isAuthenticated()
})

// 监听路由变化时检查登录状态
window.addEventListener('storage', () => {
  isLoggedIn.value = isAuthenticated()
})

// 判断是否使用仪表板布局
const useDashboardLayout = () => {
  // 如果路由有meta信息并且layout为dashboard，则使用仪表板布局
  if ($route.meta && $route.meta.layout === 'dashboard') {
    return true;
  }
  
  // 如果用户已登录且不是首页、登录页、注册页，则使用仪表板布局
  return isLoggedIn.value && 
         $route.path !== '/' && 
         $route.path !== '/login' && 
         $route.path !== '/register';
}
</script>

<template>
  <div class="app-layout">
    <!-- 使用仪表板布局 -->
    <template v-if="useDashboardLayout()">
      <RouterView />
    </template>
    
    <!-- 使用横幅式导航栏布局 -->
    <template v-else>
      <header class="banner-header">
        <div class="header-content">
          <h1 class="logo">在线学习平台</h1>
          <nav class="navigation">
            <RouterLink to="/">首页</RouterLink>
            <template v-if="!isLoggedIn">
              <RouterLink to="/login">登录</RouterLink>
              <RouterLink to="/register">注册</RouterLink>
            </template>
            <template v-else>
              <RouterLink to="/dashboard">仪表板</RouterLink>
              <a href="#" @click="() => {
                localStorage.removeItem('token');
                isLoggedIn = false;
              }">退出</a>
            </template>
          </nav>
        </div>
      </header>
      
      <!-- 页面内容 -->
      <main class="main-content">
        <RouterView />
      </main>
    </template>
  </div>
</template>

<style scoped>
.app-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.banner-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
}

.logo {
  margin: 0;
  font-size: 1.5rem;
  font-weight: bold;
}

.navigation {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.navigation a.router-link-exact-active {
  color: #ffd700;
  font-weight: bold;
}

.navigation a {
  display: inline-block;
  padding: 0.5rem 1rem;
  text-decoration: none;
  border-radius: 4px;
  color: rgba(255, 255, 255, 0.9);
  transition: background-color 0.3s ease, color 0.3s ease;
}

.navigation a:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
}

.main-content {
  flex: 1;
  width: 100%;
}

@media (min-width: 1024px) {
  .header-content {
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    width: 100%;
  }

  .navigation {
    text-align: right;
    margin-left: auto;
    font-size: 16px;
    padding: 0;
    margin-top: 0;
  }
}
</style>