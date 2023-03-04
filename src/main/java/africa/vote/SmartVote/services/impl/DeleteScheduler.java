package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DeleteScheduler {
    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 */10 * * * *")
    public void deleteToken(){
        System.out.println("Deleted");
        userService.deleteToken();
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void deleteUnverifiedUser(){
        System.out.println("user deleted");
        userService.tokenUpdatedForDeletedUser();
        userService.deleteUnverifiedUsers();
    }
}
