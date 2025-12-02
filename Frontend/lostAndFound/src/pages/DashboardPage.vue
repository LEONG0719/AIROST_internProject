<template>
  <DashboardLayout>
    <h1 class="text-2xl font-bold mb-4">Welcome to TrackNFind</h1>
    
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <!-- Quick Action Cards -->
      <div class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition cursor-pointer">
        <div class="flex items-center justify-between mb-4">
          <div class="bg-red-100 p-3 rounded-full">
            <svg class="w-6 h-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
            </svg>
          </div>
          <span class="text-2xl font-bold text-gray-800">24</span>
        </div>
        <h3 class="text-lg font-semibold text-gray-800 mb-2">Report Lost Item</h3>
        <p class="text-sm text-gray-600">Lost something? Let us help you find it</p>
      </div>

      <div class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition cursor-pointer">
        <div class="flex items-center justify-between mb-4">
          <div class="bg-green-100 p-3 rounded-full">
            <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
          </div>
          <span class="text-2xl font-bold text-gray-800">18</span>
        </div>
        <h3 class="text-lg font-semibold text-gray-800 mb-2">Report Found Item</h3>
        <p class="text-sm text-gray-600">Found something? Help return it</p>
      </div>

      <div class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition cursor-pointer">
        <div class="flex items-center justify-between mb-4">
          <div class="bg-blue-100 p-3 rounded-full">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
            </svg>
          </div>
          <span class="text-2xl font-bold text-gray-800">156</span>
        </div>
        <h3 class="text-lg font-semibold text-gray-800 mb-2">Browse Items</h3>
        <p class="text-sm text-gray-600">Search through lost & found items</p>
      </div>

      <div class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition cursor-pointer">
        <div class="flex items-center justify-between mb-4">
          <div class="bg-purple-100 p-3 rounded-full">
            <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
            </svg>
          </div>
          <span class="text-2xl font-bold text-gray-800">8</span>
        </div>
        <h3 class="text-lg font-semibold text-gray-800 mb-2">My Reports</h3>
        <p class="text-sm text-gray-600">View your reported items</p>
      </div>
    </div>

    <!-- Recent Activity -->
    <div class="bg-white rounded-lg shadow-md p-6">
      <h2 class="text-xl font-bold text-gray-800 mb-4">Recent Activity</h2>
      <div class="space-y-4">
        <div class="flex items-start gap-4 pb-4 border-b">
          <div class="bg-green-100 p-2 rounded-full flex-shrink-0">
            <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
          </div>
          <div class="flex-1">
            <p class="text-gray-800 font-medium">iPhone 13 Pro found</p>
            <p class="text-sm text-gray-600">Found at Library - 2 hours ago</p>
          </div>
        </div>
        
        <div class="flex items-start gap-4 pb-4 border-b">
          <div class="bg-red-100 p-2 rounded-full flex-shrink-0">
            <svg class="w-5 h-5 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
            </svg>
          </div>
          <div class="flex-1">
            <p class="text-gray-800 font-medium">Student ID Card lost</p>
            <p class="text-sm text-gray-600">Lost at Cafeteria - 5 hours ago</p>
          </div>
        </div>

        <div class="flex items-start gap-4">
          <div class="bg-blue-100 p-2 rounded-full flex-shrink-0">
            <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>
            </svg>
          </div>
          <div class="flex-1">
            <p class="text-gray-800 font-medium">New message received</p>
            <p class="text-sm text-gray-600">Someone claimed your item - 1 day ago</p>
          </div>
        </div>
      </div>
    </div>

  </DashboardLayout>
</template>

<script setup>
import DashboardLayout from '../layouts/DashboardLayout.vue'
</script>