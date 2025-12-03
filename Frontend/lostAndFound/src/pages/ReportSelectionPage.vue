<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        
        <!-- Page Header -->
        <div class="text-center mb-12">
          <h1 class="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
            What would you like to report?
          </h1>
          <p class="text-lg text-gray-600 max-w-2xl mx-auto">
            Choose whether you've found an item or lost something. Our AI will help match items with their owners.
          </p>
        </div>

        <!-- Selection Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-4xl mx-auto">
          
          <!-- Found Item Card -->
          <div 
            @click="navigateToFound"
            class="group relative bg-white rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-300 overflow-hidden cursor-pointer border-2 border-transparent hover:border-green-500 transform hover:-translate-y-2"
          >
            <!-- Background Pattern -->
            <div class="absolute inset-0 bg-gradient-to-br from-green-50 to-emerald-50 opacity-50"></div>
            
            <!-- Decorative Icon Background -->
            <div class="absolute top-0 right-0 w-32 h-32 transform translate-x-8 -translate-y-8">
              <div class="w-full h-full bg-green-200 rounded-full opacity-20"></div>
            </div>
            
            <div class="relative p-8">
              <!-- Icon -->
              <div class="mb-6 flex justify-center">
                <div class="w-24 h-24 bg-gradient-to-br from-green-500 to-emerald-600 rounded-full flex items-center justify-center shadow-lg transform group-hover:scale-110 transition-transform duration-300">
                  <svg class="w-12 h-12 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                </div>
              </div>
              
              <!-- Content -->
              <h2 class="text-2xl font-bold text-gray-900 mb-3 text-center">
                Report Found Item
              </h2>
              <p class="text-gray-600 text-center mb-6">
                Found something? Help return it to its owner by reporting it here.
              </p>
              
              <!-- Features List -->
              <ul class="space-y-2 mb-6">
                <li class="flex items-start gap-2 text-sm text-gray-700">
                  <svg class="w-5 h-5 text-green-500 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span>Upload photo of the item</span>
                </li>
                <li class="flex items-start gap-2 text-sm text-gray-700">
                  <svg class="w-5 h-5 text-green-500 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span>AI automatically matches with lost items</span>
                </li>
                <li class="flex items-start gap-2 text-sm text-gray-700">
                  <svg class="w-5 h-5 text-green-500 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span>Earn points for helping others</span>
                </li>
              </ul>
              
              <!-- Button -->
              <button class="w-full bg-gradient-to-r from-green-500 to-emerald-600 text-white py-3 rounded-xl font-semibold hover:from-green-600 hover:to-emerald-700 transition-all duration-300 flex items-center justify-center gap-2 group-hover:shadow-lg">
                <span>Report Found Item</span>
                <svg class="w-5 h-5 transform group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7l5 5m0 0l-5 5m5-5H6"/>
                </svg>
              </button>
            </div>
          </div>

          <!-- Lost Item Card -->
          <div 
            @click="navigateToLost"
            class="group relative bg-white rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-300 overflow-hidden cursor-pointer border-2 border-transparent hover:border-red-500 transform hover:-translate-y-2"
          >
            <!-- Background Pattern -->
            <div class="absolute inset-0 bg-gradient-to-br from-red-50 to-orange-50 opacity-50"></div>
            
            <!-- Decorative Icon Background -->
            <div class="absolute top-0 right-0 w-32 h-32 transform translate-x-8 -translate-y-8">
              <div class="w-full h-full bg-red-200 rounded-full opacity-20"></div>
            </div>
            
            <div class="relative p-8">
              <!-- Icon -->
              <div class="mb-6 flex justify-center">
                <div class="w-24 h-24 bg-gradient-to-br from-red-500 to-orange-600 rounded-full flex items-center justify-center shadow-lg transform group-hover:scale-110 transition-transform duration-300">
                  <svg class="w-12 h-12 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                  </svg>
                </div>
              </div>
              
              <!-- Content -->
              <h2 class="text-2xl font-bold text-gray-900 mb-3 text-center">
                Report Lost Item
              </h2>
              <p class="text-gray-600 text-center mb-6">
                Lost something? Let our community help you find it.
              </p>
              
              <!-- Features List -->
              <ul class="space-y-2 mb-6">
                <li class="flex items-start gap-2 text-sm text-gray-700">
                  <svg class="w-5 h-5 text-red-500 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span>Provide detailed description</span>
                </li>
                <li class="flex items-start gap-2 text-sm text-gray-700">
                  <svg class="w-5 h-5 text-red-500 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span>Optional photo upload</span>
                </li>
                <li class="flex items-start gap-2 text-sm text-gray-700">
                  <svg class="w-5 h-5 text-red-500 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                  </svg>
                  <span>Get notified when match is found</span>
                </li>
              </ul>
              
              <!-- Button -->
              <button class="w-full bg-gradient-to-r from-red-500 to-orange-600 text-white py-3 rounded-xl font-semibold hover:from-red-600 hover:to-orange-700 transition-all duration-300 flex items-center justify-center gap-2 group-hover:shadow-lg">
                <span>Report Lost Item</span>
                <svg class="w-5 h-5 transform group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7l5 5m0 0l-5 5m5-5H6"/>
                </svg>
              </button>
            </div>
          </div>

        </div>

        <!-- Statistics Section -->
        <div class="mt-16 bg-white rounded-2xl shadow-lg p-8">
          <h3 class="text-2xl font-bold text-gray-900 text-center mb-8">
            Community Impact
          </h3>
          <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
            <div class="text-center">
              <div class="text-3xl font-bold text-blue-600 mb-2">156</div>
              <div class="text-sm text-gray-600">Items Reported</div>
            </div>
            <div class="text-center">
              <div class="text-3xl font-bold text-green-600 mb-2">89</div>
              <div class="text-sm text-gray-600">Successfully Returned</div>
            </div>
            <div class="text-center">
              <div class="text-3xl font-bold text-purple-600 mb-2">234</div>
              <div class="text-sm text-gray-600">Active Users</div>
            </div>
            <div class="text-center">
              <div class="text-3xl font-bold text-orange-600 mb-2">95%</div>
              <div class="text-sm text-gray-600">Success Rate</div>
            </div>
          </div>
        </div>

        <!-- Help Section -->
        <div class="mt-8 text-center">
          <p class="text-gray-600 mb-4">Not sure which option to choose?</p>
          <button class="text-blue-600 hover:text-blue-700 font-semibold flex items-center gap-2 mx-auto">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
            <span>View Help Guide</span>
          </button>
        </div>

      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import DashboardLayout from '../layouts/DashboardLayout.vue'

const router = useRouter()

const navigateToFound = () => {
  router.push('/report/found')
}

const navigateToLost = () => {
  router.push('/report/lost')
}
</script>

<style scoped>
/* Add smooth hover animations */
@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-10px);
  }
}

.group:hover .animate-float {
  animation: float 2s ease-in-out infinite;
}
</style>