import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { setupRouteGuards } from './router/routeGuard'

const app = createApp(App)

app.use(createPinia())
app.use(router)

// 设置路由守卫
setupRouteGuards(router)

app.mount('#app')