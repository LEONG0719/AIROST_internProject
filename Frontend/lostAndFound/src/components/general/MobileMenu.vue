<template>
  <Transition name="slide">
    <div
      v-if="isOpen"
      class="md:hidden bg-white shadow-md px-4 py-4 space-y-3 text-gray-700"
    >
      <router-link 
        to="/dashboard" 
        class="block w-full text-left hover:text-blue-600 py-2 px-2 rounded hover:bg-gray-50 transition"
        :class="{ 'text-blue-600 bg-blue-50 font-semibold': isActive('/dashboard') }"
        @click="closeMenu"
      >
        Home
      </router-link>
      <router-link 
        to="/lostItems" 
        class="block w-full text-left hover:text-blue-600 py-2 px-2 rounded hover:bg-gray-50 transition"
        :class="{ 'text-blue-600 bg-blue-50 font-semibold': isActive('/lost-items') }"
        @click="closeMenu"
      >
        Lost Items
      </router-link>
      <router-link 
        to="/foundItems" 
        class="block w-full text-left hover:text-blue-600 py-2 px-2 rounded hover:bg-gray-50 transition"
        :class="{ 'text-blue-600 bg-blue-50 font-semibold': isActive('/found-items') }"
        @click="closeMenu"
      >
        Found Items
      </router-link>
      <router-link 
        to="/profile" 
        class="block w-full text-left hover:text-blue-600 py-2 px-2 rounded hover:bg-gray-50 transition"
        :class="{ 'text-blue-600 bg-blue-50 font-semibold': isActive('/profile') }"
        @click="closeMenu"
      >
        Profile
      </router-link>

      <button 
        class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition mt-2" 
        @click="handleLogout"
      >
        Logout
      </button>
    </div>
  </Transition>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { useToast } from "vue-toastification"

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(['close'])

const router = useRouter()
const route = useRoute()
const toast = useToast()

const isActive = (path) => {
  return route.path === path
}

const closeMenu = () => {
  emit('close')
}

const handleLogout = () => {
  localStorage.removeItem("authToken")
  toast.success("Logout Successfully!")
  router.push("/login")
  closeMenu()
}
</script>

<style scoped>
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