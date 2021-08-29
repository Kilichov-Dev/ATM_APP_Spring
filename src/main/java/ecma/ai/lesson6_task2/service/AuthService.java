package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.payload.LoginDto;
import ecma.ai.lesson6_task2.repository.UserRepository;
import ecma.ai.lesson6_task2.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class  AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse loginForUser(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getNumber(), loginDto.getPinCode()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getFullName(), user.getRole());
            return new ApiResponse("Success!", true, token);
        } catch (Exception e) {
            return new ApiResponse("Username not found!", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByFullName(s);
        if (optionalUser.isPresent()) {
            return (UserDetails) optionalUser.get();
        }
        throw new UsernameNotFoundException(s + " not found!");
    }
}
