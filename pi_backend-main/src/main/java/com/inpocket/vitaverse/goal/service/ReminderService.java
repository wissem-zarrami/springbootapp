package com.inpocket.vitaverse.goal.service;

import com.inpocket.vitaverse.goal.entity.Task;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReminderService {

    private final TaskService taskService;
    private final MailService mailService;
    private final UserRepository userRepository;

  //@Scheduled(fixedRate = 60000)
  @Scheduled(cron = "0 0 0 * * *") // Runs every day at 00:00 (midnight)
  public void sendDailyReminders() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<Task> incompleteTasks = taskService.findByUserUserIdAndIncomplete(user.getUserId());
            if (incompleteTasks != null && !incompleteTasks.isEmpty()) {
                mailService.sendReminderEmail(user.getEmail(), incompleteTasks);
            }
        }
    }
}
