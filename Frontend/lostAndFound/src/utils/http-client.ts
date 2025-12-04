import axios, { AxiosError } from 'axios'
import type { AxiosInstance, InternalAxiosRequestConfig } from 'axios'
import { API_CONFIG } from '../config/api.config'
import type { ApiError } from '../types/api.types'

// Create Axios instance
const httpClient: AxiosInstance = axios.create({
  baseURL: API_CONFIG.BASE_URL,
  timeout: API_CONFIG.TIMEOUT,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request Interceptor - Add auth token to every request
httpClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // Get token from localStorage
    const token = localStorage.getItem(API_CONFIG.TOKEN_KEY)
    
    // Add token to headers if it exists
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    return config
  },
  (error: AxiosError) => {
    return Promise.reject(error)
  }
)

// Response Interceptor - Handle errors globally
httpClient.interceptors.response.use(
  (response) => {
    // Return response data directly
    return response
  },
  (error: AxiosError<ApiError>) => {
    // Handle different error scenarios
    if (error.response) {
      // Server responded with error status
      const status = error.response.status
      const message = error.response.data?.message || 'An error occurred'
      
      switch (status) {
        case 401:
          // Unauthorized - Clear token and redirect to login
          localStorage.removeItem(API_CONFIG.TOKEN_KEY)
          localStorage.removeItem(API_CONFIG.USER_ID_KEY)
          localStorage.removeItem(API_CONFIG.USER_ROLE_KEY)
          
          // Only redirect if not already on login page
          if (window.location.pathname !== '/login') {
            window.location.href = '/login'
          }
          break
          
        case 403:
          // Forbidden - User doesn't have permission
          console.error('Access forbidden:', message)
          break
          
        case 404:
          // Not found
          console.error('Resource not found:', message)
          break
          
        case 500:
          // Server error
          console.error('Server error:', message)
          break
          
        default:
          console.error('Error:', message)
      }
      
      return Promise.reject({
        message,
        status,
        timestamp: new Date().toISOString()
      })
    } else if (error.request) {
      // Request made but no response received
      return Promise.reject({
        message: 'No response from server. Please check your connection.',
        status: 0
      })
    } else {
      // Something else happened
      return Promise.reject({
        message: error.message || 'An unexpected error occurred',
        status: 0
      })
    }
  }
)

export default httpClient
