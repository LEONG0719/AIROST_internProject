<!-- components/ui/InstructionCard.vue -->
<template>
  <div class="bg-blue-50 border border-blue-200 rounded-lg p-6 mb-8">
    <div class="flex gap-4">
      <div class="flex-shrink-0">
        <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
        </svg>
      </div>
      <div>
        <h3 class="text-lg font-semibold text-blue-900 mb-2">{{ title }}</h3>
        <ul class="text-sm text-blue-800 space-y-1">
          <li v-for="(instruction, index) in instructions" :key="index">
            • {{ instruction }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  title: string
  instructions: string[]
}>()
</script>