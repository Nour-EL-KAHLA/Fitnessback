package gym.controller;

import gym.models.Exercise;
import gym.service.IexerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    IexerciseService exerciseService;


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        Exercise exercise = exerciseService.getExerciseById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + id));
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PostMapping("/addexercise")
    @PreAuthorize(" hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.createExercise(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(" hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
