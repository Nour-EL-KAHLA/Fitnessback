package gym.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@Entity
public class Program implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


   // @JsonBackReference
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

   // @JsonBackReference
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private User coach;

    //@JsonIgnore
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<ProgramExercise> programExercises;


    public Program() {}

    public Program(User user, User coach) {

        this.user = user;
        this.coach = coach;
    }


}
