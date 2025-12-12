<template>
  <DashboardLayout>
    <div class="courses-container">
      <div class="page-header">
        <h1>课程管理</h1>
        <button class="btn btn-primary">添加课程</button>
      </div>

      <div class="courses-filter">
        <input type="text" placeholder="搜索课程..." class="search-input">
        <select class="category-select">
          <option value="">所有分类</option>
          <option value="frontend">前端开发</option>
          <option value="backend">后端开发</option>
          <option value="mobile">移动开发</option>
          <option value="database">数据库</option>
        </select>
      </div>

      <div class="courses-table">
        <table>
          <thead>
            <tr>
              <th>课程名称</th>
              <th>分类</th>
              <th>讲师</th>
              <th>学生数</th>
              <th>评分</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="course in courses" :key="course.id">
              <td>{{ course.name }}</td>
              <td>{{ course.category }}</td>
              <td>{{ course.instructor }}</td>
              <td>{{ course.students }}</td>
              <td>{{ course.rating }}</td>
              <td>
                <button class="btn btn-sm btn-outline">编辑</button>
                <button class="btn btn-sm btn-danger">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import DashboardLayout from '@/layouts/DashboardLayout.vue'

const courses = [
  {
    id: 1,
    name: 'Vue.js 从入门到精通',
    category: '前端开发',
    instructor: '张三',
    students: 1200,
    rating: 4.8
  },
  {
    id: 2,
    name: 'Spring Boot 实战教程',
    category: '后端开发',
    instructor: '李四',
    students: 980,
    rating: 4.9
  },
  {
    id: 3,
    name: 'React 全栈开发',
    category: '前端开发',
    instructor: '王五',
    students: 750,
    rating: 4.7
  },
  {
    id: 4,
    name: 'Python 数据分析',
    category: '数据科学',
    instructor: '赵六',
    students: 1100,
    rating: 4.6
  }
]
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

.btn-danger {
  background-color: #f56c6c;
  color: white;
}

.btn-danger:hover {
  background-color: #f78989;
}

.btn-sm {
  padding: 4px 8px;
  font-size: 12px;
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

.courses-table {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background-color: #f5f7fa;
  font-weight: 500;
  color: #909399;
}

tbody tr:hover {
  background-color: #f5f7fa;
}

tbody tr:last-child td {
  border-bottom: none;
}
</style>