<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gray-50">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <!-- Instructions Card Component -->
        <InstructionCard
          title="How to Report a Lost Item"
          :instructions="[
            'Provide detailed description to help others identify your item',
            'Include unique features, markings, or identifiers',
            'Upload a photo if available (optional but recommended)',
            'Check back regularly for potential matches from found items'
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
                  Special Marking / Identifier <span class="text-red-500">*</span>
                </label>
                <input
                  v-model="form.marking"
                  type="text"
                  placeholder="e.g., Sticker, Name tag, Scratches"
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                  required
                />
                <p class="mt-1 text-xs text-gray-500">This helps verify ownership when item is found</p>
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
                placeholder="Describe the item in detail. Include any unique features, model number, or other identifying information that would help someone recognize your item."
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition resize-none"
                required
              ></textarea>
              <p class="mt-1 text-xs text-gray-500">{{ form.description.length }}/500 characters</p>
            </div>

            <!-- Location -->
            <LocationSelect 
              :key="locationKey"
              v-model="form.location" 
              v-model:otherLocation="form.otherLocation"
              label="Where did you lose it?"
            />
            
            <!-- Date Lost -->
            <div class="mb-6">
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                When did you lose it? <span class="text-red-500">*</span>
              </label>
              <input
                v-model="form.dateLost"
                type="date"
                :max="today"
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                required
              />
            </div>
            
            <!-- Image Upload Component (OPTIONAL) -->
            <ImageUpload
              :key="imageKey"
              v-model="form.image"
              label="Upload Image (Optional)"
              :required="false"
              :maxSizeMB="5"
              @preview-update="handlePreviewUpdate"
            />
            
            <!-- Note about optional image -->
            <div class="mb-8 bg-amber-50 border border-amber-200 rounded-lg p-4">
              <div class="flex gap-3">
                <svg class="w-5 h-5 text-amber-600 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
                <div class="text-sm text-amber-800">
                  <p class="font-semibold mb-1">About Photo Upload</p>
                  <p>While a photo is optional, uploading one significantly increases the chances of our AI matching your lost item with found items reported by others.</p>
                </div>
              </div>
            </div>

            <!-- Contact Information -->
            <div class="mb-8 bg-blue-50 border border-blue-200 rounded-lg p-4">
              <h3 class="text-sm font-semibold text-blue-900 mb-3">Contact Information</h3>
              <p class="text-xs text-blue-800 mb-3">Your email from your account will be used. You can provide additional contact method below:</p>
              
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label class="block text-xs font-semibold text-gray-700 mb-2">
                    Phone Number (Optional)
                  </label>
                  <input
                    v-model="form.phoneNumber"
                    type="tel"
                    placeholder="e.g., 012-3456789"
                    class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm"
                  />
                </div>
                
                <div>
                  <label class="block text-xs font-semibold text-gray-700 mb-2">
                    Preferred Contact Method
                  </label>
                  <select
                    v-model="form.preferredContact"
                    class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm"
                  >
                    <option value="email">Email</option>
                    <option value="phone">Phone</option>
                    <option value="both">Both</option>
                  </select>
                </div>
              </div>
            </div>

            <!-- Submit Buttons -->
            <div class="flex flex-col sm:flex-row gap-4">
              <button
                @click="handleSubmit"
                type="button"
                :disabled="isSubmitting"
                class="flex-1 bg-red-600 text-white py-3 px-6 rounded-lg font-semibold hover:bg-red-700 transition disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center gap-2"
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

        <!-- AI Matching Info -->
        <AIMatchingInfo
          title="AI-Powered Matching"
          description="Once you submit, our AI will automatically search through found item reports for potential matches. If a match is found (85%+ similarity), we'll notify you immediately via your preferred contact method!"
        />

      </div>
    </div>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useToast } from "vue-toastification"
import DashboardLayout from '../layouts/DashboardLayout.vue'
import InstructionCard from '../components/ui/InstructionCard.vue'
import ImageUpload from '../components/form/ImageUpload.vue'
import AIMatchingInfo from '../components/ui/AIMatchingInfo.vue'
import LocationSelect from '../components/form/LocationSelect.vue'

const toast = useToast()

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
  dateLost: string
  phoneNumber: string
  preferredContact: string
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
  dateLost: '',
  phoneNumber: '',
  preferredContact: 'email',
  image: null
})

const imagePreview = ref<string | null>(null)
const isSubmitting = ref(false)
const locationKey = ref(0)
const imageKey = ref(0)

// Get today's date in YYYY-MM-DD format for max date
const today = computed(() => {
  const date = new Date()
  return date.toISOString().split('T')[0]
})

const handlePreviewUpdate = (preview: string | null) => {
  imagePreview.value = preview
}

const handleSubmit = async () => {
  // Validate "Other" fields
  if (form.value.category === 'Others' && !form.value.otherCategory) {
    toast.warning('Please specify the category')
    return
  }

  if (form.value.location === 'Other' && !form.value.otherLocation) {
    toast.warning('Please specify the location')
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
    
    formData.append('dateLost', form.value.dateLost)
    formData.append('phoneNumber', form.value.phoneNumber)
    formData.append('preferredContact', form.value.preferredContact)
    
    // Image is optional
    if (form.value.image) {
      formData.append('image', form.value.image)
    }

    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 2000))

    // TODO: Replace with actual API call
    // const response = await fetch('/api/lost/add', {
    //   method: 'POST',
    //   body: formData
    // })

    toast.success('Lost item reported successfully! We will notify you if a match is found.')
    resetForm()
  } catch (error) {
    console.error('Error submitting form:', error)
    toast.error('Failed to submit report. Please try again.')
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
    dateLost: '',
    phoneNumber: '',
    preferredContact: 'email',
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