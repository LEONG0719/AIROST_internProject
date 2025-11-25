<template>
  <div class="flex justify-center items-center min-h-screen bg-gradient-to-r from-indigo-400 to-purple-500">
    <div class="bg-white p-8 rounded-2xl shadow-md w-80">
      <h1 class="text-2xl font-bold text-center mb-6">Login</h1>
      <input v-model="username" placeholder="Username" class="w-full p-2 mb-3 border rounded" />
      <input v-model="password" placeholder="Password" type="password" class="w-full p-2 mb-4 border rounded" />
      <button @click="login" class="w-full bg-indigo-500 text-white py-2 rounded hover:bg-indigo-600">
        Login
      </button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

const router = useRouter()
const user = useUserStore()

const username = ref('')
const password = ref('')

const login = () => {
  if (username.value && password.value) {
    user.login()
    router.push('/app/home')
  } else {
    alert('Please enter username and password')
  }
}
</script>
