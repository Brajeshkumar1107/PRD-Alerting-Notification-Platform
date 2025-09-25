package com.brajesh.service.scheduler;



import com.brajesh.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final AlertService alertService;

    /**
     * Runs every 2 hours to trigger reminders
     */
    @Scheduled(fixedRate = 7200000) // 2 hours in milliseconds
    public void sendReminders() {
        alertService.triggerReminders();
    }
}
