<template>
  <div class="login-container">
    <div class="login-form">
      <h2>用户登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">用户名/邮箱:</label>
          <input
            id="username"
            v-model="loginForm.username"
            type="text"
            required
            placeholder="请输入用户名或邮箱"
          />
        </div>

        <div class="form-group">
          <label for="password">密码:</label>
          <input
            id="password"
            v-model="loginForm.password"
            type="password"
            required
            placeholder="请输入密码"
          />
        </div>

        <button type="submit" class="login-button" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>

        <div class="form-footer">
          <router-link to="/register">没有账户？立即注册</router-link>
        </div>
      </form>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/services/authService'

const router = useRouter()

const loginForm = ref({
  username: '',
  password: ''
})

const errorMessage = ref('')
const loading = ref(false)

const handleLogin = async () => {
  try {
    loading.value = true
    errorMessage.value = ''
    
    const data = await login(loginForm.value.username, loginForm.value.password)

    if (data.token) {
      // 存储token到localStorage
      localStorage.setItem('token', data.token)
      // 跳转到仪表板页面
      router.push('/dashboard')
    } else {
      errorMessage.value = '登录失败'
    }
  } catch (error: any) {
    console.error('Login error:', error)
    errorMessage.value = error.response?.data || '登录失败'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-form {
  width: 100%;
  max-width: 400px;
  padding: 2rem;
  background: white;
  border-radius: 8px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.login-form:hover {
  transform: translateY(-5px);
}

.login-form h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
  font-size: 1.8rem;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #555;
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.login-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 1rem;
  transition: background-color 0.3s ease;
}

.login-button:hover:not(:disabled) {
  background-color: #337ecc;
}

.login-button:disabled {
  background-color: #a0cfff;
  cursor: not-allowed;
}

.form-footer {
  text-align: center;
  margin-top: 1rem;
}

.form-footer a {
  color: #409eff;
  text-decoration: none;
  transition: color 0.3s ease;
}

.form-footer a:hover {
  color: #337ecc;
  text-decoration: underline;
}

.error-message {
  color: #f56c6c;
  text-align: center;
  margin-top: 1rem;
  padding: 0.5rem;
  background-color: #fef0f0;
  border-radius: 4px;
}
</style>