<template>
  <div class="unauthorized-container">
    <h1>访问被拒绝</h1>
    <p>您没有权限访问此页面。</p>
    <router-link to="/dashboard">返回首页</router-link>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.unauthorized-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
}

.unauthorized-container h1 {
  color: #e74c3c;
  font-size: 2rem;
  margin-bottom: 1rem;
}

.unauthorized-container p {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 2rem;
}

.unauthorized-container a {
  color: #3498db;
  text-decoration: none;
  font-size: 1rem;
}

.unauthorized-container a:hover {
  text-decoration: underline;
}
</style>