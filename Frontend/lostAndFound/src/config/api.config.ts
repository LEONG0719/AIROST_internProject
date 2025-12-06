export const API_CONFIG = {
  // Base URL for API endpoints
  BASE_URL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081',
  
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
      STATS: (userId: number) => `/api/dashboard/stats/${userId}`,
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
      SUBMIT: '/api/claims',
      CREATE: '/api/claims',
      PENDING: '/api/claims/pending-verification',
      DETAIL: (id: number) => `/api/claims/${id}`,
      APPROVE: (id: number) => `/api/claims/${id}/admin/approve`,
      REJECT: (id: number) => `/api/claims/${id}/admin/reject`,
    },
    
    // ✅ USERS ENDPOINTS (Updated)
    USERS: {
      // Maps to UserController.getProfile()
      PROFILE: '/api/user/profile',
      
      // Kept these just in case you need them later, though unused for now
      STATS: (id: number) => `/api/users/${id}/stats`,
    },
    
    // Rankings endpoints
    RANKINGS: {
      LIST: '/api/user/leaderboard',         // Updated to match UserController
      COMMUNITY: '/api/user/community-stats' // Updated to match UserController
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