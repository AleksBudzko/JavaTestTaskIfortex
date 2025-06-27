package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.sessions s GROUP BY u ORDER BY COUNT(s) DESC")
    List<User> findUsersOrderBySessionCountDesc(Pageable pageable);

    @Query("""
       SELECT u
         FROM User u JOIN u.sessions s
        WHERE s.deviceType = :deviceType
        GROUP BY u
        ORDER BY MAX(s.startedAtUtc) DESC
    """)
    List<User> findUsersWithAtLeastOneMobileSession(@Param("deviceType") DeviceType deviceType);
}
