package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.OTPVerificationRequest;
import africa.vote.SmartVote.datas.dtos.requests.SendotpRequest;
import africa.vote.SmartVote.datas.models.Users;

import java.util.Optional;

public interface UserService {
    Users saveUser(Users user);
    String createAccount(OTPVerificationRequest otpVerificationRequest);
    String sendOTP(SendotpRequest sendotpRequest);
    String otpTokenGeneration(SendotpRequest sendotpRequest, Users user);
    String OTPVerification(OTPVerificationRequest otpVerificationRequest);
    Optional<Users> getByEmailAddress(String email);
    Optional<Users> getById(Long id);
}
