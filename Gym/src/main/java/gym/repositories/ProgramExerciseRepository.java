package gym.repositories;

import gym.models.ProgramExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramExerciseRepository extends JpaRepository<ProgramExercise, Long> {
    List<ProgramExercise> findByExerciseId(Long exerciseId);
}
