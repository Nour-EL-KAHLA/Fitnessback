package gym.service;

import gym.models.Exercise;

import java.util.List;
import java.util.Optional;

public interface IexerciseService {

        List<Exercise> getAllExercises();
        Optional<Exercise> getExerciseById(Long id);
        Exercise createExercise(Exercise exercise);
        void deleteExercise(Long id);

}
