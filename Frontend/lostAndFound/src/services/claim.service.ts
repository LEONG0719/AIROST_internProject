import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'

export class ClaimService {
  /**
   * Get claim details by ID
   * Endpoint: GET /api/claims/{id}
   */
  static async getClaimById(claimId: number) {
    const response = await httpClient.get(`/api/claims/${claimId}`)
    return response.data
  }

  /**
   * Upload student card and optional receipt for verification
   * Endpoint: POST /api/claims/{id}/upload-proof
   */
  static async uploadProof(
    claimId: number,
    studentIdNumber: string,
    idCardFile: File,
    receiptFile?: File
  ) {
    const formData = new FormData()
    
    // Add student ID number
    formData.append('studentIdNumber', studentIdNumber)
    
    // Add ID card image (required)
    formData.append('idCardImage', idCardFile)
    
    // Add receipt image (optional)
    if (receiptFile) {
      formData.append('receiptImage', receiptFile)
    }

    const response = await httpClient.post(
      `/api/claims/${claimId}/upload-proof`,
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
   * Confirm return and claim item (final step)
   * Endpoint: POST /api/claims/{id}/confirm-return
   */
  static async confirmReturn(claimId: number, userId: number) {
    const response = await httpClient.post(
      `/api/claims/${claimId}/confirm-return`,
      null,
      {
        params: {
          userId: userId
        }
      }
    )
    
    return response.data
  }
}

export default ClaimService