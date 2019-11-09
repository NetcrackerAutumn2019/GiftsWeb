package com.nectcracker.studyproject.config;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;;

@Slf4j
@Component
public class AuthProvider implements AuthenticationProvider {

    private final UserService userService;

    public AuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        User user = (User) userService.loadUserByUsername(username);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }


    @Override
    public boolean supports(Class<?> arg) {
        return true;
    }
}
