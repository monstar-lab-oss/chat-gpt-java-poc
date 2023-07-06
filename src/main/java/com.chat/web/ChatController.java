package com.chat.web;

import java.util.List;
import com.chat.services.UserService;
import com.chat.models.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import com.chat.services.ChatGPT;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Duration;
import com.chat.services.ActiveDayLogService;
import java.util.Optional;
import com.chat.models.ActiveDayLogItem;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

@Controller
@RequiredArgsConstructor
class ChatController {

  private final UserService userService;
  private final ActiveDayLogService activeDayLogService;
  private final ChatGPT chatGPT;

  @PostMapping("/chat/recommendations/user")
//  String recommandations(@RequestBody String filter,
//                         @PathVariable Long id) throws Exception {
  String recommandations(@RequestParam("selectedUser") Long selectedUser, @RequestParam("type") String type,
                         @RequestParam(defaultValue = "") String filterActivities, Model model)
          throws Exception {
    User user = userService.getUser(selectedUser);
    LocalDate today = LocalDate.from(LocalDateTime.now());
    String filteredList = "";
    if (!filterActivities.equals("")) {
      filteredList = " Choose only from this activities: " + filterActivities + ". ";
    }
    Optional<ActiveDayLogItem> todayLog = activeDayLogService.getItemForDay(user, today);
    Optional<ActiveDayLogItem> closestLog = activeDayLogService.getClosestLog(user, today);
    String prompt = "";
    Integer goal = (closestLog.isPresent() ? closestLog.get().getActiveCaloriesGoal() :
            user.getActiveCaloriesGoal());
    switch (type) {
      case "daily":
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
        Long diff = Duration.between(start, end).toMinutes();
        Integer consumed = todayLog.isPresent() ? todayLog.get().getActiveCalories() : 0;
        Integer remaining = goal - consumed;
        if (remaining > 0) {
          prompt = "My goal is to do activities that consume " + remaining + " calories. State my goal. " +
                  "I only have " + diff + " minutes at my disposal. Make a list of activities (state consumed calories) " +
                  "I can do today to achieve my goal." + filteredList;
//        "If today my goal is to consume " + goal +
//                  " calories to be active and I only consumed " + (todayLog.isPresent() ? todayLog.get().getActiveCalories() : 0) +
//                  " so far, what is the minimum effort I need and what kind of activities I can do by the end of the day to have an active day? " +
//                  "My weight is " + (closestLog.isPresent() ? closestLog.get().getWeight() : user.getWeightInPounds()) + " pounds. " +
//                  "Remaining time is " + hms +
//                  ". State my today's goal, my actual consumption, remaining time and then make the recomandations " +
//                  "as a list specifying the energy for each activity." + filteredList;
        } else {
          model.addAttribute( "suggestions", "You've already achieved your goal");
          return "suggestions";
        }
             //+   " Suggestions must only contain workouts found on apple watch https://support.apple.com/en-us/HT207934";
        break;
      case "dietary":
        prompt = "I want to be more active. Please give me dietary advices";
        break;
      case "start":
        prompt = "I want to get into running. Please advice";
        break;
      case "weekly'":
      default:
        prompt = "My goal is to do activities that consume " + goal + " calories daily. State my goal. " +
                "Make a list of activities (state the consumed calories) per day for a week to " +
                "achieve my goal." + filteredList;
                //+ " Suggestions must only contain workouts found on apple watch https://support.apple.com/en-us/HT207934";
        break;
    }
    String suggestions = chatGPT.chatGPT(prompt);

    model.addAttribute( "suggestions", suggestions);
    return "suggestions";
  }
}
