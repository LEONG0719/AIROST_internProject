import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'
import type { LostClaim, LostClaimRequest } from '../types/api.types'
import AuthService from './auth.service'

export class LostClaimService {
  /**
   * Get all pending claims (for admin)
   */
  static async getPendingClaims(): Promise<LostClaim[]> {
    const response = await httpClient.get<LostClaim[]>(
      API_CONFIG.ENDPOINTS.CLAIMS.PENDING
    )
    return response.data
  }

  /**
   * Submit a lost item claim with image
   * Uses multipart/form-data for file upload
   */
  static async submitClaim(data: LostClaimRequest, imageFile?: File): Promise<LostClaim> {
    const userId = AuthService.getUserId()
    
    if (!userId) {
      throw new Error('User not authenticated')
    }

    // Create FormData for multipart request
    const formData = new FormData()
    
    // Create the JSON data part
    const requestData = {
      userId: userId,
      description: data.description,
      category: data.category,
      color: data.color,
      brand: data.brand || 'Unknown',
      remarks: data.remarks || '',
      specialMarking: data.specialMarking || '',
      location: data.location || ''
    }
    
    // Add the JSON data as a blob (backend expects @RequestPart("data"))
    const dataBlob = new Blob([JSON.stringify(requestData)], {
      type: 'application/json'
    })
    formData.append('data', dataBlob)
    
    // Add image file if provided (backend expects @RequestPart("image"))
    if (imageFile) {
      formData.append('image', imageFile)
    }

    // Send multipart request
    const response = await httpClient.post<LostClaim>(
      API_CONFIG.ENDPOINTS.CLAIMS.SUBMIT,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    )
    
    return response.data
  }

  /**
   * Get claim by ID
   * Note: Backend endpoint needs to be implemented
   */
  static async getClaimById(id: number): Promise<LostClaim> {
    const response = await httpClient.get<LostClaim>(
      API_CONFIG.ENDPOINTS.CLAIMS.DETAIL(id)
    )
    return response.data
  }

  /**
   * Admin approve claim
   */
  static async approveClaim(id: number, adminId: number, note: string): Promise<LostClaim> {
    const response = await httpClient.post<LostClaim>(
      API_CONFIG.ENDPOINTS.CLAIMS.APPROVE(id),
      null,
      {
        params: { adminId, note }
      }
    )
    return response.data
  }

  /**
   * Admin reject claim
   */
  static async rejectClaim(id: number, adminId: number, note: string): Promise<LostClaim> {
    const response = await httpClient.post<LostClaim>(
      API_CONFIG.ENDPOINTS.CLAIMS.REJECT(id),
      null,
      {
        params: { adminId, note }
      }
    )
    return response.data
  }
}

export default LostClaimService
