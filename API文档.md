# 在线学习平台后端API接口文档

## 1. 概述

本文档详细描述了在线学习平台的后端API接口，包括各接口的功能、请求方式、请求参数、返回结果等内容。该文档旨在为前端开发人员提供清晰的接口调用指引。

### 1.1 公共约定

#### 响应格式
```json
{
  "code": 200,        
  "message": "success", 
  "data": {}          
}
```

#### 状态码说明
| 状态码 | 说明 |
|-------|------|
| 200 | 请求成功 |
| 400 | 参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

#### 认证方式
除登录、注册等开放接口外，其他接口均需在HTTP Header中携带token：
```
Authorization: Bearer {token}
```

## 2. 用户管理模块

### 2.1 用户登录
#### 接口地址
`POST /api/auth/login`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | string | 是 | 用户名 |
| password | string | 是 | 密码 |

#### 响应示例
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": 1,
      "username": "student1",
      "email": "student1@example.com",
      "nickname": "学生1号",
      "avatar": "https://example.com/avatar.jpg"
    }
  }
}
```

### 2.2 用户注册
#### 接口地址
`POST /api/auth/register`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | string | 是 | 用户名 |
| email | string | 是 | 邮箱 |
| password | string | 是 | 密码 |
| confirmPassword | string | 是 | 确认密码 |

#### 响应示例
```json
{
  "code": 200,
  "message": "注册成功",
  "data": null
}
```

### 2.3 获取当前用户信息
#### 接口地址
`GET /api/user/info`

#### 请求参数
无

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "student1",
    "email": "student1@example.com",
    "nickname": "学生1号",
    "avatar": "https://example.com/avatar.jpg",
    "phone": "13800138000",
    "gender": 1,
    "birthday": "1990-01-01",
    "roles": ["STUDENT"]
  }
}
```

### 2.4 更新用户信息
#### 接口地址
`PUT /api/user/info`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| nickname | string | 否 | 昵称 |
| avatar | string | 否 | 头像URL |
| phone | string | 否 | 手机号 |
| gender | integer | 否 | 性别 |
| birthday | string | 否 | 生日 |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 2.5 修改密码
#### 接口地址
`PUT /api/user/password`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| oldPassword | string | 是 | 原密码 |
| newPassword | string | 是 | 新密码 |

#### 响应示例
```json
{
  "code": 200,
  "message": "修改成功",
  "data": null
}
```

### 2.6 退出登录
#### 接口地址
`POST /api/auth/logout`

#### 请求参数
无具体参数，但需要在请求头中携带 Authorization token

#### 响应示例
```json
{
  "code": 200,
  "message": "退出登录成功",
  "data": null
}
```

### 2.7 获取用户列表（管理员）
#### 接口地址
`GET /api/admin/users`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| keyword | string | 否 | 搜索关键词 |
| status | integer | 否 | 用户状态 |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "username": "student1",
        "email": "student1@example.com",
        "nickname": "学生1号",
        "avatar": "https://example.com/avatar.jpg",
        "status": 1,
        "roles": ["STUDENT"],
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "pages": 10
  }
}
```

### 2.8 更新用户状态（管理员）
#### 接口地址
`PUT /api/admin/users/{id}/status`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 用户ID |
| status | integer | 是 | 用户状态(0:禁用,1:启用) |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 2.9 分配用户角色（管理员）
#### 接口地址
`POST /api/admin/users/{id}/roles`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 用户ID |
| roleIds | array | 是 | 角色ID数组 |

#### 响应示例
```json
{
  "code": 200,
  "message": "分配成功",
  "data": null
}
```

## 3. 课程管理模块

### 3.1 获取课程列表
#### 接口地址
`GET /api/courses`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| categoryId | integer | 否 | 分类ID |
| keyword | string | 否 | 搜索关键词 |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |
| sortBy | string | 否 | 排序字段 |
| orderBy | string | 否 | 排序方式(asc/desc) |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "Java基础教程",
        "description": "本课程讲解Java基础知识",
        "coverImage": "https://example.com/course1.jpg",
        "categoryId": 1,
        "instructor": {
          "id": 2,
          "username": "teacher1",
          "nickname": "张老师"
        },
        "price": 99.00,
        "originPrice": 199.00,
        "level": 1,
        "recommend": 1,
        "publishedAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "pages": 10
  }
}
```

### 3.2 获取课程详情
#### 接口地址
`GET /api/courses/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 课程ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "Java基础教程",
    "description": "本课程讲解Java基础知识",
    "coverImage": "https://example.com/course1.jpg",
    "categoryId": 1,
    "instructor": {
      "id": 2,
      "username": "teacher1",
      "nickname": "张老师",
      "avatar": "https://example.com/avatar.jpg"
    },
    "price": 99.00,
    "originPrice": 199.00,
    "level": 1,
    "recommend": 1,
    "chapters": [
      {
        "id": 1,
        "title": "第一章 Java入门",
        "description": "介绍Java语言的基本概念",
        "sortOrder": 1,
        "lessons": [
          {
            "id": 1,
            "title": "1.1 Java简介",
            "description": "介绍Java的历史和发展",
            "duration": 1800,
            "freePreview": 1,
            "status": 1
          }
        ]
      }
    ],
    "progress": 25.50,
    "enrolled": true
  }
}
```

### 3.3 获取课程分类
#### 接口地址
`GET /api/course-categories`

#### 请求参数
无

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "编程语言",
      "parentId": 0,
      "children": [
        {
          "id": 2,
          "name": "Java",
          "parentId": 1,
          "children": []
        }
      ]
    }
  ]
}
```

### 3.4 发布课程（讲师）
#### 接口地址
`POST /api/instructor/courses`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | string | 是 | 课程标题 |
| description | string | 是 | 课程描述 |
| coverImage | string | 否 | 封面图片URL |
| categoryId | integer | 是 | 分类ID |
| price | number | 是 | 价格 |
| originPrice | number | 否 | 原价 |
| level | integer | 是 | 难度等级 |
| chapters | array | 是 | 章节信息 |

#### 响应示例
```json
{
  "code": 200,
  "message": "发布成功",
  "data": {
    "id": 1
  }
}
```

### 3.5 更新课程（讲师）
#### 接口地址
`PUT /api/instructor/courses/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 课程ID |
| title | string | 否 | 课程标题 |
| description | string | 否 | 课程描述 |
| coverImage | string | 否 | 封面图片URL |
| categoryId | integer | 否 | 分类ID |
| price | number | 否 | 价格 |
| originPrice | number | 否 | 原价 |
| level | integer | 否 | 难度等级 |
| chapters | array | 否 | 章节信息 |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 3.6 删除课程（讲师）
#### 接口地址
`DELETE /api/instructor/courses/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 课程ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 3.7 课程评价列表
#### 接口地址
`GET /api/courses/{id}/reviews`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 课程ID |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "content": "课程内容很丰富，讲解清晰",
        "rating": 5,
        "user": {
          "id": 1,
          "username": "student1",
          "nickname": "学生1号",
          "avatar": "https://example.com/avatar.jpg"
        },
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 10,
    "current": 1,
    "pages": 2
  }
}
```

### 3.8 提交课程评价
#### 接口地址
`POST /api/courses/{id}/reviews`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 课程ID |
| content | string | 是 | 评价内容 |
| rating | integer | 是 | 评分(1-5) |

#### 响应示例
```json
{
  "code": 200,
  "message": "评价成功",
  "data": {
    "id": 1
  }
}
```

## 4. 学习进度模块

### 4.1 获取用户课程学习进度
#### 接口地址
`GET /api/learning/progress/courses/{courseId}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| courseId | integer | 是 | 课程ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "courseId": 1,
    "progress": 25.50,
    "lastLesson": {
      "id": 1,
      "title": "1.1 Java简介"
    },
    "completedAt": null,
    "status": 0
  }
}
```

### 4.2 更新课时学习进度
#### 接口地址
`POST /api/learning/progress/lessons/{lessonId}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| lessonId | integer | 是 | 课时ID |
| progress | integer | 是 | 学习进度（秒） |
| status | integer | 是 | 学习状态(0:未学习,1:学习中,2:已完成) |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 4.3 获取学习笔记列表
#### 接口地址
`GET /api/learning/notes`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| courseId | integer | 否 | 课程ID |
| lessonId | integer | 否 | 课时ID |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "Java简介笔记",
        "content": "Java是一种面向对象的语言...",
        "course": {
          "id": 1,
          "title": "Java基础教程"
        },
        "lesson": {
          "id": 1,
          "title": "1.1 Java简介"
        },
        "createdAt": "2023-01-01 12:00:00",
        "updatedAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 10,
    "current": 1,
    "pages": 2
  }
}
```

### 4.4 创建学习笔记
#### 接口地址
`POST /api/learning/notes`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| courseId | integer | 是 | 课程ID |
| lessonId | integer | 否 | 课时ID |
| title | string | 是 | 笔记标题 |
| content | string | 是 | 笔记内容 |
| isPublic | integer | 否 | 是否公开(0:否,1:是) |

#### 响应示例
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": 1
  }
}
```

### 4.5 更新学习笔记
#### 接口地址
`PUT /api/learning/notes/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 笔记ID |
| title | string | 否 | 笔记标题 |
| content | string | 否 | 笔记内容 |
| isPublic | integer | 否 | 是否公开(0:否,1:是) |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 4.6 删除学习笔记
#### 接口地址
`DELETE /api/learning/notes/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 笔记ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 4.7 获取我的学习记录
#### 接口地址
`GET /api/learning/records`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "course": {
          "id": 1,
          "title": "Java基础教程",
          "coverImage": "https://example.com/course1.jpg"
        },
        "progress": 25.50,
        "lastAccessTime": "2023-01-01 12:00:00",
        "status": 0
      }
    ],
    "total": 5,
    "current": 1,
    "pages": 1
  }
}
```

## 5. 测评考试模块

### 5.1 获取题库分类
#### 接口地址
`GET /api/questions/categories`

#### 请求参数
无

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "编程题",
      "parentId": 0,
      "children": []
    }
  ]
}
```

### 5.2 获取题目列表
#### 接口地址
`GET /api/questions`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| categoryId | integer | 否 | 分类ID |
| type | integer | 否 | 题目类型 |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "type": 0,
        "content": "Java中int类型的取值范围是多少？",
        "options": "[\"-2^31到2^31-1\",\"-2^32到2^32-1\"]",
        "difficulty": 1,
        "analysis": "int类型占4个字节，所以取值范围是-2^31到2^31-1",
        "createdBy": {
          "id": 2,
          "username": "teacher1",
          "nickname": "张老师"
        },
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "pages": 10
  }
}
```

### 5.3 获取试卷列表
#### 接口地址
`GET /api/exam-papers`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| courseId | integer | 否 | 课程ID |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "Java基础测试卷",
        "description": "测试Java基础知识掌握情况",
        "course": {
          "id": 1,
          "title": "Java基础教程"
        },
        "totalScore": 100.00,
        "passScore": 60.00,
        "duration": 120,
        "status": 1,
        "createdBy": {
          "id": 2,
          "username": "teacher1",
          "nickname": "张老师"
        },
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 10,
    "current": 1,
    "pages": 2
  }
}
```

### 5.4 获取试卷详情
#### 接口地址
`GET /api/exam-papers/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 试卷ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "Java基础测试卷",
    "description": "测试Java基础知识掌握情况",
    "course": {
      "id": 1,
      "title": "Java基础教程"
    },
    "totalScore": 100.00,
    "passScore": 60.00,
    "duration": 120,
    "status": 1,
    "questions": [
      {
        "id": 1,
        "paperId": 1,
        "questionId": 1,
        "score": 10.00,
        "sortOrder": 1,
        "question": {
          "id": 1,
          "type": 0,
          "content": "Java中int类型的取值范围是多少？",
          "options": "[\"-2^31到2^31-1\",\"-2^32到2^32-1\"]",
          "answer": "-2^31到2^31-1",
          "difficulty": 1
        }
      }
    ],
    "createdBy": {
      "id": 2,
      "username": "teacher1",
      "nickname": "张老师"
    },
    "createdAt": "2023-01-01 12:00:00"
  }
}
```

### 5.5 提交试卷
#### 接口地址
`POST /api/exam-records`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| paperId | integer | 是 | 试卷ID |
| answers | string | 是 | 答案(JSON格式) |

#### 响应示例
```json
{
  "code": 200,
  "message": "提交成功",
  "data": {
    "id": 1
  }
}
```

### 5.6 获取考试记录
#### 接口地址
`GET /api/exam-records/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 考试记录ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "paper": {
      "id": 1,
      "title": "Java基础测试卷"
    },
    "user": {
      "id": 1,
      "username": "student1",
      "nickname": "学生1号"
    },
    "startTime": "2023-01-01 12:00:00",
    "endTime": "2023-01-01 13:00:00",
    "score": 85.00,
    "status": 3,
    "answers": "{\"1\":\"-2^31到2^31-1\"}",
    "createdAt": "2023-01-01 12:00:00"
  }
}
```

### 5.7 创建题目（教师）
#### 接口地址
`POST /api/instructor/questions`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| categoryId | integer | 是 | 分类ID |
| type | integer | 是 | 题目类型 |
| content | string | 是 | 题目内容 |
| options | string | 否 | 选项（JSON格式） |
| answer | string | 是 | 参考答案 |
| analysis | string | 否 | 解析 |
| difficulty | integer | 否 | 难度等级 |

#### 响应示例
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": 1
  }
}
```

### 5.8 更新题目（教师）
#### 接口地址
`PUT /api/instructor/questions/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 题目ID |
| categoryId | integer | 否 | 分类ID |
| type | integer | 否 | 题目类型 |
| content | string | 否 | 题目内容 |
| options | string | 否 | 选项（JSON格式） |
| answer | string | 否 | 参考答案 |
| analysis | string | 否 | 解析 |
| difficulty | integer | 否 | 难度等级 |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 5.9 删除题目（教师）
#### 接口地址
`DELETE /api/instructor/questions/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 题目ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 5.10 创建试卷（教师）
#### 接口地址
`POST /api/instructor/exam-papers`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | string | 是 | 试卷标题 |
| description | string | 否 | 试卷说明 |
| courseId | integer | 否 | 关联课程ID |
| totalScore | number | 否 | 总分 |
| passScore | number | 否 | 及格分 |
| duration | integer | 否 | 考试时长（分钟） |
| status | integer | 否 | 状态 |
| questions | array | 是 | 题目列表 |

#### 响应示例
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": 1
  }
}
```

### 5.11 更新试卷（教师）
#### 接口地址
`PUT /api/instructor/exam-papers/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 试卷ID |
| title | string | 否 | 试卷标题 |
| description | string | 否 | 试卷说明 |
| courseId | integer | 否 | 关联课程ID |
| totalScore | number | 否 | 总分 |
| passScore | number | 否 | 及格分 |
| duration | integer | 否 | 考试时长（分钟） |
| status | integer | 否 | 状态 |
| questions | array | 否 | 题目列表 |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 5.12 删除试卷（教师）
#### 接口地址
`DELETE /api/instructor/exam-papers/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 试卷ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 5.13 获取我的考试记录
#### 接口地址
`GET /api/exam-records/my`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "paper": {
          "id": 1,
          "title": "Java基础测试卷"
        },
        "startTime": "2023-01-01 12:00:00",
        "endTime": "2023-01-01 13:00:00",
        "score": 85.00,
        "status": 3,
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 5,
    "current": 1,
    "pages": 1
  }
}
```

## 6. 交流互动模块

### 6.1 获取社区板块
#### 接口地址
`GET /api/community/sections`

#### 请求参数
无

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "技术交流",
      "description": "技术问题讨论区"
    }
  ]
}
```

### 6.2 获取帖子列表
#### 接口地址
`GET /api/community/posts`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| sectionId | integer | 否 | 板块ID |
| keyword | string | 否 | 搜索关键词 |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "关于Java多线程的问题",
        "content": "在使用多线程时遇到一个问题...",
        "viewCount": 100,
        "replyCount": 10,
        "likeCount": 5,
        "isTop": 0,
        "isElite": 0,
        "section": {
          "id": 1,
          "name": "技术交流"
        },
        "author": {
          "id": 1,
          "username": "student1",
          "nickname": "学生1号",
          "avatar": "https://example.com/avatar.jpg"
        },
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "pages": 10
  }
}
```

### 6.3 发布帖子
#### 接口地址
`POST /api/community/posts`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| sectionId | integer | 是 | 板块ID |
| title | string | 是 | 帖子标题 |
| content | string | 是 | 帖子内容 |

#### 响应示例
```json
{
  "code": 200,
  "message": "发布成功",
  "data": {
    "id": 1
  }
}
```

### 6.4 获取帖子详情
#### 接口地址
`GET /api/community/posts/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 帖子ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "关于Java多线程的问题",
    "content": "在使用多线程时遇到一个问题...",
    "viewCount": 100,
    "replyCount": 10,
    "likeCount": 5,
    "isTop": 0,
    "isElite": 0,
    "section": {
      "id": 1,
      "name": "技术交流"
    },
    "author": {
      "id": 1,
      "username": "student1",
      "nickname": "学生1号",
      "avatar": "https://example.com/avatar.jpg"
    },
    "replies": [
      {
        "id": 1,
        "content": "这个问题可以通过...",
        "likeCount": 2,
        "author": {
          "id": 2,
          "username": "teacher1",
          "nickname": "张老师",
          "avatar": "https://example.com/avatar.jpg"
        },
        "createdAt": "2023-01-01 13:00:00"
      }
    ],
    "createdAt": "2023-01-01 12:00:00"
  }
}
```

### 6.5 发布回复
#### 接口地址
`POST /api/community/replies`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| postId | integer | 是 | 帖子ID |
| content | string | 是 | 回复内容 |
| parentId | integer | 否 | 父级回复ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "回复成功",
  "data": {
    "id": 1
  }
}
```

### 6.6 点赞帖子/回复
#### 接口地址
`POST /api/community/likes`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| targetType | integer | 是 | 目标类型(1:帖子,2:回复) |
| targetId | integer | 是 | 目标ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "点赞成功",
  "data": null
}
```

### 6.7 取消点赞
#### 接口地址
`DELETE /api/community/likes`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| targetType | integer | 是 | 目标类型(1:帖子,2:回复) |
| targetId | integer | 是 | 目标ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "取消点赞成功",
  "data": null
}
```

### 6.8 更新帖子
#### 接口地址
`PUT /api/community/posts/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 帖子ID |
| title | string | 否 | 帖子标题 |
| content | string | 否 | 帖子内容 |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 6.9 删除帖子
#### 接口地址
`DELETE /api/community/posts/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 帖子ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 6.10 更新回复
#### 接口地址
`PUT /api/community/replies/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 回复ID |
| content | string | 是 | 回复内容 |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 6.11 删除回复
#### 接口地址
`DELETE /api/community/replies/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 回复ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 6.12 获取私信列表
#### 接口地址
`GET /api/community/messages`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "sender": {
          "id": 2,
          "username": "teacher1",
          "nickname": "张老师",
          "avatar": "https://example.com/avatar.jpg"
        },
        "content": "你好，有什么问题吗？",
        "isRead": 0,
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 10,
    "current": 1,
    "pages": 2
  }
}
```

### 6.13 发送私信
#### 接口地址
`POST /api/community/messages`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| receiverId | integer | 是 | 接收者ID |
| content | string | 是 | 私信内容 |

#### 响应示例
```json
{
  "code": 200,
  "message": "发送成功",
  "data": {
    "id": 1
  }
}
```

### 6.14 标记私信已读
#### 接口地址
`PUT /api/community/messages/{id}/read`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 私信ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "标记成功",
  "data": null
}
```

## 7. 资源管理模块

### 7.1 获取资源分类
#### 接口地址
`GET /api/resources/categories`

#### 请求参数
无

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "教学资料",
      "parentId": 0,
      "children": []
    }
  ]
}
```

### 7.2 获取资源列表
#### 接口地址
`GET /api/resources`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| categoryId | integer | 否 | 分类ID |
| keyword | string | 否 | 搜索关键词 |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "Java基础教程PDF",
        "description": "Java基础教程完整版PDF",
        "fileUrl": "https://example.com/java-basic.pdf",
        "fileSize": 1024000,
        "fileType": "pdf",
        "downloadCount": 100,
        "uploader": {
          "id": 2,
          "username": "teacher1",
          "nickname": "张老师"
        },
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "pages": 10
  }
}
```

### 7.3 下载资源
#### 接口地址
`GET /api/resources/{id}/download`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 资源ID |

#### 响应示例
直接返回文件流

### 7.4 上传资源（讲师）
#### 接口地址
`POST /api/instructor/resources`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| categoryId | integer | 是 | 分类ID |
| title | string | 是 | 资源标题 |
| description | string | 否 | 资源描述 |
| file | file | 是 | 资源文件 |

#### 响应示例
```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "id": 1
  }
}
```

### 7.5 更新资源（讲师）
#### 接口地址
`PUT /api/instructor/resources/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 资源ID |
| categoryId | integer | 否 | 分类ID |
| title | string | 否 | 资源标题 |
| description | string | 否 | 资源描述 |

#### 响应示例
```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 7.6 删除资源（讲师）
#### 接口地址
`DELETE /api/instructor/resources/{id}`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 资源ID |

#### 响应示例
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 7.7 获取课程资源
#### 接口地址
`GET /api/courses/{id}/resources`

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | integer | 是 | 课程ID |
| page | integer | 否 | 页码，默认1 |
| size | integer | 否 | 每页数量，默认10 |

#### 响应示例
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "Java基础教程PDF",
        "description": "Java基础教程完整版PDF",
        "fileUrl": "https://example.com/java-basic.pdf",
        "fileSize": 1024000,
        "fileType": "pdf",
        "downloadCount": 100,
        "uploader": {
          "id": 2,
          "username": "teacher1",
          "nickname": "张老师"
        },
        "createdAt": "2023-01-01 12:00:00"
      }
    ],
    "total": 5,
    "current": 1,
    "pages": 1
  }
}