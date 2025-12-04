export const API_CONFIG = {
  // Base URL for API endpoints
  BASE_URL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  
  // API endpoints
  ENDPOINTS: {
    // Auth endpoints
    AUTH: {
      REGISTER: '/api/auth/register',
      LOGIN: '/api/auth/login',
      VERIFY: '/api/auth/verify',
    },
    
    // Dashboard endpoints
    DASHBOARD: {
      RECENT_ACTIVITY: '/api/dashboard/recent-activity',
      STATS: (userId: number) => `/api/dashboard/stats/${userId}`, // To be implemented by backend
    },
    
    // Found Items endpoints
    FOUND_ITEMS: {
      LIST: '/api/found-items',
      CREATE: '/api/found-items',
      CLAIMED: '/api/found-items/browse/claimed',
      DETAIL: (id: number) => `/api/found-items/${id}`,
    },
    
    // Lost Claims endpoints
    CLAIMS: {
      SUBMIT: '/api/claims',         // Same as CREATE - for submitting claims
      CREATE: '/api/claims',         // Keep for backwards compatibility
      PENDING: '/api/claims/pending-verification',
      DETAIL: (id: number) => `/api/claims/${id}`,  // Get claim by ID
      APPROVE: (id: number) => `/api/claims/${id}/admin/approve`,
      REJECT: (id: number) => `/api/claims/${id}/admin/reject`,
    },
    
    // User endpoints (to be implemented by backend)
    USERS: {
      PROFILE: (id: number) => `/api/users/${id}`,
      UPDATE: (id: number) => `/api/users/${id}`,
      STATS: (id: number) => `/api/users/${id}/stats`,
    },
    
    // Rankings endpoints (to be implemented by backend)
    RANKINGS: {
      LIST: '/api/rankings',
      MY_RANK: (userId: number) => `/api/rankings/me/${userId}`,
    },
  },
  
  // Request timeout (30 seconds)
  TIMEOUT: 30000,
  
  // Token storage key
  TOKEN_KEY: 'authToken',
  USER_ID_KEY: 'userId',
  USER_ROLE_KEY: 'userRole',
}

export default API_CONFIG
