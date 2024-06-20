package gym.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProgramDTO {
    private Long id;
    private String coachName;
    private String userName;
    private List<ProgramExerciseDTO> programExercises;

    // Getters and setters
}
