package gym.service;

import gym.models.Exercise;
import gym.models.ProgramExercise;
import gym.repositories.ExerciseRepository;
import gym.repositories.ProgramExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImplExerciseService implements IexerciseService {

    private ExerciseRepository exerciseRepository;
    @Autowired
    private ProgramExerciseRepository programExerciseRepository;

    @Autowired
    public ImplExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

  /*  @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }*/
    @Transactional
    public void deleteExercise(Long id) {
        // Find all ProgramExercise entities associated with this Exercise
        List<ProgramExercise> programExercises = programExerciseRepository.findByExerciseId(id);

        // Delete all ProgramExercise entities associated with this Exercise
        for (ProgramExercise programExercise : programExercises) {
            programExerciseRepository.delete(programExercise);
        }

        // Delete the Exercise itself
        exerciseRepository.deleteById(id);
    }
}

