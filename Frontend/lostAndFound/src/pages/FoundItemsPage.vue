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
import DashboardLayout from '../layouts/DashboardLayout.vue'
import InstructionCard from '../components/ui/InstructionCard.vue'
import ImageUpload from '../components/ui/ImageUpload.vue'
import AIMatchingInfo from '../components/ui/AIMatchingInfo.vue'
import LocationSelect from '../components/form/LocationSelect.vue'

interface FormData {
  itemName: string
  category: string
  otherCategory: string
  brand: string
  color: string
  marking: string
  description: string
  location: string
  otherLocation: string
  itemCurrentLocation: string
  itemStorageLocation: string
  image: File | null
}

const form = ref<FormData>({
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
})

const imagePreview = ref<string | null>(null)
const isSubmitting = ref(false)

// Add a key to force re-render components
const locationKey = ref(0)
const imageKey = ref(0)

const handlePreviewUpdate = (preview: string | null) => {
  imagePreview.value = preview
}

const handleSubmit = async () => {
  if (!form.value.image) {
    alert('Please upload an image of the found item')
    return
  }

  // Validate "Other" fields
  if (form.value.category === 'Others' && !form.value.otherCategory) {
    alert('Please specify the category')
    return
  }

  if (form.value.location === 'Other' && !form.value.otherLocation) {
    alert('Please specify the location')
    return
  }

  // Validate storage location
  if (form.value.itemCurrentLocation === 'other_place' && !form.value.itemStorageLocation) {
    alert('Please specify where the item is currently kept')
    return
  }

  isSubmitting.value = true

  try {
    const formData = new FormData()
    formData.append('itemName', form.value.itemName)
    
    const finalCategory = form.value.category === 'Others' ? form.value.otherCategory : form.value.category
    formData.append('category', finalCategory)
    
    formData.append('brand', form.value.brand)
    formData.append('color', form.value.color)
    formData.append('marking', form.value.marking)
    formData.append('description', form.value.description)
    
    const finalLocation = form.value.location === 'Other' ? form.value.otherLocation : form.value.location
    formData.append('location', finalLocation)
    
    formData.append('itemCurrentLocation', form.value.itemCurrentLocation)
    
    // Add storage location if "other_place" is selected
    if (form.value.itemCurrentLocation === 'other_place') {
      formData.append('itemStorageLocation', form.value.itemStorageLocation)
    }
    
    if (form.value.image) {
      formData.append('image', form.value.image)
    }

    await new Promise(resolve => setTimeout(resolve, 2000))

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
    otherCategory: '',
    brand: '',
    color: '',
    marking: '',
    description: '',
    location: '',
    otherLocation: '',
    itemCurrentLocation: '',
    itemStorageLocation: '',  // Add this
    image: null
  }
  imagePreview.value = null
  
  locationKey.value++
  imageKey.value++
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

/* Expand animation */
.expand-enter-active,
.expand-leave-active {
  transition: all 0.3s ease;
  max-height: 100px;
  overflow: hidden;
}

.expand-enter-from,
.expand-leave-to {
  opacity: 0;
  max-height: 0;
  margin-top: 0;
}
</style>