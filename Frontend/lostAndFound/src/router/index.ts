import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import LoginPage from '../pages/LoginPage.vue'
import DashboardPage from '../pages/DashboardPage.vue'
import LostItemsPage from '../pages/LostItemsPage.vue'
import FoundItemsPage from '../pages/FoundItemsPage.vue'
import ReportSelectionPage from '../pages/ReportSelectionPage.vue'
import RankingPage from '../pages/RankingPage.vue'
import ProfilePage from '../pages/ProfilePage.vue'
import SignUpPage from '../pages/SignUpPage.vue'
import AdminPage from '../pages/AdminPage.vue'

const routes: RouteRecordRaw[] = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'Login', component: LoginPage },
  { path: '/signup', name: 'SignUp', component: SignUpPage },
  { path: '/dashboard', name: 'Dashboard', component: DashboardPage },
  { path: '/report', name: 'ReportSelection', component: ReportSelectionPage},
  { path: '/report/found' ,name: 'ReportFound' ,component: FoundItemsPage },
  { path: '/report/lost' , name: 'ReportLost' , component: LostItemsPage},
  { path: '/ranking' , name: 'Ranking' , component: RankingPage},
  { path: '/profile' , name: 'Profile' , component: ProfilePage},
   { path: '/admin' , name: 'Admin' , component: AdminPage}
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
