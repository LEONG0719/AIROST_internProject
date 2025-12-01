<template>
  <div class="min-h-screen bg-gray-100">

    <!-- TOPBAR -->
    <header class="w-full bg-white shadow-sm px-4 py-3 flex justify-between items-center">

      <!-- Logo -->
      <div class="flex items-center gap-2">
        <img src="/logo.png" alt="TrackNFind" class="h-8 w-auto" />
        <span class="font-semibold text-lg">TrackNFind</span>
      </div>

      <!-- Desktop Menu -->
      <nav class="hidden md:flex items-center gap-6 text-gray-700 text-sm font-medium">
        <button class="hover:text-blue-600">Home</button>
        <button class="hover:text-blue-600">Lost Items</button>
        <button class="hover:text-blue-600">Found Items</button>
        <button class="hover:text-blue-600">Profile</button>
        <button class="bg-blue-600 text-white px-4 py-1.5 rounded hover:bg-blue-700" @click = "logout">
          Logout
        </button>
      </nav>

      <!-- Mobile Menu Button -->
      <button class="md:hidden" @click="toggleMobileMenu">
        <svg
          class="w-7 h-7"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          viewBox="0 0 24 24"
        >
          <path stroke-linecap="round" stroke-linejoin="round"
            d="M4 6h16M4 12h16M4 18h16"/>
        </svg>
      </button>

    </header>


    <!-- MOBILE DROPDOWN MENU -->
    <Transition name="slide">
      <div
        v-if="isMobileMenuOpen"
        class="md:hidden bg-white shadow-md px-4 py-4 space-y-3 text-gray-700"
      >
        <button class="w-full text-left hover:text-blue-600">Home</button>
        <button class="w-full text-left hover:text-blue-600">Lost Items</button>
        <button class="w-full text-left hover:text-blue-600">Found Items</button>
        <button class="w-full text-left hover:text-blue-600">Profile</button>

        <button class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700" @click = "logout">
          Logout
        </button>
      </div>
    </Transition>

    <!-- PAGE CONTENT -->
    <main class="p-6">
      <h1 class="text-2xl font-bold mb-4">Welcome to TrackNFind</h1>

      <p class="text-gray-600">
      
      </p>
    </main>

  </div>
</template>


<script setup>

import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from "vue-toastification";

const router = useRouter();
const toast = useToast();

const isMobileMenuOpen = ref(false)

const toggleMobileMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
}

const logout = () => {
  localStorage.removeItem("authToken");
  toast.success("Logout Successfully!");
  router.push("/login");
}
</script>


<style scoped>
/* Slide down animation */
.slide-enter-active,
.slide-leave-active {
  transition: all 0.25s ease-in-out;
}

.slide-enter-from,
.slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
