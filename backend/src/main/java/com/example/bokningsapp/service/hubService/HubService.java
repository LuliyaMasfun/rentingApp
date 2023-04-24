package com.example.bokningsapp.service.hubService;


import com.example.bokningsapp.exception.EquipmentNotFoundException;
import com.example.bokningsapp.model.Equipment;
import com.example.bokningsapp.model.Hub;
import com.example.bokningsapp.model.User;
import com.example.bokningsapp.repository.HubRepository;
import com.example.bokningsapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HubService {

    private HubRepository hubRepository;

    private UserRepository userRepository;


    @Autowired
    public HubService(HubRepository hubRepository, UserRepository userRepository) {
        this.hubRepository = hubRepository;
        this.userRepository = userRepository;
    }


    public Hub saveHub(Hub hub) {
        return hubRepository.save(hub);
    }

    @Transactional
    public void deleteHub(Long id) {
        Hub deletedHub = hubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hub not found with id " + id));
        hubRepository.delete(deletedHub);
    }

    public String saveHubToUser(Long userId, Hub hub){

       User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Hub not found with id" + userId));

  /*     try{
           user.getHubs().stream().filter(hub1 -> hub1.)

       }catch(Error error)*/

       user.getHubs().add(hub);

       userRepository.save(user);

       return "User was successfully updated";


    }


}
