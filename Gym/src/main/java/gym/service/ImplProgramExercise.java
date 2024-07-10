package gym.service;

import gym.models.Program;
import gym.models.ProgramExercise;
import gym.repositories.ExerciseRepository;
import gym.repositories.ProgramExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImplProgramExercise implements IprogramExerciseService {
    private ProgramExerciseRepository programExerciseRepository;
    @Autowired
    public ImplProgramExercise(ProgramExerciseRepository programExerciseRepository) {
        this.programExerciseRepository = programExerciseRepository;
    }

    @Override
    public List<ProgramExercise> getAllProgramExercises() {
        return programExerciseRepository.findAll();
    }

    @Override
    public Optional<ProgramExercise> getProgramExerciseById(Long id) {
        return programExerciseRepository.findById(id);
    }
    @Transactional
    public void deleteProgramExerciseFromProgram(Program program, ProgramExercise programExercise) {
        if (programExercise.getProgram().equals(program)) {
            programExerciseRepository.delete(programExercise);
        } else {
            throw new IllegalArgumentException("The provided ProgramExercise does not belong to the specified Program.");
        }
    }

}
