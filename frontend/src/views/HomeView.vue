<template>
  <main class="home-container">
    <div class="hero-section">
      <div class="hero-content">
        <h1>开启您的学习之旅</h1>
        <p>探索丰富的在线课程，提升技能，实现职业目标</p>
        <div class="cta-buttons">
          <router-link to="/login" class="btn btn-primary">开始学习</router-link>
        </div>
      </div>
    </div>

    <div class="features-section">
      <div class="feature-card">
        <div class="feature-icon">📚</div>
        <h3>丰富课程</h3>
        <p>涵盖编程、设计、营销等多个领域的高质量课程</p>
      </div>
      <div class="feature-card">
        <div class="feature-icon">🎓</div>
        <h3>专家讲师</h3>
        <p>来自行业一线的专家亲自授课，分享实战经验</p>
      </div>
      <div class="feature-card">
        <div class="feature-icon">🏆</div>
        <h3>认证证书</h3>
        <p>完成课程后获得认证证书，提升职场竞争力</p>
      </div>
    </div>

    <div class="popular-courses">
      <h2>热门课程</h2>
      <div class="courses-grid">
        <div class="course-card" v-for="course in popularCourses" :key="course.id">
          <div class="course-image">
            <div class="image-placeholder">{{ course.category }}</div>
          </div>
          <div class="course-details">
            <h3>{{ course.title }}</h3>
            <p class="instructor">{{ course.instructor }}</p>
            <div class="course-meta">
              <span class="rating">⭐ {{ course.rating }}</span>
              <span class="students">👥 {{ course.students }} 学生</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
const popularCourses = [
  {
    id: 1,
    title: 'Vue.js 从入门到精通',
    instructor: '张老师',
    category: '前端开发',
    rating: 4.8,
    students: 1200
  },
  {
    id: 2,
    title: 'Spring Boot 实战教程',
    instructor: '李老师',
    category: '后端开发',
    rating: 4.9,
    students: 980
  },
  {
    id: 3,
    title: 'UI/UX 设计原理',
    instructor: '王老师',
    category: '设计',
    rating: 4.7,
    students: 750
  },
  {
    id: 4,
    title: '数据分析与可视化',
    instructor: '赵老师',
    category: '数据科学',
    rating: 4.6,
    students: 1100
  }
]
</script>

<style scoped>
.home-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 12px;
  padding: 4rem 2rem;
  text-align: center;
  margin-bottom: 3rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.hero-content h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.hero-content p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0.9;
}

.cta-buttons {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  text-decoration: none;
  border-radius: 50px;
  font-weight: bold;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.btn-primary {
  background-color: #ffffff;
  color: #667eea;
}

.features-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.feature-card {
  background: white;
  border-radius: 8px;
  padding: 2rem;
  text-align: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-5px);
}

.feature-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.feature-card h3 {
  margin: 0 0 1rem 0;
  color: #333;
}

.feature-card p {
  color: #666;
  margin: 0;
  line-height: 1.6;
}

.popular-courses h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
  font-size: 2rem;
}

.courses-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 2rem;
}

.course-card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.course-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.course-image {
  height: 150px;
  background-color: #f0f2f5;
}

.image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 1.2rem;
  color: #666;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.course-details {
  padding: 1.5rem;
}

.course-details h3 {
  margin: 0 0 0.5rem 0;
  color: #333;
}

.instructor {
  color: #666;
  margin: 0 0 1rem 0;
  font-size: 0.9rem;
}

.course-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
}

.rating {
  color: #f5a623;
}

.students {
  color: #666;
}

@media (max-width: 768px) {
  .hero-section {
    padding: 2rem 1rem;
  }
  
  .hero-content h1 {
    font-size: 2rem;
  }
  
  .hero-content p {
    font-size: 1rem;
  }
  
  .features-section {
    grid-template-columns: 1fr;
  }
}
</style>