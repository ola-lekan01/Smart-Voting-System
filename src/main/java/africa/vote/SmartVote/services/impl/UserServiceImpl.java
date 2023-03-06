package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.LoginRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.UpdateUserRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           TokenRepository tokenRepository,
                           EmailService emailService,
                           JWTService jwtService,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public ApiData createAccount(TokenRequest tokenRequest) {
        tokenVerification(tokenRequest);
        var foundUser = findByEmailIgnoreCase(tokenRequest.getEmail())
                .orElseThrow(() -> new GenericException("User Not found"));
        userRepository.verifyUser(Status.VERIFIED, tokenRequest.getEmail());
        return ApiData.builder()
                .data("Welcome, " + foundUser.getFirstName()  + " Account Verified Successfully")
                .build();
    }

    @Override
    public ApiData sendOTP(ResendTokenRequest tokenRequest) {
        var foundUser = userRepository.findByEmailIgnoreCase(tokenRequest.getEmail())
                .orElseThrow(() -> new GenericException("User with " + tokenRequest.getEmail() + " not found"));
        return generateToken(tokenRequest, foundUser);
    }


    private ApiData generateToken(ResendTokenRequest resendTokenRequest, User savedUser) {
        final String generateToken = TokenGenerator.generaToken();
        var token = new Token(generateToken, savedUser);
        if(tokenRepository.findByUserId(savedUser.getId()).isEmpty()) tokenRepository.save(token);

        else{
            var foundUserOTP = tokenRepository.findByUserId(savedUser.getId()).get();
            foundUserOTP.setToken(generateToken);
            foundUserOTP.setCreatedTime(LocalDateTime.now());
            foundUserOTP.setExpiredTime(LocalDateTime.now().plusMinutes(10));
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
    public ApiData tokenVerification(TokenRequest tokenRequest) {
        var foundUser = findByEmailIgnoreCase(tokenRequest.getEmail())
                .orElseThrow(()-> new GenericException("User Does not Exist"));

        Token foundToken = tokenRepository.findByToken(tokenRequest.getToken())
                        .orElseThrow(() -> new GenericException("Token doesn't exist"));

        if(!Objects.equals(tokenRequest.getToken(), foundToken.getToken())) throw new GenericException("OTP isn't correct");
        if(foundToken.getExpiredTime().isBefore(LocalDateTime.now())) throw new GenericException("OTP already expired");
        if(!Objects.equals(foundToken.getUser().getId(), foundUser.getId())) throw new GenericException("Invalid Token");
        tokenRepository.setConfirmedAt(LocalDateTime.now(), foundToken.getId());
        tokenRepository.delete(foundToken);

        return ApiData.builder()
                .data("Token Verified")
                .build();
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }


    @Override
    public ApiData authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var foundUser = findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(()-> new GenericException("Uer does not Exist"));
        return ApiData.builder()
                .data(jwtService.generateToken(foundUser))
                .build();
    }
    @Override
    public Optional<User> getById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public String getUserName() {
        return jwtService.getUserName();
    }

    @Override
    public ApiData deleteUser() {
        var userEmail = getUserName();
        var foundUser = findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));
        userRepository.delete(foundUser);
        return ApiData.builder()
                .data("User Deleted Successfully")
                .build();
    }


    @Override
    public void tokenUpdatedForDeletedUser() {
        String userEmail = getUserName();
        User userId = tokenRepository.findByUserId(userEmail).get().getUser();
        System.out.println(userId);
        tokenRepository.updateTokenForDeletedUnverifiedUsers(userId);
    }

    @Override
    public ApiData updateAppUser(UpdateUserRequest userRequest) {
        var userEmail = getUserName();
        var foundUser = findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));

        if(userRequest.getFirstName() != null) foundUser.setFirstName(userRequest.getFirstName());
        if(userRequest.getLastName() != null) foundUser.setLastName(userRequest.getLastName());
        if(userRequest.getPassword() != null) foundUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        if(userRequest.getImageURL() != null) foundUser.setImageURL(userRequest.getImageURL());
        if(userRequest.getPhoneNumber() != null) foundUser.setImageURL(userRequest.getPhoneNumber());
        saveUser(foundUser);

        return ApiData.builder()
                .data(userRequest.getFirstName() + " updated Successfully")
                .build();
    }
}