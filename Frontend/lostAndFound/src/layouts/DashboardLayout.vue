<template>
  <div class="min-h-screen bg-gray-100">
    
    <!-- Header Component -->
    <AppHeader @toggle-mobile-menu="handleToggleMobileMenu" ref="headerRef" />

    <!-- Mobile Menu Component -->
    <MobileMenu :is-open="isMobileMenuOpen" @close="closeMobileMenu" />

    <!-- Main Content Slot -->
    <main class="p-6">
      <slot></slot>
    </main>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import AppHeader from '../components/AppHeader.vue'
import MobileMenu from '../components/MobileMenu.vue'

const isMobileMenuOpen = ref(false)
const headerRef = ref(null)

const handleToggleMobileMenu = (isOpen) => {
  isMobileMenuOpen.value = isOpen
}

const closeMobileMenu = () => {
  isMobileMenuOpen.value = false
  if (headerRef.value) {
    headerRef.value.isMobileMenuOpen = false
  }
}
</script>