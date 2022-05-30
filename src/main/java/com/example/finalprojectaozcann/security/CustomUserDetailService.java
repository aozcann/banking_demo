package com.example.finalprojectaozcann.security;

import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.UserStatus;
import com.example.finalprojectaozcann.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByIdentityNumberAndStatus(Long.parseLong(username), UserStatus.ACTIVE).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new UserDetail(String.valueOf(user.getIdentityNumber()), user.getPassword(), user.getRoles(), user.getStatus(), user.getId());
    }
}
