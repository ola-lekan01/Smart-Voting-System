package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    ApiData createAccount(TokenRequest tokenRequest);
    ApiData sendOTP(ResendTokenRequest resendTokenRequest);
    ApiData generateToken(ResendTokenRequest resendTokenRequest, User user);
    ApiData TokenVerification(TokenRequest tokenRequest);
    Optional<User> findByEmailIgnoreCase(String email);
    ApiData resendOTP(ResendTokenRequest tokenRequest);
}

