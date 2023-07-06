package com.chat.services;

import com.chat.models.User;
import com.chat.models.Workout;
import com.chat.repositories.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** Provide workout related functionality. */
@Service
@RequiredArgsConstructor
public class WorkoutService {

  private final WorkoutRepository workoutRepository;

  public String getYourWorkouts(User user) {
    List<Workout> workouts = workoutRepository.findDistinctByUser(user);
    return workouts.stream().map( workout -> workout.getWorkoutType() ).distinct()
            .collect( Collectors.joining( "," ) );
  }
}
