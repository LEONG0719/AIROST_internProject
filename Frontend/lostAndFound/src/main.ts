import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Toast from "vue-toastification"
import 'vue-toastification/dist/index.css'

import './assets/main.css'

const app = createApp(App)

app.use(router)
app.use(Toast, {
  position: "top-right",
  timeout: 2000,
  closeOnClick: true,
  pauseOnFocusLoss: true,
  pauseOnHover: true,
})

app.mount('#app')
