package com.example.mvc.controllers;
import com.example.mvc.entity.User;
import com.example.mvc.repositorys.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/User")
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/GetAll")
    ResponseEntity<String> GetAll(){
        StringBuilder stringBuilder=new StringBuilder();
        for (User user : userRepository.findAll()) {
            stringBuilder.append(user.getLogin()).append('\n');

        }
        return ResponseEntity.ok(stringBuilder.toString());
    }

    @PostMapping("/Create")
    ResponseEntity<String> Create(@Valid @RequestBody User user){
            userRepository.save(user);
            return ResponseEntity.ok("You have successfully completed registration");

    }

    @DeleteMapping("/Delete")
    ResponseEntity<String> DeleteUser( @RequestBody User user){
        if(userRepository.deleteByEmailAndLogin(user.getEmail(), user.getLogin())==1)
            return ResponseEntity.ok("User deleted successfully");
        else return ResponseEntity.badRequest().body("User with this login does not exist");
    }

    @PutMapping("/Update")
    ResponseEntity<String> UpdateUser( @RequestBody User user){
        if(userRepository.updateUserEmailByLogin(user.getEmail(), user.getLogin())==1)
            return ResponseEntity.ok("Your email has been updated");
         else return ResponseEntity.badRequest().body("User with this login does not exist");
    }
}

