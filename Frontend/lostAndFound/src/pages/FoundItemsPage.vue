<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gray-50">
      

      <!-- Main Content -->
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <!-- Instructions Card -->
        <div class="bg-blue-50 border border-blue-200 rounded-lg p-6 mb-8">
          <div class="flex gap-4">
            <div class="flex-shrink-0">
              <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
            </div>
            <div>
              <h3 class="text-lg font-semibold text-blue-900 mb-2">How to Report a Found Item</h3>
              <ul class="text-sm text-blue-800 space-y-1">
                <li>• Provide as many details as possible to help identify the owner</li>
                <li>• Upload a clear photo of the item</li>
                <li>• Our AI will automatically match with lost item reports</li>
                <li>• Keep the item safe until the owner is verified</li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Report Form -->
        <div class="bg-white rounded-lg shadow-md p-6 md:p-8">
          <form @submit.prevent="handleSubmit">
            
            <!-- Item Name -->
            <div class="mb-6">
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Item Name <span class="text-red-500">*</span>
              </label>
              <input
                v-model="form.itemName"
                type="text"
                placeholder="e.g., iPhone 13 Pro, Student ID Card, Blue Backpack"
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                required
              />
            </div>

            <!-- Category & Brand -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">
                  Category <span class="text-red-500">*</span>
                </label>
                <select
                  v-model="form.category"
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                  required
                >
                  <option value="">Select a category</option>
                  <option value="Electronics">Electronics</option>
                  <option value="Documents">Documents</option>
                  <option value="Accessories">Accessories</option>
                  <option value="Keys">Keys</option>
                  <option value="Wallets">Wallets</option>
                  <option value="Books">Books</option>
                  <option value="Clothing">Clothing</option>
                  <option value="Others">Others</option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">
                  Brand / Make
                </label>
                <input
                  v-model="form.brand"
                  type="text"
                  placeholder="e.g., Apple, Nike, Samsung"
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                />
              </div>
            </div>

            <!-- Color & Marking -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">
                  Color <span class="text-red-500">*</span>
                </label>
                <input
                  v-model="form.color"
                  type="text"
                  placeholder="e.g., Black, Navy Blue, Silver"
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                  required
                />
              </div>

              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">
                  Special Marking / Identifier
                </label>
                <input
                  v-model="form.marking"
                  type="text"
                  placeholder="e.g., Sticker, Name tag, Scratches"
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                />
              </div>
            </div>

            <!-- Description -->
            <div class="mb-6">
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Description <span class="text-red-500">*</span>
              </label>
              <textarea
                v-model="form.description"
                rows="4"
                placeholder="Describe the item in detail. Include any unique features that could help identify the owner."
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition resize-none"
                required
              ></textarea>
              <p class="mt-1 text-xs text-gray-500">{{ form.description.length }}/500 characters</p>
            </div>

            <!-- Location -->
            <div class="mb-6">
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Where did you find it? <span class="text-red-500">*</span>
              </label>
              <select
                v-model="form.location"
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                required
              >
                <option value="">Select location</option>
                <option value="Library Main Entrance">Library Main Entrance</option>
                <option value="Cafeteria Block A">Cafeteria Block A</option>
                <option value="Cafeteria Block B">Cafeteria Block B</option>
                <option value="Parking Lot A">Parking Lot A</option>
                <option value="Parking Lot B">Parking Lot B</option>
                <option value="Parking Lot C">Parking Lot C</option>
                <option value="Sports Complex - Gym">Sports Complex - Gym</option>
                <option value="Building A">Building A</option>
                <option value="Building B">Building B</option>
                <option value="Garden Area">Garden Area</option>
                <option value="Lecture Hall 1">Lecture Hall 1</option>
                <option value="Lecture Hall 2">Lecture Hall 2</option>
              </select>
            </div>

            <!-- Image Upload -->
            <div class="mb-8">
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Upload Image <span class="text-red-500">*</span>
              </label>
              <div class="mt-2">
                <div 
                  v-if="!imagePreview"
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
                  <p class="text-xs text-gray-500 mt-1">PNG, JPG, JPEG up to 5MB</p>
                </div>

                <!-- Image Preview -->
                <div v-else class="relative">
                  <img :src="imagePreview" alt="Preview" class="w-full h-64 object-cover rounded-lg">
                  <button
                    @click="removeImage"
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

            <!-- Current Item Location -->
            <div class="mb-8">
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Where is the item now? <span class="text-red-500">*</span>
              </label>
              <div class="space-y-3">
                <label class="flex items-center gap-3 p-4 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50 transition">
                  <input
                    v-model="form.itemCurrentLocation"
                    type="radio"
                    value="with_me"
                    class="w-4 h-4 text-blue-600"
                    required
                  />
                  <div>
                    <div class="font-medium text-gray-900">I have the item with me</div>
                    <div class="text-sm text-gray-500">You're keeping the item safe</div>
                  </div>
                </label>
                <label class="flex items-center gap-3 p-4 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50 transition">
                  <input
                    v-model="form.itemCurrentLocation"
                    type="radio"
                    value="lost_and_found"
                    class="w-4 h-4 text-blue-600"
                    required
                  />
                  <div>
                    <div class="font-medium text-gray-900">Submitted to Lost & Found Office</div>
                    <div class="text-sm text-gray-500">Item is at the campus lost & found center</div>
                  </div>
                </label>
              </div>
            </div>

            <!-- Submit Button -->
            <div class="flex flex-col sm:flex-row gap-4">
              <button
                type="submit"
                :disabled="isSubmitting"
                class="flex-1 bg-blue-600 text-white py-3 px-6 rounded-lg font-semibold hover:bg-blue-700 transition disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center gap-2"
              >
                <svg v-if="isSubmitting" class="animate-spin h-5 w-5" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                <span>{{ isSubmitting ? 'Submitting...' : 'Submit Report' }}</span>
              </button>
              <button
                type="button"
                @click="resetForm"
                class="sm:w-auto px-6 py-3 border border-gray-300 text-gray-700 rounded-lg font-semibold hover:bg-gray-50 transition"
              >
                Reset
              </button>
            </div>

          </form>
        </div>

        <!-- AI Matching Info -->
        <div class="mt-8 bg-gradient-to-r from-purple-50 to-blue-50 border border-purple-200 rounded-lg p-6">
          <div class="flex gap-4">
            <div class="flex-shrink-0">
              <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"/>
              </svg>
            </div>
            <div>
              <h3 class="text-lg font-semibold text-purple-900 mb-2">AI-Powered Matching</h3>
              <p class="text-sm text-purple-800">
                Once you submit, our AI will automatically compare your found item with lost item reports. 
                If a potential match is found (85%+ similarity), we'll notify you for verification!
              </p>
            </div>
          </div>
        </div>

      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import DashboardLayout from '../layouts/DashboardLayout.vue'

interface FormData {
  itemName: string
  category: string
  brand: string
  color: string
  marking: string
  description: string
  location: string
  itemCurrentLocation: string
  image: File | null
}

const form = ref<FormData>({
  itemName: '',
  category: '',
  brand: '',
  color: '',
  marking: '',
  description: '',
  location: '',
  itemCurrentLocation: '',
  image: null
})

const imagePreview = ref<string | null>(null)
const isDragging = ref(false)
const isSubmitting = ref(false)
const showRecentReports = ref(false)
const fileInput = ref<HTMLInputElement | null>(null)

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
  if (file.size > 5 * 1024 * 1024) {
    alert('File size must be less than 5MB')
    return
  }
  
  if (!file.type.startsWith('image/')) {
    alert('Please upload an image file')
    return
  }

  form.value.image = file
  const reader = new FileReader()
  reader.onload = (e) => {
    imagePreview.value = e.target?.result as string
  }
  reader.readAsDataURL(file)
}

const removeImage = () => {
  form.value.image = null
  imagePreview.value = null
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}

const handleSubmit = async () => {
  if (!form.value.image) {
    alert('Please upload an image of the found item')
    return
  }

  isSubmitting.value = true

  try {
    // TODO: Replace with actual API call
    const formData = new FormData()
    formData.append('itemName', form.value.itemName)
    formData.append('category', form.value.category)
    formData.append('brand', form.value.brand)
    formData.append('color', form.value.color)
    formData.append('marking', form.value.marking)
    formData.append('description', form.value.description)
    formData.append('location', form.value.location)
    formData.append('itemCurrentLocation', form.value.itemCurrentLocation)
    if (form.value.image) {
      formData.append('image', form.value.image)
    }

    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 2000))

    // const response = await fetch('/api/found/add', {
    //   method: 'POST',
    //   body: formData
    // })

    alert('Found item reported successfully! Our AI will check for matches.')
    resetForm()
  } catch (error) {
    console.error('Error submitting form:', error)
    alert('Failed to submit report. Please try again.')
  } finally {
    isSubmitting.value = false
  }
}

const resetForm = () => {
  form.value = {
    itemName: '',
    category: '',
    brand: '',
    color: '',
    marking: '',
    description: '',
    location: '',
    itemCurrentLocation: '',
    image: null
  }
  imagePreview.value = null
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}
</script>

<style scoped>
/* Custom scrollbar */
textarea::-webkit-scrollbar {
  width: 8px;
}

textarea::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

textarea::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

textarea::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
