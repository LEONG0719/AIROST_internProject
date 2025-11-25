<template>
  <div class="w-full bg-white shadow p-4 flex justify-between items-center">
    <h1 class="text-lg font-semibold">Dashboard</h1>
    <button
      @click="$router.push('/login')"
      class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded"
    >
      Logout
    </button>
  </div>
</template>
