package com.project.airost.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //setter and getter
@NoArgsConstructor
@Entity(name = "user") //database table name
public class User {

    @Id //notify springboot this is an id (userid)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    private String email;

}
