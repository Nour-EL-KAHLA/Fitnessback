package gym.controller;

import gym.models.Program;
import gym.models.ProgramExercise;
import gym.service.IprogramExerciseService;
import gym.service.IprogramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/programex")
public class ProgramExerciseController {
    @Autowired
    private IprogramExerciseService programExerciseService;

    @GetMapping
    @PreAuthorize(" hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<List<ProgramExercise>> getAllPrograms() {
        List<ProgramExercise> ProgramExercise = programExerciseService.getAllProgramExercises();
        return new ResponseEntity<>(ProgramExercise, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<ProgramExercise> getProgramById(@PathVariable Long id) {
        ProgramExercise ProgramExercise = programExerciseService.getProgramExerciseById(id)
                .orElseThrow(() -> new RuntimeException("Program not found with id: " + id));
        return new ResponseEntity<>(ProgramExercise, HttpStatus.OK);
    }
}
