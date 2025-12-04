package com.project.airost.service;

import com.project.airost.dto.CommunityStatsResponse;
import com.project.airost.dto.LeaderboardEntry;
import com.project.airost.dto.UserProfileResponse;

import java.util.List;

public interface UserService {
    UserProfileResponse getUserProfile(Long userId);
    UserProfileResponse getUserProfileByEmail(String email);
    public List<LeaderboardEntry> getLeaderboard();
    CommunityStatsResponse getCommunityStats();
}