import axios from 'axios'

const API_BASE_URL = '/api'

// 设置默认的axios配置
axios.defaults.baseURL = API_BASE_URL
axios.defaults.headers.common['Content-Type'] = 'application/json'

// 添加请求拦截器
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      // 确保token被正确附加到请求头，不做任何修改
      console.log('Adding token to request header:', token)
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 添加响应拦截器
axios.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      // token过期或无效，清除本地存储并跳转到登录页
      localStorage.removeItem('token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// 登录
export const login = async (username: string, password: string) => {
  const response = await axios.post('/auth/login', { username, password })
  // 如果返回了token，则存储到localStorage
  if (response.data && response.data.token) {
    localStorage.setItem('token', response.data.token)
  }
  return response.data
}

// 注册
export const register = async (username: string, email: string, password: string, confirmPassword: string) => {
  const response = await axios.post('/auth/register', { username, email, password, confirmPassword })
  return response.data
}

// 获取当前用户信息
export const getCurrentUser = async () => {
  const response = await axios.get('/auth/userinfo')
  return response.data
}

// 检查是否已认证
export const isAuthenticated = () => {
  const token = localStorage.getItem('token')
  return !!token
}

// 登出
export const logout = async () => {
  try {
    const token = localStorage.getItem('token')
    if (token) {
      await axios.post('/auth/logout', {}, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
    }
  } catch (error) {
    console.error('退出登录时发生错误:', error)
  } finally {
    localStorage.removeItem('token')
  }
}
