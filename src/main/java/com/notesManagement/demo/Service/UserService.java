package com.notesManagement.demo.Service;

import com.notesManagement.demo.Dto.JwtAuthResponse;
import com.notesManagement.demo.Dto.LoginDto;
import com.notesManagement.demo.Dto.RegisterDto;
import com.notesManagement.demo.Dto.UserDetailsDto;
import com.notesManagement.demo.Model.User;

public interface UserService {



    String register(RegisterDto registerDto);


    JwtAuthResponse login(LoginDto loginDto);

    UserDetailsDto getCurrentUserDetails();
}
