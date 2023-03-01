package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Role;
import africa.vote.SmartVote.datas.models.User;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.RegistrationService;
import africa.vote.SmartVote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static africa.vote.SmartVote.datas.enums.Status.UNVERIFIED;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public RegistrationServiceImpl(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public ApiData register(RegistrationRequest registrationRequest) {
        if(userService.findByEmailIgnoreCase(registrationRequest.getEmail())
                .isPresent()) throw new GenericException(String.format("User with %s already exist in the database", registrationRequest.getEmail()));
        var user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .email(registrationRequest.getEmail().toLowerCase())
                .imageUrl(registrationRequest.getImageUrl())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .category(Category.getCategory(registrationRequest.getCategory()))
                .status(UNVERIFIED)
                .role(Role.USER)
                .build();
        userService.saveUser(user);
        ResendTokenRequest tokenRequest = ResendTokenRequest.builder()
                .email(registrationRequest.getEmail())
                .build();
        userService.sendOTP(tokenRequest);

        return ApiData.builder()
                .data("User Registration successful")
                .build();
    }
}
