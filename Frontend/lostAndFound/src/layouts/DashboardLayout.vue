<template>
  <div class="flex">
    <Sidebar />
    <div class="flex-1 flex flex-col min-h-screen">
      <Topbar />
      <main class="p-6 bg-gray-50 flex-1">
        <slot />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import Sidebar from '../components/Sidebar.vue'
import Topbar from '../components/Topbar.vue'
</script>
