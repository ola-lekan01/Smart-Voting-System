package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    ApiData createAccount(TokenRequest tokenRequest);
    ApiData sendOTP(ResendTokenRequest resendTokenRequest);
    ApiData otpTokenGeneration(ResendTokenRequest resendTokenRequest, User user);
    ApiData OTPVerification(TokenRequest tokenRequest);
    Optional<User> findByEmailIgnoreCase(String email);
    String resendOTP(TokenRequest tokenRequest);
}

