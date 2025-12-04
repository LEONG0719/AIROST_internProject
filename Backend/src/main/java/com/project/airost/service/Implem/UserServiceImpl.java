package com.project.airost.service.Implem;

import com.project.airost.domain.User;
import com.project.airost.domain.LostClaim;
import com.project.airost.dto.LeaderboardEntry;
import com.project.airost.dto.UserProfileResponse;
import com.project.airost.repository.FoundItemRepository;
import com.project.airost.repository.LostClaimRepository;
import com.project.airost.repository.UserRepository;
import com.project.airost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final FoundItemRepository foundItemRepo;
    private final LostClaimRepository lostClaimRepo;

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        // 1. Fetch User
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Fetch Stats
        long foundCount = foundItemRepo.countByUserId(userId);
        long lostCount = lostClaimRepo.countByUserId(userId);
        long matchedCount = lostClaimRepo.countByUserIdAndStatus(userId, LostClaim.ClaimStatus.APPROVED);

        // 3. Calculate Rank
        // Rank = (Number of people with more points than me) + 1
        long betterPlayers = userRepo.countByPointsGreaterThan(user.getPoints());
        long rank = betterPlayers + 1;
        long totalUsers = userRepo.count();

        // 4. Build Response
        return UserProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .points(user.getPoints())
                .phoneNumber(user.getPhoneNumber())
                .itemsFoundCount(foundCount)
                .itemsLostCount(lostCount)
                .itemsMatchedCount(matchedCount)
                .globalRank(rank)
                .totalUsers(totalUsers)
                .build();
    }

    @Override
    public UserProfileResponse getUserProfileByEmail(String email) {
        // 1. Find the user ID using the email
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Call the main method above using that ID
        return getUserProfile(user.getId());
    }

    @Override
    public List<LeaderboardEntry> getLeaderboard() {
        List<User> topUsers = userRepo.findTop10ByOrderByPointsDesc();
        List<LeaderboardEntry> leaderboard = new ArrayList<>();

        int rank = 1;
        for (User user : topUsers) {
            leaderboard.add(LeaderboardEntry.builder()
                    .rank(rank++)
                    .fullName(user.getFullName())
                    .points(user.getPoints())
                    .build());
        }
        return leaderboard;
    }
}