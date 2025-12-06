import httpClient from '../utils/http-client'
import { API_CONFIG } from '../config/api.config'
import type { FoundItem, FoundItemRequest } from '../types/api.types'
import AuthService from './auth.service'

export class FoundItemService {
  /**
   * Get all unclaimed found items
   */
  static async getUnclaimedItems(): Promise<FoundItem[]> {
    const response = await httpClient.get<FoundItem[]>(
      API_CONFIG.ENDPOINTS.FOUND_ITEMS.LIST
    )
    return response.data
  }

  /**
   * Get claimed found items
   */
  static async getClaimedItems(): Promise<FoundItem[]> {
    const response = await httpClient.get<FoundItem[]>(
      API_CONFIG.ENDPOINTS.FOUND_ITEMS.CLAIMED
    )
    return response.data
  }

  /**
   * Report a found item with image
   * Uses multipart/form-data for file upload
   */
  static async reportFoundItem(data: FoundItemRequest, imageFile?: File): Promise<FoundItem> {
    const userId = AuthService.getUserId()
    
    if (!userId) {
      throw new Error('User not authenticated')
    }

    // Create FormData for multipart request
    const formData = new FormData()
    
    // Add all form fields
    formData.append('userId', userId.toString())
    formData.append('title', data.title)
    formData.append('description', data.description)
    formData.append('category', data.category)
    formData.append('color', data.color)
    formData.append('brand', data.brand || '')
    
    if (data.specialMarking) {
      formData.append('specialMarking', data.specialMarking)
    }
    
    if (data.location) {
      formData.append('location', data.location)
    }
    
    // ✅ NEW: Add return location fields
    if (data.returnLocation) {
      formData.append('returnLocation', data.returnLocation)
    }
    
    if (data.returnDate) {
      formData.append('returnDate', data.returnDate)
    }
    
    if (data.returnTime) {
      formData.append('returnTime', data.returnTime)
    }
    
    // Add image file if provided
    if (imageFile) {
      formData.append('image', imageFile)
    }

    // Send multipart request
    const response = await httpClient.post<FoundItem>(
      API_CONFIG.ENDPOINTS.FOUND_ITEMS.CREATE,
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
   * Get found item by ID
   * Note: Backend endpoint needs to be implemented
   */
  static async getItemById(id: number): Promise<FoundItem> {
    const response = await httpClient.get<FoundItem>(
      API_CONFIG.ENDPOINTS.FOUND_ITEMS.DETAIL(id)
    )
    return response.data
  }
}

export default FoundItemService
