<template>
  <DashboardLayout>
    <div class="courses-container">
      <div class="page-header">
        <h1>我的课程</h1>
      </div>

      <div class="courses-filter">
        <input type="text" placeholder="搜索我的课程..." class="search-input">
        <select class="category-select">
          <option value="">所有分类</option>
          <option value="frontend">前端开发</option>
          <option value="backend">后端开发</option>
          <option value="mobile">移动开发</option>
          <option value="database">数据库</option>
        </select>
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
              <span class="instructor">讲师: {{ course.instructor }}</span>
              <span class="progress-text">{{ course.progress }}% 完成</span>
            </div>
            <div class="progress-bar">
              <div class="progress-fill" :style="{ width: course.progress + '%' }"></div>
            </div>
            <div class="course-actions">
              <button class="btn btn-primary" @click="continueLearning(course)">继续学习</button>
              <button class="btn btn-outline" @click="viewDetails(course)">查看详情</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import DashboardLayout from '@/layouts/DashboardLayout.vue'

const courses = [
  {
    id: 1,
    title: 'Vue.js 从入门到精通',
    description: '从基础概念到实战项目，全面掌握Vue.js开发技能',
    instructor: '张三',
    image: '',
    progress: 30
  },
  {
    id: 2,
    title: 'Spring Boot 实战教程',
    description: '深入学习Spring Boot框架，快速构建企业级应用',
    instructor: '李四',
    image: '',
    progress: 75
  },
  {
    id: 3,
    title: 'React 全栈开发',
    description: '掌握React全家桶，构建现代化Web应用',
    instructor: '王五',
    image: '',
    progress: 10
  },
  {
    id: 4,
    title: 'Python 数据分析',
    description: '使用Python进行数据分析和可视化',
    instructor: '赵六',
    image: '',
    progress: 0
  }
]

const continueLearning = (course: any) => {
  console.log('继续学习课程:', course.title)
  // 这里可以导航到具体的课程学习页面
}

const viewDetails = (course: any) => {
  console.log('查看课程详情:', course.title)
  // 这里可以导航到课程详情页面
}
</script>

<style scoped>
.courses-container {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.page-header h1 {
  margin: 0;
  color: #333;
}

.courses-filter {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
}

.search-input,
.category-select {
  padding: 8px 12px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  font-size: 14px;
}

.search-input:focus,
.category-select:focus {
  outline: none;
  border-color: #409eff;
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
  margin-bottom: 10px;
  font-size: 0.9rem;
  color: #999;
}

.progress-bar {
  width: 100%;
  height: 6px;
  background-color: #e0e0e0;
  border-radius: 3px;
  overflow: hidden;
  margin-bottom: 15px;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #409eff, #66b1ff);
  border-radius: 3px;
  transition: width 0.3s ease;
}

.course-actions {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
  flex: 1;
  text-align: center;
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
</style>