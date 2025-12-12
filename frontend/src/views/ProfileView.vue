<template>
  <DashboardLayout>
    <div class="profile-container">
      <h1 class="page-title">个人资料</h1>

      <div class="profile-content">
        <!-- 用户信息表单 -->
        <div class="profile-section">
          <h2>基本信息</h2>
          <form @submit.prevent="updateProfile" class="profile-form">
            <div class="form-group">
              <label for="username">用户名</label>
              <input
                id="username"
                v-model="userInfo.username"
                type="text"
                disabled
                class="form-control"
              />
            </div>

            <div class="form-group">
              <label for="email">邮箱</label>
              <input
                id="email"
                v-model="userInfo.email"
                type="email"
                disabled
                class="form-control"
              />
            </div>

            <div class="form-group">
              <label for="nickname">昵称</label>
              <input
                id="nickname"
                v-model="form.nickname"
                type="text"
                class="form-control"
                placeholder="请输入昵称"
              />
            </div>

            <div class="form-group">
              <label for="phone">手机号</label>
              <input
                id="phone"
                v-model="form.phone"
                type="tel"
                class="form-control"
                placeholder="请输入手机号"
              />
            </div>

            <div class="form-group">
              <label>性别</label>
              <div class="radio-group">
                <label class="radio-label">
                  <input
                    v-model="form.gender"
                    type="radio"
                    :value="1"
                    class="radio-input"
                  /> 男
                </label>
                <label class="radio-label">
                  <input
                    v-model="form.gender"
                    type="radio"
                    :value="2"
                    class="radio-input"
                  /> 女
                </label>
                <label class="radio-label">
                  <input
                    v-model="form.gender"
                    type="radio"
                    :value="0"
                    class="radio-input"
                  /> 保密
                </label>
              </div>
            </div>

            <div class="form-group">
              <label for="birthday">生日</label>
              <input
                id="birthday"
                v-model="form.birthday"
                type="date"
                class="form-control"
              />
            </div>

            <div class="form-actions">
              <button type="submit" class="btn btn-primary" :disabled="isUpdating">
                {{ isUpdating ? '更新中...' : '更新信息' }}
              </button>
            </div>
          </form>
        </div>

        <!-- 修改密码表单 -->
        <div class="profile-section">
          <h2>修改密码</h2>
          <form @submit.prevent="changePasswordHandler" class="password-form">
            <div class="form-group">
              <label for="oldPassword">原密码</label>
              <input
                id="oldPassword"
                v-model="passwordForm.oldPassword"
                type="password"
                class="form-control"
                placeholder="请输入原密码"
                required
              />
            </div>

            <div class="form-group">
              <label for="newPassword">新密码</label>
              <input
                id="newPassword"
                v-model="passwordForm.newPassword"
                type="password"
                class="form-control"
                placeholder="请输入新密码"
                required
              />
            </div>

            <div class="form-group">
              <label for="confirmPassword">确认新密码</label>
              <input
                id="confirmPassword"
                v-model="passwordForm.confirmPassword"
                type="password"
                class="form-control"
                placeholder="请再次输入新密码"
                required
              />
            </div>

            <div class="form-actions">
              <button type="submit" class="btn btn-primary" :disabled="isChangingPassword">
                {{ isChangingPassword ? '提交中...' : '修改密码' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import DashboardLayout from '@/layouts/DashboardLayout.vue'
import {changePassword, getCurrentUser, updateUserInfo} from '@/services/authService'

const router = useRouter()

// 用户信息
const userInfo = ref({
  id: 0,
  username: '',
  email: '',
  nickname: '',
  phone: '',
  gender: 0,
  birthday: ''
})

// 表单数据
const form = reactive({
  nickname: '',
  phone: '',
  gender: 0,
  birthday: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 状态
const isUpdating = ref(false)
const isChangingPassword = ref(false)

// 获取用户信息
const loadUserInfo = async () => {
  try {
    const userData = await getCurrentUser()
    userInfo.value = userData

    // 初始化表单数据
    form.nickname = userData.nickname || ''
    form.phone = userData.phone || ''
    form.gender = userData.gender || 0
    form.birthday = userData.birthday || ''
  } catch (error) {
    console.error('获取用户信息失败:', error)
    alert('获取用户信息失败')
  }
}

// 更新用户信息
const updateProfile = async () => {
  isUpdating.value = true
  try {
    // 构造更新数据对象
    const updateData: any = {}
    if (form.nickname) updateData.nickname = form.nickname
    if (form.phone) updateData.phone = form.phone
    if (form.gender !== undefined) updateData.gender = form.gender
    if (form.birthday) updateData.birthday = form.birthday

    const response = await updateUserInfo(updateData)

    if (response === '更新成功') {
      alert('用户信息更新成功')
      // 重新加载用户信息
      await loadUserInfo()
    } else {
      alert('更新失败: ' + response)
    }
  } catch (error: any) {
    console.error('更新用户信息失败:', error)
    alert('更新失败: ' + (error.response?.data || error.message))
  } finally {
    isUpdating.value = false
  }
}

// 修改密码
const changePasswordHandler = async () => {
  // 验证密码确认
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    alert('新密码和确认密码不一致')
    return
  }

  isChangingPassword.value = true
  try {
    const response = await changePassword({
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    })

    if (response === '修改成功') {
      alert('密码修改成功')
      // 清空密码表单
      passwordForm.oldPassword = ''
      passwordForm.newPassword = ''
      passwordForm.confirmPassword = ''
    } else {
      alert('修改失败: ' + response)
    }
  } catch (error: any) {
    console.error('修改密码失败:', error)
    alert('修改失败: ' + (error.response?.data || error.message))
  } finally {
    isChangingPassword.value = false
  }
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped>
.profile-container {
  padding: 20px;
}

.page-title {
  color: #333;
  margin-bottom: 30px;
  font-size: 1.8rem;
}

.profile-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
}

@media (max-width: 768px) {
  .profile-content {
    grid-template-columns: 1fr;
  }
}

.profile-section {
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.profile-section h2 {
  margin-top: 0;
  color: #333;
  font-size: 1.4rem;
  border-bottom: 1px solid #eee;
  padding-bottom: 12px;
  margin-bottom: 20px;
}

.profile-form,
.password-form {
  max-width: 500px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  font-weight: 500;
  color: #555;
}

.form-control {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.3s;
}

.form-control:focus {
  outline: none;
  border-color: #409eff;
}

.form-control:disabled {
  background-color: #f5f7fa;
  cursor: not-allowed;
}

.radio-group {
  display: flex;
  gap: 20px;
}

.radio-label {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.radio-input {
  margin-right: 6px;
}

.form-actions {
  margin-top: 30px;
}

.btn {
  padding: 10px 20px;
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

.btn-primary:hover:not(:disabled) {
  background-color: #66b1ff;
}

.btn-primary:disabled {
  background-color: #a0cfff;
  cursor: not-allowed;
}
</style>
