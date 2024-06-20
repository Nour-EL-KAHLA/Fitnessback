package gym.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ProgramExercise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  //  @JsonIgnore
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;


    private  LocalDate dayOfWeek;


    public ProgramExercise() {}

    public ProgramExercise(Program program, Exercise exercise,  LocalDate date) {
        this.program = program;
        this.exercise = exercise;
        this.dayOfWeek = date;
    }


}
