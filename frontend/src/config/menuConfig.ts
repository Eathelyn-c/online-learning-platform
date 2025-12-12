// 菜单配置文件
export interface MenuItem {
  name: string;
  path: string;
  icon: string;
}

// 学生菜单
export const studentMenuItems: MenuItem[] = [
  { name: '仪表板', path: '/student/dashboard', icon: '📊' },
  { name: '我的课程', path: '/student/courses', icon: '📚' },
  { name: '学习进度', path: '/student/learning', icon: '📖' },
  { name: '考试中心', path: '/student/exams', icon: '📝' },
  { name: '社区交流', path: '/student/community', icon: '💬' },
  { name: '个人资料', path: '/profile', icon: '👤' }
];

// 教师菜单
export const teacherMenuItems: MenuItem[] = [
  { name: '仪表板', path: '/teacher/dashboard', icon: '📊' },
  { name: '课程管理', path: '/teacher/courses', icon: '📚' },
  { name: '题库管理', path: '/teacher/questions', icon: '❓' },
  { name: '试卷管理', path: '/teacher/papers', icon: '📋' },
  { name: '学生管理', path: '/teacher/students', icon: '👨‍🎓' },
  { name: '资源管理', path: '/teacher/resources', icon: '📂' },
  { name: '个人资料', path: '/profile', icon: '👤' }
];

// 管理员菜单
export const adminMenuItems: MenuItem[] = [
  { name: '仪表板', path: '/admin/dashboard', icon: '📊' },
  { name: '用户管理', path: '/admin/users', icon: '👥' },
  { name: '课程管理', path: '/admin/courses', icon: '📚' },
  { name: '社区管理', path: '/admin/community', icon: '💬' },
  { name: '统计分析', path: '/analytics', icon: '📈' },
  { name: '系统设置', path: '/admin/settings', icon: '⚙️' },
  { name: '个人资料', path: '/profile', icon: '👤' }
];

// 根据角色获取菜单项
export const getMenuItemsByRole = (role: string): MenuItem[] => {
  switch (role) {
    case 'STUDENT':
      return studentMenuItems;
    case 'TEACHER':
      return teacherMenuItems;
    case 'ADMIN':
      return adminMenuItems;
    default:
      return studentMenuItems; // 默认返回学生菜单
  }
};