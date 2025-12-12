<template>
  <DashboardLayout>
    <div class="dashboard-container">
      <div class="welcome-section">
        <h1>管理员您好，{{ user.nickname || user.username }}！</h1>
        <p>系统运行状态良好</p>
      </div>

      <div class="stats-card">
        <h2>系统概览</h2>
        <div class="stats-grid">
          <div class="stat-item">
            <span class="stat-value">126</span>
            <span class="stat-label">总课程数</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">8540</span>
            <span class="stat-label">注册用户</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">24</span>
            <span class="stat-label">待审核内容</span>
          </div>
        </div>
      </div>

      <div class="management-section">
        <div class="section-header">
          <h2>管理系统</h2>
        </div>
        <div class="management-grid">
          <div class="management-card" @click="navigateTo('/admin/users')">
            <div class="card-icon">👥</div>
            <h3>用户管理</h3>
            <p>管理平台用户和权限</p>
          </div>
          <div class="management-card" @click="navigateTo('/admin/courses')">
            <div class="card-icon">📚</div>
            <h3>课程管理</h3>
            <p>审核和管理课程内容</p>
          </div>
          <div class="management-card" @click="navigateTo('/admin/community')">
            <div class="card-icon">💬</div>
            <h3>社区管理</h3>
            <p>监管社区内容和活动</p>
          </div>
          <div class="management-card" @click="navigateTo('/analytics')">
            <div class="card-icon">📈</div>
            <h3>数据分析</h3>
            <p>查看平台运营数据</p>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import DashboardLayout from '@/layouts/DashboardLayout.vue'

const userStore = useUserStore()
const router = useRouter()

const user = userStore.user || {
  id: 0,
  username: '',
  nickname: '',
  email: '',
  avatar: ''
}

const navigateTo = (path: string) => {
  router.push(path)
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.welcome-section {
  margin-bottom: 30px;
}

.welcome-section h1 {
  color: #333;
  font-size: 1.8rem;
  margin: 0;
}

.welcome-section p {
  color: #666;
  margin: 5px 0 0 0;
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

.section-header {
  margin-bottom: 20px;
}

.section-header h2 {
  margin: 0;
  color: #333;
  font-size: 1.5rem;
}

.management-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.management-card {
  background: white;
  border-radius: 8px;
  padding: 2rem 1.5rem;
  text-align: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.management-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.card-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.management-card h3 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 1.2rem;
}

.management-card p {
  color: #666;
  margin: 0;
  font-size: 0.9rem;
}
</style>