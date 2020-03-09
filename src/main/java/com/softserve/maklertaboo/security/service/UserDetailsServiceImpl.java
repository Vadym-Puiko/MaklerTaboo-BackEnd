package com.softserve.maklertaboo.security.service;

import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.entity.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            return UserDetailsImpl.create(user);
        }else {
                throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }
}
