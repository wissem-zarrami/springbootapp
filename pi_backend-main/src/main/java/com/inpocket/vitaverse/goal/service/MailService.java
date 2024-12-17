package com.inpocket.vitaverse.goal.service;

import com.inpocket.vitaverse.goal.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    // Method to send a test email
    public void sendTestEmail() {
        String to = "ghanmimohamedaziz5@gmail.com"; // Update with recipient's email
        String subject = "Test Email";
        String body = "This is a test email sent using Spring Mail.";
        sendEmail(to, subject, body);
    }

    // Method to send an email
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


    public void sendReminderEmail(String to, List<Task> incompleteTasks) {
        StringBuilder body = new StringBuilder();
        body.append("Daily Reminder: Incomplete Tasks");
        Date now = new Date();
        for (Task task : incompleteTasks) {
            Date deadline = task.getDeadline();
            long millisecondsLeft = deadline.getTime() - now.getTime();
            long hoursLeft = TimeUnit.MILLISECONDS.toHours(millisecondsLeft);
            long daysLeft = TimeUnit.HOURS.toDays(hoursLeft);

            String timeLeft;
            if (daysLeft > 0) {
                timeLeft = daysLeft + " days";
            } else if (hoursLeft > 0) {
                timeLeft = hoursLeft + " hours";
            } else {
                timeLeft = "less than an hour";
            }

            body.append(" ").append(task.getDescription()).append(": ").append(timeLeft).append(" left ");
        }
        sendEmail(to, "Daily Reminder: Incomplete Tasks ", body.toString());
    }

}
