package com.example.bokningsapp.service.activationTokenService;

import com.example.bokningsapp.model.ActivationToken;
import com.example.bokningsapp.model.User;
import com.example.bokningsapp.repository.ActivationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ActivationTokenService {


   private ActivationTokenRepository activationTokenRepository;

   @Autowired
    public ActivationTokenService(ActivationTokenRepository activationTokenRepository) {
        this.activationTokenRepository = activationTokenRepository;
    }

    public ActivationToken createActivationToken(User user){
        ActivationToken activationToken = new ActivationToken();
        activationToken.setUser(user);
        activationToken.setToken(UUID.randomUUID().toString());
        activationToken.setExpiryDateTime(LocalDateTime.now().plusHours(24));
        return activationTokenRepository.save(activationToken);
    }


}
