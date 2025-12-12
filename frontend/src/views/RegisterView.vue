<template>
  <div class="register-container">
    <div class="register-form">
      <h2>用户注册</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="username">用户名:</label>
          <input 
            id="username" 
            v-model="registerForm.username" 
            type="text" 
            required 
            placeholder="请输入用户名"
            :class="{ 'is-invalid': errors.username }"
          />
          <div v-if="errors.username" class="invalid-feedback">
            {{ errors.username }}
          </div>
        </div>
        
        <div class="form-group">
          <label for="email">邮箱:</label>
          <input 
            id="email" 
            v-model="registerForm.email" 
            type="email" 
            required 
            placeholder="请输入邮箱"
            :class="{ 'is-invalid': errors.email }"
          />
          <div v-if="errors.email" class="invalid-feedback">
            {{ errors.email }}
          </div>
        </div>
        
        <div class="form-group">
          <label for="password">密码:</label>
          <input 
            id="password" 
            v-model="registerForm.password" 
            type="password" 
            required 
            placeholder="请输入密码"
            :class="{ 'is-invalid': errors.password }"
          />
          <div v-if="errors.password" class="invalid-feedback">
            {{ errors.password }}
          </div>
        </div>
        
        <div class="form-group">
          <label for="confirmPassword">确认密码:</label>
          <input 
            id="confirmPassword" 
            v-model="registerForm.confirmPassword" 
            type="password" 
            required 
            placeholder="请再次输入密码"
            :class="{ 'is-invalid': errors.confirmPassword }"
          />
          <div v-if="errors.confirmPassword" class="invalid-feedback">
            {{ errors.confirmPassword }}
          </div>
        </div>
        
        <button type="submit" class="register-button" :disabled="loading">
          {{ loading ? '注册中...' : '注册' }}
        </button>
        
        <div class="form-footer">
          <router-link to="/login">已有账户？立即登录</router-link>
        </div>
      </form>
      
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
      
      <div v-if="successMessage" class="success-message">
        {{ successMessage }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/services/authService'

const router = useRouter()

const registerForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const errors = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const errorMessage = ref('')
const successMessage = ref('')
const loading = ref(false)

const validateForm = () => {
  // 清除之前的错误
  Object.keys(errors).forEach(key => {
    (errors as any)[key] = ''
  })
  
  let isValid = true
  
  // 验证用户名
  if (!registerForm.username) {
    errors.username = '用户名不能为空'
    isValid = false
  } else if (registerForm.username.length < 3) {
    errors.username = '用户名至少3个字符'
    isValid = false
  }
  
  // 验证邮箱
  if (!registerForm.email) {
    errors.email = '邮箱不能为空'
    isValid = false
  } else if (!/\S+@\S+\.\S+/.test(registerForm.email)) {
    errors.email = '邮箱格式不正确'
    isValid = false
  }
  
  // 验证密码
  if (!registerForm.password) {
    errors.password = '密码不能为空'
    isValid = false
  } else if (registerForm.password.length < 6) {
    errors.password = '密码至少6个字符'
    isValid = false
  }
  
  // 验证确认密码
  if (!registerForm.confirmPassword) {
    errors.confirmPassword = '请确认密码'
    isValid = false
  } else if (registerForm.password !== registerForm.confirmPassword) {
    errors.confirmPassword = '两次输入的密码不一致'
    isValid = false
  }
  
  return isValid
}

const handleRegister = async () => {
  // 重置消息
  errorMessage.value = ''
  successMessage.value = ''
  
  // 表单验证
  if (!validateForm()) {
    return
  }
  
  try {
    loading.value = true
    
    const data = await register(
      registerForm.username, 
      registerForm.email, 
      registerForm.password, 
      registerForm.confirmPassword
    )
    
    if (data === '注册成功') {
      successMessage.value = '注册成功，请登录'
      // 清空表单
      Object.assign(registerForm, {
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
      })
      
      // 3秒后跳转到登录页面
      setTimeout(() => {
        router.push('/login')
      }, 3000)
    } else {
      errorMessage.value = data
    }
  } catch (error: any) {
    errorMessage.value = error.response?.data || '注册失败'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 70px);
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.register-form {
  width: 100%;
  max-width: 400px;
  padding: 2rem;
  background: white;
  border-radius: 8px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.register-form:hover {
  transform: translateY(-5px);
}

.register-form h2 {
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
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.form-group input.is-invalid {
  border-color: #f56c6c;
  box-shadow: 0 0 0 2px rgba(245, 108, 108, 0.2);
}

.invalid-feedback {
  color: #f56c6c;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.register-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #67c23a;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 1rem;
  transition: background-color 0.3s ease;
}

.register-button:hover:not(:disabled) {
  background-color: #5daf34;
}

.register-button:disabled {
  background-color: #b3e19d;
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

.success-message {
  color: #67c23a;
  text-align: center;
  margin-top: 1rem;
  padding: 0.5rem;
  background-color: #f0f9ec;
  border-radius: 4px;
}

@media (max-width: 768px) {
  .register-container {
    padding: 10px;
  }
  
  .register-form {
    padding: 1.5rem;
  }
}
</style>