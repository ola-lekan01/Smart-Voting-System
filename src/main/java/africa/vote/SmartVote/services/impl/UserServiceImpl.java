package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.OTPVerificationRequest;
import africa.vote.SmartVote.datas.dtos.requests.SendotpRequest;
import africa.vote.SmartVote.datas.models.Users;
import africa.vote.SmartVote.datas.repositories.UserRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public String createAccount(OTPVerificationRequest otpVerificationRequest) {
        return null;
    }

    @Override
    public String sendOTP(SendotpRequest sendotpRequest) {
        Users users = userRepository.findByEmailIgnoreCase(sendotpRequest.getEmail()).orElseThrow(() -> new GenericException("user with " + sendotpRequest.getEmail() + " doesn't exist"));
        return "OTP sent successfully";
    }

    @Override
    public String otpTokenGeneration(SendotpRequest sendotpRequest, User user) {
        return null;
    }
}
