<template>
  <div class="mb-6">
    <label class="block text-sm font-semibold text-gray-700 mb-2">
      Where did you lose / found it? 
    </label>
    
    <div class="relative" ref="dropdownRef">
      <!-- Search Input -->
      <div
        @click="toggleDropdown"
        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus-within:ring-2 focus-within:ring-blue-500 focus-within:border-transparent outline-none transition cursor-pointer bg-white"
      >
        <div class="flex items-center justify-between">
          <div class="flex-1">
            <input
              v-if="!selectedLocation || isOpen"
              v-model="searchQuery"
              @input="isOpen = true"
              @focus="isOpen = true"
              type="text"
              placeholder="Search or select location..."
              class="w-full outline-none bg-transparent text-sm"
            />
            <span 
              v-else
              class="text-sm text-gray-900"
            >
              {{ selectedLocation }}
            </span>
          </div>
          <svg 
            class="w-5 h-5 text-gray-400 flex-shrink-0 ml-2 transition-transform"
            :class="{ 'rotate-180': isOpen }"
            fill="none" 
            stroke="currentColor" 
            viewBox="0 0 24 24"
          >
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
          </svg>
        </div>
      </div>

      <!-- Dropdown List -->
      <transition name="dropdown">
        <div
          v-if="isOpen"
          class="absolute z-50 w-full mt-2 bg-white border border-gray-300 rounded-lg shadow-lg max-h-64 overflow-y-auto"
        >
          <!-- No Results -->
          <div v-if="filteredLocations.length === 0" class="px-4 py-3 text-sm text-gray-500 text-center">
            No locations found
          </div>

          <!-- Location Groups -->
          <div v-for="group in filteredLocations" :key="group.category">
            <div class="px-3 py-2 text-xs font-semibold text-gray-500 bg-gray-50 sticky top-0">
              {{ group.emoji }} {{ group.category }}
            </div>
            <button
              v-for="location in group.items"
              :key="location.value"
              @click="selectLocation(location)"
              type="button"
              class="w-full text-left px-4 py-2.5 text-sm hover:bg-blue-50 transition flex items-center justify-between group"
              :class="{ 'bg-blue-50 text-blue-700 font-medium': selectedLocation === location.label }"
            >
              <span class="truncate">{{ location.label }}</span>
              <svg 
                v-if="selectedLocation === location.label"
                class="w-4 h-4 text-blue-600 flex-shrink-0 ml-2" 
                fill="none" 
                stroke="currentColor" 
                viewBox="0 0 24 24"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
              </svg>
            </button>
          </div>
        </div>
      </transition>
    </div>
  </div>
  
  <!-- Other Location Text Box (appears when "Other" is selected) -->
  <transition name="expand">
    <div v-if="selectedLocation === 'Other Location'" class="mt-3">
      <input
        :value="otherLocationText"
        @input="$emit('update:otherLocation', ($event.target as HTMLInputElement).value)"
        type="text"
        placeholder="Please specify the location..."
        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm"
      />
    </div>
  </transition>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'

interface Location {
  value: string
  label: string
}

interface LocationGroup {
  category: string
  emoji: string
  items: Location[]
}

const props = defineProps<{
  modelValue: string
  otherLocationText?: string
}>()

const emit = defineEmits<{
  'update:modelValue': [value: string]
  'update:otherLocation': [value: string]
}>()

const isOpen = ref(false)
const searchQuery = ref('')
const selectedLocation = ref(props.modelValue)
const dropdownRef = ref<HTMLElement | null>(null)

const locations: LocationGroup[] = [
  {
    category: 'Computing',
    emoji: '🖥️',
    items: [
      { value: 'FC N28', label: 'FC N28 Main' },
      { value: 'FC N28A', label: 'FC N28A Bitara' },
      { value: 'FC N29', label: 'FC N29 AI' },
      { value: 'FC N24', label: 'FC N24 IS' },
      { value: 'FC Atrium', label: 'FC Atrium' }
    ]
  },
  {
    category: 'Engineering',
    emoji: '⚙️',
    items: [
      { value: 'FE Block P', label: 'FE Block P' },
      { value: 'FE P07', label: 'FE P07 Electrical' },
      { value: 'FE Block B', label: 'FE Block B Civil' },
      { value: 'FE Block T', label: 'FE Block T Chemical' }
    ]
  },
  {
    category: 'Science',
    emoji: '🔬',
    items: [
      { value: 'FS Block C', label: 'FS Block C' },
      { value: 'FS Block D', label: 'FS Block D' },
      { value: 'FS Block E', label: 'FS Block E' }
    ]
  },
  {
    category: 'FABU',
    emoji: '🏢',
    items: [
      { value: 'FABU N3', label: 'FABU Block N3' },
      { value: 'FABU N4', label: 'FABU Block N4' },
      { value: 'FABU Canteen', label: 'FABU Canteen' }
    ]
  },
  {
    category: 'AHIBS',
    emoji: '💼',
    items: [
      { value: 'AHIBS S01', label: 'AHIBS S01' },
      { value: 'AHIBS S02', label: 'AHIBS S02' }
    ]
  },
  {
    category: 'Library & Study',
    emoji: '📚',
    items: [
      { value: 'Library PSZ', label: 'Library PSZ' },
      { value: 'L50', label: 'L50 Lecture Hall' },
      { value: 'L40', label: 'L40 Lecture Hall' },
      { value: 'P19', label: 'P19 Exam Hall' }
    ]
  },
  {
    category: 'Food Courts',
    emoji: '🍽️',
    items: [
      { value: 'K11', label: 'K11 Food Court' },
      { value: 'K9 Cafe', label: 'K9 Cafe' },
      { value: 'Arked Meranti', label: 'Arked Meranti' },
      { value: 'Arked Cengal', label: 'Arked Cengal' }
    ]
  },
  {
    category: 'Sports',
    emoji: '⚽',
    items: [
      { value: 'K10 Sports', label: 'K10 Sports Complex' },
      { value: 'UTM Stadium', label: 'UTM Stadium' },
      { value: 'UTM Lake', label: 'UTM Lake' }
    ]
  },
  {
    category: 'Religious',
    emoji: '🕌',
    items: [
      { value: 'Masjid', label: 'Masjid Sultan Ismail' },
      { value: 'DSI', label: 'DSI Dewan' }
    ]
  },
  {
    category: 'Colleges',
    emoji: '🏠',
    items: [
      { value: 'K1', label: 'Kolej 1' },
      { value: 'K2', label: 'Kolej 2' },
      { value: 'K3', label: 'Kolej 3' },
      { value: 'K4', label: 'Kolej 4' },
      { value: 'KTDI', label: 'KTDI' },
      { value: 'KTR', label: 'Kolej Tun Razak' },
      { value: 'KP', label: 'Kolej Perdana' },
      { value: 'K9', label: 'Kolej 9' },
      { value: 'K10', label: 'Kolej 10' }
    ]
  },
  {
    category: 'Services',
    emoji: '🏥',
    items: [
      { value: 'Health Centre', label: 'Health Centre' },
      { value: 'Scholars Inn', label: "Scholar's Inn" }
    ]
  },
  {
    category: 'Other',
    emoji: '📍',
    items: [
      { value: 'Other', label: 'Other Location' }
    ]
  }
]

const filteredLocations = computed(() => {
  if (!searchQuery.value) {
    return locations
  }

  const query = searchQuery.value.toLowerCase()
  return locations
    .map(group => ({
      ...group,
      items: group.items.filter(item => 
        item.label.toLowerCase().includes(query) ||
        item.value.toLowerCase().includes(query)
      )
    }))
    .filter(group => group.items.length > 0)
})

const toggleDropdown = () => {
  isOpen.value = !isOpen.value
}

const selectLocation = (location: Location) => {
  selectedLocation.value = location.label
  emit('update:modelValue', location.value)
  
  // Don't close dropdown if "Other" is selected
  if (location.value !== 'Other') {
    isOpen.value = false
  }
  searchQuery.value = ''
}

const handleClickOutside = (event: MouseEvent) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target as Node)) {
    isOpen.value = false
    searchQuery.value = ''
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.2s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* Custom scrollbar */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #cbd5e0;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a0aec0;
}

/* Expand animation for Other text box */
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