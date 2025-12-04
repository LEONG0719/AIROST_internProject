import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'
import type { ActivityFeed, DashboardStats } from '../types/api.types'

export class DashboardService {
  /**
   * Get recent activity feed
   */
  static async getRecentActivity(): Promise<ActivityFeed[]> {
    const response = await httpClient.get<ActivityFeed[]>(
      API_CONFIG.ENDPOINTS.DASHBOARD.RECENT_ACTIVITY
    )
    return response.data
  }

  /**
   * Get user dashboard statistics
   * Note: This endpoint needs to be implemented by backend
   * For now, we'll return mock data and you can update when backend is ready
   */
  static async getUserStats(userId: number): Promise<DashboardStats> {
    try {
      // Try to fetch from backend (when endpoint is ready)
      const response = await httpClient.get<DashboardStats>(
        API_CONFIG.ENDPOINTS.DASHBOARD.STATS(userId)
      )
      return response.data
    } catch (error) {
      // Fallback to mock data until backend implements this endpoint
      console.warn('Dashboard stats endpoint not yet implemented, using mock data')
      return {
        itemsFound: 0,
        itemsLost: 0,
        matched: 0,
        points: 0
      }
    }
  }
}

export default DashboardService