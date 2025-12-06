import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'

export class AdminService {
  /**
   * Get all claims (admin only)
   * Endpoint: GET /api/claims/admin/all
   */
  static async getAllClaims(): Promise<any[]> {
    const response = await httpClient.get('/api/claims/admin/all')
    return response.data
  }

  /**
   * Get pending verifications (admin only)
   * Endpoint: GET /api/claims/admin/pending
   */
  static async getPendingVerifications(): Promise<any[]> {
    const response = await httpClient.get('/api/claims/admin/pending')
    return response.data
  }

  /**
   * Approve a claim
   * Endpoint: POST /api/claims/{id}/admin/approve
   */
  static async approveClaim(claimId: number, adminId: number, note: string): Promise<any> {
    const response = await httpClient.post(
      `/api/claims/${claimId}/admin/approve`,
      null,
      {
        params: {
          adminId: adminId,
          note: note
        }
      }
    )
    return response.data
  }

  /**
   * Reject a claim
   * Endpoint: POST /api/claims/{id}/admin/reject
   */
  static async rejectClaim(claimId: number, adminId: number, reason: string): Promise<any> {
    const response = await httpClient.post(
      `/api/claims/${claimId}/admin/reject`,
      null,
      {
        params: {
          adminId: adminId,
          reason: reason
        }
      }
    )
    return response.data
  }

  /**
   * Get claim by ID (admin view with all details)
   * Endpoint: GET /api/claims/{id}
   */
  static async getClaimById(claimId: number): Promise<any> {
    const response = await httpClient.get(`/api/claims/${claimId}`)
    return response.data
  }

  /**
   * Get system statistics (admin only)
   * Endpoint: GET /api/admin/stats
   */
  static async getSystemStats(): Promise<any> {
    const response = await httpClient.get('/api/admin/stats')
    return response.data
  }
}

export default AdminService
