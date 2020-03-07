package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    private final UserRepository userRepository;
    private final FlatRepository flatRepository;

    public StatisticsService(UserRepository userRepository, FlatRepository flatRepository) {
        this.userRepository = userRepository;
        this.flatRepository = flatRepository;
    }

    public Long getCountOfActiveFlats(){
        return flatRepository.countActiveFlats();
    }

    public Long getCountOfActiveUsers(){
        return userRepository.countActiveUsers();
    }

}
