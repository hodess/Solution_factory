import { createRouter, createWebHistory } from 'vue-router'
import MetroMap from '../components/MetroMap.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: MetroMap
    }
  ]
})

export default router
