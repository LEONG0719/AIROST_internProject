<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gray-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          
          <!-- Left Side - Main Content -->
          <div class="lg:col-span-2 space-y-6">
            
            <!-- Header Card -->
            <div class="relative overflow-hidden bg-gradient-to-br from-indigo-600 via-purple-600 to-pink-500 rounded-3xl shadow-2xl p-8 text-white">
              <!-- Decorative Elements -->
              <div class="absolute top-0 right-0 w-64 h-64 bg-white opacity-10 rounded-full -mr-32 -mt-32"></div>
              <div class="absolute bottom-0 left-0 w-48 h-48 bg-white opacity-10 rounded-full -ml-24 -mb-24"></div>
              
              <div class="relative z-10">
                <div class="flex items-center gap-3 mb-3">
                  <div class="w-14 h-14 bg-yellow-500 rounded-full flex items-center justify-center text-3xl shadow-lg">
                    🏆
                  </div>
                  <div>
                    <h1 class="text-3xl md:text-4xl font-bold">Community Heroes</h1>
                    <p class="text-purple-200 text-sm">Hall of Fame</p>
                  </div>
                </div>
                
                <p class="text-purple-100 mb-6 text-sm md:text-base">
                  Celebrating our amazing community members who help reunite lost items!
                </p>
                
                <div class="grid grid-cols-2 md:grid-cols-4 gap-3">
                  <div class="bg-blue-700 bg-opacity-20 backdrop-blur-md rounded-2xl p-4 hover:bg-opacity-30 transition">
                    <div class="flex items-center gap-2 mb-2">
                      <svg class="w-5 h-5 text-yellow-300" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
                      </svg>
                      <div class="text-2xl font-bold text-white">{{ totalReturned }}</div>
                    </div>
                    <div class="text-xs text-white font-semibold">Items Returned</div>
                  </div>
                  
                  <div class="bg-blue-700 bg-opacity-20 backdrop-blur-md rounded-2xl p-4 hover:bg-opacity-30 transition">
                    <div class="flex items-center gap-2 mb-2">
                      <svg class="w-5 h-5 text-green-300" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9 6a3 3 0 11-6 0 3 3 0 016 0zM17 6a3 3 0 11-6 0 3 3 0 016 0zM12.93 17c.046-.327.07-.66.07-1a6.97 6.97 0 00-1.5-4.33A5 5 0 0119 16v1h-6.07zM6 11a5 5 0 015 5v1H1v-1a5 5 0 015-5z"/>
                      </svg>
                      <div class="text-2xl font-bold text-white">{{ totalUsers }}</div>
                    </div>
                    <div class="text-xs text-white font-semibold">Active Heroes</div>
                  </div>
                  
                  <div class="bg-blue-700 bg-opacity-20 backdrop-blur-md rounded-2xl p-4 hover:bg-opacity-30 transition">
                    <div class="flex items-center gap-2 mb-2">
                      <svg class="w-5 h-5 text-blue-300" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M6.267 3.455a3.066 3.066 0 001.745-.723 3.066 3.066 0 013.976 0 3.066 3.066 0 001.745.723 3.066 3.066 0 012.812 2.812c.051.643.304 1.254.723 1.745a3.066 3.066 0 010 3.976 3.066 3.066 0 00-.723 1.745 3.066 3.066 0 01-2.812 2.812 3.066 3.066 0 00-1.745.723 3.066 3.066 0 01-3.976 0 3.066 3.066 0 00-1.745-.723 3.066 3.066 0 01-2.812-2.812 3.066 3.066 0 00-.723-1.745 3.066 3.066 0 010-3.976 3.066 3.066 0 00.723-1.745 3.066 3.066 0 012.812-2.812zm7.44 5.252a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                      </svg>
                      <div class="text-2xl font-bold text-white">{{ successRate }}%</div>
                    </div>
                    <div class="text-xs text-white font-semibold">Success Rate</div>
                  </div>
                  
                  <div class="bg-blue-700 bg-opacity-20 backdrop-blur-md rounded-2xl p-4 hover:bg-opacity-30 transition">
                    <div class="flex items-center gap-2 mb-2">
                      <svg class="w-5 h-5 text-pink-400" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" clip-rule="evenodd"/>
                      </svg>
                      <div class="text-2xl font-bold text-white">{{ totalPoints }}</div>
                    </div>
                    <div class="text-xs text-white font-semibold">Total Points</div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Top 3 Winners -->
            <div class="bg-white rounded-2xl shadow-lg p-4 md:p-6">
              <div class="flex items-center justify-between mb-4 md:mb-6">
                <h2 class="text-xl md:text-2xl font-bold text-gray-900">Top Champions</h2>
                <span class="text-xs md:text-sm text-gray-500">This Month</span>
              </div>

              <div class="grid grid-cols-3 gap-2 md:gap-4">
                <!-- Winner Cards -->
                <div 
                  v-for="(winner, index) in topThree" 
                  :key="index"
                  class="relative bg-gradient-to-br rounded-lg md:rounded-xl p-3 md:p-6 text-center transform hover:scale-105 transition-transform cursor-pointer"
                  :class="winner.gradient"
                >
                  <!-- Rank Badge -->
                  <div class="absolute -top-2 -right-2 w-8 h-8 md:w-12 md:h-12 rounded-full flex items-center justify-center text-sm md:text-xl font-bold shadow-lg"
                    :class="winner.badgeColor">
                    {{ winner.rank }}
                  </div>

                  <!-- Avatar -->
                  <div class="relative w-12 h-12 md:w-20 md:h-20 mx-auto mb-2 md:mb-4">
                    <div class="w-full h-full rounded-full bg-white flex items-center justify-center text-lg md:text-2xl font-bold shadow-lg"
                      :class="winner.avatarText">
                      {{ winner.name.charAt(0) }}
                    </div>
                    <div v-if="winner.rank === 1" class="absolute -top-1 -right-1 md:-top-2 md:-right-2 text-xl md:text-3xl">👑</div>
                  </div>

                  <h3 class="font-bold text-gray-900 mb-1 text-xs md:text-base truncate">{{ winner.name }}</h3>
                  <p class="text-[10px] md:text-xs text-gray-600 mb-2 md:mb-3 truncate">{{ winner.studentId }}</p>
                  
                  <div class="flex items-center justify-center gap-1 md:gap-2 mb-1 md:mb-2">
                    <svg class="w-3 h-3 md:w-5 md:h-5 text-yellow-500" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
                    </svg>
                    <span class="font-bold text-gray-900 text-sm md:text-base">{{ winner.points }}</span>
                  </div>
                  
                  <div class="text-[10px] md:text-xs text-gray-600">{{ winner.itemsFound }} items</div>
                </div>
              </div>
            </div>

          </div>

          <!-- Right Side - Leaderboard List -->
          <div class="lg:col-span-1">
            <div class="bg-white rounded-2xl shadow-lg overflow-hidden sticky top-6">
              <div class="bg-gradient-to-r from-blue-600 to-purple-600 p-6 text-white">
                <h2 class="text-xl font-bold mb-1">Leaderboard</h2>
                <p class="text-sm text-blue-100">All Time Rankings</p>
              </div>

              <div class="p-4 max-h-[600px] overflow-y-auto">
                <div 
                  v-for="(user, index) in allRankings" 
                  :key="index"
                  class="flex items-center gap-3 p-3 rounded-xl hover:bg-gray-50 transition cursor-pointer mb-2"
                  :class="{ 'bg-blue-50': user.isCurrentUser }"
                >
                  <!-- Rank -->
                  <div class="flex-shrink-0 w-8 text-center">
                    <span class="font-bold text-gray-900">#{{ user.rank }}</span>
                  </div>

                  <!-- Avatar -->
                  <div class="flex-shrink-0 w-12 h-12 rounded-full bg-gradient-to-br flex items-center justify-center text-white font-semibold shadow-md"
                    :class="user.avatarGradient">
                    {{ user.name.charAt(0) }}
                  </div>

                  <!-- User Info -->
                  <div class="flex-1 min-w-0">
                    <div class="font-semibold text-gray-900 truncate">{{ user.name }}</div>
                    <div class="text-xs text-gray-500">{{ user.points }} points</div>
                  </div>

                  <!-- Like/Points Icon -->
                  <div class="flex-shrink-0">
                    <svg v-if="user.isCurrentUser" class="w-5 h-5 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                    </svg>
                    <svg v-else class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                    </svg>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>

        <!-- How Points Work Section -->
        <div class="mt-8 bg-white rounded-2xl shadow-lg p-6">
          <h3 class="text-xl font-bold text-gray-900 mb-4">🎯 How to Earn Points</h3>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div class="flex items-start gap-3 p-4 bg-green-50 rounded-xl">
              <div class="flex-shrink-0 w-10 h-10 bg-green-500 rounded-full flex items-center justify-center text-white">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
              </div>
              <div>
                <div class="font-semibold text-gray-900 mb-1">Report Found Item</div>
                <div class="text-sm text-gray-600">+10 points per item</div>
              </div>
            </div>

            <div class="flex items-start gap-3 p-4 bg-blue-50 rounded-xl">
              <div class="flex-shrink-0 w-10 h-10 bg-blue-500 rounded-full flex items-center justify-center text-white">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                </svg>
              </div>
              <div>
                <div class="font-semibold text-gray-900 mb-1">Successful Match</div>
                <div class="text-sm text-gray-600">+25 bonus points</div>
              </div>
            </div>

            <div class="flex items-start gap-3 p-4 bg-purple-50 rounded-xl">
              <div class="flex-shrink-0 w-10 h-10 bg-purple-500 rounded-full flex items-center justify-center text-white">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"/>
                </svg>
              </div>
              <div>
                <div class="font-semibold text-gray-900 mb-1">Item Returned</div>
                <div class="text-sm text-gray-600">+50 points when claimed</div>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import DashboardLayout from '../layouts/DashboardLayout.vue'

const totalReturned = ref(89)
const totalUsers = ref(234)
const successRate = ref(95)
const totalPoints = ref(1847)

const topThree = [
  {
    rank: 1,
    name: 'Ahmad Faris',
    studentId: 'A20EC0123',
    points: 245,
    itemsFound: 15,
    gradient: 'from-yellow-400 to-orange-500',
    badgeColor: 'bg-yellow-400 text-yellow-900',
    avatarText: 'text-yellow-600'
  },
  {
    rank: 2,
    name: 'Sarah Chen',
    studentId: 'A20EC0234',
    points: 185,
    itemsFound: 12,
    gradient: 'from-gray-300 to-gray-400',
    badgeColor: 'bg-gray-300 text-gray-900',
    avatarText: 'text-gray-600'
  },
  {
    rank: 3,
    name: 'Lisa Wong',
    studentId: 'A20EC0456',
    points: 142,
    itemsFound: 9,
    gradient: 'from-orange-400 to-red-500',
    badgeColor: 'bg-orange-400 text-orange-900',
    avatarText: 'text-orange-600'
  }
]

const allRankings = [
  { rank: 1, name: 'Ahmad Faris', points: 245, avatarGradient: 'from-yellow-400 to-orange-500', isCurrentUser: false },
  { rank: 2, name: 'Sarah Chen', points: 185, avatarGradient: 'from-blue-400 to-blue-600', isCurrentUser: false },
  { rank: 3, name: 'Lisa Wong', points: 142, avatarGradient: 'from-purple-400 to-purple-600', isCurrentUser: false },
  { rank: 4, name: 'David Kumar', points: 135, avatarGradient: 'from-green-400 to-green-600', isCurrentUser: false },
  { rank: 5, name: 'Emily Tan', points: 120, avatarGradient: 'from-pink-400 to-pink-600', isCurrentUser: false },
  { rank: 6, name: 'Michael Lee', points: 105, avatarGradient: 'from-indigo-400 to-indigo-600', isCurrentUser: false },
  { rank: 7, name: 'Fatimah Ali', points: 90, avatarGradient: 'from-red-400 to-red-600', isCurrentUser: false },
  { rank: 8, name: 'John Smith', points: 75, avatarGradient: 'from-teal-400 to-teal-600', isCurrentUser: false },
  { rank: 9, name: 'Nurul Huda', points: 60, avatarGradient: 'from-cyan-400 to-cyan-600', isCurrentUser: false },
  { rank: 10, name: 'Alex Wong', points: 45, avatarGradient: 'from-orange-400 to-orange-600', isCurrentUser: false },
  { rank: 11, name: 'Siti Aminah', points: 40, avatarGradient: 'from-lime-400 to-lime-600', isCurrentUser: false },
  { rank: 12, name: 'James Lim', points: 35, avatarGradient: 'from-emerald-400 to-emerald-600', isCurrentUser: false },
  { rank: 13, name: 'Rachel Ng', points: 30, avatarGradient: 'from-violet-400 to-violet-600', isCurrentUser: false },
  { rank: 14, name: 'Hassan Ibrahim', points: 28, avatarGradient: 'from-fuchsia-400 to-fuchsia-600', isCurrentUser: false },
  { rank: 15, name: 'You', points: 25, avatarGradient: 'from-blue-500 to-blue-700', isCurrentUser: true }
]
</script>

<style scoped>
/* Custom scrollbar for leaderboard */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #cbd5e0;
  border-radius: 10px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a0aec0;
}
</style>