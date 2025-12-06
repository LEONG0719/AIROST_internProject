<template>
  <!-- Modal Overlay -->
  <Transition name="modal">
    <div v-if="show" class="fixed inset-0 z-50 overflow-y-auto">
      <!-- Backdrop with blur -->
      <div 
        class="fixed inset-0 bg-black/20 backdrop-blur-sm transition-opacity"
        @click="closeModal"
      ></div>
      
      <!-- Modal Content -->
      <div class="flex min-h-screen items-center justify-center p-4">
        <div class="relative bg-white rounded-3xl shadow-2xl max-w-4xl w-full max-h-[90vh] overflow-y-auto z-10">
          
          <!-- Close Button -->
          <button
            @click="closeModal"
            class="absolute top-4 right-4 z-10 w-10 h-10 bg-gray-100 hover:bg-gray-200 rounded-full flex items-center justify-center transition"
          >
            <svg class="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
            </svg>
          </button>

          <!-- Success Banner -->
          <div class="bg-gradient-to-r from-green-500 to-emerald-600 p-6 text-white rounded-t-3xl">
            <div class="flex items-center gap-3">
              <div class="w-14 h-14 bg-white bg-opacity-20 rounded-full flex items-center justify-center">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
              </div>
              <div>
                <h2 class="text-2xl font-bold">Great News! 🎉</h2>
                <p class="text-green-100">Your lost item has been found!</p>
              </div>
            </div>
          </div>

          <div class="p-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              
              <!-- Left: Item Details -->
              <div class="space-y-4">
                <h3 class="text-xl font-bold text-gray-900 mb-4">Item Details</h3>
                
                <!-- Item Image -->
                <div class="aspect-square rounded-2xl overflow-hidden border-2 border-gray-200 bg-gray-100">
                  <img 
                    v-if="item.imageUrl" 
                    :src="item.imageUrl" 
                    :alt="item.title"
                    class="w-full h-full object-cover"
                  />
                  <div v-else class="w-full h-full flex items-center justify-center text-gray-400">
                    <svg class="w-20 h-20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                    </svg>
                  </div>
                </div>

                <!-- Item Info Cards -->
                <div class="space-y-3">
                  <div class="flex items-center gap-3 p-3 bg-blue-50 rounded-xl">
                    <div class="w-10 h-10 bg-blue-500 rounded-lg flex items-center justify-center">
                      <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"/>
                      </svg>
                    </div>
                    <div class="flex-1">
                      <div class="text-xs text-blue-700 font-medium">Item Name</div>
                      <div class="font-semibold text-gray-900">{{ item.title }}</div>
                    </div>
                  </div>

                  <div class="flex items-center gap-3 p-3 bg-purple-50 rounded-xl">
                    <div class="w-10 h-10 bg-purple-500 rounded-lg flex items-center justify-center">
                      <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17h.01"/>
                      </svg>
                    </div>
                    <div class="flex-1">
                      <div class="text-xs text-purple-700 font-medium">Category & Color</div>
                      <div class="font-semibold text-gray-900">{{ item.category }} - {{ item.color }}</div>
                    </div>
                  </div>

                  <div v-if="item.description" class="p-3 bg-gray-50 rounded-xl">
                    <div class="text-xs text-gray-600 font-medium mb-1">Description</div>
                    <div class="text-sm text-gray-800">{{ item.description }}</div>
                  </div>
                </div>
              </div>

              <!-- Right: Pickup Details -->
              <div class="space-y-4">
                <h3 class="text-xl font-bold text-gray-900 mb-4">Pickup Information</h3>
                
                <!-- Status Card -->
                <div class="bg-gradient-to-br from-green-50 to-emerald-50 border-2 border-green-200 rounded-2xl p-4">
                  <div class="flex items-center gap-2 mb-3">
                    <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                    </svg>
                    <span class="font-bold text-green-900">Item Status</span>
                  </div>
                  <div class="text-sm text-green-800">
                    {{ itemStatus }}
                  </div>
                </div>

                <!-- Location Card -->
                <div class="bg-blue-50 border-2 border-blue-200 rounded-2xl p-4">
                  <div class="flex items-center gap-2 mb-3">
                    <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/>
                    </svg>
                    <span class="font-bold text-blue-900">Pickup Location</span>
                  </div>
                  <div class="text-sm font-semibold text-blue-900 mb-1">
                    UTM Lost & Found Office
                  </div>
                  <div class="text-xs text-blue-700">
                    Faculty of Computing, N28 Building<br>
                    Office Hours: Mon-Fri, 9:00 AM - 5:00 PM
                  </div>
                </div>

                <!-- Date/Time Card (if applicable) -->
                <div v-if="item.returnDate" class="bg-orange-50 border-2 border-orange-200 rounded-2xl p-4">
                  <div class="flex items-center gap-2 mb-3">
                    <svg class="w-5 h-5 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                    </svg>
                    <span class="font-bold text-orange-900">Available From</span>
                  </div>
                  <div class="text-sm font-semibold text-orange-900">
                    {{ formatDate(item.returnDate) }} at {{ item.returnTime }}
                  </div>
                  <div class="text-xs text-orange-700 mt-1">
                    Item will be at the office after this time
                  </div>
                </div>

                <!-- Map -->
                <div class="rounded-2xl overflow-hidden border-2 border-gray-200">
                  <iframe
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.394997842935!2d103.63669!3d1.55787!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31da7713bbf8e80b%3A0x4e1a0a19dd15c04d!2sFaculty%20of%20Computing%2C%20Universiti%20Teknologi%20Malaysia!5e0!3m2!1sen!2smy!4v1234567890"
                    width="100%"
                    height="200"
                    style="border:0;"
                    allowfullscreen
                    loading="lazy"
                    referrerpolicy="no-referrer-when-downgrade"
                  ></iframe>
                </div>

                <!-- Claim Button -->
                <button
                  @click="handleClaim"
                  :disabled="isClaiming"
                  class="w-full bg-gradient-to-r from-green-500 to-emerald-600 text-white py-4 px-6 rounded-2xl font-bold text-lg hover:from-green-600 hover:to-emerald-700 transition disabled:opacity-50 disabled:cursor-not-allowed shadow-lg flex items-center justify-center gap-3"
                >
                  <svg v-if="!isClaiming" class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                  <svg v-else class="w-6 h-6 animate-spin" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  <span>{{ isClaiming ? 'Processing...' : 'Confirm & Claim Item' }}</span>
                </button>

                <p class="text-xs text-center text-gray-500">
                  By claiming, you confirm this is your item and will pick it up at the specified location.
                </p>
              </div>

            </div>
          </div>

        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useToast } from 'vue-toastification'

interface ItemClaimProps {
  show: boolean
  item: {
    id?: number
    claimId?: number
    title: string
    category?: string
    color?: string
    description?: string
    imageUrl?: string
    returnLocation?: string
    returnDate?: string
    returnTime?: string
  }
}

const props = defineProps<ItemClaimProps>()
const emit = defineEmits(['close', 'claimed'])
const toast = useToast()

const isClaiming = ref(false)

const itemStatus = computed(() => {
  if (props.item.returnLocation === 'office') {
    return '✅ Item is ready at Lost & Found Office. You can pick it up during office hours!'
  } else if (props.item.returnDate) {
    return `⏳ Item will be brought to office on ${formatDate(props.item.returnDate)}. Pick up after that!`
  } else {
    return '✅ Item is ready for pickup at Lost & Found Office!'
  }
})

const formatDate = (dateString: string) => {
  try {
    const date = new Date(dateString)
    return date.toLocaleDateString('en-US', { 
      weekday: 'long', 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric' 
    })
  } catch {
    return dateString
  }
}

const closeModal = () => {
  emit('close')
}

const handleClaim = async () => {
  isClaiming.value = true
  
  try {
    // TODO: Call API to mark item as claimed
    // await ClaimService.claimItem(props.item.claimId || props.item.id)
    
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    toast.success('🎉 Item claimed successfully!')
    
    // Emit claimed event
    emit('claimed', props.item)
    
    // Close modal after short delay
    setTimeout(() => {
      closeModal()
    }, 1000)
    
  } catch (error: any) {
    console.error('Error claiming item:', error)
    toast.error('Failed to claim item. Please try again.')
  } finally {
    isClaiming.value = false
  }
}
</script>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-active .relative,
.modal-leave-active .relative {
  transition: transform 0.3s ease;
}

.modal-enter-from .relative,
.modal-leave-to .relative {
  transform: scale(0.95);
}
</style>