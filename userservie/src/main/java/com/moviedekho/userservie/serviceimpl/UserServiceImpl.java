package com.moviedekho.userservie.serviceimpl;

import com.moviedekho.userservie.entity.RoleEntity;
import com.moviedekho.userservie.entity.UserEntity;
import com.moviedekho.userservie.exception.UserAlreadyExistsException;
import com.moviedekho.userservie.model.request.UserRequest;
import com.moviedekho.userservie.model.response.UserLoginResponse;
import com.moviedekho.userservie.model.response.UserResponse;
import com.moviedekho.userservie.repository.RoleRepository;
import com.moviedekho.userservie.repository.UserRepository;
import com.moviedekho.userservie.service.UserService;
import com.moviedekho.userservie.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @Override
    public UserResponse register(UserRequest userRequest) throws Exception {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }
        if (userRepository.existsByMobileNumber(userRequest.getMobileNumber())) {
            throw new UserAlreadyExistsException("Mobile number already in use");
        }
        UserEntity userEntity = createUserEntity(userRequest);
        UserEntity entity = userRepository.save(userEntity);

        return createUserResponse(userRequest, entity, userEntity);

    }

    private UserResponse createUserResponse(UserRequest userRequest, UserEntity entity,  UserEntity userEntity) {
        UserResponse response = new UserResponse();
        if (entity != null ){
            response.setMessage("User Registered Successfully");
            response.setGender(userEntity.getGender());
            response.setRole(userRequest.getRoleName().name());
            response.setEmail(userEntity.getEmail());
            response.setCountry(userEntity.getCountry());
            response.setMobileNumber(userEntity.getMobileNumber());
            response.setSubscriptionPlan(userEntity.getSubscriptionPlan());
            response.setUsername(userEntity.getUsername());
            response.setDateOfBirth(userEntity.getDateOfBirth().toString());
        }
        return response;
    }

    private UserEntity createUserEntity(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setCountry(userRequest.getCountry());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setGender(userRequest.getGender());
        userEntity.setDateOfBirth(userRequest.getDateOfBirth());
        userEntity.setSubscriptionPlan(userRequest.getSubscriptionPlan().name());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setMobileNumber(userRequest.getMobileNumber());

        RoleEntity roleEntity = roleRepository.findByName(userRequest.getRoleName())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        userEntity.setRoles(Set.of(roleEntity)); 
        
        userEntity.setAuthorities(userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet()));

        return userEntity;
    }

    @Override
    public UserLoginResponse authenticate(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        String jwtToken = tokenProvider.createToken(userEntity);
        return createUserLoginResponse(jwtToken, userEntity);
    }

    private UserLoginResponse createUserLoginResponse(String jwtToken, UserEntity userEntity) {

        UserLoginResponse loginResponse = new UserLoginResponse();

        loginResponse.setUsername(userEntity.getUsername());
        loginResponse.setToken(jwtToken);
        loginResponse.setMobileNumber(userEntity.getMobileNumber());
        loginResponse.setDateOfBirth(userEntity.getDateOfBirth());
        loginResponse.setMessage("Login Success");

        return loginResponse;
    }
}
