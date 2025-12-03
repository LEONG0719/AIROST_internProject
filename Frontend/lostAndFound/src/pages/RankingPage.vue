<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gray-50">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <!-- Page Header -->
        <div class="bg-gradient-to-r from-purple-600 to-blue-600 rounded-lg shadow-md p-8 mb-8 text-white">
          <h1 class="text-3xl md:text-4xl font-bold mb-2">🏆 Leaderboard</h1>
          <p class="text-purple-100">Top contributors who help reunite lost items with their owners</p>
        </div>

        <!-- Top 3 Podium -->
        <div class="grid grid-cols-3 gap-4 mb-8 max-w-3xl mx-auto">
          
          <!-- 2nd Place -->
          <div class="text-center pt-12">
            <div class="bg-white rounded-lg shadow-lg p-4 relative">
              <div class="absolute -top-8 left-1/2 transform -translate-x-1/2">
                <div class="w-16 h-16 bg-gray-300 rounded-full flex items-center justify-center text-2xl font-bold border-4 border-white shadow-lg">
                  2
                </div>
              </div>
              <div class="mt-8">
                <div class="w-16 h-16 bg-gray-200 rounded-full mx-auto mb-2 flex items-center justify-center">
                  <svg class="w-8 h-8 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                  </svg>
                </div>
                <h3 class="font-semibold text-gray-900 text-sm">Sarah Chen</h3>
                <p class="text-xs text-gray-600 mb-2">A20EC0234</p>
                <p class="text-lg font-bold text-gray-700">185 pts</p>
              </div>
            </div>
          </div>

          <!-- 1st Place -->
          <div class="text-center">
            <div class="bg-white rounded-lg shadow-xl p-4 relative">
              <div class="absolute -top-10 left-1/2 transform -translate-x-1/2">
                <div class="w-20 h-20 bg-yellow-400 rounded-full flex items-center justify-center text-3xl font-bold border-4 border-white shadow-lg">
                  👑
                </div>
              </div>
              <div class="mt-10">
                <div class="w-20 h-20 bg-yellow-100 rounded-full mx-auto mb-2 flex items-center justify-center">
                  <svg class="w-10 h-10 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                  </svg>
                </div>
                <h3 class="font-semibold text-gray-900">Ahmad Faris</h3>
                <p class="text-xs text-gray-600 mb-2">A20EC0123</p>
                <p class="text-2xl font-bold text-yellow-600">245 pts</p>
              </div>
            </div>
          </div>

          <!-- 3rd Place -->
          <div class="text-center pt-16">
            <div class="bg-white rounded-lg shadow-lg p-4 relative">
              <div class="absolute -top-6 left-1/2 transform -translate-x-1/2">
                <div class="w-14 h-14 bg-orange-400 rounded-full flex items-center justify-center text-xl font-bold border-4 border-white shadow-lg">
                  3
                </div>
              </div>
              <div class="mt-6">
                <div class="w-14 h-14 bg-orange-100 rounded-full mx-auto mb-2 flex items-center justify-center">
                  <svg class="w-7 h-7 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                  </svg>
                </div>
                <h3 class="font-semibold text-gray-900 text-sm">Lisa Wong</h3>
                <p class="text-xs text-gray-600 mb-2">A20EC0456</p>
                <p class="text-lg font-bold text-orange-600">142 pts</p>
              </div>
            </div>
          </div>

        </div>

        <!-- Ranking Table -->
        <div class="bg-white rounded-lg shadow-md overflow-hidden">
          <table class="w-full">
            <thead class="bg-gray-50 border-b">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Rank</th>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">User</th>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Items Found</th>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Points</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
              <tr v-for="(user, index) in rankings" :key="index" class="hover:bg-gray-50">
                <td class="px-6 py-4">
                  <span class="font-semibold text-gray-900">#{{ user.rank }}</span>
                </td>
                <td class="px-6 py-4">
                  <div class="flex items-center gap-3">
                    <div class="w-10 h-10 bg-blue-100 rounded-full flex items-center justify-center">
                      <span class="text-sm font-semibold text-blue-600">{{ user.name.charAt(0) }}</span>
                    </div>
                    <div>
                      <div class="font-medium text-gray-900">{{ user.name }}</div>
                      <div class="text-sm text-gray-500">{{ user.studentId }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4 text-gray-700">{{ user.itemsFound }}</td>
                <td class="px-6 py-4">
                  <span class="font-semibold text-blue-600">{{ user.points }} pts</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import DashboardLayout from '../layouts/DashboardLayout.vue'

// Mock data
const rankings = [
  { rank: 4, name: 'David Kumar', studentId: 'A20EC0567', itemsFound: 9, points: 135 },
  { rank: 5, name: 'Emily Tan', studentId: 'A20EC0678', itemsFound: 8, points: 120 },
  { rank: 6, name: 'Michael Lee', studentId: 'A20EC0789', itemsFound: 7, points: 105 },
  { rank: 7, name: 'Fatimah Ali', studentId: 'A20EC0890', itemsFound: 6, points: 90 },
  { rank: 8, name: 'John Smith', studentId: 'A20EC0901', itemsFound: 5, points: 75 },
  { rank: 9, name: 'Nurul Huda', studentId: 'A20EC1012', itemsFound: 4, points: 60 },
  { rank: 10, name: 'Alex Wong', studentId: 'A20EC1123', itemsFound: 3, points: 45 }
]
</script>