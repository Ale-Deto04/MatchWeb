package org.partiteweb.matchweb.services;

import org.partiteweb.matchweb.classes.myUsers.InfoUser;
import org.partiteweb.matchweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminActionService {

    private final UserRepository userRepository;

    @Autowired
    public AdminActionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<InfoUser> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void promoteUser(String username) {
        userRepository.promoteUser(username);
    }
}
