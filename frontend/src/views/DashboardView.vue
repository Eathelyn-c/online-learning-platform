<template>
  <div class="dashboard-container">
    <header class="dashboard-header">
      <h1>欢迎回来，{{ user.nickname || user.username }}！</h1>
      <button class="logout-btn" @click="handleLogout">退出登录</button>
    </header>

    <main class="dashboard-main">
      <div class="stats-card">
        <h2>学习统计</h2>
        <div class="stats-grid">
          <div class="stat-item">
            <span class="stat-value">0</span>
            <span class="stat-label">已完成课程</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">0</span>
            <span class="stat-label">学习时长(小时)</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">0</span>
            <span class="stat-label">获得证书</span>
          </div>
        </div>
      </div>

      <div class="courses-section">
        <h2>我的课程</h2>
        <div class="course-list">
          <div class="course-card" v-for="course in courses" :key="course.id">
            <div class="course-image">
              <img :src="course.image || '/placeholder-course.png'" :alt="course.title">
            </div>
            <div class="course-info">
              <h3>{{ course.title }}</h3>
              <p>{{ course.description }}</p>
              <div class="progress-bar">
                <div class="progress-fill" :style="{ width: course.progress + '%' }"></div>
              </div>
              <span class="progress-text">{{ course.progress }}% 完成</span>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCurrentUser, logout } from '@/services/authService'

const router = useRouter()
const user = ref({
  id: 0,
  username: '',
  nickname: '',
  email: '',
  avatar: ''
})

const courses = ref([
  {
    id: 1,
    title: 'Vue.js 基础教程',
    description: '学习Vue.js的核心概念和基础知识',
    image: '',
    progress: 30
  },
  {
    id: 2,
    title: 'Spring Boot 实战',
    description: '深入学习Spring Boot框架的应用',
    image: '',
    progress: 75
  },
  {
    id: 3,
    title: '数据库设计与优化',
    description: '掌握数据库设计原则和性能优化技巧',
    image: '',
    progress: 10
  }
])

onMounted(async () => {
  try {
    const userData = await getCurrentUser()
    user.value = userData
  } catch (error) {
    console.error('获取用户信息失败:', error)
    router.push('/login')
  }
})

const handleLogout = () => {
  logout()
  router.push('/login')
}
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.dashboard-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1.5rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.dashboard-header h1 {
  margin: 0;
  font-size: 1.8rem;
}

.logout-btn {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.logout-btn:hover {
  background-color: rgba(255, 255, 255, 0.3);
}

.dashboard-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.stats-card {
  background: white;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
}

.stats-card h2 {
  margin-top: 0;
  color: #333;
  font-size: 1.5rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-top: 1rem;
}

.stat-item {
  text-align: center;
  padding: 1rem;
  border-radius: 6px;
  background-color: #f8f9fa;
}

.stat-value {
  display: block;
  font-size: 2rem;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 0.5rem;
}

.stat-label {
  color: #666;
  font-size: 0.9rem;
}

.courses-section h2 {
  color: #333;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.course-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.course-card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.course-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.course-image {
  height: 150px;
  background-color: #eee;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}

.course-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.course-info {
  padding: 1.5rem;
}

.course-info h3 {
  margin: 0 0 0.5rem 0;
  color: #333;
  font-size: 1.2rem;
}

.course-info p {
  color: #666;
  font-size: 0.9rem;
  margin: 0 0 1rem 0;
  line-height: 1.5;
}

.progress-bar {
  width: 100%;
  height: 8px;
  background-color: #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
  margin-bottom: 0.5rem;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #409eff, #66b1ff);
  border-radius: 4px;
  transition: width 0.3s ease;
}

.progress-text {
  font-size: 0.8rem;
  color: #999;
}
</style>