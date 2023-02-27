package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;
import africa.vote.SmartVote.datas.dtos.requests.SendotpRequest;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.Users;
import africa.vote.SmartVote.services.RegistrationService;
import africa.vote.SmartVote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        Users users = Users.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .email(registrationRequest.getEmail())
                .imageUrl(registrationRequest.getImageUrl())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .category(Category.COHORT12)
                .status(Status.UNVERIFIED)
                .build();
        Users savedUser = userService.saveUser(users);
        SendotpRequest otpRequest = new SendotpRequest();
        otpRequest.setEmail(registrationRequest.getEmail());
        userService.sendOTP(otpRequest);
        return "User Registration successful";
    }
}
