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
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import ImageUpload from '../components/form/ImageUpload.vue'
import LocationSelect from '../components/form/LocationSelect.vue'
import InstructionCard from '../components/ui/InstructionCard.vue'
import LostClaimService from '../services/lostclaim.service'
import AuthService from '../services/auth.service'
import type { LostClaimRequest } from '../types/api.types'

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
  dateLost: '',
  phoneNumber: '',
  preferredContact: 'email',
  image: null as File | null
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

  // Location is now OPTIONAL - removed validation
  // If "Other" is selected, still validate the otherLocation field
  if (form.value.location === 'Other' && !form.value.otherLocation.trim()) {
    toast.error('Please specify the location')
    return false
  }

  if (!form.value.dateLost) {
    toast.error('Please select when you lost the item')
    return false
  }

  if (!form.value.phoneNumber.trim()) {
    toast.error('Please enter your phone number')
    return false
  }

  return true
}

const handleSubmit = async () => {
  console.log('=== LOST ITEM SUBMIT STARTED ===')
  
  // Check if user is logged in
  if (!AuthService.isAuthenticated()) {
    console.log('ERROR: Not authenticated')
    toast.error('Please login first')
    router.push('/login')
    return
  }
  
  console.log('User ID:', AuthService.getUserId())
  console.log('Form data:', form.value)

  // Validate form
  if (!validateForm()) {
    console.log('ERROR: Validation failed')
    return
  }
  
  console.log('Validation passed!')

  isSubmitting.value = true

  try {
    // Prepare data for API
    const finalCategory = form.value.category === 'Others' 
      ? form.value.otherCategory 
      : form.value.category

    const finalLocation = form.value.location === 'Other'
      ? form.value.otherLocation
      : form.value.location

    console.log('Final category:', finalCategory)
    console.log('Final location:', finalLocation)

    // Build full description with all details
    const fullDescription = `
${form.value.description}

Lost at: ${finalLocation}
Date Lost: ${form.value.dateLost}
Phone: ${form.value.phoneNumber}
Preferred Contact: ${form.value.preferredContact}
    `.trim()

    const requestData: LostClaimRequest = {
      description: fullDescription,
      category: finalCategory,
      color: form.value.color,
      brand: form.value.brand || 'Unknown',
      specialMarking: form.value.marking || undefined,
      remarks: `Contact: ${form.value.preferredContact} - ${form.value.phoneNumber}`,
      location: finalLocation
    }

    console.log('Request data:', requestData)
    console.log('Image file:', form.value.image)

    // Submit to backend with image
    console.log('Calling LostClaimService.submitClaim...')
    const result = await LostClaimService.submitClaim(
      requestData,
      form.value.image || undefined
    )

    console.log('SUCCESS! Claim submitted:', result)
    
    toast.success('Lost item reported successfully! We will notify you if a match is found.')
    
    // Reset form
    resetForm()
    
    // Redirect to dashboard after 2 seconds
    setTimeout(() => {
      router.push('/dashboard')
    }, 2000)
    
  } catch (error: any) {
    console.error('=== ERROR SUBMITTING ===')
    console.error('Error object:', error)
    console.error('Error message:', error.message)
    console.error('Error response:', error.response)
    
    // Show specific error message if available
    if (error.message) {
      toast.error(error.message)
    } else {
      toast.error('Failed to submit report. Please try again.')
    }
  } finally {
    console.log('=== SUBMIT ENDED ===')
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