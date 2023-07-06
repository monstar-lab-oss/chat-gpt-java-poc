package com.chat.repositories;

import com.chat.models.User;
import com.chat.models.Workout;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Workout.
 *
 *
 * @author carmen
 *
 */
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
  Optional<Workout> findFirstByUserAndStartDate(User user, LocalDateTime startDate);

  List<Workout> findByUserAndStartDateBetween(
          User user, LocalDateTime startDate, LocalDateTime endDate);

  List<Workout> findDistinctByUser(User user);
}
