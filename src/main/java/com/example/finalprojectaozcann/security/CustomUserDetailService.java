package com.example.finalprojectaozcann.security;

import com.example.finalprojectaozcann.model.entity.Customer;
import com.example.finalprojectaozcann.model.enums.CustomerStatus;
import com.example.finalprojectaozcann.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByIdentityNumberAndStatus(Long.parseLong(username), CustomerStatus.ACTIVE).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new UserDetail(String.valueOf(customer.getIdentityNumber()), customer.getPassword(), customer.getRoles(), customer.getStatus());
    }
}
