package gym.service;

import gym.models.Exercise;
import gym.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplExerciseService implements IexerciseService {

    private ExerciseRepository exerciseRepository;

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

    @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}
