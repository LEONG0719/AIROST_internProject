import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'
import type { ActivityFeed, DashboardStats, CommunityStats } from '../types/api.types'

export class DashboardService {
  /**
   * Get recent activity feed (user-specific)
   */
  static async getRecentActivity(): Promise<ActivityFeed[]> {
    const response = await httpClient.get<ActivityFeed[]>(
      API_CONFIG.ENDPOINTS.DASHBOARD.RECENT_ACTIVITY
    )
    return response.data
  }

  /**
   * Get user profile info
   */
  static async getUserProfile(): Promise<any> {
    const response = await httpClient.get('/api/user/profile')
    return response.data
  }

  /**
   * Get community-wide statistics
   */
  static async getCommunityStats(): Promise<CommunityStats> {
    const response = await httpClient.get<CommunityStats>('/api/user/community-stats')
    return response.data
  }

  /**
   * Get user dashboard statistics (personal stats)
   * Note: This endpoint needs to be implemented by backend
   * For now, we'll get it from user profile
   */
  static async getUserStats(userId: number): Promise<DashboardStats> {
    try {
      // Try to fetch from backend (when endpoint is ready)
      const response = await httpClient.get<DashboardStats>(
        API_CONFIG.ENDPOINTS.DASHBOARD.STATS(userId)
      )
      return response.data
    } catch (error) {
      // Fallback: Get from user profile
      try {
        const profile = await this.getUserProfile()
        return {
          itemsFound: profile.itemsFound || 0,
          itemsLost: profile.itemsLost || 0,
          matched: profile.itemsMatched || 0,
          points: profile.points || 0
        }
      } catch {
        // Final fallback
        return {
          itemsFound: 0,
          itemsLost: 0,
          matched: 0,
          points: 0
        }
      }
    }
  }
}

export default DashboardService