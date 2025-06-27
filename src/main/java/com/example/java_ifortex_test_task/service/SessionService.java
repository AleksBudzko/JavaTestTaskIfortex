package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionResponseDTO getFirstDesktopSession() {
        Session session = sessionRepository
                .findFirstByDeviceTypeOrderByStartedAtUtcAsc(DeviceType.DESKTOP)
                .orElseThrow(() -> new EntityNotFoundException("No desktop session found"));
        return sessionMapper.toDto(session);
    }

    public List<SessionResponseDTO> getSessionsFromActiveUsersEndedBefore2025() {
        LocalDateTime cutoff = LocalDateTime.of(2025,1,1,0,0);
        return sessionRepository
                .findByUserDeletedFalseAndEndedAtUtcBeforeOrderByStartedAtUtcDesc(cutoff)
                .stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }
}
