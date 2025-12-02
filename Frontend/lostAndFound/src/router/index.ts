import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import LoginPage from '../pages/LoginPage.vue'
import DashboardPage from '../pages/DashboardPage.vue'
import LostItemsPage from '../pages/LostItemsPage.vue'
import FoundItemsPage from '../pages/FoundItemsPage.vue'

const routes: RouteRecordRaw[] = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'Login', component: LoginPage },
  { path: '/dashboard', name: 'Dashboard', component: DashboardPage },
  { path: '/lostItems', name: 'LostItems', component: LostItemsPage},
  { path: '/foundItems', name: "FoundItems", component: FoundItemsPage}
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
