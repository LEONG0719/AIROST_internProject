<template>
  <header class="w-full bg-white shadow-sm px-4 py-3 flex justify-between items-center sticky top-0 z-50">
    
    <!-- Logo -->
    <router-link to="/dashboard" class="flex items-center gap-2 hover:opacity-80 transition">
      <img src="/logo.png" alt="TrackNFind" class="h-8 w-auto" />
      <span class="font-semibold text-lg">TrackNFind</span>
    </router-link>

    <!-- Desktop Menu -->
    <nav class="hidden md:flex items-center gap-6 text-gray-700 text-sm font-medium">
      <router-link 
        to="/dashboard" 
        class="hover:text-blue-600 transition"
        :class="{ 'text-blue-600 font-semibold': isActive('/dashboard') }"
      >
        Home
      </router-link>
      
      <router-link 
        to="/ranking" 
        class="hover:text-blue-600 transition"
        :class="{ 'text-blue-600 font-semibold': isActive('/ranking') }"
      >
        Ranking
      </router-link>
      
      <router-link 
        to="/report" 
        class="hover:text-blue-600 transition"
        :class="{ 'text-blue-600 font-semibold': isActive('/report') }"
      >
        Report
      </router-link>
      
      <router-link 
        to="/profile" 
        class="hover:text-blue-600 transition"
        :class="{ 'text-blue-600 font-semibold': isActive('/profile') }"
      >
        Profile
      </router-link>
      
      <button 
        class="bg-blue-600 text-white px-4 py-1.5 rounded hover:bg-blue-700 transition" 
        @click="handleLogout"
      >
        Logout
      </button>
    </nav>

    <!-- Mobile Menu Button -->
    <button class="md:hidden" @click="toggleMenu">
      <svg
        class="w-7 h-7"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        viewBox="0 0 24 24"
      >
        <path 
          v-if="!isMobileMenuOpen"
          stroke-linecap="round" 
          stroke-linejoin="round"
          d="M4 6h16M4 12h16M4 18h16"
        />
        <path 
          v-else
          stroke-linecap="round" 
          stroke-linejoin="round"
          d="M6 18L18 6M6 6l12 12"
        />
      </svg>
    </button>

  </header>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useToast } from "vue-toastification"

const router = useRouter()
const route = useRoute()
const toast = useToast()

const isMobileMenuOpen = ref(false)

const emit = defineEmits(['toggle-mobile-menu'])

const toggleMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
  emit('toggle-mobile-menu', isMobileMenuOpen.value)
}

const isActive = (path) => {
  return route.path === path || route.path.startsWith(path + '/')
}

const handleLogout = () => {
  localStorage.removeItem("authToken")
  toast.success("Logout Successfully!")
  router.push("/login")
}

defineExpose({ isMobileMenuOpen })
</script>