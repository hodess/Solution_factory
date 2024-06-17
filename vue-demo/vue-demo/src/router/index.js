import { createRouter, createWebHistory } from 'vue-router'
import MetroMap from '../components/MetroMap.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/about',
      name: 'about',
      component: MetroMap
    }
  ]
})

export default router
