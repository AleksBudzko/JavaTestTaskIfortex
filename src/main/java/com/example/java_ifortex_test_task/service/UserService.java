package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.UserResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import com.example.java_ifortex_test_task.mapper.UserMapper;
import com.example.java_ifortex_test_task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO getUserWithMostSessions() {
        User user = userRepository
                .findUsersOrderBySessionCountDesc(PageRequest.of(0,1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No users with sessions found"));
        return userMapper.toDto(user);
    }

    public List<UserResponseDTO> getUsersWithAtLeastOneMobileSession() {
        return userRepository
                .findUsersWithAtLeastOneMobileSession(DeviceType.MOBILE)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
