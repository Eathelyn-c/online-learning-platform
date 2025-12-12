<template>
  <DashboardLayout>
    <div class="dashboard-container">
      <div class="welcome-section">
        <h1>欢迎回来，{{ user.nickname || user.username }}老师！</h1>
        <p>今天您想管理哪些课程呢？</p>
      </div>

      <div class="stats-card">
        <h2>教学统计</h2>
        <div class="stats-grid">
          <div class="stat-item">
            <span class="stat-value">8</span>
            <span class="stat-label">正在教授课程</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">1250</span>
            <span class="stat-label">总学生数</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">320</span>
            <span class="stat-label">待批改作业</span>
          </div>
        </div>
      </div>

      <div class="courses-section">
        <div class="section-header">
          <h2>我的课程</h2>
          <button class="btn btn-primary">创建新课程</button>
        </div>
        <div class="course-list">
          <div class="course-card" v-for="course in courses" :key="course.id">
            <div class="course-image">
              <img :src="course.image || '/placeholder-course.png'" :alt="course.title">
            </div>
            <div class="course-info">
              <h3>{{ course.title }}</h3>
              <p>{{ course.description }}</p>
              <div class="course-meta">
                <span class="students">学生: {{ course.students }}</span>
                <span class="rating">评分: {{ course.rating }}/5</span>
              </div>
              <div class="course-actions">
                <button class="btn btn-primary" @click="manageCourse(course)">管理课程</button>
                <button class="btn btn-outline" @click="viewAnalytics(course)">查看分析</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import DashboardLayout from '@/layouts/DashboardLayout.vue'

const userStore = useUserStore()

const user = userStore.user || {
  id: 0,
  username: '',
  nickname: '',
  email: '',
  avatar: ''
}

const courses = ref([
  {
    id: 1,
    title: 'Vue.js 从入门到精通',
    description: '从基础概念到实战项目，全面掌握Vue.js开发技能',
    image: '',
    students: 1200,
    rating: 4.8
  },
  {
    id: 2,
    title: 'Spring Boot 实战教程',
    description: '深入学习Spring Boot框架，快速构建企业级应用',
    image: '',
    students: 980,
    rating: 4.9
  },
  {
    id: 3,
    title: 'React 全栈开发',
    description: '掌握React全家桶，构建现代化Web应用',
    image: '',
    students: 750,
    rating: 4.7
  }
])

const manageCourse = (course: any) => {
  console.log('管理课程:', course.title)
  // 这里可以导航到课程管理页面
}

const viewAnalytics = (course: any) => {
  console.log('查看课程分析:', course.title)
  // 这里可以导航到课程分析页面
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
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h2 {
  margin: 0;
  color: #333;
  font-size: 1.5rem;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.btn-primary {
  background-color: #409eff;
  color: white;
}

.btn-primary:hover {
  background-color: #66b1ff;
}

.btn-outline {
  background-color: transparent;
  border: 1px solid #409eff;
  color: #409eff;
}

.btn-outline:hover {
  background-color: #ecf5ff;
}

.course-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
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
  height: 180px;
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
  padding: 20px;
}

.course-info h3 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 1.3rem;
}

.course-info p {
  color: #666;
  font-size: 0.9rem;
  margin: 0 0 15px 0;
  line-height: 1.5;
}

.course-meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  font-size: 0.9rem;
  color: #999;
}

.course-actions {
  display: flex;
  gap: 10px;
}
</style>