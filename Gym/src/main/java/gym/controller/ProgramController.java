package gym.controller;

import gym.models.Program;

import gym.models.ProgramExercise;
import gym.models.User;
import gym.service.IprogramExerciseService;
import gym.service.IprogramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/program")
public class ProgramController {
    @Autowired
    private IprogramService programService;

    @Autowired
    private IprogramExerciseService programExerciseService;
    @GetMapping
    @PreAuthorize(" hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<List<Program>> getAllPrograms() {
        List<Program> programs = programService.getAllPrograms();
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Program> getProgramById(@PathVariable Long id) {
        Program program = programService.getProgramById(id)
                .orElseThrow(() -> new RuntimeException("Program not found with id: " + id));
        return new ResponseEntity<>(program, HttpStatus.OK);
    }

    @PostMapping("/create/{userId}/{coachId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Program> createProgram(@PathVariable Long userId, @PathVariable Long coachId) {
        Program createdProgram = programService.createProgram(userId, coachId);
        return new ResponseEntity<>(createdProgram, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Program programDetails) {
        Program updatedProgram = programService.updateProgram(id, programDetails);
        return new ResponseEntity<>(updatedProgram, HttpStatus.OK);
    }

    @PostMapping("/{programId}/exercises/{exerciseId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ResponseEntity<Void> addExerciseToProgram(@PathVariable Long programId,
                                                     @PathVariable Long exerciseId,
                                                     @RequestParam("dayOfWeek")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dayOfWeek) {

        if (dayOfWeek == null) {
            return ResponseEntity.badRequest().build();
        }

        programService.addExerciseToProgram(programId, exerciseId, dayOfWeek);

        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @DeleteMapping("/{programId}/exercises/{programExerciseId}")
    @PreAuthorize("hasRole('COACH') or hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProgramExerciseFromProgram(
            @PathVariable Long programId,
            @PathVariable Long programExerciseId
    ) {
        Optional<Program> optionalProgram = programService.getProgramById(programId);
        Optional<ProgramExercise> optionalProgramExercise = programExerciseService.getProgramExerciseById(programExerciseId);

        if (!optionalProgram.isPresent() || !optionalProgramExercise.isPresent() || !optionalProgramExercise.get().getProgram().equals(optionalProgram.get())) {
            return ResponseEntity.notFound().build();
        }

        programExerciseService.deleteProgramExerciseFromProgram(optionalProgram.get(), optionalProgramExercise.get());
        return ResponseEntity.noContent().build();
}}
