import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'
import type { 
  AuthRequest, 
  AuthResponse, 
  RegisterResponse, 
  VerifyResponse 
} from '../types/api.types'

export class AuthService {
  /**
   * Register a new user
   */
  static async register(data: AuthRequest): Promise<RegisterResponse> {
    const response = await httpClient.post<RegisterResponse>(
      API_CONFIG.ENDPOINTS.AUTH.REGISTER,
      data
    )
    return response.data
  }

  /**
   * Verify user email with code
   */
  static async verifyEmail(code: string): Promise<VerifyResponse> {
    const response = await httpClient.get<VerifyResponse>(
      API_CONFIG.ENDPOINTS.AUTH.VERIFY,
      { params: { code } }
    )
    return response.data
  }

  /**
   * Login user
   */
  static async login(email: string, password: string): Promise<AuthResponse> {
    const response = await httpClient.post<AuthResponse>(
      API_CONFIG.ENDPOINTS.AUTH.LOGIN,
      { email, password }
    )
    
    const authData = response.data
    
    // Store auth data in localStorage
    if (authData.token) {
      this.setAuthData(authData)
    }
    
    return authData
  }

  /**
   * Logout user
   */
  static logout(): void {
    // Clear all auth data from localStorage
    localStorage.removeItem(API_CONFIG.TOKEN_KEY)
    localStorage.removeItem(API_CONFIG.USER_ID_KEY)
    localStorage.removeItem(API_CONFIG.USER_ROLE_KEY)
  }

  /**
   * Store authentication data
   */
  static setAuthData(authData: AuthResponse): void {
    localStorage.setItem(API_CONFIG.TOKEN_KEY, authData.token)
    localStorage.setItem(API_CONFIG.USER_ID_KEY, authData.userId.toString())
    localStorage.setItem(API_CONFIG.USER_ROLE_KEY, authData.role)
  }

  /**
   * Get stored auth token
   */
  static getToken(): string | null {
    return localStorage.getItem(API_CONFIG.TOKEN_KEY)
  }

  /**
   * Get stored user ID
   */
  static getUserId(): number | null {
    const userId = localStorage.getItem(API_CONFIG.USER_ID_KEY)
    return userId ? parseInt(userId, 10) : null
  }

  /**
   * Get stored user role
   */
  static getUserRole(): 'USER' | 'ADMIN' | null {
    const role = localStorage.getItem(API_CONFIG.USER_ROLE_KEY)
    return role as 'USER' | 'ADMIN' | null
  }

  /**
   * Check if user is authenticated
   */
  static isAuthenticated(): boolean {
    return !!this.getToken()
  }

  /**
   * Check if user is admin
   */
  static isAdmin(): boolean {
    return this.getUserRole() === 'ADMIN'
  }
}

export default AuthService