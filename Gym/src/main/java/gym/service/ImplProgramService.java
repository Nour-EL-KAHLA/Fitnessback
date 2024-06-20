package gym.service;
import gym.models.Program;
import gym.models.Exercise;
import gym.models.ProgramExercise;
import gym.models.User;
import gym.repositories.ExerciseRepository;
import gym.repositories.ProgramExerciseRepository;
import gym.repositories.ProgramRepository;
import gym.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ImplProgramService implements IprogramService {
    private UserRepository userRepository;
    private ProgramRepository programRepository;
    private ExerciseRepository exerciseRepository;
    private ProgramExerciseRepository programExerciseRepository;

    @Autowired
    public ImplProgramService(ProgramRepository programRepository, UserRepository userRepository,ExerciseRepository exerciseRepository, ProgramExerciseRepository programExerciseRepository) {
        this.programRepository = programRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.programExerciseRepository = programExerciseRepository;
    }

    @Override
    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    @Override
    public Optional<Program> getProgramById(Long id) {
        return programRepository.findById(id);
    }

    @Override
    public Program createProgram(Long userId, Long coachId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User coach = userRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach not found"));

        Program program = new Program(user, coach);
        return programRepository.save(program);
    }

    @Override
    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }

    @Override
    public Program updateProgram(Long id, Program programDetails) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        program.setUser(programDetails.getUser());
        program.setCoach(programDetails.getCoach());
        return programRepository.save(program);
    }

    @Override
    public void addExerciseToProgram(Long programId, Long exerciseId, LocalDate date) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        ProgramExercise programExercise = new ProgramExercise(program, exercise, date);
        programExerciseRepository.save(programExercise);
    }
}
