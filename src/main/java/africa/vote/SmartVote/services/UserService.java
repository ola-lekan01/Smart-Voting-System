package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.LoginRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.UpdateUserRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    ApiData createAccount(TokenRequest tokenRequest);
    ApiData tokenVerification(TokenRequest tokenRequest);
    Optional<User> findByEmailIgnoreCase(String email);
    ApiData sendOTP(ResendTokenRequest tokenRequest);
    ApiData authenticate(LoginRequest request);
    Optional<User> getById(String userId);
    String getUserName();
    ApiData deleteUser();
    void tokenUpdatedForDeletedUser();
    ApiData updateAppUser(UpdateUserRequest userRequest);
}

