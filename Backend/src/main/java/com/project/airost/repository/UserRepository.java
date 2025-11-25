package com.project.airost.repository;

import com.project.airost.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select * from user")
    List<User> getUserList ();

    @Query(nativeQuery = true, value = "select * from user where username = :username")
    List<User> getUserByUsername (@Param("username") String username );

}
