<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 p-6">
      
      <!-- Page Header -->
      <div class="mb-6">
        <h1 class="text-3xl font-bold text-gray-900">My Profile</h1>
        <p class="text-gray-600 mt-1">Manage your account information and activity</p>
      </div>

      <!-- Loading State -->
      <div v-if="isLoading" class="flex justify-center items-center py-20">
        <div class="text-center">
          <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mx-auto"></div>
          <p class="text-gray-600 mt-4">Loading profile...</p>
        </div>
      </div>

      <!-- Profile Content -->
      <div v-else class="max-w-5xl mx-auto space-y-6">
        
        <!-- Profile Card -->
        <div class="bg-white rounded-2xl shadow-lg overflow-hidden">
          <!-- Header with gradient -->
          <div class="bg-gradient-to-r from-blue-600 to-purple-600 h-32"></div>
          
          <div class="px-6 pb-6">
            <!-- Profile Picture & Basic Info -->
            <div class="flex flex-col md:flex-row items-start md:items-end -mt-16 gap-6">
              <!-- Profile Picture -->
              <div class="relative">
                <div class="w-32 h-32 rounded-full bg-white p-2 shadow-xl">
                  <div class="w-full h-full rounded-full bg-gradient-to-br from-blue-500 to-purple-500 flex items-center justify-center text-white text-4xl font-bold">
                    {{ userInitials }}
                  </div>
                </div>
                <!-- Edit button (future feature) -->
                <button class="absolute bottom-0 right-0 w-10 h-10 bg-blue-600 rounded-full text-white flex items-center justify-center shadow-lg hover:bg-blue-700 transition">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"/>
                  </svg>
                </button>
              </div>

              <!-- Name & Email -->
              <div class="flex-1">
                <h2 class="text-3xl font-bold text-gray-900">{{ profile.fullName }}</h2>
                <p class="text-gray-600 mt-1 flex items-center gap-2">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                  </svg>
                  {{ profile.email }}
                </p>
                <p v-if="profile.studentId" class="text-gray-600 mt-1 flex items-center gap-2">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2"/>
                  </svg>
                  Matric: {{ profile.studentId }}
                </p>
              </div>

              <!-- Points Badge -->
              <div class="bg-gradient-to-br from-yellow-400 to-orange-500 rounded-2xl px-6 py-4 text-white shadow-lg">
                <div class="text-center">
                  <div class="text-sm font-semibold opacity-90">Total Points</div>
                  <div class="text-4xl font-bold">{{ profile.points || 0 }}</div>
                  <div class="text-xs opacity-90 mt-1">🏆 Rewards</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Stats Cards -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <!-- Items Found -->
          <div class="bg-white rounded-2xl shadow-lg p-6 border-l-4 border-green-500">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-600 font-semibold mb-1">Items Reported</p>
                <p class="text-3xl font-bold text-gray-900">{{ profile.itemsFound || 0 }}</p>
                <p class="text-xs text-green-600 mt-2">✅ Found & Reported</p>
              </div>
              <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center">
                <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
              </div>
            </div>
          </div>

          <!-- Items Lost -->
          <div class="bg-white rounded-2xl shadow-lg p-6 border-l-4 border-red-500">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-600 font-semibold mb-1">Items Lost</p>
                <p class="text-3xl font-bold text-gray-900">{{ profile.itemsLost || 0 }}</p>
                <p class="text-xs text-red-600 mt-2">🔍 Reported Missing</p>
              </div>
              <div class="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center">
                <svg class="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                </svg>
              </div>
            </div>
          </div>

          <!-- Items Matched -->
          <div class="bg-white rounded-2xl shadow-lg p-6 border-l-4 border-blue-500">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-600 font-semibold mb-1">Successfully Matched</p>
                <p class="text-3xl font-bold text-gray-900">{{ profile.itemsMatched || 0 }}</p>
                <p class="text-xs text-blue-600 mt-2">🎯 AI Matched</p>
              </div>
              <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center">
                <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- Account Information -->
        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h3 class="text-xl font-bold text-gray-900 mb-6 flex items-center gap-2">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
            </svg>
            Account Information
          </h3>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Full Name -->
            <div>
              <label class="text-sm font-semibold text-gray-600">Full Name</label>
              <div class="mt-2 p-4 bg-gray-50 rounded-xl border border-gray-200">
                <p class="text-gray-900 font-medium">{{ profile.fullName }}</p>
              </div>
            </div>

            <!-- Email -->
            <div>
              <label class="text-sm font-semibold text-gray-600">Email Address</label>
              <div class="mt-2 p-4 bg-gray-50 rounded-xl border border-gray-200">
                <p class="text-gray-900 font-medium">{{ profile.email }}</p>
              </div>
            </div>

            <!-- Matric Number (Full Width) -->
            <div class="md:col-span-2">
              <label class="text-sm font-semibold text-gray-600">Matric Number</label>
              <div class="mt-2 p-4 bg-gradient-to-r from-blue-50 to-purple-50 rounded-xl border-2 border-blue-200">
                <p class="text-gray-900 font-bold text-lg">{{ profile.studentId || 'Not provided' }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Actions -->
        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h3 class="text-xl font-bold text-gray-900 mb-4">Quick Actions</h3>
          
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <button 
              @click="$router.push('/report-found')"
              class="p-4 border-2 border-blue-200 rounded-xl hover:bg-blue-50 transition text-left">
              <div class="flex items-center gap-3">
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                  <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
                  </svg>
                </div>
                <div>
                  <div class="font-semibold text-gray-900">Report Found Item</div>
                  <div class="text-xs text-gray-600">Help others find their items</div>
                </div>
              </div>
            </button>

            <button 
              @click="$router.push('/report-lost')"
              class="p-4 border-2 border-red-200 rounded-xl hover:bg-red-50 transition text-left">
              <div class="flex items-center gap-3">
                <div class="w-12 h-12 bg-red-100 rounded-lg flex items-center justify-center">
                  <svg class="w-6 h-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                  </svg>
                </div>
                <div>
                  <div class="font-semibold text-gray-900">Report Lost Item</div>
                  <div class="text-xs text-gray-600">Start searching for your item</div>
                </div>
              </div>
            </button>

            <button 
              @click="handleLogout"
              class="p-4 border-2 border-gray-200 rounded-xl hover:bg-gray-50 transition text-left">
              <div class="flex items-center gap-3">
                <div class="w-12 h-12 bg-gray-100 rounded-lg flex items-center justify-center">
                  <svg class="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
                  </svg>
                </div>
                <div>
                  <div class="font-semibold text-gray-900">Logout</div>
                  <div class="text-xs text-gray-600">Sign out of your account</div>
                </div>
              </div>
            </button>
          </div>
        </div>

      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import DashboardService from '../services/dashboard.service'
import FoundItemService from '../services/founditem.service'
import AuthService from '../services/auth.service'

const router = useRouter()
const toast = useToast()

const isLoading = ref(true)

const profile = ref<any>({
  fullName: '',
  email: '',
  studentId: '',
  points: 0,
  itemsFound: 0,
  itemsLost: 0,
  itemsMatched: 0
})

// Computed: User Initials
const userInitials = computed(() => {
  const names = profile.value.fullName?.split(' ') || []
  if (names.length >= 2) {
    return names[0][0] + names[names.length - 1][0]
  } else if (names.length === 1) {
    return names[0][0]
  }
  return 'U'
})

// Load profile data
const loadProfile = async () => {
  isLoading.value = true
  
  try {
    console.log('Loading profile...')
    
    // ✅ Get user profile (name, email, studentId)
    const profileData = await DashboardService.getUserProfile()
    
    console.log('Profile data:', profileData)
    
    // ✅ Get user statistics from recent activity
    const userId = AuthService.getUserId()
    const activities = await DashboardService.getRecentActivity()
    
    console.log('Activities:', activities)
    
    // Calculate stats from activities
    const itemsFound = activities.filter((a: any) => a.type === 'FOUND').length
    const itemsLost = activities.filter((a: any) => 
      a.type === 'LOST' || 
      a.type === 'LOST_MATCHED' || 
      a.type === 'PENDING_VERIFICATION' ||
      a.type === 'CLAIMED'
    ).length
    const itemsMatched = activities.filter((a: any) => 
      a.type === 'LOST_MATCHED' || 
      a.type === 'CLAIMED'
    ).length
    
    profile.value = {
      fullName: profileData.fullName || '',
      email: profileData.email || '',
      studentId: profileData.studentId || '',
      points: profileData.points || 0,
      itemsFound: itemsFound,
      itemsLost: itemsLost,
      itemsMatched: itemsMatched
    }
    
    console.log('Profile loaded:', profile.value)
    
  } catch (error: any) {
    console.error('Error loading profile:', error)
    toast.error('Failed to load profile data')
  } finally {
    isLoading.value = false
  }
}

// Logout
const handleLogout = () => {
  AuthService.logout()
  toast.success('Logged out successfully')
  router.push('/login')
}

// Load on mount
onMounted(() => {
  loadProfile()
})
</script>

<style scoped>
/* Custom animations */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>