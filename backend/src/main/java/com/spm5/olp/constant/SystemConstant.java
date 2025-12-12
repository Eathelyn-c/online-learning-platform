package com.spm5.olp.constant;

/**
 * 系统常量类
 */
public class SystemConstant {
    
    /**
     * JWT相关常量
     */
    public static final String JWT_SECRET_KEY = "online_learning_platform_secret";
    public static final Long JWT_TTL = 86400000L; // 24小时
    public static final String JWT_TOKEN_NAME = "token";
    
    /**
     * 用户状态常量
     */
    public static final Integer USER_STATUS_DISABLED = 0; // 禁用
    public static final Integer USER_STATUS_ENABLED = 1;  // 正常
    
    /**
     * 性别常量
     */
    public static final Integer GENDER_UNKNOWN = 0; // 未知
    public static final Integer GENDER_MALE = 1;    // 男
    public static final Integer GENDER_FEMALE = 2;  // 女
    
    /**
     * 课程状态常量
     */
    public static final Integer COURSE_STATUS_DRAFT = 0;      // 草稿
    public static final Integer COURSE_STATUS_PUBLISHED = 1;  // 已发布
    public static final Integer COURSE_STATUS_ARCHIVED = 2;   // 已下架
    
    /**
     * 课程难度常量
     */
    public static final Integer COURSE_LEVEL_BEGINNER = 0;  // 初级
    public static final Integer COURSE_LEVEL_INTERMEDIATE = 1; // 中级
    public static final Integer COURSE_LEVEL_ADVANCED = 2;     // 高级
    
    /**
     * 课时状态常量
     */
    public static final Integer LESSON_STATUS_UNPUBLISHED = 0; // 未发布
    public static final Integer LESSON_STATUS_PUBLISHED = 1;   // 已发布
    
    /**
     * 是否推荐课程常量
     */
    public static final Integer COURSE_NOT_RECOMMEND = 0; // 不推荐
    public static final Integer COURSE_RECOMMEND = 1;     // 推荐
    
    /**
     * 是否可试看常量
     */
    public static final Integer LESSON_NOT_FREE_PREVIEW = 0; // 不可试看
    public static final Integer LESSON_FREE_PREVIEW = 1;     // 可试看
    
    /**
     * 学习进度状态常量
     */
    public static final Integer PROGRESS_STATUS_LEARNING = 0;   // 学习中
    public static final Integer PROGRESS_STATUS_COMPLETED = 1;  // 已完成
    
    /**
     * 课时学习状态常量
     */
    public static final Integer LESSON_PROGRESS_STATUS_NOT_STARTED = 0; // 未学习
    public static final Integer LESSON_PROGRESS_STATUS_IN_PROGRESS = 1; // 学习中
    public static final Integer LESSON_PROGRESS_STATUS_COMPLETED = 2;    // 已完成
    
    /**
     * 是否公开笔记常量
     */
    public static final Integer NOTE_PRIVATE = 0; // 私有
    public static final Integer NOTE_PUBLIC = 1;  // 公开
    
    /**
     * 题目类型常量
     */
    public static final Integer QUESTION_TYPE_SINGLE_CHOICE = 0;    // 单选题
    public static final Integer QUESTION_TYPE_MULTIPLE_CHOICE = 1;  // 多选题
    public static final Integer QUESTION_TYPE_TRUE_FALSE = 2;       // 判断题
    public static final Integer QUESTION_TYPE_FILL_BLANK = 3;       // 填空题
    public static final Integer QUESTION_TYPE_SHORT_ANSWER = 4;     // 问答题
    
    /**
     * 题目难度常量
     */
    public static final Integer QUESTION_DIFFICULTY_EASY = 1;    // 简单
    public static final Integer QUESTION_DIFFICULTY_MEDIUM = 2;  // 中等
    public static final Integer QUESTION_DIFFICULTY_HARD = 3;    // 困难
    
    /**
     * 试卷状态常量
     */
    public static final Integer PAPER_STATUS_DRAFT = 0;      // 草稿
    public static final Integer PAPER_STATUS_PUBLISHED = 1;  // 已发布
    
    /**
     * 考试记录状态常量
     */
    public static final Integer EXAM_STATUS_NOT_STARTED = 0;  // 未开始
    public static final Integer EXAM_STATUS_IN_PROGRESS = 1;  // 进行中
    public static final Integer EXAM_STATUS_SUBMITTED = 2;    // 已提交
    public static final Integer EXAM_STATUS_GRADED = 3;       // 已批改
    
    /**
     * 帖子状态常量
     */
    public static final Integer POST_STATUS_DELETED = 0;  // 删除
    public static final Integer POST_STATUS_NORMAL = 1;   // 正常
    
    /**
     * 是否置顶帖子常量
     */
    public static final Integer POST_NOT_TOP = 0;  // 不置顶
    public static final Integer POST_TOP = 1;      // 置顶
    
    /**
     * 是否精华帖子常量
     */
    public static final Integer POST_NOT_ELITE = 0;  // 非精华
    public static final Integer POST_ELITE = 1;      // 精华
    
    /**
     * 回复状态常量
     */
    public static final Integer REPLY_STATUS_DELETED = 0;  // 删除
    public static final Integer REPLY_STATUS_NORMAL = 1;   // 正常
    
    /**
     * 私信状态常量
     */
    public static final Integer MESSAGE_STATUS_DELETED = 0;  // 删除
    public static final Integer MESSAGE_STATUS_NORMAL = 1;   // 正常
    
    /**
     * 私信是否已读常量
     */
    public static final Integer MESSAGE_UNREAD = 0;  // 未读
    public static final Integer MESSAGE_READ = 1;    // 已读
    
    /**
     * 资源状态常量
     */
    public static final Integer RESOURCE_STATUS_PENDING = 0;    // 待审核
    public static final Integer RESOURCE_STATUS_PUBLISHED = 1;  // 已发布
    public static final Integer RESOURCE_STATUS_ARCHIVED = 2;   // 已下架
    
    /**
     * 点赞目标类型常量
     */
    public static final Integer LIKE_TARGET_POST = 1;    // 帖子
    public static final Integer LIKE_TARGET_REPLY = 2;   // 回复
    
    /**
     * 角色常量
     */
    public static final String ROLE_ADMIN = "ADMIN";         // 管理员
    public static final String ROLE_INSTRUCTOR = "INSTRUCTOR"; // 讲师
    public static final String ROLE_STUDENT = "STUDENT";     // 学员
    
    /**
     * HTTP状态码常量
     */
    public static final Integer HTTP_SUCCESS = 200;   // 请求成功
    public static final Integer HTTP_BAD_REQUEST = 400; // 参数错误
    public static final Integer HTTP_UNAUTHORIZED = 401; // 未授权
    public static final Integer HTTP_FORBIDDEN = 403;    // 禁止访问
    public static final Integer HTTP_NOT_FOUND = 404;    // 资源不存在
    public static final Integer HTTP_INTERNAL_ERROR = 500; // 服务器内部错误
    
    /**
     * 响应消息常量
     */
    public static final String MESSAGE_SUCCESS = "success";              // 成功
    public static final String MESSAGE_BAD_REQUEST = "参数错误";          // 参数错误
    public static final String MESSAGE_UNAUTHORIZED = "未授权访问";        // 未授权
    public static final String MESSAGE_FORBIDDEN = "权限不足";            // 禁止访问
    public static final String MESSAGE_NOT_FOUND = "资源不存在";          // 资源不存在
    public static final String MESSAGE_INTERNAL_ERROR = "服务器内部错误";   // 服务器内部错误
}