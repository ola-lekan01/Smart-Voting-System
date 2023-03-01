package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.LoginRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    ApiData createAccount(TokenRequest tokenRequest);
    ApiData sendOTP(ResendTokenRequest resendTokenRequest);
    void TokenVerification(TokenRequest tokenRequest);
    Optional<User> findByEmailIgnoreCase(String email);
    ApiData resendOTP(ResendTokenRequest tokenRequest);
    ApiData authenticate(LoginRequest request);
    Optional<User> getById(Long userId);
}

