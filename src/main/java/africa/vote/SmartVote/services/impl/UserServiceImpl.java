package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.OTPVerificationRequest;
import africa.vote.SmartVote.datas.dtos.requests.SendotpRequest;
import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.OTPtoken;
import africa.vote.SmartVote.datas.models.Users;
import africa.vote.SmartVote.datas.repositories.TokenRepository;
import africa.vote.SmartVote.datas.repositories.UserRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.EmailService;
import africa.vote.SmartVote.services.UserService;
import africa.vote.SmartVote.utils.TokenGenerator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static africa.vote.SmartVote.utils.EmailUtils.buildEmail;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private EmailService emailService;
    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public String createAccount(OTPVerificationRequest otpVerificationRequest) {
        OTPVerification(otpVerificationRequest);
        userRepository.verifyUser(Status.VERIFIED, otpVerificationRequest.getEmail());
        return "User verification successful";
    }

    @Override
    public String sendOTP(SendotpRequest sendotpRequest) {
        Users users = userRepository.findByEmailIgnoreCase(sendotpRequest.getEmail()).orElseThrow(() -> new GenericException("user with " + sendotpRequest.getEmail() + " doesn't exist"));
        return otpTokenGeneration(sendotpRequest, users);
    }

    @Override
    public String otpTokenGeneration(SendotpRequest sendotpRequest, Users savedUser) {
        String generateToken = TokenGenerator.generaToken();

        OTPtoken otPtoken = new OTPtoken(generateToken, Instant.now(), Instant.now().plusSeconds(600), savedUser);
        var foundUserOTP = tokenRepository.findOTPtokenById(savedUser.getId());
        if(Objects.isNull(foundUserOTP)) tokenRepository.save(otPtoken);
        else{
            var foundToken = tokenRepository.findById(savedUser.getId()).orElseThrow(() -> new GenericException("Token doesn't exist"));
            foundToken.setToken(generateToken);
            foundToken.setCreatedTime(Instant.now());
            foundToken.setConfirmedTime(Instant.now().plusSeconds(600));
            foundToken.setUser(savedUser);
            tokenRepository.save(foundToken);
        }
        emailService.sendEmail(sendotpRequest.getEmail(), buildEmail(savedUser.getFirstName(), generateToken));
        return "Token successfully sent to your email, please confirm now!!!";
    }

    @Override
    public String OTPVerification(OTPVerificationRequest otpVerificationRequest) {
        OTPtoken foundOtPtoken = tokenRepository.findOTPtokenByToken(otpVerificationRequest.getToken()).
                orElseThrow(() -> new GenericException("Token doesn't exist"));

        if(foundOtPtoken.getExpiredTime().isBefore(Instant.now())) throw new GenericException("OTP already expired");
        if(foundOtPtoken.getConfirmedTime() != null) throw new GenericException("OTP has already been used");
        if(!Objects.equals(otpVerificationRequest.getToken(), foundOtPtoken.getToken())) throw new GenericException("OTP isn't correct");
        tokenRepository.setConfirmedAt(Instant.now(), otpVerificationRequest.getToken());
        return "Confirmed";
    }

    @Override
    public Optional<Users> getByEmailAddress(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }
}
