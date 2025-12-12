import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getCurrentUser } from '@/services/authService'

export const useUserStore = defineStore('user', () => {
  const user = ref<any>(null)
  const isAuthenticated = ref<boolean>(false)
  
  // 获取当前用户信息
  const fetchCurrentUser = async () => {
    try {
      const userData = await getCurrentUser()
      user.value = userData
      isAuthenticated.value = true
      return userData
    } catch (error) {
      user.value = null
      isAuthenticated.value = false
      throw error
    }
  }
  
  // 设置用户信息
  const setUser = (userData: any) => {
    user.value = userData
    isAuthenticated.value = true
  }
  
  // 清除用户信息
  const clearUser = () => {
    user.value = null
    isAuthenticated.value = false
  }
  
  return { 
    user, 
    isAuthenticated, 
    fetchCurrentUser, 
    setUser, 
    clearUser 
  }
})