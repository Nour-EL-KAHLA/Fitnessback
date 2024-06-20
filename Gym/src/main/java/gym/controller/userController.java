package gym.controller;

import gym.models.User;
import gym.payload.request.SignupRequest;
import gym.service.IuserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@AllArgsConstructor
@RequestMapping("/usermanagement")
public class userController {
    @Autowired
    IuserService userService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        List<User> user = userService.getAllUsers();
        return user;
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COACH') or hasRole('ROLE_USER')")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/adduser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {
        System.out.println(signUpRequest);
        return userService.createUser(signUpRequest);
    }
    @DeleteMapping("/deleteuser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println(id + "delete user");
        userService.deleteUser(id);
    }
    @PutMapping("/updateuser/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_COACH')")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody SignupRequest signupRequest) throws Exception {
        if(signupRequest!= null) {
            return userService.updateUser(id,signupRequest);}
        else return null ;}
    @GetMapping("/searchByUserEmail/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUserByEmail(@PathVariable(value="email") String email) {
        return userService.getSearchUserEmail(email);
    }
}
