package com.chat.web;

import java.util.List;
import com.chat.services.UserService;
import com.chat.services.WorkoutService;
import com.chat.models.User;
import com.chat.web.dto.UserDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
class UserController {

  private final UserService userService;
  private final WorkoutService workoutService;

  @GetMapping("/users")
  String all(Model model) {

    List<User> users = userService.findAll();
    List<UserDto> userDtos = users.stream().map(user ->
      {return new UserDto(user, workoutService.getYourWorkouts(user));}).toList();
    model.addAttribute( "users", userDtos);
    return "chat";
  }
}
