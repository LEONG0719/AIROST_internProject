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
          Join TrackNFind Today!<br />
          Help the community
        </h1>

        <p class="text-sm md:text-base leading-relaxed">
          Create your account to report lost or found items and earn points by helping others.
          Together, we can reunite people with their belongings.
        </p>
      </div>
    </div>


    <!-- RIGHT SIGNUP PANEL -->
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
              Creating your account...
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

      <!-- Success Message Overlay -->
      <Transition name="fade">
        <div v-if="showSuccessMessage" class="absolute inset-0 bg-white bg-opacity-95 flex items-center justify-center z-50">
          <div class="text-center max-w-md px-6">
            <!-- Success Icon -->
            <div class="mb-6">
              <div class="w-24 h-24 bg-green-100 rounded-full flex items-center justify-center mx-auto">
                <svg class="w-12 h-12 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                </svg>
              </div>
            </div>
            
            <!-- Success Text -->
            <h3 class="text-2xl font-bold text-gray-800 mb-3">
              Registration Successful! 🎉
            </h3>
            <p class="text-gray-600 mb-6">
              Please check your email to verify your account. Click the verification link to activate your account.
            </p>
            
            <!-- Back to Login Button -->
            <button
              @click="goToLogin"
              class="bg-blue-600 text-white px-8 py-3 rounded-full font-semibold hover:bg-blue-700 transition">
              Go to Login
            </button>
          </div>
        </div>
      </Transition>

      <!-- Content (on top of background) - Centered -->
      <div class="relative z-10 w-full max-w-md">

        <!-- Main SignUp Content -->
        <h2 class="text-2xl md:text-3xl font-semibold mb-2">
          Create Your Account
        </h2>
        <p class="text-gray-600 mb-6 text-sm">
          Join TrackNFind and start helping the community
        </p>

        <form @submit.prevent="handleSignup">

          <!-- Full Name -->
          <label class="text-xs font-semibold text-gray-600">FULL NAME</label>
          <input
            v-model="form.fullName"
            type="text"
            placeholder="Enter your full name"
            class="w-full mb-4 mt-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
            required
          />

          <!-- ✅ MATRIC NUMBER (NEW FIELD) -->
          <label class="text-xs font-semibold text-gray-600">MATRIC NUMBER</label>
          <input
            v-model="form.studentId"
            type="text"
            placeholder="e.g., A12345678"
            maxlength="20"
            class="w-full mb-4 mt-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
            required
          />

          <!-- Email -->
          <label class="text-xs font-semibold text-gray-600">EMAIL ADDRESS (UTM Graduate only)</label>
          <input
            v-model="form.email"
            type="email"
            placeholder="yourname@graduate.utm.my"
            class="w-full mt-1 p-3 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
            :class="{ 'border-red-500': emailError && form.email }"
            required
            @blur="validateEmail"
          />
          <!-- Email Error Message -->
          <transition name="slide-down">
            <p v-if="emailError && form.email" class="text-red-500 text-xs mt-1 mb-4">
              {{ emailError }}
            </p>
          </transition>
          <div v-if="!emailError" class="mb-4"></div>

          <!-- Password -->
          <label class="text-xs font-semibold text-gray-600">PASSWORD</label>
          <div class="relative mb-4 mt-1">
            <input
              v-model="form.password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="Create a strong password"
              class="w-full p-3 pr-12 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
              required
              minlength="6"
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

          <!-- Confirm Password -->
          <label class="text-xs font-semibold text-gray-600">CONFIRM PASSWORD</label>
          <div class="relative mb-4 mt-1">
            <input
              v-model="form.confirmPassword"
              :type="showConfirmPassword ? 'text' : 'password'"
              placeholder="Re-enter your password"
              class="w-full p-3 pr-12 border rounded focus:ring-2 focus:ring-blue-600 bg-white outline-none"
              :class="{ 'border-red-500': passwordMismatch && form.confirmPassword }"
              required
            />
            <button
              type="button"
              @click="showConfirmPassword = !showConfirmPassword"
              class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 transition"
            >
              <svg v-if="!showConfirmPassword" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
              </svg>
              <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21"/>
              </svg>
            </button>
          </div>
          
          <!-- Password Mismatch Warning -->
          <transition name="slide-down">
            <p v-if="passwordMismatch && form.confirmPassword" class="text-red-500 text-xs mb-4 -mt-2">
              Passwords do not match
            </p>
          </transition>

          <!-- Password Requirements -->
          <div class="mb-6 p-3 bg-blue-50 rounded-lg">
            <p class="text-xs font-semibold text-blue-900 mb-2">Requirements:</p>
            <ul class="text-xs text-blue-700 space-y-1">
              <li class="flex items-center gap-2">
                <svg class="w-3 h-3" :class="form.studentId.length > 0 ? 'text-green-600' : 'text-gray-400'" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                </svg>
                Valid UTM matric number
              </li>
              <li class="flex items-center gap-2">
                <svg class="w-3 h-3" :class="form.email && !emailError ? 'text-green-600' : 'text-gray-400'" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                </svg>
                Valid @graduate.utm.my email
              </li>
              <li class="flex items-center gap-2">
                <svg class="w-3 h-3" :class="form.password.length >= 6 ? 'text-green-600' : 'text-gray-400'" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                </svg>
                At least 6 characters
              </li>
              <li class="flex items-center gap-2">
                <svg class="w-3 h-3" :class="form.password === form.confirmPassword && form.password ? 'text-green-600' : 'text-gray-400'" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                </svg>
                Passwords match
              </li>
            </ul>
          </div>

          <!-- Terms & Conditions -->
          <label class="flex items-start gap-3 mb-6 cursor-pointer">
            <input
              v-model="form.agreeToTerms"
              type="checkbox"
              class="mt-1 w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
              required
            />
            <span class="text-xs text-gray-600">
              I agree to the <a href="#" class="text-blue-600 hover:underline">Terms & Conditions</a> and <a href="#" class="text-blue-600 hover:underline">Privacy Policy</a>
            </span>
          </label>

          <!-- Sign Up Button -->
          <button
            type="submit"
            :disabled="isLoading || passwordMismatch || !form.agreeToTerms || !!emailError"
            class="w-full bg-blue-700 text-white py-3 rounded-full text-center font-semibold hover:bg-blue-800 transition disabled:opacity-50 disabled:cursor-not-allowed">
            {{ isLoading ? 'CREATING ACCOUNT...' : 'SIGN UP' }}
          </button>

        </form>
        
        <!-- Already Have Account -->
        <div class="flex items-center text-sm mb-8 justify-center mt-8 md:mt-12">
          <span>Already Have An Account?</span>
          <button
            @click="goToLogin"
            class="ml-3 border border-blue-600 text-blue-700 px-4 py-1 rounded-full hover:bg-blue-600 hover:text-white transition">
            SIGN IN
          </button>
        </div>

      </div>

    </div>

  </div>
  
</template>


<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from "vue-toastification"
import AuthService from '../services/auth.service'

const router = useRouter()
const toast = useToast()

const form = ref({
  fullName: '',
  studentId: '',  // ✅ Added matric number field
  email: '',
  password: '',
  confirmPassword: '',
  agreeToTerms: false
})

const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(false)
const showSuccessMessage = ref(false)
const emailError = ref('')

// Computed property to check if passwords match
const passwordMismatch = computed(() => {
  return form.value.password !== form.value.confirmPassword && form.value.confirmPassword.length > 0
})

// Validate email domain
const validateEmail = () => {
  const email = form.value.email.toLowerCase().trim()
  
  if (!email) {
    emailError.value = ''
    return
  }
  
  // Check if email ends with @graduate.utm.my
  if (!email.endsWith('@graduate.utm.my')) {
    emailError.value = 'Only @graduate.utm.my email addresses are allowed'
    return
  }
  
  // Check if there's a username before @
  const username = email.split('@')[0]
  if (!username || username.length === 0) {
    emailError.value = 'Please enter a valid email address'
    return
  }
  
  // Email is valid
  emailError.value = ''
}

const handleSignup = async () => {
  // Validate matric number
  if (!form.value.studentId || form.value.studentId.trim().length === 0) {
    toast.error('Please enter your matric number')
    return
  }
  
  // Validate email domain first
  validateEmail()
  if (emailError.value) {
    toast.error('Please use a valid @graduate.utm.my email address')
    return
  }
  
  // Validate passwords match
  if (passwordMismatch.value) {
    toast.error('Passwords do not match!')
    return
  }

  // Validate password length
  if (form.value.password.length < 6) {
    toast.error('Password must be at least 6 characters long!')
    return
  }

  // Validate terms agreement
  if (!form.value.agreeToTerms) {
    toast.error('Please agree to the Terms & Conditions!')
    return
  }

  isLoading.value = true
  
  try {
    // ✅ Real API call to backend with studentId
    await AuthService.register({
      fullName: form.value.fullName,
      studentId: form.value.studentId,  // ✅ Include studentId
      email: form.value.email,
      password: form.value.password
    })
    
    // Show success message
    toast.success('Registration successful! Please check your email.')
    showSuccessMessage.value = true
    
    // Optional: Auto redirect after 5 seconds
    setTimeout(() => {
      goToLogin()
    }, 5000)
    
  } catch (error: any) {
    isLoading.value = false
    
    // Handle error from backend
    if (error.message) {
      toast.error(error.message)
    } else {
      toast.error('Registration failed. Please try again.')
    }
    
    console.error('Registration error:', error)
  }
}

const goToLogin = () => {
  router.push('/login')
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

/* Slide down animation for error message */
.slide-down-enter-active, .slide-down-leave-active {
  transition: all 0.3s ease;
}

.slide-down-enter-from, .slide-down-leave-to {
  opacity: 0;
  transform: translateY(-10px);
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