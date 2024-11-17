package com.CampProject.CampServer.services.auth;

import com.CampProject.CampServer.dto.SignupRequest;
import com.CampProject.CampServer.dto.UserDto;
import org.springframework.stereotype.Service;


public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
