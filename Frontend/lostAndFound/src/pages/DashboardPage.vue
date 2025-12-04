<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <!-- Welcome Banner -->
        <div class="relative overflow-hidden bg-gradient-to-r from-blue-600 via-purple-600 to-pink-500 rounded-3xl shadow-2xl p-8 mb-8 text-white">
          <!-- Decorative Elements -->
          <div class="absolute top-0 right-0 w-64 h-64 bg-white opacity-10 rounded-full -mr-32 -mt-32"></div>
          <div class="absolute bottom-0 left-0 w-48 h-48 bg-white opacity-10 rounded-full -ml-24 -mb-24"></div>
          
          <div class="relative z-10">
            <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
              <div class="flex-1">
                <h1 class="text-3xl md:text-4xl font-bold mb-2">
                  Welcome back, {{ userName }}! 👋
                </h1>
                <p class="text-purple-100 text-sm md:text-base">
                  Let's make a difference today by helping others find their lost items.
                </p>
              </div>
              <button 
                @click="goToReport"
                class="bg-white text-purple-600 px-6 py-3 rounded-xl font-semibold hover:bg-opacity-90 transition shadow-lg flex items-center gap-2 whitespace-nowrap cursor-pointer"
              >
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
                </svg>
                Report Item
              </button>
            </div>
          </div>
        </div>

        <!-- Quick Stats -->
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
          <div class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition cursor-pointer">
            <div class="flex items-center gap-4 mb-3">
              <div class="w-12 h-12 bg-blue-100 rounded-xl flex items-center justify-center flex-shrink-0">
                <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
              </div>
              <h3 class="text-2xl font-bold text-gray-900">{{ stats.itemsFound }}</h3>
            </div>
            <p class="text-sm text-gray-600">Items Found</p>
          </div>

          <div class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition cursor-pointer">
            <div class="flex items-center gap-4 mb-3">
              <div class="w-12 h-12 bg-red-100 rounded-xl flex items-center justify-center flex-shrink-0">
                <svg class="w-6 h-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                </svg>
              </div>
              <h3 class="text-2xl font-bold text-gray-900">{{ stats.itemsLost }}</h3>
            </div>
            <p class="text-sm text-gray-600">Items Lost</p>
          </div>

          <div class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition cursor-pointer">
            <div class="flex items-center gap-4 mb-3">
              <div class="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center flex-shrink-0">
                <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                </svg>
              </div>
              <h3 class="text-2xl font-bold text-gray-900">{{ stats.matched }}</h3>
            </div>
            <p class="text-sm text-gray-600">Matched</p>
          </div>

          <div class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition cursor-pointer">
            <div class="flex items-center gap-4 mb-3">
              <div class="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center flex-shrink-0">
                <svg class="w-6 h-6 text-purple-600" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
                </svg>
              </div>
              <h3 class="text-2xl font-bold text-gray-900">{{ stats.points }}</h3>
            </div>
            <p class="text-sm text-gray-600">My Points</p>
          </div>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          
          <!-- Left Column -->
          <div class="lg:col-span-2 space-y-6">
            
            <!-- Quick Actions -->
            <div class="bg-white rounded-2xl shadow-lg p-6">
              <h2 class="text-xl font-bold text-gray-900 mb-4">Quick Actions</h2>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <button 
                  @click="goToReportFound"
                  class="group relative overflow-hidden bg-gradient-to-br from-green-500 to-emerald-600 rounded-xl p-6 text-white text-left cursor-pointer hover:shadow-xl transition"
                >
                  <div class="absolute top-0 right-0 w-20 h-20 bg-white opacity-10 rounded-full -mr-10 -mt-10"></div>
                  <div class="relative z-10">
                    <div class="w-12 h-12 bg-white rounded-xl flex items-center justify-center mb-4 shadow-md">
                      <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                      </svg>
                    </div>
                    <h3 class="text-lg font-semibold mb-1">Report Found Item</h3>
                    <p class="text-sm text-green-100">Help return items to owners</p>
                  </div>
                </button>

                <button 
                  @click="goToReportLost"
                  class="group relative overflow-hidden bg-gradient-to-br from-red-500 to-orange-600 rounded-xl p-6 text-white text-left cursor-pointer hover:shadow-xl transition"
                >
                  <div class="absolute top-0 right-0 w-20 h-20 bg-white opacity-10 rounded-full -mr-10 -mt-10"></div>
                  <div class="relative z-10">
                    <div class="w-12 h-12 bg-white rounded-xl flex items-center justify-center mb-4 shadow-md">
                      <svg class="w-6 h-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                      </svg>
                    </div>
                    <h3 class="text-lg font-semibold mb-1">Report Lost Item</h3>
                    <p class="text-sm text-red-100">Let us help you find it</p>
                  </div>
                </button>
              </div>
            </div>

            <!-- Recent Activity -->
            <div class="bg-white rounded-2xl shadow-lg p-6">
              <div class="flex items-center justify-between mb-4">
                <h2 class="text-xl font-bold text-gray-900">Recent Activity</h2>
                <button class="text-sm text-blue-600 hover:text-blue-700 font-semibold">View All</button>
              </div>
              
              <div class="space-y-4">
                <div 
                  v-for="(activity, index) in recentActivities" 
                  :key="index"
                  class="flex items-start gap-4 pb-4 border-b last:border-b-0 hover:bg-gray-50 p-3 rounded-lg transition cursor-pointer"
                >
                  <div class="flex-shrink-0 w-12 h-12 rounded-full flex items-center justify-center"
                    :class="activity.bgColor">
                    <svg class="w-6 h-6" :class="activity.iconColor" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="activity.icon"/>
                    </svg>
                  </div>
                  <div class="flex-1 min-w-0">
                    <p class="font-medium text-gray-900">{{ activity.title }}</p>
                    <p class="text-sm text-gray-600 mt-1">{{ activity.description }}</p>
                    <p class="text-xs text-gray-400 mt-1">{{ activity.time }}</p>
                  </div>
                  <span 
                    class="flex-shrink-0 px-2 py-1 text-xs font-semibold rounded-full"
                    :class="activity.statusClass">
                    {{ activity.status }}
                  </span>
                </div>
              </div>
            </div>

          </div>

          <!-- Right Column -->
          <div class="lg:col-span-1 space-y-6">
            
            <!-- Your Rank Card -->
            <div class="bg-gradient-to-br from-yellow-400 via-orange-500 to-pink-500 rounded-2xl shadow-lg p-6 text-white">
              <div class="flex items-center gap-3 mb-4">
                <div class="w-12 h-12 bg-white bg-opacity-30 rounded-full flex items-center justify-center text-2xl">
                  🏆
                </div>
                <div>
                  <h3 class="font-semibold">Your Rank</h3>
                  <p class="text-sm text-orange-100">Keep climbing!</p>
                </div>
              </div>
              <div class="text-4xl font-bold mb-2">#{{ userRank }}</div>
              <p class="text-sm text-orange-100 mb-4">Out of {{ totalUsers }} heroes</p>
              <button 
                @click="goToRanking"
                class="w-full bg-white text-orange-600 py-2 rounded-xl font-semibold cursor-pointer hover:bg-opacity-90 transition"
              >
                View Leaderboard
              </button>
            </div>

            <!-- AI Stats -->
            <div class="bg-white rounded-2xl shadow-lg p-6">
              <div class="flex items-center gap-2 mb-4">
                <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"/>
                </svg>
                <h3 class="font-bold text-gray-900">AI Matching</h3>
              </div>
              <div class="space-y-3">
                <div class="flex justify-between items-center">
                  <span class="text-sm text-gray-600">Items Scanned</span>
                  <span class="font-semibold text-gray-900">{{ aiStats.scanned }}</span>
                </div>
                <div class="flex justify-between items-center">
                  <span class="text-sm text-gray-600">Potential Matches</span>
                  <span class="font-semibold text-purple-600">{{ aiStats.matches }}</span>
                </div>
                <div class="flex justify-between items-center">
                  <span class="text-sm text-gray-600">Success Rate</span>
                  <span class="font-semibold text-green-600">{{ aiStats.successRate }}%</span>
                </div>
              </div>
            </div>

            <!-- Tips Card -->
            <div class="bg-blue-50 border border-blue-200 rounded-2xl p-6">
              <div class="flex items-start gap-3">
                <svg class="w-6 h-6 text-blue-600 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
                <div>
                  <h4 class="font-semibold text-blue-900 mb-2">💡 Pro Tip</h4>
                  <p class="text-sm text-blue-800">
                    Upload clear photos when reporting found items. Our AI works better with high-quality images!
                  </p>
                </div>
              </div>
            </div>

          </div>

        </div>

      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import AuthService from '../services/auth.service'
import DashboardService from '../services/dashboard.service'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import type { ActivityFeed, DashboardStats } from '../types/api.types'

const router = useRouter()
const toast = useToast()

// User info from localStorage
const userId = ref<number | null>(null)
const userName = ref('User')
const userRank = ref(15)
const totalUsers = ref(234)

// Dashboard stats
const stats = ref<DashboardStats>({
  itemsFound: 0,
  itemsLost: 0,
  matched: 0,
  points: 0
})

const aiStats = ref({
  scanned: 0,
  matches: 0,
  successRate: 0
})

// Recent activities from API
const recentActivities = ref<any[]>([])
const isLoadingActivities = ref(false)
const isLoadingStats = ref(false)

// Fetch user info from localStorage
const loadUserInfo = () => {
  userId.value = AuthService.getUserId()
  
  // For now, use a default name
  // You can fetch full user details from backend later
  userName.value = 'Student'
  
  if (!userId.value) {
    toast.error('Please login first')
    router.push('/login')
  }
}

// Fetch dashboard statistics
const loadDashboardStats = async () => {
  if (!userId.value) return
  
  isLoadingStats.value = true
  try {
    const data = await DashboardService.getUserStats(userId.value)
    stats.value = data
  } catch (error: any) {
    console.error('Error loading stats:', error)
    // Keep default values on error
  } finally {
    isLoadingStats.value = false
  }
}

// Fetch recent activities
const loadRecentActivities = async () => {
  isLoadingActivities.value = true
  try {
    const activities = await DashboardService.getRecentActivity()
    
    // Transform API data to display format
    recentActivities.value = activities.map((activity: ActivityFeed) => {
      const isFound = activity.type === 'FOUND'
      return {
        title: activity.title,
        description: activity.description,
        time: formatTime(activity.timestamp),
        status: isFound ? 'Found' : 'Lost',
        statusClass: isFound ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700',
        bgColor: isFound ? 'bg-green-100' : 'bg-red-100',
        iconColor: isFound ? 'text-green-600' : 'text-red-600',
        icon: isFound 
          ? 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z'
          : 'M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z'
      }
    })
    
    // If no activities, show placeholder
    if (recentActivities.value.length === 0) {
      recentActivities.value = [{
        title: 'No recent activities',
        description: 'Start by reporting a found or lost item!',
        time: 'Now',
        status: 'Info',
        statusClass: 'bg-blue-100 text-blue-700',
        bgColor: 'bg-blue-100',
        iconColor: 'text-blue-600',
        icon: 'M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z'
      }]
    }
  } catch (error: any) {
    console.error('Error loading activities:', error)
    toast.error('Failed to load recent activities')
    
    // Show placeholder on error
    recentActivities.value = [{
      title: 'Unable to load activities',
      description: 'Please refresh the page',
      time: 'Now',
      status: 'Error',
      statusClass: 'bg-red-100 text-red-700',
      bgColor: 'bg-red-100',
      iconColor: 'text-red-600',
      icon: 'M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z'
    }]
  } finally {
    isLoadingActivities.value = false
  }
}

// Format timestamp to relative time
const formatTime = (timestamp: string): string => {
  const date = new Date(timestamp)
  const now = new Date()
  const diffInMs = now.getTime() - date.getTime()
  const diffInHours = Math.floor(diffInMs / (1000 * 60 * 60))
  const diffInDays = Math.floor(diffInHours / 24)
  
  if (diffInHours < 1) {
    const diffInMinutes = Math.floor(diffInMs / (1000 * 60))
    return `${diffInMinutes} minute${diffInMinutes !== 1 ? 's' : ''} ago`
  } else if (diffInHours < 24) {
    return `${diffInHours} hour${diffInHours !== 1 ? 's' : ''} ago`
  } else if (diffInDays < 7) {
    return `${diffInDays} day${diffInDays !== 1 ? 's' : ''} ago`
  } else {
    return date.toLocaleDateString()
  }
}

// Navigation functions
const goToReport = () => {
  router.push('/report')
}

const goToReportFound = () => {
  router.push('/report/found')
}

const goToReportLost = () => {
  router.push('/report/lost')
}

const goToRanking = () => {
  router.push('/ranking')
}

// Load all data on component mount
onMounted(async () => {
  loadUserInfo()
  await Promise.all([
    loadDashboardStats(),
    loadRecentActivities()
  ])
})
</script>