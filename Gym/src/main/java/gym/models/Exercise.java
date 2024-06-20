package gym.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@Entity
public class Exercise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ElementCollection
    private List<String> photos;

    @ElementCollection
    private List<String> videos;

    // Constructors
    public Exercise() {}

    public Exercise(String name, String description, List<String> photos, List<String> videos) {
        this.name = name;
        this.description = description;
        this.photos = photos;
        this.videos = videos;
    }


}
