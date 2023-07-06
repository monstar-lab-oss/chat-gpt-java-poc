package com.chat.services;

import com.chat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.chat.models.User;
import com.chat.exceptions.UserNotFoundException;
import java.util.List;

/** Provide user related functionality. */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public String getFullName(Long id) {
    var user = userRepository
          .findById(id)
          .orElseThrow();
    return user.getFirstName() + " " + user.getLastName();
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User createNew(User user) {
    return userRepository.save(user);
  }

  public User getUser(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
  }

  public User updateUser(User newUser, Long id) {
    return userRepository.findById(id)
            .map(user -> {
              user.setFirstName(newUser.getFirstName());
              user.setLastName(newUser.getLastName());
              return userRepository.save(user);
            })
            .orElseGet(() -> {
              newUser.setId(id);
              return userRepository.save(newUser);
            });
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
