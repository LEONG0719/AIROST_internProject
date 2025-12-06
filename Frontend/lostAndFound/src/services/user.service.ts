import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'
import type { UserProfileResponse, LeaderboardEntry, CommunityStats } from '../types/api.types'

export class UserService {
  /**
   * Get current user profile, stats, and rank
   * Endpoint: GET /api/user/profile
   */
  static async getProfile() {
    const response = await httpClient.get<UserProfileResponse>(
      API_CONFIG.ENDPOINTS.USERS.PROFILE
    )
    return response.data
  }

  /**
   * Get Leaderboard (Top users)
   * Endpoint: GET /api/user/leaderboard
   */
  static async getLeaderboard() {
    const response = await httpClient.get<LeaderboardEntry[]>(
      API_CONFIG.ENDPOINTS.RANKINGS.LIST
    )
    return response.data
  }

  /**
   * Get Community Stats
   * Endpoint: GET /api/user/community-stats
   */
  static async getCommunityStats() {
    const response = await httpClient.get<CommunityStats>(
      API_CONFIG.ENDPOINTS.RANKINGS.COMMUNITY
    )
    return response.data
  }
}

export default UserService