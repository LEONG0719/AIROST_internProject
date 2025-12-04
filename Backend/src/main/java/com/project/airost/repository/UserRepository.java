package com.project.airost.repository;

import com.project.airost.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByVerificationCode(String code);
    long countByPointsGreaterThan(Integer points);
    List<User> findTop10ByOrderByPointsDesc();
    @Query("SELECT COALESCE(SUM(u.points), 0) FROM User u")
    Long sumTotalPoints();
    Optional<User> findByStudentId(String studentId);
}