package com.CampProject.CampServer.services.auth;

import com.CampProject.CampServer.dto.SignupRequest;
import com.CampProject.CampServer.dto.UserDto;
import com.CampProject.CampServer.entity.User;
import com.CampProject.CampServer.enums.UserRole;
import com.CampProject.CampServer.repository.UserRepository;
import com.CampProject.CampServer.utill.jwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final jwtUtil jwtUtil;


    @PostConstruct
    private  void createAnAdminAccount(){
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){

            User user= new User();
            user.setEmail("admin@test.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created successfully");
        }else{
            System.out.println("Admin account already exist");
        }
    }

    public UserDto createUser(SignupRequest signupRequest){
        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()){

            throw new EntityExistsException("User Already Present With email" + signupRequest.getEmail());
        }
        User user= new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setUserRole(UserRole.RECEPTION);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUSer = userRepository.save(user);
        System.out.println("PH"+ createdUSer);
        return createdUSer.getUserDto();


    }
}
