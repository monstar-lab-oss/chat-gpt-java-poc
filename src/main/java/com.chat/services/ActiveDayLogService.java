package com.chat.services;

import com.chat.models.ActiveDayLogItem;
import com.chat.models.User;
import com.chat.repositories.ActiveDayLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.time.temporal.WeekFields;

/** Provide active day related functionality. */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveDayLogService {

  private final ActiveDayLogRepository activeDayLogRepository;

  public long countByUser(User user) {
    return activeDayLogRepository.countByUser(user);
  }

  public long countActiveDaysForUser(User user) {
    return activeDayLogRepository.countActiveDays(user);
  }

  public Optional<ActiveDayLogItem> getFirstItem(User user) {
    return activeDayLogRepository.findFirstByUserOrderByDateAsc(user);
  }

  public Optional<ActiveDayLogItem> getLatestItem(User user) {
    return activeDayLogRepository.findFirstByUserOrderByDateDesc(user);
  }

  public boolean hasNoData(User user) {
    var activeCaloriesFromDate =
            activeDayLogRepository.findFirstByUserOrderByDateAsc(user)
            .map(ActiveDayLogItem::getDate)
            .orElse(LocalDate.EPOCH);
    return activeCaloriesFromDate.isEqual(LocalDate.EPOCH);
  }

  public Optional<ActiveDayLogItem> getItemForDay(User user, LocalDate day) {
    return activeDayLogRepository.findFirstByUserAndDate(user, day);
  }

  public List<ActiveDayLogItem> getItems(
      User user, LocalDate startDate, LocalDate endDate) {
    return activeDayLogRepository
        .findByUserAndDateBetweenOrderByDateDesc(
        user, startDate, endDate);
  }

  private void calculateActiveDay(ActiveDayLogItem item) {
    item.setActiveDay(item.getActiveCaloriesGoal() <= item.getActiveCalories());
  }

  public long countPositiveCalories(User user, LocalDate startDate, LocalDate endDate) {
    return activeDayLogRepository
        .countByUserAndActiveCaloriesGreaterThanAndDateBetween(user, 0, startDate, endDate);
  }

  public boolean hasFirstCalorie(User user, LocalDate day) {
    Optional<ActiveDayLogItem> firstItem = getFirstItem(user);
    if (firstItem.isPresent() && day.equals(firstItem.get().getDate())) {
      return true;
    }
    return false;
  }

  public Optional<ActiveDayLogItem> getClosestLog(User user, LocalDate date) {
    Optional<ActiveDayLogItem> logDay = activeDayLogRepository
            .findFirstByUserAndDate(user, date);
    if (logDay.isPresent() && logDay.get().getActiveCaloriesGoal() != null) {
      return logDay;
    }
    Optional<ActiveDayLogItem> logBeforeDay = activeDayLogRepository
            .findFirstByUserAndDateLessThanOrderByDateDesc(user, date);
    if (logBeforeDay.isPresent() && logBeforeDay.get().getActiveCaloriesGoal() != null) {
      return logBeforeDay;
    }
    Optional<ActiveDayLogItem> logAfterDay = activeDayLogRepository
            .findFirstByUserAndDateGreaterThanOrderByDateAsc(user, date);
    if (logAfterDay.isPresent() && logAfterDay.get().getActiveCaloriesGoal() != null) {
      return logAfterDay;
    }
    return Optional.empty();
  }

  private Map.Entry<LocalDate, LocalDate> asWeekPeriod(LocalDate date) {
    var dayOfWeek = WeekFields.ISO.dayOfWeek();
    var startOfWeek = date.with(dayOfWeek, 1);
    var endOfWeek = date.with(dayOfWeek, 7);
    return Map.entry(startOfWeek, endOfWeek);
  }
}
