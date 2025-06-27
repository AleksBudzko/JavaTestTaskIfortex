package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findFirstByDeviceTypeOrderByStartedAtUtcAsc(DeviceType deviceType);

    List<Session> findByUserDeletedFalseAndEndedAtUtcBeforeOrderByStartedAtUtcDesc(LocalDateTime endDate);
}
