<template>
  <div class="min-h-screen flex flex-col md:flex-row">

    <!-- LEFT IMAGE PANEL (Desktop only) -->
    <div class="hidden md:flex md:w-1/2 relative items-center justify-center">
      <!-- Background Image -->
      <div class="absolute inset-0 bg-cover bg-center "
           style="background-image: url('/utmlogooo.png');">
      </div>

      <!-- Left side text -->
      <div class="relative text-center text-white px-6 md:px-12">
        <h1 class="text-2xl md:text-3xl font-bold mb-4">
          Lost something?<br />
          Let's find it together!
        </h1>

        <p class="text-sm md:text-base leading-relaxed">
          Sign in or sign up to access our community-driven lost and found platform.
          You can assist fellow students in finding their lost items.
        </p>
      </div>
    </div>


    <!-- RIGHT LOGIN PANEL -->
    <div class="w-full md:w-1/2 relative flex items-center justify-center min-h-screen md:min-h-0 px-6 md:px-16 py-10 bg-white md:bg-white">
      
      <!-- Mobile Background Image (behind form) -->
      <div class="md:hidden absolute inset-0 bg-cover bg-center opacity-50"
           style="background-image: url('/utmlogooo.png');">
      </div>

      <!-- Loading Animation Overlay -->
      <Transition name="fade">
        <div v-if="isLoading" class="absolute inset-0 bg-white bg-opacity-95 flex items-center justify-center z-50">
          <div class="text-center">
            <!-- Animated Logo -->
            <div class="mb-6 animate-bounce-slow">
              <img src="/logo.png" alt="TrackNFind Logo" class="h-24 md:h-32 w-auto mx-auto" />
            </div>
            
            <!-- Loading Text -->
            <h3 class="text-xl md:text-2xl font-semibold text-gray-800 mb-2 animate-pulse">
              Signing you in...
            </h3>
            
            <!-- Loading Spinner -->
            <div class="flex justify-center space-x-2 mt-4">
              <div class="w-3 h-3 bg-blue-600 rounded-full animate-bounce" style="animation-delay: 0s"></div>
              <div class="w-3 h-3 bg-blue-600 rounded-full animate-bounce" style="animation-delay: 0.2s"></div>
              <div class="w-3 h-3 bg-blue-600 rounded-full animate-bounce" style="animation-delay: 0.4s"></div>
            </div>
          </div>
        </div>
      </Transition>

      <!-- Content (on top of background) - Centered -->
      <div class="relative z-10 w-full max-w-md">
        

        <!-- Main Login Content -->
        <h2 class="text-2xl md:text-3xl font-semibold mb-2">
          Sign in to TrackNFind
        </h2>
        <p class="text-gray-600 mb-6 text-sm">
          Enter your details below.
        </p>

        <form @submit.prevent="handleLogin">

          <!-- Registration Input -->
          <label class="text-xs font-semibold text-gray-600">REGISTRATION NO. / EMAIL</label>
          <input
            v-model="username"
            type="text"
            placeholder="eg. yourname@graduate.utm.my"
            class="w-full mb-4 mt-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white"
            required
          />

          <!-- Password Row -->
          <div class="flex justify-between items-center">
            <label class="text-xs font-semibold text-gray-600">PASSWORD</label>
            <button type="button" class="text-xs text-gray-500 hover:underline">Forgot password?</button>
          </div>

          <input
            v-model="password"
            type="password"
            placeholder="Enter your password"
            class="w-full mb-4 mt-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white"
            required
          />

          <!-- Captcha input -->
          <div class="flex items-center gap-3 mb-4">
            <button
              type="button"
              class="border border-gray-400 px-4 py-2 text-sm rounded w-32 bg-white">
              CAPTCHA
            </button>

            <input
              type="text"
              placeholder="Enter captcha shown above"
              class="flex-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white"
            />
          </div>

          <!-- Sign In Button -->
          <button
            type="submit"
            :disabled="isLoading"
            class="w-full bg-blue-700 text-white py-3 rounded-full text-center font-semibold hover:bg-blue-800 transition disabled:opacity-50 disabled:cursor-not-allowed">
            {{ isLoading ? 'SIGNING IN...' : 'SIGN IN' }}
          </button>

        </form>
          <!-- Top Right: Get Started -->
        <div class="flex items-center text-sm mb-8 justify-center mt-8 md:mt-12">
          <span>Don't Have An Account?</span>
          <button
            class="ml-3 border border-blue-600 text-blue-700 px-4 py-1 rounded-full hover:bg-blue-600 hover:text-white transition">
            GET STARTED
          </button>
        </div>

      </div>

    </div>

  </div>
  
</template>


<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const username = ref('')
const password = ref('')
const isLoading = ref(false)
const router = useRouter()

const handleLogin = async () => {
  if (username.value && password.value) {
    // Show loading animation
    isLoading.value = true
    
    // Simulate authentication delay (replace with actual API call)
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    // Navigate to dashboard
    router.push('/dashboard')
  } else {
    alert('Please enter username and password')
  }
}
</script>

<style scoped>
/* Fade transition for loading overlay */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

/* Custom bounce animation for logo */
@keyframes bounce-slow {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

.animate-bounce-slow {
  animation: bounce-slow 1.5s ease-in-out infinite;
}
</style>