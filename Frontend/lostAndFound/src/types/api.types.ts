export interface AuthRequest {
  email: string
  password: string
  fullName?: string
}

export interface AuthResponse {
  token: string
  role: 'USER' | 'ADMIN'
  userId: number
}

export interface RegisterResponse {
  message: string
}

export interface VerifyResponse {
  message: string
}

// ============= USER TYPES =============
export interface User {
  id: number
  email: string
  fullName: string
  role: 'USER' | 'ADMIN'
  profilePicture?: string
  points: number
  createdAt: string
  enabled: boolean
}

export interface UserStats {
  itemsFound: number
  itemsLost: number
  matched: number
  points: number
}

// ============= FOUND ITEM TYPES =============
export interface FoundItem {
  id: number
  user: {
    id: number
    fullName: string
  }
  title: string
  description: string
  category: string
  color: string
  brand: string
  specialMarking?: string
  imageUrl?: string
  foundAt: string
  claimed: boolean
  location?: string
}

export interface FoundItemRequest {
  userId: number
  title: string
  description: string
  category: string
  color: string
  brand: string
  specialMarking?: string
  location?: string
}

// ============= LOST CLAIM TYPES =============
export interface LostClaim {
  id: number
  user: {
    id: number
    fullName: string
  }
  description: string
  category: string
  color: string
  brand: string
  remarks?: string
  specialMarking?: string
  imageUrl?: string
  status: 'PENDING' | 'APPROVED' | 'REJECTED' | 'MATCHED'
  createdAt: string
  location?: string
}

export interface LostClaimRequest {
  userId: number
  description: string
  category: string
  color: string
  brand: string
  remarks?: string
  specialMarking?: string
  location?: string
}

// ============= DASHBOARD TYPES =============
export interface ActivityFeed {
  type: 'FOUND' | 'LOST'
  title: string
  description: string
  timestamp: string
}

export interface DashboardStats {
  itemsFound: number
  itemsLost: number
  matched: number
  points: number
}

// ============= RANKING TYPES =============
export interface RankingEntry {
  rank: number
  userId: number
  fullName: string
  points: number
  itemsFound: number
  itemsReturned: number
  profilePicture?: string
}

export interface RankingResponse {
  rankings: RankingEntry[]
  myRank?: {
    rank: number
    points: number
  }
}

// ============= API ERROR TYPES =============
export interface ApiError {
  message: string
  status: number
  timestamp?: string
}

// ============= GENERIC RESPONSE =============
export interface ApiResponse<T = any> {
  success: boolean
  message?: string
  data?: T
  error?: string
}
