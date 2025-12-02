<!-- components/form/ImageUpload.vue -->
<template>
  <div class="mb-8">
    <label class="block text-sm font-semibold text-gray-700 mb-2">
      {{ label }} <span v-if="required" class="text-red-500">*</span>
    </label>
    <div class="mt-2">
      <div 
        v-if="!preview"
        @click="$refs.fileInput.click()"
        @dragover.prevent="isDragging = true"
        @dragleave.prevent="isDragging = false"
        @drop.prevent="handleFileDrop"
        :class="[
          'border-2 border-dashed rounded-lg p-8 text-center cursor-pointer transition',
          isDragging ? 'border-blue-500 bg-blue-50' : 'border-gray-300 hover:border-blue-400'
        ]"
      >
        <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"/>
        </svg>
        <p class="mt-2 text-sm text-gray-600">
          <span class="font-semibold text-blue-600">Click to upload</span> or drag and drop
        </p>
        <p class="text-xs text-gray-500 mt-1">PNG, JPG, JPEG up to {{ maxSizeMB }}MB</p>
      </div>

      <!-- Image Preview -->
      <div v-else class="relative">
        <img :src="preview" alt="Preview" class="w-full h-64 object-cover rounded-lg">
        <button
          @click="handleRemove"
          type="button"
          class="absolute top-2 right-2 bg-red-500 text-white p-2 rounded-full hover:bg-red-600 transition"
        >
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
          </svg>
        </button>
      </div>

      <input
        ref="fileInput"
        type="file"
        accept="image/*"
        @change="handleFileSelect"
        class="hidden"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps<{
  label?: string
  required?: boolean
  maxSizeMB?: number
  modelValue: File | null
}>()

const emit = defineEmits<{
  'update:modelValue': [value: File | null]
  'preview-update': [preview: string | null]
}>()

const fileInput = ref<HTMLInputElement | null>(null)
const isDragging = ref(false)
const preview = ref<string | null>(null)

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  if (file) {
    processFile(file)
  }
}

const handleFileDrop = (event: DragEvent) => {
  isDragging.value = false
  const file = event.dataTransfer?.files[0]
  if (file) {
    processFile(file)
  }
}

const processFile = (file: File) => {
  const maxSize = (props.maxSizeMB || 5) * 1024 * 1024
  
  if (file.size > maxSize) {
    alert(`File size must be less than ${props.maxSizeMB || 5}MB`)
    return
  }
  
  if (!file.type.startsWith('image/')) {
    alert('Please upload an image file')
    return
  }

  emit('update:modelValue', file)
  
  const reader = new FileReader()
  reader.onload = (e) => {
    preview.value = e.target?.result as string
    emit('preview-update', preview.value)
  }
  reader.readAsDataURL(file)
}

const handleRemove = () => {
  emit('update:modelValue', null)
  preview.value = null
  emit('preview-update', null)
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}
</script>