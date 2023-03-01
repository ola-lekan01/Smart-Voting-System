package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.LoginRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.Token;
import africa.vote.SmartVote.datas.models.User;
import africa.vote.SmartVote.datas.repositories.TokenRepository;
import africa.vote.SmartVote.datas.repositories.UserRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.security.config.JWTService;
import africa.vote.SmartVote.services.EmailService;
import africa.vote.SmartVote.services.UserService;
import africa.vote.SmartVote.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static africa.vote.SmartVote.utils.EmailUtils.buildEmail;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JWTService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository,
                           EmailService emailService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public ApiData createAccount(TokenRequest tokenRequest) {
        TokenVerification(tokenRequest);
        userRepository.verifyUser(Status.VERIFIED, tokenRequest.getEmail());
        var foundUser = findByEmailIgnoreCase(tokenRequest.getEmail())
                .orElseThrow(() -> new GenericException("User Not found"));
        return ApiData.builder()
                .data(jwtService.generateToken(foundUser))
                .build();
    }

    @Override
    public ApiData sendOTP(ResendTokenRequest resendTokenRequest) {
        var user = userRepository.findByEmailIgnoreCase(resendTokenRequest.getEmail())
                .orElseThrow(() -> new GenericException
                        ("user with " + resendTokenRequest.getEmail() + " doesn't exist"));

        return generateToken(resendTokenRequest, user);
    }


    private ApiData generateToken(ResendTokenRequest resendTokenRequest, User savedUser) {
        final String generateToken = TokenGenerator.generaToken();
        var token = new Token(generateToken, savedUser);
        if(tokenRepository.findByUserId(savedUser.getId()).isEmpty()) tokenRepository.save(token);

        else{
            var foundUserOTP = tokenRepository.findByUserId(savedUser.getId()).get();
            foundUserOTP.setToken(generateToken);
            foundUserOTP.setCreatedTime(LocalDateTime.now());
            foundUserOTP.setConfirmedTime(LocalDateTime.now().plusMinutes(10));
            foundUserOTP.setUser(savedUser);
            tokenRepository.save(foundUserOTP);
        }
        emailService.sendEmail(resendTokenRequest.getEmail(),
                buildEmail(savedUser.getFirstName(), generateToken));

        return ApiData.builder()
                .data("Token successfully sent to  " + resendTokenRequest.getEmail())
                .build();
    }

    @Override
    public void TokenVerification(TokenRequest tokenRequest) {
        Token foundToken = tokenRepository.findByToken(tokenRequest.getToken()).
                orElseThrow(() -> new GenericException("Token doesn't exist"));

        if(foundToken.getExpiredTime().isBefore(LocalDateTime.now())) throw new GenericException("OTP already expired");
        if(foundToken.getConfirmedTime() != null) throw new GenericException("OTP has already been used");
        if(!Objects.equals(tokenRequest.getToken(), foundToken.getToken())) throw new GenericException("OTP isn't correct");
        tokenRepository.setConfirmedAt(LocalDateTime.now(), foundToken.getId());
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public ApiData resendOTP(ResendTokenRequest tokenRequest) {
        var foundUser = userRepository.findByEmailIgnoreCase(tokenRequest.getEmail())
                .orElseThrow(() -> new GenericException("User with " + tokenRequest.getEmail() + " not found"));

        if(Objects.equals(tokenRequest.getEmail(), foundUser.getEmail())){
            generateToken(tokenRequest, foundUser);
        }
        return ApiData.builder()
                .data("Token sent to " + tokenRequest.getEmail())
                .build();
    }

    @Override
    public ApiData authenticate(LoginRequest request) {
        var foundUser = findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new GenericException("User Not found"));
        return ApiData.builder()
                .data(jwtService.generateToken(foundUser))
                .build();
    }
}