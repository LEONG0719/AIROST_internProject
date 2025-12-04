<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gray-50">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <!-- Instructions Card Component -->
        <InstructionCard
          title="How to Report a Found Item"
          :instructions="[
            'Provide as many details as possible to help identify the owner',
            'Upload a clear photo of the item',
            'Our AI will automatically match with lost item reports',
            'Keep the item safe until the owner is verified'
          ]"
        />

        <!-- Report Form -->
        <div class="bg-white rounded-lg shadow-md p-6 md:p-8">
          <div>
            
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
                
                <!-- Other Category Text Box -->
                <transition name="expand">
                  <div v-if="form.category === 'Others'" class="mt-3">
                    <input
                      v-model="form.otherCategory"
                      type="text"
                      placeholder="Please specify the category..."
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm"
                    />
                  </div>
                </transition>
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

            <!-- Location - Custom Component -->
            <LocationSelect 
                :key="locationKey"
                v-model="form.location" 
                v-model:otherLocation="form.otherLocation"
            />
            
            <!-- Image Upload Component -->
                <ImageUpload
                :key="imageKey"
                v-model="form.image"
                label="Upload Image"
                :required="true"
                :maxSizeMB="5"
                @preview-update="handlePreviewUpdate"
            />

            <!-- Current Item Location -->
            <div class="mb-8">
            <label class="block text-sm font-semibold text-gray-700 mb-2">
                Where is the item now? <span class="text-red-500">*</span>
            </label>
            <div class="space-y-3">
                <label class="flex items-center gap-3 p-4 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50 transition"
                :class="{ 'border-blue-500 bg-blue-50': form.itemCurrentLocation === 'with_me' }">
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
                
                <label class="flex items-center gap-3 p-4 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50 transition"
                :class="{ 'border-blue-500 bg-blue-50': form.itemCurrentLocation === 'other_place' }">
                <input
                    v-model="form.itemCurrentLocation"
                    type="radio"
                    value="other_place"
                    class="w-4 h-4 text-blue-600"
                    required
                />
                <div class="flex-1">
                    <div class="font-medium text-gray-900">At another location</div>
                    <div class="text-sm text-gray-500">Item is kept somewhere else</div>
                </div>
                </label>
                
                <!-- Text box for specifying other location -->
                <transition name="expand">
                <div v-if="form.itemCurrentLocation === 'other_place'" class="ml-7 mt-2">
                    <input
                    v-model="form.itemStorageLocation"
                    type="text"
                    placeholder="Please specify where the item is kept (e.g., Security office, Friend's room, Cafeteria counter)..."
                    class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm"
                    required
                    />
                </div>
                </transition>
            </div>
            </div>

            <!-- Submit Buttons -->
            <div class="flex flex-col sm:flex-row gap-4">
              <button
                @click="handleSubmit"
                type="button"
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
            
          </div>
        </div>

        <!-- AI Matching Info Component -->
        <AIMatchingInfo
          title="AI-Powered Matching"
          description="Once you submit, our AI will automatically compare your found item with lost item reports. If a potential match is found (85%+ similarity), we'll notify you for verification!"
        />

      </div>
    </div>
  </DashboardLayout>
</template>


<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import ImageUpload from '../components/form/ImageUpload.vue'
import LocationSelect from '../components/form/LocationSelect.vue'
import InstructionCard from '../components/ui/InstructionCard.vue'
import FoundItemService from '../services/founditem.service'
import AuthService from '../services/auth.service'
import type { FoundItemRequest } from '../types/api.types'

const router = useRouter()
const toast = useToast()

const form = ref({
  itemName: '',
  category: '',
  otherCategory: '',
  brand: '',
  color: '',
  marking: '',
  description: '',
  location: '',
  otherLocation: '',
  itemCurrentLocation: '',
  itemStorageLocation: '',
  image: null as File | null
})

const imagePreview = ref<string | null>(null)
const isSubmitting = ref(false)
const locationKey = ref(0)
const imageKey = ref(0)

const handleImageSelected = (file: File | null) => {
  form.value.image = file
  
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      imagePreview.value = e.target?.result as string
    }
    reader.readAsDataURL(file)
  } else {
    imagePreview.value = null
  }
}

const handlePreviewUpdate = (preview: string | null) => {
  imagePreview.value = preview
}

const handleLocationSelected = (location: string) => {
  form.value.location = location
}

const validateForm = (): boolean => {
  // Check required fields
  if (!form.value.itemName.trim()) {
    toast.error('Please enter the item name')
    return false
  }

  if (!form.value.category) {
    toast.error('Please select a category')
    return false
  }

  if (form.value.category === 'Others' && !form.value.otherCategory.trim()) {
    toast.error('Please specify the category')
    return false
  }

  if (!form.value.color.trim()) {
    toast.error('Please enter the color')
    return false
  }

  if (!form.value.description.trim()) {
    toast.error('Please enter a description')
    return false
  }

  if (!form.value.location) {
    toast.error('Please select where you found the item')
    return false
  }

  if (form.value.location === 'Others' && !form.value.otherLocation.trim()) {
    toast.error('Please specify the location')
    return false
  }

  if (!form.value.itemCurrentLocation.trim()) {
    toast.error('Please specify the current location of the item')
    return false
  }

  return true
}

const handleSubmit = async () => {
  // Check if user is logged in
  if (!AuthService.isAuthenticated()) {
    toast.error('Please login first')
    router.push('/login')
    return
  }

  // Validate form
  if (!validateForm()) {
    return
  }

  isSubmitting.value = true

  try {
    // Prepare data for API
    const finalCategory = form.value.category === 'Others' 
      ? form.value.otherCategory 
      : form.value.category

    const finalLocation = form.value.location === 'Others'
      ? form.value.otherLocation
      : form.value.location

    // Build description with all details
    const fullDescription = `
${form.value.description}

Found at: ${finalLocation}
Current Location: ${form.value.itemCurrentLocation}
${form.value.itemStorageLocation ? `Storage Location: ${form.value.itemStorageLocation}` : ''}
    `.trim()

    const requestData: FoundItemRequest = {
      title: form.value.itemName,
      description: fullDescription,
      category: finalCategory,
      color: form.value.color,
      brand: form.value.brand || 'Unknown',
      specialMarking: form.value.marking || undefined
    }

    // Submit to backend with image
    const result = await FoundItemService.reportFoundItem(
      requestData,
      form.value.image || undefined
    )

    console.log('Item reported successfully:', result)
    
    toast.success('Found item reported successfully! Our AI will check for matches.')
    
    // Reset form
    resetForm()
    
    // Redirect to dashboard after 2 seconds
    setTimeout(() => {
      router.push('/dashboard')
    }, 2000)
    
  } catch (error: any) {
    console.error('Error submitting form:', error)
    
    // Show specific error message if available
    if (error.message) {
      toast.error(error.message)
    } else {
      toast.error('Failed to submit report. Please try again.')
    }
  } finally {
    isSubmitting.value = false
  }
}

const resetForm = () => {
  form.value = {
    itemName: '',
    category: '',
    otherCategory: '',
    brand: '',
    color: '',
    marking: '',
    description: '',
    location: '',
    otherLocation: '',
    itemCurrentLocation: '',
    itemStorageLocation: '',
    image: null
  }
  imagePreview.value = null
  locationKey.value++
  imageKey.value++
}
</script>