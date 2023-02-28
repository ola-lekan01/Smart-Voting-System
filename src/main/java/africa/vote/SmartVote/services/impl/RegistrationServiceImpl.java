package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;
import africa.vote.SmartVote.datas.dtos.requests.SendotpRequest;
import africa.vote.SmartVote.datas.models.Users;
import africa.vote.SmartVote.datas.repositories.UserRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.RegistrationService;
import africa.vote.SmartVote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static africa.vote.SmartVote.datas.enums.Cohort.COHORT_I;
import static africa.vote.SmartVote.datas.enums.Status.UNVERIFIED;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String register(RegistrationRequest registrationRequest) {
        if(userRepository.findByEmailIgnoreCase(registrationRequest.getEmail()).isPresent()) throw new GenericException(String.format("User with %s already exist in the database", registrationRequest.getEmail()));
        Users users = Users.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .email(registrationRequest.getEmail())
                .imageUrl(registrationRequest.getImageUrl())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .cohort(COHORT_I)
                .status(UNVERIFIED)
                .build();
        userService.saveUser(users);
        SendotpRequest otpRequest = new SendotpRequest();
        otpRequest.setEmail(registrationRequest.getEmail());
        userService.sendOTP(otpRequest);
        return "User Registration successful";
    }
}
