package gym.service;

import gym.models.ERole;
import gym.models.Program;
import gym.models.Role;
import gym.models.User;
import gym.payload.request.SignupRequest;
import gym.payload.response.MessageResponse;
import gym.repositories.ProgramRepository;
import gym.repositories.RoleRepository;
import gym.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImplUserService implements IuserService {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }



    @Override
    public ResponseEntity<User> updateUser(Long id, SignupRequest signupRequest) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no user with this id" + id));;


        System.out.println(user.getRoles());
        if(signupRequest.getUsername()!=null){user.setUsername(signupRequest.getUsername());}
        if(signupRequest.getEmail()!=null){
        user.setEmail(signupRequest.getEmail());}
        if(signupRequest.getWeight()!=null){user.setWeight(signupRequest.getWeight());}
        if(signupRequest.getSexe()!=null){user.setSexe(signupRequest.getSexe());}

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {

            for(Role r:user.getRoles()){
                roles.add(r);
               }
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role n'existe pas."));
                        roles.add(userRole);
                        break;
                    case "coach":
                        Role coachRole = roleRepository.findByName(ERole.ROLE_COACH)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role n'existe pas."));
                        roles.add(coachRole);
                        break;
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role n'existe pas."));
                        roles.add(adminRole);
                }
            });}
        user.setRoles(roles);
        System.out.println(signupRequest.getRole());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public Map<String, Boolean> deleteUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("There is no user with this id " + userId));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("User deleted", Boolean.TRUE);
        return response;
    }

    @Override
    public ResponseEntity<?> createUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(" Email already exists !"));
        }


        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),

                encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRole();

        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Erreur : Role doesn't exists !"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Erreur : Role doesn't exists !"));
                        roles.add(userRole);
                        break;

                    case "coach":
                        Role coachRole = roleRepository.findByName(ERole.ROLE_COACH)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role n'existe pas."));
                        roles.add(coachRole);
                        break;
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role n'existe pas."));
                        roles.add(adminRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        //if a want a program auto when a user is added
     /*  User savedUser = userRepository.save(user);
        Program defaultProgram = new Program(savedUser);
        programRepository.save(defaultProgram);*/

        return ResponseEntity.ok(new MessageResponse("User is created with success !"));
    }

    @Override
    public Optional<User> getSearchUserEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    @Override
    public List<User> findbyCoachId(Long id) {
        // Stream
        List<User> users = userRepository.findAll();

        return users.stream()
                .filter(user -> {
                    Program program = user.getProgram();
                    return program != null && program.getCoach() != null && program.getCoach().getId().equals(id);
                })
                .collect(Collectors.toList());
    }



    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
