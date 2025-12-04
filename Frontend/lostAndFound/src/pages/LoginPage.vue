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
            class="w-full mb-4 mt-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
            required
          />

          <!-- Password Row -->
          <div class="flex justify-between items-center">
            <label class="text-xs font-semibold text-gray-600">PASSWORD</label>
            <button type="button" class="text-xs text-gray-500 hover:underline">Forgot password?</button>
          </div>

          <div class="relative mb-4 mt-1">
            <input
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="Enter your password"
              class="w-full p-3 pr-12 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
              required
            />
            <button
              type="button"
              @click="showPassword = !showPassword"
              class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 transition"
              :title="showPassword ? 'Hide password' : 'Show password'"
            >
              <!-- Eye Icon (Show) -->
              <svg v-if="!showPassword" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
              </svg>
              <!-- Eye Off Icon (Hide) -->
              <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21"/>
              </svg>
            </button>
          </div>

          <!-- Captcha -->
          <label class="text-xs font-semibold text-gray-600 block mb-2">CAPTCHA</label>
          <div class="flex items-center gap-3 mb-4">
            <!-- Captcha Display -->
            <div class="relative flex-shrink-0">
              <div 
                class="w-32 h-12 bg-gray-100 border border-gray-300 rounded flex items-center justify-center select-none"
                :style="{ 
                  fontFamily: 'monospace', 
                  letterSpacing: '4px',
                  transform: `rotate(${captchaRotation}deg)`,
                  background: `linear-gradient(${captchaGradient}deg, #f3f4f6, #e5e7eb)`
                }"
              >
                <span class="text-xl font-bold text-gray-700" style="text-shadow: 1px 1px 2px rgba(0,0,0,0.1)">
                  {{ captchaCode }}
                </span>
              </div>
              
              <!-- Refresh Button -->
              <button
                type="button"
                @click="generateCaptcha"
                class="absolute -top-1 -right-1 bg-blue-600 text-white p-1.5 rounded-full hover:bg-blue-700 transition"
                title="Refresh Captcha"
              >
                <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/>
                </svg>
              </button>
            </div>

            <!-- Captcha Input -->
            <input
              v-model="captchaInput"
              type="text"
              placeholder="Enter captcha"
              class="flex-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
              maxlength="6"
              required
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
          <!-- Get Started / Sign Up Link -->
        <div class="flex items-center text-sm mb-8 justify-center mt-8 md:mt-12">
          <span>Don't Have An Account?</span>
          <button
            @click="goToSignUp"
            class="ml-3 border border-blue-600 text-blue-700 px-4 py-1 rounded-full hover:bg-blue-600 hover:text-white transition">
            GET STARTED
          </button>
        </div>

      </div>

    </div>

  </div>
  
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from "vue-toastification"

const username = ref('')
const password = ref('')
const showPassword = ref(false)
const captchaCode = ref('')
const captchaInput = ref('')
const captchaRotation = ref(0)
const captchaGradient = ref(45)
const isLoading = ref(false)
const router = useRouter()
const toast = useToast() 

// Generate random captcha code
const generateCaptcha = () => {
  const characters = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789'
  let code = ''
  for (let i = 0; i < 6; i++) {
    code += characters.charAt(Math.floor(Math.random() * characters.length))
  }
  captchaCode.value = code
  captchaInput.value = ''
  captchaRotation.value = Math.floor(Math.random() * 10) - 5
  captchaGradient.value = Math.floor(Math.random() * 360)
}

onMounted(() => {
  generateCaptcha()
})

const handleLogin = async () => {
  // Validate captcha first
  if (captchaInput.value.toUpperCase() !== captchaCode.value) {
    toast.error('Incorrect CAPTCHA. Please try again.') 
    generateCaptcha()
    return
  }

  if (!username.value || !password.value) {
    toast.warning('Please enter username and password') 
    return
  }

  isLoading.value = true
  
  try {
    // Simulate authentication delay (replace with actual API call)
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    toast.success('Login successful! Redirecting...') 
    
    // Navigate to dashboard
    setTimeout(() => {
      router.push('/dashboard')
    }, 500)
  } catch (error) {
    toast.error('Login failed. Please try again.') 
    isLoading.value = false
  }
}

const goToSignUp = () => {
  router.push('/signup')
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