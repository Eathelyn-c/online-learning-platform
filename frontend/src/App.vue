<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { ref, onMounted } from 'vue'
import { isAuthenticated } from '@/services/authService'

const isLoggedIn = ref(false)

onMounted(() => {
  isLoggedIn.value = isAuthenticated()
})

// 监听路由变化时检查登录状态
window.addEventListener('storage', () => {
  isLoggedIn.value = isAuthenticated()
})
</script>

<template>
  <header>
    <div class="wrapper">
      <h1>在线学习平台</h1>
      <nav>
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
            $router.push('/login');
          }">退出</a>
        </template>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
}

nav {
  display: flex;
  align-items: center;
  gap: 1rem;
}

nav a.router-link-exact-active {
  color: #ffd700;
  font-weight: bold;
}

nav a {
  display: inline-block;
  padding: 0.5rem 1rem;
  text-decoration: none;
  border-radius: 4px;
  color: rgba(255, 255, 255, 0.9);
  transition: background-color 0.3s ease, color 0.3s ease;
}

nav a:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
    width: 100%;
  }

  nav {
    text-align: right;
    margin-left: auto;
    font-size: 16px;
    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>