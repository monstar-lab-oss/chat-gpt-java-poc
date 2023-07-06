package com.chat.web.dto;

import com.chat.models.User;
import com.chat.models.Workout;
import java.util.List;

public record UserDto(User user, String workouts) {
}
