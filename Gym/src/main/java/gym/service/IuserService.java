package gym.service;

import gym.models.User;
import gym.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IuserService {

    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    ResponseEntity<User> updateUser(Long id, SignupRequest signupRequest) throws Exception;
    Map<String, Boolean> deleteUser(Long userId) throws Exception;
    ResponseEntity<?> createUser(SignupRequest signUpRequest) ;
    User getSearchUserEmail(String email) ;
    User addUser(User user);
    Optional<User> getUser (Long id);
}
