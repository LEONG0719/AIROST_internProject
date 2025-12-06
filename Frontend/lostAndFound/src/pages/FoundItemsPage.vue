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

 <!-- Return Location & Schedule -->
            <div class="mb-8 bg-gradient-to-br from-blue-50 to-indigo-50 border-2 border-blue-200 rounded-2xl p-6">
              <h3 class="text-lg font-bold text-blue-900 mb-2 flex items-center gap-2">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/>
                </svg>
                Return Location & Pickup Schedule
              </h3>
              <p class="text-sm text-blue-800 mb-6">Let the owner know where and when they can collect their item</p>

              <!-- Return Location Options -->
              <div class="space-y-3 mb-6">
                <!-- Option 1: Already at UTM Lost & Found Office -->
                <label class="flex items-start gap-3 p-5 bg-white border-2 border-blue-200 rounded-xl cursor-pointer hover:shadow-md transition"
                  :class="{ 'border-blue-600 bg-blue-50 shadow-md': form.returnLocation === 'office' }">
                  <input
                    v-model="form.returnLocation"
                    type="radio"
                    value="office"
                    class="mt-1 w-5 h-5 text-blue-600"
                    required
                  />
                  <div class="flex-1">
                    <div class="font-bold text-gray-900 mb-1 text-lg">✅ Item Already at UTM Lost & Found Office</div>
                    <div class="text-sm text-gray-700 mb-3">
                      The item is already kept at the official Lost & Found office
                    </div>
                    <div class="bg-blue-100 rounded-lg p-3 space-y-2">
                      <div class="flex items-center gap-2 text-sm text-blue-900">
                        <svg class="w-4 h-4 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/>
                        </svg>
                        <span class="font-semibold">Faculty of Computing (FC), N28 Building</span>
                      </div>
                      <div class="flex items-center gap-2 text-sm text-blue-800">
                        <svg class="w-4 h-4 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                        </svg>
                        <span>Office Hours: Monday - Friday, 9:00 AM - 5:00 PM</span>
                      </div>
                      <div class="text-xs text-blue-700 mt-2">
                        ℹ️ Owner can pick up during office hours
                      </div>
                    </div>
                  </div>
                </label>

                <!-- Option 2: Will Return to Office Later -->
                <label class="flex items-start gap-3 p-5 bg-white border-2 border-blue-200 rounded-xl cursor-pointer hover:shadow-md transition"
                  :class="{ 'border-blue-600 bg-blue-50 shadow-md': form.returnLocation === 'personal' }">
                  <input
                    v-model="form.returnLocation"
                    type="radio"
                    value="personal"
                    class="mt-1 w-5 h-5 text-blue-600"
                    required
                  />
                  <div class="flex-1">
                    <div class="font-bold text-gray-900 mb-1 text-lg">🤝 I'll Bring It to Office Later</div>
                    <div class="text-sm text-gray-700">
                      You have the item now and will bring it to the office on a specific date
                    </div>
                  </div>
                </label>
              </div>

              <!-- Date & Time Selection - ONLY for "personal" (will bring later) -->
              <div v-if="form.returnLocation === 'personal'" class="bg-white rounded-xl p-5 border-2 border-blue-200 shadow-sm">
                <label class="block text-base font-bold text-gray-900 mb-4 flex items-center gap-2">
                  <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                  </svg>
                  When will you bring the item to the office? <span class="text-red-500">*</span>
                </label>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label class="block text-sm font-semibold text-gray-700 mb-2">📅 Date</label>
                    <input
                      v-model="form.returnDate"
                      type="date"
                      :min="today"
                      class="w-full px-4 py-3 border-2 border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition text-base"
                      required
                    />
                  </div>
                  <div>
                    <label class="block text-sm font-semibold text-gray-700 mb-2">⏰ Time</label>
                    <input
                      v-model="form.returnTime"
                      type="time"
                      class="w-full px-4 py-3 border-2 border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition text-base"
                      required
                    />
                  </div>
                </div>
                <p class="text-xs text-gray-600 mt-3 flex items-center gap-1">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  After you bring it to the office, the owner will be notified
                </p>
              </div>

              <!-- Map Section - Show for BOTH options -->
              <div v-if="form.returnLocation" class="mt-6">
                <div class="bg-white rounded-xl p-5 border-2 border-blue-200 shadow-sm">
                  <h4 class="text-base font-bold text-gray-900 mb-3 flex items-center gap-2">
                    <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-.553-.894L15 4m0 13V4m0 0L9 7"/>
                    </svg>
                    UTM Lost & Found Office Location - FC N28
                  </h4>
                  <div class="rounded-lg overflow-hidden border-2 border-gray-200 shadow-sm">
                    <!-- Embedded Google Maps for FC UTM -->
                    <iframe
                      src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.394997842935!2d103.63669!3d1.55787!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31da7713bbf8e80b%3A0x4e1a0a19dd15c04d!2sFaculty%20of%20Computing%2C%20Universiti%20Teknologi%20Malaysia!5e0!3m2!1sen!2smy!4v1234567890"
                      width="100%"
                      height="300"
                      style="border:0;"
                      allowfullscreen
                      loading="lazy"
                      referrerpolicy="no-referrer-when-downgrade"
                    ></iframe>
                  </div>
                  <div class="mt-3 p-3 bg-blue-50 rounded-lg">
                    <p class="text-sm text-blue-900 font-semibold mb-1">📍 Full Address:</p>
                    <p class="text-sm text-blue-800">
                      Faculty of Computing, N28 Building<br>
                      Universiti Teknologi Malaysia<br>
                      81310 Johor Bahru, Johor, Malaysia
                    </p>
                  </div>
                </div>
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
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from "vue-toastification"  
import DashboardLayout from '../layouts/DashboardLayout.vue'
import InstructionCard from '../components/ui/InstructionCard.vue'
import ImageUpload from '../components/form/ImageUpload.vue'
import AIMatchingInfo from '../components/ui/AIMatchingInfo.vue'
import LocationSelect from '../components/form/LocationSelect.vue'
import AuthService from '../services/auth.service'
import FoundItemService from '../services/founditem.service'

const router = useRouter()
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
  // ❌ REMOVED: itemCurrentLocation and itemStorageLocation
  // ✅ NEW: Return location fields
  returnLocation: string     // 'office' or 'personal'
  returnDate: string         // 'YYYY-MM-DD'
  returnTime: string         // 'HH:MM'
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
  // ✅ NEW fields
  returnLocation: '',
  returnDate: '',
  returnTime: '',
  image: null
})

const imagePreview = ref<string | null>(null)
const isSubmitting = ref(false)
const locationKey = ref(0)
const imageKey = ref(0)
const fileInputRef = ref<any>(null)

// ✅ NEW: Computed property for today's date (min date for picker)
const today = computed(() => {
  const date = new Date()
  return date.toISOString().split('T')[0]
})

const handlePreviewUpdate = (preview: string | null) => {
  imagePreview.value = preview
}

const handleSubmit = async () => {
  console.log('=== SUBMIT STARTED ===')
  console.log('Form data:', form.value)

  // Check if user is logged in
  if (!AuthService.isAuthenticated()) {
    toast.error('Please login first')
    router.push('/login')
    return
  }

  // Validate required fields
  if (!form.value.itemName) {
    toast.error('Please enter the item name')
    return
  }

  if (!form.value.category) {
    toast.error('Please select a category')
    return
  }

  if (form.value.category === 'Others' && !form.value.otherCategory) {
    toast.error('Please specify the category')
    return
  }

  if (!form.value.color) {
    toast.error('Please enter the color')
    return
  }

  if (!form.value.description) {
    toast.error('Please enter a description')
    return
  }

  if (!form.value.location) {
    toast.error('Please select where you found the item')
    return
  }

  if (form.value.location === 'Others' && !form.value.otherLocation) {
    toast.error('Please specify where you found the item')
    return
  }

  // ✅ NEW: Validate return location
  if (!form.value.returnLocation) {
    toast.error('Please select where the item is (At office or Will bring later)')
    return
  }

  // ✅ NEW: Validate return date/time ONLY if "personal" (will bring later)
  if (form.value.returnLocation === 'personal') {
    if (!form.value.returnDate) {
      toast.error('Please select when you will bring the item to office')
      return
    }
    
    if (!form.value.returnTime) {
      toast.error('Please select what time you will bring the item')
      return
    }
  }

  if (!form.value.image) {
    toast.warning('Please upload an image of the found item')
    return
  }

  isSubmitting.value = true

  try {
    const userId = AuthService.getUserId()
    if (!userId) {
      toast.error('Please login first')
      router.push('/login')
      return
    }

    // Build final category and location
    const finalCategory = form.value.category === 'Others'
      ? form.value.otherCategory
      : form.value.category

    const finalLocation = form.value.location === 'Others'
      ? form.value.otherLocation
      : form.value.location

    // ✅ NEW: Build return info based on location
    let returnInfo = ''
    let returnDateTime = ''
    
    if (form.value.returnLocation === 'office') {
      // Item already at office
      returnInfo = '✅ Item is at: UTM Lost & Found Office (FC N28)\n📍 Owner can pick up during office hours: Mon-Fri, 9AM-5PM'
    } else if (form.value.returnLocation === 'personal') {
      // Will bring to office later
      returnDateTime = `${form.value.returnDate} at ${form.value.returnTime}`
      returnInfo = `🤝 Item will be brought to office on: ${returnDateTime}\n📍 Office: UTM Lost & Found (FC N28)`
    }

    // ✅ NEW: Build full description with all info
    const fullDescription = `
${form.value.description}

📍 Found at: ${finalLocation}
${returnInfo}
    `.trim()

    console.log('Full description:', fullDescription)

    // Build request object
    const requestData = {
      userId: userId,
      title: form.value.itemName,
      description: fullDescription,
      category: finalCategory,
      color: form.value.color,
      brand: form.value.brand || '',
      specialMarking: form.value.marking || '',
      location: finalLocation,
      // ✅ NEW: Add return fields
      returnLocation: form.value.returnLocation,
      returnDate: form.value.returnDate,
      returnTime: form.value.returnTime,
    }

    console.log('Request data:', requestData)

    // Get image file
    const imageFile = form.value.image || undefined

    console.log('Calling API...')
    const response = await FoundItemService.reportFoundItem(requestData, imageFile)
    console.log('API response:', response)

    // Success
    toast.success('Found item reported successfully! Our AI will start matching.')

    // Reset form
    resetForm()

    // Redirect to dashboard after 2 seconds
    setTimeout(() => {
      router.push('/dashboard')
    }, 2000)

  } catch (error: any) {
    console.error('Submit error:', error)
    toast.error(error.response?.data?.message || 'Failed to submit report. Please try again.')
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
    // ✅ NEW fields
    returnLocation: '',
    returnDate: '',
    returnTime: '',
    image: null
  }
  imagePreview.value = null
  locationKey.value++
  imageKey.value++
  
  // Clear file input preview if component ref exists
  if (fileInputRef.value) {
    fileInputRef.value.clearPreview()
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

/* Expand animation */
.expand-enter-active,
.expand-leave-active {
  transition: all 0.3s ease;
  max-height: 200px;
  overflow: hidden;
}

.expand-enter-from,
.expand-leave-to {
  opacity: 0;
  max-height: 0;
  margin-top: 0;
}
</style>