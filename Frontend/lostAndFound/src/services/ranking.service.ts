import httpClient from '../utils/http-client'
import type { RankingEntry, RankingResponse } from '../types/api.types'

export class RankingService {
  /**
   * Get leaderboard from backend
   * Backend endpoint: GET /api/user/leaderboard
   */
  static async getLeaderboard(): Promise<RankingEntry[]> {
    const response = await httpClient.get<RankingEntry[]>('/api/user/leaderboard')
    return response.data
  }

  /**
   * Get user's rank (if backend implements this endpoint later)
   * For now, we'll calculate it from the leaderboard
   */
  static async getMyRank(userId: number, leaderboard: RankingEntry[]): Promise<number | null> {
    const myEntry = leaderboard.find(entry => entry.userId === userId)
    return myEntry ? myEntry.rank : null
  }
}

export default RankingService
