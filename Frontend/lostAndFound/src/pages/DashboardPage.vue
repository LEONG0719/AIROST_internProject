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
              <div class="mb-4">
                <h2 class="text-xl font-bold text-gray-900">Recent Activity</h2>
                <p class="text-sm text-gray-500 mt-1">Your latest found and lost item reports</p>
              </div>
              
              <div v-if="isLoadingActivities" class="flex justify-center py-8">
                <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
              </div>

              <div v-else-if="recentActivities.length === 0" class="text-center py-8">
                <div class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                  <svg class="w-8 h-8 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"/>
                  </svg>
                </div>
                <p class="text-gray-600 font-medium">No recent activities</p>
                <p class="text-sm text-gray-500 mt-1">Start by reporting a found or lost item!</p>
              </div>
              
              <div v-else class="space-y-3">
                <div 
                  v-for="(activity, index) in recentActivities" 
                  :key="index"
                  @click="handleActivityClick(activity)"
                  class="flex items-start gap-4 p-4 rounded-xl transition"
                  :class="[
                    activity.clickable 
                      ? 'hover:bg-gradient-to-r hover:from-green-50 hover:to-emerald-50 cursor-pointer border-2 border-green-200 bg-green-50' 
                      : 'hover:bg-gray-50 border border-gray-200',
                    'relative'
                  ]"
                >
                  <!-- Icon -->
                  <div class="flex-shrink-0 w-12 h-12 rounded-full flex items-center justify-center"
                    :class="activity.bgColor">
                    <svg class="w-6 h-6" :class="activity.iconColor" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="activity.icon"/>
                    </svg>
                  </div>

                  <!-- Content -->
                  <div class="flex-1 min-w-0">
                    <div class="flex items-start justify-between gap-2">
                      <p class="font-semibold text-gray-900 flex items-center gap-2">
                        {{ activity.title }}
                        <!-- Matched badge -->
                        <span v-if="activity.type === 'MATCHED'" class="inline-flex items-center px-2 py-0.5 rounded-full text-xs font-bold bg-green-500 text-white animate-pulse">
                          ✨ NEW
                        </span>
                      </p>
                      <span 
                        class="flex-shrink-0 px-2.5 py-1 text-xs font-bold rounded-full"
                        :class="activity.statusClass">
                        {{ activity.status }}
                      </span>
                    </div>
                    <p class="text-sm text-gray-600 mt-1.5">{{ activity.description }}</p>
                    <div class="flex items-center gap-3 mt-2">
                      <p class="text-xs text-gray-500 flex items-center gap-1">
                        <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                        </svg>
                        {{ activity.time }}
                      </p>
                      <!-- Click to claim indicator for matched items -->
                      <p v-if="activity.clickable" class="text-xs font-semibold text-green-600 flex items-center gap-1">
                        <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 15l-2 5L9 9l11 4-5 2zm0 0l5 5M7.188 2.239l.777 2.897M5.136 7.965l-2.898-.777M13.95 4.05l-2.122 2.122m-5.657 5.656l-2.12 2.122"/>
                        </svg>
                        Click to claim item
                      </p>
                    </div>
                  </div>

                  <!-- Arrow indicator for clickable items -->
                  <div v-if="activity.clickable" class="flex-shrink-0">
                    <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                    </svg>
                  </div>
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

            <div class="bg-white rounded-2xl shadow-lg p-6">
            <div class="flex items-center gap-3 mb-4">
              <div class="w-12 h-12 bg-gradient-to-br from-purple-500 to-pink-500 rounded-xl flex items-center justify-center">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-7.072 5.657z"/>
                </svg>
              </div>
              <div>
                <h2 class="text-xl font-bold text-gray-900">AI Matching System</h2>
                <p class="text-sm text-gray-600">Intelligent item matching</p>
              </div>
            </div>

            <div class="grid grid-cols-3 gap-4">
              <!-- Total Items -->
              <div class="text-center">
                <div class="text-2xl font-bold text-gray-900">{{ aiStats.totalItems }}</div>
                <div class="text-xs text-gray-600">Items in Database</div>
              </div>
              
              <!-- Recent Matches -->
              <div class="text-center">
                <div class="text-2xl font-bold text-purple-600">{{ aiStats.recentMatches }}</div>
                <div class="text-xs text-gray-600">Recent Matches</div>
              </div>
              
              <!-- Success Rate -->
              <div class="text-center">
                <div class="text-2xl font-bold text-green-600">{{ aiStats.successRate }}%</div>
                <div class="text-xs text-gray-600">Success Rate</div>
              </div>
            </div>

            <div class="mt-4 text-xs text-gray-500 text-center">
              AI-powered matching updated in real-time
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
    <!-- Item Claim Modal -->
    <ItemClaimModal
      :show="showClaimModal"
      :item="selectedItem || {}"
      @close="closeClaimModal"
      @claimed="handleItemClaimed"
    />

    <StudentCardVerificationModal
      :show="showVerificationModal"
      :item="selectedItem || {}"
      @close="closeVerificationModal"
      @submitted="handleVerificationSubmitted"
    />
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import AuthService from '../services/auth.service'
import DashboardService from '../services/dashboard.service'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import ItemClaimModal from '../components/ItemClaimModal.vue'
import StudentCardVerificationModal from '../components/StudentCardVerificationModal.vue'
import type { DashboardStats, CommunityStats } from '../types/api.types'

const router = useRouter()
const toast = useToast()

// User info
const userId = ref<number | null>(null)
const userName = ref('Student')
const userRank = ref(0)
const totalUsers = ref(0)

// Personal stats (from user profile)
const stats = ref<DashboardStats>({
  itemsFound: 0,
  itemsLost: 0,
  matched: 0,
  points: 0
})

// Community stats
const communityStats = ref<CommunityStats>({
  totalItemsReturned: 0,
  totalUsers: 0,
  successRate: 0,
  totalPoints: 0
})

// AI Stats - Real data from backend
const aiStats = ref({
  totalItems: 0,        // Total items in database (found + lost)
  recentMatches: 0,     // Matches in last 7 days
  successRate: 0        // Overall success rate
})

// Recent activities
const recentActivities = ref<any[]>([])
const isLoadingActivities = ref(false)
const isLoadingStats = ref(false)
const isLoadingProfile = ref(false)

// ✅ TWO MODALS: Claim Modal & Verification Modal
const showClaimModal = ref(false)                    // For LOST items (direct claim)
const showVerificationModal = ref(false)             // For FOUND items (need verification)
const selectedItem = ref<any>(null)

// Fetch user profile
const loadUserProfile = async () => {
  isLoadingProfile.value = true
  try {
    const profile = await DashboardService.getUserProfile()
    console.log('=== PROFILE RESPONSE ===')
    console.log('Full profile object:', profile)
    
    userName.value = profile.fullName || 'Student'
    userRank.value = profile.globalRank || 0
    totalUsers.value = profile.totalUsers || 0
    
    // Use correct field names from backend
    stats.value = {
      itemsFound: profile.itemsFoundCount || 0,
      itemsLost: profile.itemsLostCount || 0,
      matched: profile.itemsMatchedCount || 0,
      points: profile.points || 0
    }
    
    // Calculate total items in database
    const itemsFoundCount = profile.itemsFoundCount || 0
    const itemsLostCount = profile.itemsLostCount || 0
    const totalUsersCount = profile.totalUsers || 1
    const userTotalItems = itemsFoundCount + itemsLostCount
    const estimatedTotalItems = userTotalItems * totalUsersCount
    aiStats.value.totalItems = estimatedTotalItems || 0
    
  } catch (error: any) {
    console.error('Error loading profile:', error)
  } finally {
    isLoadingProfile.value = false
  }
}

// Fetch community stats
const loadCommunityStats = async () => {
  isLoadingStats.value = true
  try {
    const data = await DashboardService.getCommunityStats()
    communityStats.value = data
    
    // Get real claimed items count from backend
    const claimedCount = await DashboardService.getClaimedItemsCount()
    
    // Update only recentMatches and successRate
    aiStats.value.recentMatches = claimedCount
    aiStats.value.successRate = data.successRate || 0
    
  } catch (error: any) {
    console.error('Error loading community stats:', error)
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
    recentActivities.value = activities.map((activity: any) => {
      const isFound = activity.type === 'FOUND'
      const isLost = activity.type === 'LOST'
      const isLostMatched = activity.type === 'LOST_MATCHED'      // ✅ Lost item matched with found
      const isFoundNeedsVerify = activity.type === 'FOUND_VERIFY' // ✅ Found item needs verification
      
      const timeDisplay = activity.timestamp ? formatTime(activity.timestamp) : 'Recently'
      
      // ✅ LOST ITEM MATCHED - Direct claim
      if (isLostMatched) {
        return {
          title: activity.title || 'Your Lost Item Found!',
          description: activity.description || 'Your lost item has been found! Click to claim.',
          time: timeDisplay,
          status: 'Ready to Claim',
          statusClass: 'bg-green-100 text-green-700 border border-green-300',
          bgColor: 'bg-gradient-to-br from-green-400 to-emerald-500',
          iconColor: 'text-white',
          icon: 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z',
          type: 'LOST_MATCHED',
          clickable: true,
          modalType: 'CLAIM',  // ✅ Opens ItemClaimModal
          claimId: activity.claimId,
          category: activity.category || 'Electronics',
          color: activity.color || 'Blue',
          imageUrl: activity.imageUrl,
          returnLocation: activity.returnLocation || 'office',
          returnDate: activity.returnDate,
          returnTime: activity.returnTime
        }
      }
      
      // ✅ FOUND ITEM NEEDS VERIFICATION - Upload student card
      if (isFoundNeedsVerify) {
        return {
          title: activity.title || 'Verification Required',
          description: activity.description || 'Upload your student card to verify ownership.',
          time: timeDisplay,
          status: 'Verify Ownership',
          statusClass: 'bg-blue-100 text-blue-700 border border-blue-300',
          bgColor: 'bg-gradient-to-br from-blue-400 to-indigo-500',
          iconColor: 'text-white',
          icon: 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z',
          type: 'FOUND_VERIFY',
          clickable: true,
          modalType: 'VERIFY',  // ✅ Opens StudentCardVerificationModal
          claimId: activity.claimId,
          category: activity.category || 'Electronics',
          color: activity.color || 'Blue',
          imageUrl: activity.imageUrl
        }
      }
      
      // Existing handling for FOUND and LOST
      return {
        title: activity.title,
        description: activity.description,
        time: timeDisplay,
        status: isFound ? 'Found' : 'Lost',
        statusClass: isFound ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700',
        bgColor: isFound ? 'bg-green-100' : 'bg-red-100',
        iconColor: isFound ? 'text-green-600' : 'text-red-600',
        icon: isFound 
          ? 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z'
          : 'M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z',
        type: activity.type,
        clickable: false,
        modalType: null,
        claimId: null
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
        icon: 'M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z',
        type: 'INFO',
        clickable: false,
        modalType: null,
        claimId: null
      }]
    }
  } catch (error: any) {
    console.error('Error loading activities:', error)
    toast.error('Failed to load recent activities')
  } finally {
    isLoadingActivities.value = false
  }
}

// ✅ Handle activity click - Opens DIFFERENT modals based on type
const handleActivityClick = (activity: any) => {
  console.log('=== ACTIVITY CLICKED ===')
  console.log('Activity:', activity)
  console.log('Modal Type:', activity.modalType)
  console.log('========================')
  
  if (!activity.clickable) {
    console.log('⚠️ Activity is not clickable')
    return
  }
  
  if (!activity.claimId) {
    toast.error('No claim ID available')
    return
  }
  
  // Set selected item data
  selectedItem.value = {
    claimId: activity.claimId,
    title: activity.title,
    category: activity.category || 'Electronics',
    color: activity.color || 'Blue',
    description: activity.description,
    imageUrl: activity.imageUrl || 'https://via.placeholder.com/400',
    returnLocation: activity.returnLocation,
    returnDate: activity.returnDate,
    returnTime: activity.returnTime
  }
  
  // ✅ Open DIFFERENT modal based on modalType
  if (activity.modalType === 'CLAIM') {
    // Lost item matched → Direct claim modal
    console.log('✅ Opening ItemClaimModal (Lost item claim)')
    showClaimModal.value = true
    toast.info('Review item details and claim')
  } else if (activity.modalType === 'VERIFY') {
    // Found item → Verification modal
    console.log('✅ Opening VerificationModal (Found item verification)')
    showVerificationModal.value = true
    toast.info('Upload student card for verification')
  }
}

// ✅ Close claim modal
const closeClaimModal = () => {
  console.log('Closing claim modal')
  showClaimModal.value = false
  selectedItem.value = null
}

// ✅ Handle item claimed
const handleItemClaimed = (item: any) => {
  console.log('=== ITEM CLAIMED ===')
  console.log('Item:', item)
  
  // Remove from activities
  recentActivities.value = recentActivities.value.filter(
    activity => activity.claimId !== item.claimId
  )
  
  toast.success('Item claimed! Reload to see updated stats.')
  
  // Reload data
  setTimeout(() => {
    loadRecentActivities()
    loadUserProfile()
  }, 1000)
}

// ✅ Close verification modal
const closeVerificationModal = () => {
  console.log('Closing verification modal')
  showVerificationModal.value = false
  selectedItem.value = null
}

// ✅ Handle verification submitted
const handleVerificationSubmitted = (item: any) => {
  console.log('=== VERIFICATION SUBMITTED ===')
  console.log('Item:', item)
  
  // Update activity status
  const activityIndex = recentActivities.value.findIndex(
    activity => activity.claimId === item.claimId
  )
  
  if (activityIndex !== -1) {
    recentActivities.value[activityIndex] = {
      ...recentActivities.value[activityIndex],
      status: 'Pending Verification',
      statusClass: 'bg-orange-100 text-orange-700 border border-orange-300',
      description: 'Verification submitted. Waiting for admin approval.',
      clickable: false,
      modalType: null
    }
  }
  
  toast.success('Verification submitted! Check back for admin approval.')
}

// Format timestamp
const formatTime = (timestamp: string): string => {
  try {
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
  } catch {
    return 'Recently'
  }
}

// Navigation
const goToReport = () => router.push('/report')
const goToReportFound = () => router.push('/report/found')
const goToReportLost = () => router.push('/report/lost')
const goToRanking = () => router.push('/ranking')

// Load data on mount
onMounted(async () => {
  userId.value = AuthService.getUserId()
  
  if (!userId.value) {
    toast.error('Please login first')
    router.push('/login')
    return
  }
  
  await Promise.all([
    loadUserProfile(),
    loadCommunityStats(),
    loadRecentActivities()
  ])
  
  // ✅ TEMPORARY: Add test activities for both scenarios
  console.log('💡 Adding test activities...')
  
  // Test 1: Lost item matched (direct claim)
  recentActivities.value.unshift({
    title: '🎉 Your iPhone 13 Pro Found!',
    description: 'Click to review details and claim your item',
    time: 'Just now',
    status: 'Ready to Claim',
    statusClass: 'bg-green-100 text-green-700 border border-green-300',
    bgColor: 'bg-gradient-to-br from-green-400 to-emerald-500',
    iconColor: 'text-white',
    icon: 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z',
    type: 'LOST_MATCHED',
    clickable: true,
    modalType: 'CLAIM',
    claimId: 999,
    category: 'Electronics',
    color: 'Pacific Blue',
    imageUrl: 'https://via.placeholder.com/400/0ea5e9/ffffff?text=iPhone+13',
    returnLocation: 'office',
    returnDate: '2025-12-10',
    returnTime: '14:00'
  })
  
  // Test 2: Found item needs verification
  recentActivities.value.unshift({
    title: '📄 Laptop Found - Verify Ownership',
    description: 'Upload your student card to verify and claim',
    time: '5 min ago',
    status: 'Verify Ownership',
    statusClass: 'bg-blue-100 text-blue-700 border border-blue-300',
    bgColor: 'bg-gradient-to-br from-blue-400 to-indigo-500',
    iconColor: 'text-white',
    icon: 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z',
    type: 'FOUND_VERIFY',
    clickable: true,
    modalType: 'VERIFY',
    claimId: 888,
    category: 'Electronics',
    color: 'Silver',
    imageUrl: 'https://via.placeholder.com/400/6366f1/ffffff?text=Laptop'
  })
  
  console.log('✅ Test activities added!')
})
</script>