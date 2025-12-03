<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gray-50">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <!-- Page Header -->
        <div class="bg-white rounded-lg shadow-md p-6 mb-6">
          <h1 class="text-3xl font-bold text-gray-900 mb-2">My Profile</h1>
          <p class="text-gray-600">Manage your account settings and preferences</p>
        </div>

        <!-- Profile Content -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          
          <!-- Profile Card -->
          <div class="md:col-span-1">
            <div class="bg-white rounded-lg shadow-md p-6 text-center">
              <!-- Avatar -->
              <div class="w-32 h-32 bg-blue-100 rounded-full mx-auto mb-4 flex items-center justify-center">
                <svg class="w-16 h-16 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                </svg>
              </div>
              
              <h2 class="text-xl font-bold text-gray-900 mb-1">John Doe</h2>
              <p class="text-sm text-gray-600 mb-4">john.doe@graduate.utm.my</p>
              
              <button class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition">
                Edit Profile
              </button>
            </div>

            <!-- Stats Card -->
            <div class="bg-white rounded-lg shadow-md p-6 mt-6">
              <h3 class="font-semibold text-gray-900 mb-4">My Statistics</h3>
              <div class="space-y-3">
                <div class="flex justify-between">
                  <span class="text-gray-600 text-sm">Items Found</span>
                  <span class="font-semibold text-green-600">12</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600 text-sm">Items Lost</span>
                  <span class="font-semibold text-red-600">3</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600 text-sm">Total Points</span>
                  <span class="font-semibold text-blue-600">245</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600 text-sm">Rank</span>
                  <span class="font-semibold text-purple-600">#15</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Profile Details -->
          <div class="md:col-span-2">
            <div class="bg-white rounded-lg shadow-md p-6">
              <h3 class="text-xl font-semibold text-gray-900 mb-6">Account Information</h3>
              
              <div class="space-y-4">
                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">Full Name</label>
                  <input 
                    type="text" 
                    value="John Doe"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                  />
                </div>

                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">Email</label>
                  <input 
                    type="email" 
                    value="john.doe@graduate.utm.my"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                  />
                </div>

                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">Phone Number</label>
                  <input 
                    type="tel" 
                    value="012-3456789"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                  />
                </div>

                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">Student ID</label>
                  <input 
                    type="text" 
                    value="A20EC0123"
                    disabled
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-100 text-gray-600"
                  />
                </div>

                <div class="pt-4">
                  <button class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition">
                    Save Changes
                  </button>
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
import DashboardLayout from '../layouts/DashboardLayout.vue'
</script>