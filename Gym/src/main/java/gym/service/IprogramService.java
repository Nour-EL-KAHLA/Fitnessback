package gym.service;

import gym.models.Program;
import gym.models.ProgramExercise;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IprogramService {
    List<Program> getAllPrograms();
    Optional<Program> getProgramById(Long id);

    Program createProgram( Long userId, Long coachId);
    void deleteProgram(Long id);
    Program updateProgram(Long id, Program programDetails);
    void addExerciseToProgram(Long programId, Long exerciseId, LocalDate date);
    void deleteProgramExerciseFromProgram(Program program, ProgramExercise programExercise);
}
