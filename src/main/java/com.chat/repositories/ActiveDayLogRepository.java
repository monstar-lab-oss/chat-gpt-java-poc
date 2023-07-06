package com.chat.repositories;

import com.chat.models.ActiveDayLogItem;
import com.chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for ActiveDayLogItem
 *
 */
@Repository
public interface ActiveDayLogRepository extends JpaRepository<ActiveDayLogItem, Long> {

  Optional<ActiveDayLogItem> findFirstByUserAndDate(User user, LocalDate date);

  List<ActiveDayLogItem> findByUserAndDateGreaterThan(
      User user, LocalDate date);

  List<ActiveDayLogItem> findByUserAndDateBetweenOrderByDateDesc(
      User user, LocalDate start, LocalDate end);

  long countByUserAndActiveDay(User user, boolean activeDay);

  long countByUserAndActiveCaloriesGreaterThanAndDateBetween(User user, int calories,
      LocalDate startDay, LocalDate endDay);

  long countByUserAndActiveDayAndDateBetween(User user, boolean activeDay,
      LocalDate startDay, LocalDate endDay);

  long countByUser(User user);

  default long countActiveDays(User user) {
    return countByUserAndActiveDay(user, true);
  }

  Optional<ActiveDayLogItem> findFirstByUserOrderByDateAsc(User user);

  Optional<ActiveDayLogItem> findFirstByUserOrderByDateDesc(User user);

  Optional<ActiveDayLogItem> findFirstByUserAndDateLessThanOrderByDateDesc(
      User user, LocalDate day);

  Optional<ActiveDayLogItem> findFirstByUserAndDateGreaterThanOrderByDateAsc(
      User user, LocalDate day);
}
