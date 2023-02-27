package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;

public interface RegistrationService {
    String register(RegistrationRequest registrationRequest);
}
