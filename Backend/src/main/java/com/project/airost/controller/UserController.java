package com.project.airost.controller;

import com.project.airost.dto.LeaderboardEntry;
import com.project.airost.dto.UserProfileResponse;
import com.project.airost.security.JwtUtil;
import com.project.airost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, allowedHeaders = "*", methods = {RequestMethod.GET})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader("Authorization") String token) {
        // 1. Remove "Bearer " prefix to get the raw token
        String jwt = token.substring(7);

        // 2. Extract the email hidden inside the token
        String email = jwtUtil.extractUsername(jwt);

        // 3. Fetch full profile data using the email
        return ResponseEntity.ok(userService.getUserProfileByEmail(email));


    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard() {
        return ResponseEntity.ok(userService.getLeaderboard());
    }
}