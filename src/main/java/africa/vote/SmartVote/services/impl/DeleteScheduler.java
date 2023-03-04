package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.repositories.TokenRepository;
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
    private final TokenRepository tokenRepository;

    // Clear confirmed token from the token repo every 24hrs
    @Scheduled(cron = "0 0 0/24 * * ?")
    public void deleteToken(){
        userService.deleteToken();
    }

    // Clear all unverified users after 5 days
    @Scheduled(cron = "0 0 0 */5 * *")
    public void deleteUnverifiedUser(){
        userService.deleteUnverifiedUsers();
    }

    // Clear unused token after 2 days
    @Scheduled(cron = "0 0 0 */2 * *")
    public void deleteUnconfirmedToken(){
        tokenRepository.deleteUnconfirmedToken();
    }
}
