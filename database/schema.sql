-- 在线学习平台数据库设计
CREATE DATABASE IF NOT EXISTS online_learning_platform;
USE online_learning_platform;

-- 1. 用户管理模块

-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    email VARCHAR(100) UNIQUE NOT NULL COMMENT '邮箱',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    nickname VARCHAR(100) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    phone VARCHAR(20) COMMENT '手机号',
    gender TINYINT DEFAULT 0 COMMENT '性别：0未知，1男，2女',
    birthday DATE COMMENT '生日',
    status TINYINT DEFAULT 1 COMMENT '状态：0禁用，1正常',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户表';

-- 角色表
CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    name VARCHAR(50) UNIQUE NOT NULL COMMENT '角色名称',
    description VARCHAR(255) COMMENT '角色描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '角色表';

-- 用户角色关联表
CREATE TABLE user_roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id INT NOT NULL COMMENT '角色ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_role (user_id, role_id)
) COMMENT '用户角色关联表';

-- 权限表
CREATE TABLE permissions (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    name VARCHAR(100) UNIQUE NOT NULL COMMENT '权限名称',
    description VARCHAR(255) COMMENT '权限描述',
    url VARCHAR(255) COMMENT '权限路径',
    method VARCHAR(10) COMMENT '请求方法',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '权限表';

-- 角色权限关联表
CREATE TABLE role_permissions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    role_id INT NOT NULL COMMENT '角色ID',
    permission_id INT NOT NULL COMMENT '权限ID',
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE,
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) COMMENT '角色权限关联表';

-- 2. 课程管理模块

-- 课程分类表
CREATE TABLE course_categories (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id INT DEFAULT 0 COMMENT '父级分类ID，0表示顶级分类',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '课程分类表';

-- 课程表
CREATE TABLE courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课程ID',
    title VARCHAR(200) NOT NULL COMMENT '课程标题',
    description TEXT COMMENT '课程描述',
    cover_image VARCHAR(255) COMMENT '封面图片URL',
    category_id INT COMMENT '分类ID',
    instructor_id BIGINT NOT NULL COMMENT '讲师ID',
    price DECIMAL(10,2) DEFAULT 0.00 COMMENT '价格',
    origin_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '原价',
    status TINYINT DEFAULT 0 COMMENT '状态：0草稿，1已发布，2已下架',
    level TINYINT DEFAULT 0 COMMENT '难度等级：0初级，1中级，2高级',
    recommend TINYINT DEFAULT 0 COMMENT '是否推荐：0否，1是',
    sort_order INT DEFAULT 0 COMMENT '排序',
    published_at TIMESTAMP NULL COMMENT '发布时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (instructor_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES course_categories(id)
) COMMENT '课程表';

-- 课程章节表
CREATE TABLE course_chapters (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '章节ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    title VARCHAR(200) NOT NULL COMMENT '章节标题',
    description TEXT COMMENT '章节描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
) COMMENT '课程章节表';

-- 课时表
CREATE TABLE course_lessons (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课时ID',
    chapter_id BIGINT NOT NULL COMMENT '章节ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    title VARCHAR(200) NOT NULL COMMENT '课时标题',
    description TEXT COMMENT '课时描述',
    video_url VARCHAR(255) COMMENT '视频URL',
    duration INT DEFAULT 0 COMMENT '时长（秒）',
    sort_order INT DEFAULT 0 COMMENT '排序',
    free_preview TINYINT DEFAULT 0 COMMENT '是否可试看：0否，1是',
    status TINYINT DEFAULT 0 COMMENT '状态：0未发布，1已发布',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (chapter_id) REFERENCES course_chapters(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
) COMMENT '课时表';

-- 3. 学习进度模块

-- 用户课程学习记录表
CREATE TABLE user_course_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    last_lesson_id BIGINT COMMENT '最后学习的课时ID',
    progress DECIMAL(5,2) DEFAULT 0.00 COMMENT '学习进度百分比',
    status TINYINT DEFAULT 0 COMMENT '状态：0学习中，1已完成',
    completed_at TIMESTAMP NULL COMMENT '完成时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (last_lesson_id) REFERENCES course_lessons(id) ON DELETE SET NULL,
    UNIQUE KEY uk_user_course (user_id, course_id)
) COMMENT '用户课程学习记录表';

-- 用户课时学习记录表
CREATE TABLE user_lesson_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    lesson_id BIGINT NOT NULL COMMENT '课时ID',
    status TINYINT DEFAULT 0 COMMENT '状态：0未学习，1学习中，2已完成',
    progress INT DEFAULT 0 COMMENT '课时学习进度（秒）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (lesson_id) REFERENCES course_lessons(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_lesson (user_id, lesson_id)
) COMMENT '用户课时学习记录表';

-- 学习笔记表
CREATE TABLE learning_notes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '笔记ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    lesson_id BIGINT COMMENT '课时ID',
    title VARCHAR(200) NOT NULL COMMENT '笔记标题',
    content TEXT COMMENT '笔记内容',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开：0否，1是',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (lesson_id) REFERENCES course_lessons(id) ON DELETE SET NULL
) COMMENT '学习笔记表';

-- 4. 测评考试模块

-- 题库分类表
CREATE TABLE question_categories (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id INT DEFAULT 0 COMMENT '父级分类ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '题库分类表';

-- 题目表
CREATE TABLE questions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '题目ID',
    category_id INT COMMENT '分类ID',
    type TINYINT NOT NULL COMMENT '题目类型：0单选题，1多选题，2判断题，3填空题，4问答题',
    content TEXT NOT NULL COMMENT '题目内容',
    options TEXT COMMENT '选项（JSON格式）',
    answer TEXT COMMENT '参考答案',
    analysis TEXT COMMENT '解析',
    difficulty TINYINT DEFAULT 1 COMMENT '难度等级：1简单，2中等，3困难',
    created_by BIGINT NOT NULL COMMENT '创建人',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES question_categories(id)
) COMMENT '题目表';

-- 试卷表
CREATE TABLE exam_papers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '试卷ID',
    title VARCHAR(200) NOT NULL COMMENT '试卷标题',
    description TEXT COMMENT '试卷说明',
    course_id BIGINT COMMENT '关联课程ID',
    total_score DECIMAL(10,2) DEFAULT 100.00 COMMENT '总分',
    pass_score DECIMAL(10,2) DEFAULT 60.00 COMMENT '及格分',
    duration INT DEFAULT 0 COMMENT '考试时长（分钟）',
    status TINYINT DEFAULT 0 COMMENT '状态：0草稿，1已发布',
    created_by BIGINT NOT NULL COMMENT '创建人',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE SET NULL
) COMMENT '试卷表';

-- 试卷题目关联表
CREATE TABLE exam_paper_questions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    paper_id BIGINT NOT NULL COMMENT '试卷ID',
    question_id BIGINT NOT NULL COMMENT '题目ID',
    score DECIMAL(10,2) DEFAULT 0.00 COMMENT '题目分数',
    sort_order INT DEFAULT 0 COMMENT '排序',
    FOREIGN KEY (paper_id) REFERENCES exam_papers(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
) COMMENT '试卷题目关联表';

-- 考试记录表
CREATE TABLE exam_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '考试记录ID',
    paper_id BIGINT NOT NULL COMMENT '试卷ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    start_time TIMESTAMP NULL COMMENT '开始考试时间',
    end_time TIMESTAMP NULL COMMENT '结束考试时间',
    score DECIMAL(10,2) DEFAULT 0.00 COMMENT '考试得分',
    status TINYINT DEFAULT 0 COMMENT '状态：0未开始，1进行中，2已提交，3已批改',
    answers TEXT COMMENT '答题内容（JSON格式）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (paper_id) REFERENCES exam_papers(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
) COMMENT '考试记录表';

-- 5. 交流互动模块

-- 社区板块表
CREATE TABLE community_sections (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '板块ID',
    name VARCHAR(100) NOT NULL COMMENT '板块名称',
    description VARCHAR(255) COMMENT '板块描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '社区板块表';

-- 帖子表
CREATE TABLE posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '帖子ID',
    section_id INT NOT NULL COMMENT '板块ID',
    user_id BIGINT NOT NULL COMMENT '发帖用户ID',
    title VARCHAR(200) NOT NULL COMMENT '帖子标题',
    content LONGTEXT COMMENT '帖子内容',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶：0否，1是',
    is_elite TINYINT DEFAULT 0 COMMENT '是否精华：0否，1是',
    status TINYINT DEFAULT 1 COMMENT '状态：0删除，1正常',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (section_id) REFERENCES community_sections(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
) COMMENT '帖子表';

-- 回复表
CREATE TABLE post_replies (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '回复ID',
    post_id BIGINT NOT NULL COMMENT '帖子ID',
    user_id BIGINT NOT NULL COMMENT '回复用户ID',
    content TEXT NOT NULL COMMENT '回复内容',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    parent_id BIGINT DEFAULT 0 COMMENT '父级回复ID，0表示直接回复帖子',
    status TINYINT DEFAULT 1 COMMENT '状态：0删除，1正常',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id)
) COMMENT '回复表';

-- 私信表
CREATE TABLE private_messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '私信ID',
    sender_id BIGINT NOT NULL COMMENT '发送者ID',
    receiver_id BIGINT NOT NULL COMMENT '接收者ID',
    content TEXT NOT NULL COMMENT '私信内容',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读：0否，1是',
    status TINYINT DEFAULT 1 COMMENT '状态：0删除，1正常',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '私信表';

-- 点赞表
CREATE TABLE likes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '点赞ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    target_type TINYINT NOT NULL COMMENT '目标类型：1帖子，2回复',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_target (user_id, target_type, target_id)
) COMMENT '点赞表';

-- 6. 资源管理模块

-- 资源分类表
CREATE TABLE resource_categories (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id INT DEFAULT 0 COMMENT '父级分类ID',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '资源分类表';

-- 资源表
CREATE TABLE resources (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '资源ID',
    category_id INT COMMENT '分类ID',
    title VARCHAR(200) NOT NULL COMMENT '资源标题',
    description TEXT COMMENT '资源描述',
    file_url VARCHAR(255) NOT NULL COMMENT '文件URL',
    file_size BIGINT DEFAULT 0 COMMENT '文件大小（字节）',
    file_type VARCHAR(50) COMMENT '文件类型',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    uploader_id BIGINT NOT NULL COMMENT '上传者ID',
    status TINYINT DEFAULT 1 COMMENT '状态：0待审核，1已发布，2已下架',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (uploader_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES resource_categories(id)
) COMMENT '资源表';

-- 课程资源关联表
CREATE TABLE course_resources (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    resource_id BIGINT NOT NULL COMMENT '资源ID',
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resources(id) ON DELETE CASCADE,
    UNIQUE KEY uk_course_resource (course_id, resource_id)
) COMMENT '课程资源关联表';

-- 初始角色数据
INSERT INTO roles (name, description) VALUES 
('ADMIN', '管理员'),
('TEACHER', '讲师'),
('STUDENT', '学员');