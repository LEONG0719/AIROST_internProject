<template>
  <DashboardLayout>
    <div class="min-h-screen bg-gray-50 p-6">
      
      <!-- Header -->
      <div class="mb-6">
        <h1 class="text-3xl font-bold text-gray-900 flex items-center gap-3">
          <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"/>
          </svg>
          Admin Dashboard
        </h1>
        <p class="text-gray-600 mt-1">Manage claims, verifications, and system overview</p>
      </div>

      <!-- Stats Overview -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-6">
        <!-- Total Pending -->
        <div class="bg-white rounded-2xl shadow-lg p-6 border-l-4 border-orange-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600 font-semibold mb-1">Pending Verifications</p>
              <p class="text-3xl font-bold text-gray-900">{{ pendingCount }}</p>
            </div>
            <div class="w-14 h-14 bg-orange-100 rounded-full flex items-center justify-center">
              <svg class="w-7 h-7 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
            </div>
          </div>
        </div>

        <!-- Total Claims -->
        <div class="bg-white rounded-2xl shadow-lg p-6 border-l-4 border-blue-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600 font-semibold mb-1">Total Claims</p>
              <p class="text-3xl font-bold text-gray-900">{{ totalClaims }}</p>
            </div>
            <div class="w-14 h-14 bg-blue-100 rounded-full flex items-center justify-center">
              <svg class="w-7 h-7 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
              </svg>
            </div>
          </div>
        </div>

        <!-- Approved -->
        <div class="bg-white rounded-2xl shadow-lg p-6 border-l-4 border-green-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600 font-semibold mb-1">Approved</p>
              <p class="text-3xl font-bold text-gray-900">{{ approvedCount }}</p>
            </div>
            <div class="w-14 h-14 bg-green-100 rounded-full flex items-center justify-center">
              <svg class="w-7 h-7 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
            </div>
          </div>
        </div>

        <!-- Rejected -->
        <div class="bg-white rounded-2xl shadow-lg p-6 border-l-4 border-red-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600 font-semibold mb-1">Rejected</p>
              <p class="text-3xl font-bold text-gray-900">{{ rejectedCount }}</p>
            </div>
            <div class="w-14 h-14 bg-red-100 rounded-full flex items-center justify-center">
              <svg class="w-7 h-7 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
              </svg>
            </div>
          </div>
        </div>
      </div>

      <!-- Tabs -->
      <div class="bg-white rounded-2xl shadow-lg overflow-hidden">
        <!-- Tab Headers -->
        <div class="border-b border-gray-200">
          <div class="flex gap-4 px-6">
            <button
              v-for="tab in tabs"
              :key="tab.id"
              @click="activeTab = tab.id"
              class="py-4 px-4 font-semibold border-b-2 transition"
              :class="activeTab === tab.id 
                ? 'border-blue-600 text-blue-600' 
                : 'border-transparent text-gray-600 hover:text-gray-900'">
              {{ tab.label }}
              <span v-if="tab.count > 0" class="ml-2 px-2 py-1 text-xs rounded-full"
                :class="tab.id === 'pending' ? 'bg-orange-100 text-orange-700' : 'bg-gray-100 text-gray-700'">
                {{ tab.count }}
              </span>
            </button>
          </div>
        </div>

        <!-- Tab Content -->
        <div class="p-6">
          
          <!-- Loading State -->
          <div v-if="isLoading" class="text-center py-12">
            <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
            <p class="text-gray-600 mt-4">Loading claims...</p>
          </div>

          <!-- Empty State -->
          <div v-else-if="currentTabClaims.length === 0" class="text-center py-12">
            <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"/>
            </svg>
            <p class="text-gray-600">No claims found</p>
          </div>

          <!-- Claims List -->
          <div v-else class="space-y-4">
            <div
              v-for="claim in currentTabClaims"
              :key="claim.id"
              class="border-2 border-gray-200 rounded-2xl p-6 hover:border-blue-300 transition">
              
              <div class="flex flex-col md:flex-row gap-6">
                <!-- Image -->
                <div class="flex-shrink-0">
                  <div class="w-32 h-32 rounded-xl overflow-hidden bg-gray-100">
                    <img 
                      v-if="claim.imageUrl" 
                      :src="claim.imageUrl" 
                      :alt="claim.category"
                      class="w-full h-full object-cover"
                    />
                    <div v-else class="w-full h-full flex items-center justify-center text-gray-400">
                      <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                      </svg>
                    </div>
                  </div>
                </div>

                <!-- Details -->
                <div class="flex-1">
                  <div class="flex items-start justify-between mb-3">
                    <div>
                      <h3 class="text-xl font-bold text-gray-900">{{ claim.category }} - {{ claim.brand }}</h3>
                      <p class="text-sm text-gray-600">Claim ID: #{{ claim.id }}</p>
                    </div>
                    <span class="px-3 py-1 rounded-full text-sm font-semibold"
                      :class="getStatusClass(claim.status)">
                      {{ claim.status }}
                    </span>
                  </div>

                  <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                    <div>
                      <p class="text-xs text-gray-600 font-semibold mb-1">User</p>
                      <p class="text-sm text-gray-900">{{ claim.user?.fullName || 'Unknown' }}</p>
                      <p class="text-xs text-gray-500">{{ claim.user?.email }}</p>
                    </div>

                    <div>
                      <p class="text-xs text-gray-600 font-semibold mb-1">Details</p>
                      <p class="text-sm text-gray-900">Color: {{ claim.color }}</p>
                      <p class="text-sm text-gray-900">Location: {{ claim.location }}</p>
                    </div>

                    <div class="md:col-span-2">
                      <p class="text-xs text-gray-600 font-semibold mb-1">Description</p>
                      <p class="text-sm text-gray-700">{{ claim.description }}</p>
                    </div>

                    <!-- Student ID & Proof (for pending verifications) -->
                    <div v-if="claim.status === 'NEEDS_MANUAL_CHECK' && claim.studentIdNumber" class="md:col-span-2">
                      <p class="text-xs text-gray-600 font-semibold mb-2">Verification Documents</p>
                      <div class="flex gap-4">
                        <div v-if="claim.idCardUrl" class="flex items-center gap-2">
                          <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                          </svg>
                          <a :href="claim.idCardUrl" target="_blank" class="text-blue-600 hover:underline text-sm">
                            View Student Card
                          </a>
                        </div>
                        <div v-if="claim.receiptUrl" class="flex items-center gap-2">
                          <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                          </svg>
                          <a :href="claim.receiptUrl" target="_blank" class="text-green-600 hover:underline text-sm">
                            View Receipt
                          </a>
                        </div>
                        <div class="text-sm text-gray-700">
                          Student ID: <span class="font-semibold">{{ claim.studentIdNumber }}</span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Actions (Only for Pending) -->
                  <div v-if="claim.status === 'NEEDS_MANUAL_CHECK'" class="flex gap-3">
                    <button
                      @click="openApproveModal(claim)"
                      class="flex-1 bg-green-600 text-white px-4 py-2 rounded-xl font-semibold hover:bg-green-700 transition flex items-center justify-center gap-2">
                      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                      </svg>
                      Approve
                    </button>
                    <button
                      @click="openRejectModal(claim)"
                      class="flex-1 bg-red-600 text-white px-4 py-2 rounded-xl font-semibold hover:bg-red-700 transition flex items-center justify-center gap-2">
                      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                      </svg>
                      Reject
                    </button>
                  </div>

                  <!-- Admin Note (for approved/rejected) -->
                  <div v-if="claim.adminNote && (claim.status === 'APPROVED' || claim.status === 'REJECTED')" 
                    class="mt-4 p-3 bg-gray-50 rounded-lg">
                    <p class="text-xs text-gray-600 font-semibold mb-1">Admin Note:</p>
                    <p class="text-sm text-gray-700">{{ claim.adminNote }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Approve Modal -->
    <Transition name="modal">
      <div v-if="showApproveModal" class="fixed inset-0 z-50 overflow-y-auto">
        <div class="fixed inset-0 bg-black/50 backdrop-blur-sm" @click="closeApproveModal"></div>
        
        <div class="flex min-h-screen items-center justify-center p-4">
          <div class="relative bg-white rounded-2xl shadow-2xl max-w-md w-full p-6 z-10">
            <h3 class="text-xl font-bold text-gray-900 mb-4">Approve Claim</h3>
            
            <p class="text-gray-600 mb-4">
              Are you sure you want to approve this claim for <strong>{{ selectedClaim?.category }}</strong>?
            </p>

            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">Admin Note (Optional)</label>
              <textarea
                v-model="approveNote"
                rows="3"
                class="w-full px-4 py-2 border-2 border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-green-500 outline-none"
                placeholder="Add a note..."></textarea>
            </div>

            <div class="flex gap-3">
              <button
                @click="closeApproveModal"
                class="flex-1 bg-gray-200 text-gray-700 px-4 py-2 rounded-xl font-semibold hover:bg-gray-300 transition">
                Cancel
              </button>
              <button
                @click="handleApprove"
                :disabled="isProcessing"
                class="flex-1 bg-green-600 text-white px-4 py-2 rounded-xl font-semibold hover:bg-green-700 transition disabled:opacity-50">
                {{ isProcessing ? 'Approving...' : 'Approve' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </Transition>

    <!-- Reject Modal -->
    <Transition name="modal">
      <div v-if="showRejectModal" class="fixed inset-0 z-50 overflow-y-auto">
        <div class="fixed inset-0 bg-black/50 backdrop-blur-sm" @click="closeRejectModal"></div>
        
        <div class="flex min-h-screen items-center justify-center p-4">
          <div class="relative bg-white rounded-2xl shadow-2xl max-w-md w-full p-6 z-10">
            <h3 class="text-xl font-bold text-gray-900 mb-4">Reject Claim</h3>
            
            <p class="text-gray-600 mb-4">
              Are you sure you want to reject this claim for <strong>{{ selectedClaim?.category }}</strong>?
            </p>

            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-700 mb-2">Reason for Rejection *</label>
              <textarea
                v-model="rejectNote"
                rows="3"
                required
                class="w-full px-4 py-2 border-2 border-gray-300 rounded-xl focus:ring-2 focus:ring-red-500 focus:border-red-500 outline-none"
                placeholder="Explain why this claim is being rejected..."></textarea>
            </div>

            <div class="flex gap-3">
              <button
                @click="closeRejectModal"
                class="flex-1 bg-gray-200 text-gray-700 px-4 py-2 rounded-xl font-semibold hover:bg-gray-300 transition">
                Cancel
              </button>
              <button
                @click="handleReject"
                :disabled="isProcessing || !rejectNote"
                class="flex-1 bg-red-600 text-white px-4 py-2 rounded-xl font-semibold hover:bg-red-700 transition disabled:opacity-50">
                {{ isProcessing ? 'Rejecting...' : 'Reject' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </DashboardLayout>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import DashboardLayout from '../layouts/DashboardLayout.vue'
import AdminService from '../services/admin.service'
import AuthService from '../services/auth.service'

const router = useRouter()
const toast = useToast()

const isLoading = ref(false)
const isProcessing = ref(false)
const activeTab = ref('pending')

const allClaims = ref<any[]>([])
const pendingClaims = ref<any[]>([])

const showApproveModal = ref(false)
const showRejectModal = ref(false)
const selectedClaim = ref<any>(null)
const approveNote = ref('')
const rejectNote = ref('')

// Tabs
const tabs = computed(() => [
  { id: 'pending', label: 'Pending Verifications', count: pendingCount.value },
  { id: 'all', label: 'All Claims', count: totalClaims.value },
])

// Stats
const pendingCount = computed(() => 
  allClaims.value.filter(c => c.status === 'NEEDS_MANUAL_CHECK').length
)
const totalClaims = computed(() => allClaims.value.length)
const approvedCount = computed(() => 
  allClaims.value.filter(c => c.status === 'APPROVED').length
)
const rejectedCount = computed(() => 
  allClaims.value.filter(c => c.status === 'REJECTED').length
)

// Current tab claims
const currentTabClaims = computed(() => {
  if (activeTab.value === 'pending') {
    return allClaims.value.filter(c => c.status === 'NEEDS_MANUAL_CHECK')
  }
  return allClaims.value
})

// Get status class
const getStatusClass = (status: string) => {
  const classes = {
    'PENDING': 'bg-gray-100 text-gray-700',
    'NEEDS_MANUAL_CHECK': 'bg-orange-100 text-orange-700',
    'APPROVED': 'bg-green-100 text-green-700',
    'CLAIMED': 'bg-blue-100 text-blue-700',
    'REJECTED': 'bg-red-100 text-red-700'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-700'
}

// Load claims
const loadClaims = async () => {
  isLoading.value = true
  
  try {
    console.log('Loading all claims...')
    const claims = await AdminService.getAllClaims()
    console.log('Claims loaded:', claims)
    
    allClaims.value = claims
    
  } catch (error: any) {
    console.error('Error loading claims:', error)
    toast.error('Failed to load claims')
  } finally {
    isLoading.value = false
  }
}

// Modal handlers
const openApproveModal = (claim: any) => {
  selectedClaim.value = claim
  approveNote.value = ''
  showApproveModal.value = true
}

const closeApproveModal = () => {
  showApproveModal.value = false
  selectedClaim.value = null
  approveNote.value = ''
}

const openRejectModal = (claim: any) => {
  selectedClaim.value = claim
  rejectNote.value = ''
  showRejectModal.value = true
}

const closeRejectModal = () => {
  showRejectModal.value = false
  selectedClaim.value = null
  rejectNote.value = ''
}

// Approve claim
const handleApprove = async () => {
  if (!selectedClaim.value) return
  
  isProcessing.value = true
  
  try {
    const adminId = AuthService.getUserId()
    if (!adminId) {
      toast.error('Admin not authenticated')
      return
    }
    
    console.log('Approving claim:', selectedClaim.value.id)
    
    await AdminService.approveClaim(
      selectedClaim.value.id,
      adminId,
      approveNote.value || 'Approved'
    )
    
    toast.success('✅ Claim approved successfully!')
    closeApproveModal()
    await loadClaims() // Reload
    
  } catch (error: any) {
    console.error('Error approving claim:', error)
    toast.error('Failed to approve claim')
  } finally {
    isProcessing.value = false
  }
}

// Reject claim
const handleReject = async () => {
  if (!selectedClaim.value || !rejectNote.value) {
    toast.error('Please provide a reason for rejection')
    return
  }
  
  isProcessing.value = true
  
  try {
    const adminId = AuthService.getUserId()
    if (!adminId) {
      toast.error('Admin not authenticated')
      return
    }
    
    console.log('Rejecting claim:', selectedClaim.value.id)
    
    await AdminService.rejectClaim(
      selectedClaim.value.id,
      adminId,
      rejectNote.value
    )
    
    toast.success('❌ Claim rejected')
    closeRejectModal()
    await loadClaims() // Reload
    
  } catch (error: any) {
    console.error('Error rejecting claim:', error)
    toast.error('Failed to reject claim')
  } finally {
    isProcessing.value = false
  }
}

// Check admin access
onMounted(async () => {
  if (!AuthService.isAuthenticated()) {
    toast.error('Please login first')
    router.push('/login')
    return
  }
  
  if (!AuthService.isAdmin()) {
    toast.error('Admin access required')
    router.push('/dashboard')
    return
  }
  
  await loadClaims()
})
</script>

<style scoped>
/* Modal transition */
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}
</style>