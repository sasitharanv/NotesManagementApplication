package com.notesManagement.demo.Controller;
import com.notesManagement.demo.Dto.JwtAuthResponse;
import com.notesManagement.demo.Dto.LoginDto;
import com.notesManagement.demo.Dto.RegisterDto;
import com.notesManagement.demo.Dto.UserDetailsDto;
import com.notesManagement.demo.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private UserService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Build login api
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);


        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetailsDto> getUserDetails() {

        UserDetailsDto userDetailsDto = authService.getCurrentUserDetails();
        return ResponseEntity.ok(userDetailsDto);

    }
}