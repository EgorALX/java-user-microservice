package ru.users.userservice.service;

import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import ru.users.userservice.exception.model.NotFoundException;
import ru.users.userservice.mapper.UserMapper;
import ru.users.userservice.model.User;
import ru.users.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers(String name, String surname, LocalDate registrationDate, PageRequest pageRequest) {
        List<User> users = userRepository.getUsersByParams(name,
                surname, registrationDate, pageRequest);
        return users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " not found."));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto add(NewUserDto dto) {
        User user = userMapper.toUser(dto);
        User newUser = userRepository.save(user);
        return userMapper.toUserDto(newUser);
    }

    @Override
    public UpdateUserDto update(Integer userId, UpdateUserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            user.setSurname(dto.getSurname());
        }
        if (dto.getRegistrationDate() != null) {
            user.setRegistrationDate(dto.getRegistrationDate());
        }
        return userMapper.toUpdateDto(user);
    }

    @Override
    public void removeById(Integer userId) {
        userRepository.deleteById(userId);
    }
}
