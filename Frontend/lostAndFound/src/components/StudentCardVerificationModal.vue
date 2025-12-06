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
        <div class="relative bg-white rounded-3xl shadow-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto z-10">
          
          <!-- Close Button -->
          <button
            @click="closeModal"
            class="absolute top-4 right-4 z-10 w-10 h-10 bg-gray-100 hover:bg-gray-200 rounded-full flex items-center justify-center transition"
          >
            <svg class="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
            </svg>
          </button>

          <!-- Header -->
          <div class="bg-gradient-to-r from-blue-600 to-indigo-600 p-8 text-white rounded-t-3xl">
            <div class="flex items-center gap-4">
              <div class="w-16 h-16 bg-white bg-opacity-20 rounded-full flex items-center justify-center">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                </svg>
              </div>
              <div>
                <h2 class="text-2xl font-bold">Verify Your Identity 🎓</h2>
                <p class="text-blue-100 mt-1">Upload your student card to claim this item</p>
              </div>
            </div>
          </div>

          <div class="p-8">
            
            <!-- Item Info Preview -->
            <div class="bg-gradient-to-br from-green-50 to-emerald-50 border-2 border-green-200 rounded-2xl p-6 mb-6">
              <div class="flex items-center gap-2 mb-3">
                <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
                <span class="font-bold text-green-900">Item Matched!</span>
              </div>
              <div class="flex items-center gap-4">
                <div v-if="item.imageUrl" class="w-20 h-20 rounded-xl overflow-hidden border-2 border-white shadow-md flex-shrink-0">
                  <img :src="item.imageUrl" :alt="item.title" class="w-full h-full object-cover"/>
                </div>
                <div class="flex-shrink-0 w-20 h-20 bg-white rounded-xl flex items-center justify-center border-2 border-green-200" v-else>
                  <svg class="w-10 h-10 text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                  </svg>
                </div>
                <div class="flex-1">
                  <div class="font-bold text-gray-900 text-lg">{{ item.title }}</div>
                  <div class="text-sm text-gray-600">{{ item.category }} • {{ item.color }}</div>
                </div>
              </div>
            </div>

            <!-- Verification Process Info -->
            <div class="mb-6">
              <h3 class="text-lg font-bold text-gray-900 mb-4">📋 Verification Process</h3>
              <div class="space-y-3">
                <div class="flex items-start gap-3">
                  <div class="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5">
                    <span class="text-blue-700 font-bold text-sm">1</span>
                  </div>
                  <div class="flex-1">
                    <div class="font-semibold text-gray-900">Upload Student Card (PDF)</div>
                    <div class="text-sm text-gray-600">Upload your UTM student ID card as PDF for verification</div>
                  </div>
                </div>

                <div class="flex items-start gap-3">
                  <div class="w-8 h-8 bg-orange-100 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5">
                    <span class="text-orange-700 font-bold text-sm">2</span>
                  </div>
                  <div class="flex-1">
                    <div class="font-semibold text-gray-900">Admin Review</div>
                    <div class="text-sm text-gray-600">Our admin will verify your identity (usually within 24 hours)</div>
                  </div>
                </div>

                <div class="flex items-start gap-3">
                  <div class="w-8 h-8 bg-green-100 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5">
                    <span class="text-green-700 font-bold text-sm">3</span>
                  </div>
                  <div class="flex-1">
                    <div class="font-semibold text-gray-900">Get Notification</div>
                    <div class="text-sm text-gray-600">Once approved, you can proceed to claim your item!</div>
                  </div>
                </div>
              </div>
            </div>

            <!-- File Upload Section - Student Card -->
            <div class="border-3 border-dashed border-gray-300 rounded-2xl p-8 mb-4 hover:border-blue-400 transition"
              :class="{ 'border-blue-500 bg-blue-50': isDragging || selectedFile }"
              @dragover.prevent="isDragging = true"
              @dragleave.prevent="isDragging = false"
              @drop.prevent="handleFileDrop">
              
              <input
                ref="fileInput"
                type="file"
                accept=".pdf"
                @change="handleFileSelect"
                class="hidden"
              />

              <div v-if="!selectedFile" class="text-center">
                <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
                  <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"/>
                  </svg>
                </div>
                <h4 class="text-lg font-semibold text-gray-900 mb-2">Upload Student Card</h4>
                <p class="text-sm text-gray-600 mb-4">Drag and drop your PDF file here, or click to browse</p>
                <button
                  @click="fileInput?.click()"
                  type="button"
                  class="bg-blue-600 text-white px-6 py-3 rounded-xl font-semibold hover:bg-blue-700 transition"
                >
                  Choose PDF File
                </button>
                <p class="text-xs text-gray-500 mt-3">Maximum file size: 5MB • PDF format only</p>
              </div>

              <div v-else class="text-center">
                <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                  <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                  </svg>
                </div>
                <h4 class="text-lg font-semibold text-gray-900 mb-2">Student Card Selected</h4>
                <div class="bg-white rounded-xl p-4 border-2 border-green-200 inline-flex items-center gap-3 mb-4">
                  <svg class="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 21h10a2 2 0 002-2V9.414a1 1 0 00-.293-.707l-5.414-5.414A1 1 0 0012.586 3H7a2 2 0 00-2 2v14a2 2 0 002 2z"/>
                  </svg>
                  <div class="text-left">
                    <div class="font-semibold text-gray-900">{{ selectedFile.name }}</div>
                    <div class="text-sm text-gray-600">{{ formatFileSize(selectedFile.size) }}</div>
                  </div>
                </div>
                <div>
                  <button
                    @click="removeFile"
                    type="button"
                    class="text-sm text-red-600 hover:text-red-700 font-semibold"
                  >
                    Remove File
                  </button>
                </div>
              </div>
            </div>

            <!-- Optional Receipt Upload Section -->
            <div class="mb-6">
              <div class="flex items-center justify-between mb-3">
                <h4 class="text-md font-bold text-gray-900">📄 Receipt (Optional)</h4>
                <span class="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded-full">Optional</span>
              </div>
              <p class="text-sm text-gray-600 mb-4">
                If you have a purchase receipt or any proof of ownership, you can upload it here to speed up verification.
              </p>
              
              <div class="border-2 border-dashed border-gray-200 rounded-xl p-6 hover:border-gray-300 transition"
                :class="{ 'border-gray-400 bg-gray-50': isReceiptDragging || selectedReceipt }"
                @dragover.prevent="isReceiptDragging = true"
                @dragleave.prevent="isReceiptDragging = false"
                @drop.prevent="handleReceiptDrop">
                
                <input
                  ref="receiptInput"
                  type="file"
                  accept=".pdf,image/*"
                  @change="handleReceiptSelect"
                  class="hidden"
                />

                <div v-if="!selectedReceipt" class="text-center">
                  <div class="w-12 h-12 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-3">
                    <svg class="w-6 h-6 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                    </svg>
                  </div>
                  <p class="text-sm text-gray-600 mb-3">Upload receipt or proof of ownership</p>
                  <button
                    @click="receiptInput?.click()"
                    type="button"
                    class="bg-gray-600 text-white px-4 py-2 rounded-lg font-medium hover:bg-gray-700 transition text-sm"
                  >
                    Choose File
                  </button>
                  <p class="text-xs text-gray-500 mt-2">PDF or Image • Max 5MB</p>
                </div>

                <div v-else class="text-center">
                  <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-3">
                    <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                    </svg>
                  </div>
                  <div class="bg-white rounded-lg p-3 border border-gray-200 inline-flex items-center gap-2 mb-3">
                    <svg class="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                    </svg>
                    <div class="text-left">
                      <div class="font-medium text-gray-900 text-sm">{{ selectedReceipt.name }}</div>
                      <div class="text-xs text-gray-500">{{ formatFileSize(selectedReceipt.size) }}</div>
                    </div>
                  </div>
                  <div>
                    <button
                      @click="removeReceipt"
                      type="button"
                      class="text-xs text-red-600 hover:text-red-700 font-semibold"
                    >
                      Remove Receipt
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Important Notes -->
            <div class="bg-yellow-50 border-2 border-yellow-200 rounded-2xl p-4 mb-6">
              <div class="flex items-start gap-3">
                <svg class="w-6 h-6 text-yellow-600 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                </svg>
                <div class="flex-1">
                  <div class="font-semibold text-yellow-900 mb-1">Important Notes</div>
                  <ul class="text-sm text-yellow-800 space-y-1">
                    <li>• Your student card must be clear and readable</li>
                    <li>• File must be in PDF format (max 5MB)</li>
                    <li>• Admin review typically takes 24-48 hours</li>
                    <li>• You'll be notified via email once reviewed</li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- Action Buttons -->
            <div class="flex flex-col sm:flex-row gap-4">
              <button
                @click="handleSubmit"
                :disabled="!selectedFile || isUploading"
                class="flex-1 bg-gradient-to-r from-blue-600 to-indigo-600 text-white py-4 px-6 rounded-2xl font-bold text-lg hover:from-blue-700 hover:to-indigo-700 transition disabled:opacity-50 disabled:cursor-not-allowed shadow-lg flex items-center justify-center gap-3"
              >
                <svg v-if="!isUploading" class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"/>
                </svg>
                <svg v-else class="w-6 h-6 animate-spin" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                <span>{{ isUploading ? 'Uploading...' : 'Submit for Verification' }}</span>
              </button>

              <button
                @click="closeModal"
                type="button"
                class="sm:w-auto px-6 py-4 border-2 border-gray-300 text-gray-700 rounded-2xl font-semibold hover:bg-gray-50 transition"
              >
                Cancel
              </button>
            </div>

          </div>

        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useToast } from 'vue-toastification'

interface VerificationModalProps {
  show: boolean
  item: {
    id?: number
    claimId?: number
    title: string
    category?: string
    color?: string
    description?: string
    imageUrl?: string
  }
}

const props = defineProps<VerificationModalProps>()
const emit = defineEmits(['close', 'submitted'])
const toast = useToast()

const fileInput = ref<HTMLInputElement | null>(null)
const receiptInput = ref<HTMLInputElement | null>(null)
const selectedFile = ref<File | null>(null)
const selectedReceipt = ref<File | null>(null)
const isDragging = ref(false)
const isReceiptDragging = ref(false)
const isUploading = ref(false)

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  
  if (file) {
    validateAndSetFile(file)
  }
}

const handleFileDrop = (event: DragEvent) => {
  isDragging.value = false
  const file = event.dataTransfer?.files[0]
  
  if (file) {
    validateAndSetFile(file)
  }
}

const validateAndSetFile = (file: File) => {
  // Check file type
  if (file.type !== 'application/pdf') {
    toast.error('Please upload a PDF file')
    return
  }
  
  // Check file size (5MB)
  const maxSize = 5 * 1024 * 1024 // 5MB in bytes
  if (file.size > maxSize) {
    toast.error('File size must be less than 5MB')
    return
  }
  
  selectedFile.value = file
  toast.success('File selected successfully')
}

const removeFile = () => {
  selectedFile.value = null
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}

// ✅ Receipt handlers
const handleReceiptSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  
  if (file) {
    validateAndSetReceipt(file)
  }
}

const handleReceiptDrop = (event: DragEvent) => {
  isReceiptDragging.value = false
  const file = event.dataTransfer?.files[0]
  
  if (file) {
    validateAndSetReceipt(file)
  }
}

const validateAndSetReceipt = (file: File) => {
  // Check file type (PDF or image)
  const isPDF = file.type === 'application/pdf'
  const isImage = file.type.startsWith('image/')
  
  if (!isPDF && !isImage) {
    toast.error('Please upload a PDF or image file')
    return
  }
  
  // Check file size (5MB)
  const maxSize = 5 * 1024 * 1024
  if (file.size > maxSize) {
    toast.error('Receipt file size must be less than 5MB')
    return
  }
  
  selectedReceipt.value = file
  toast.success('Receipt uploaded successfully')
}

const removeReceipt = () => {
  selectedReceipt.value = null
  if (receiptInput.value) {
    receiptInput.value.value = ''
  }
}

const formatFileSize = (bytes: number): string => {
  if (bytes < 1024) return bytes + ' B'
  if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB'
  return (bytes / (1024 * 1024)).toFixed(1) + ' MB'
}

const closeModal = () => {
  removeFile()
  removeReceipt()
  emit('close')
}

const handleSubmit = async () => {
  if (!selectedFile.value) {
    toast.error('Please select your student card first')
    return
  }
  
  isUploading.value = true
  
  try {
    // TODO: Call API to upload student card and optional receipt
    // const formData = new FormData()
    // formData.append('studentCard', selectedFile.value)
    // formData.append('claimId', props.item.claimId)
    // if (selectedReceipt.value) {
    //   formData.append('receipt', selectedReceipt.value)
    // }
    // await VerificationService.submitVerification(formData)
    
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    const hasReceipt = selectedReceipt.value ? ' and receipt' : ''
    toast.success(`✅ Verification${hasReceipt} submitted! Please wait for admin approval.`)
    
    // Emit submitted event
    emit('submitted', {
      ...props.item,
      status: 'PENDING_VERIFICATION',
      hasReceipt: !!selectedReceipt.value
    })
    
    // Close modal after short delay
    setTimeout(() => {
      closeModal()
    }, 1000)
    
  } catch (error: any) {
    console.error('Error submitting verification:', error)
    toast.error('Failed to submit verification. Please try again.')
  } finally {
    isUploading.value = false
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