package gym.service;


import gym.models.Program;
import gym.models.ProgramExercise;

import java.util.List;
import java.util.Optional;

public interface IprogramExerciseService
{
    List<ProgramExercise> getAllProgramExercises();
    Optional<ProgramExercise> getProgramExerciseById(Long id);

    void deleteProgramExerciseFromProgram(Program program, ProgramExercise programExercise) ;

}
