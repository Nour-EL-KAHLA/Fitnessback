package gym.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "email")
		})
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();


	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Program program;

	private Integer weight;

	private ESexe sexe;


	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String password , ESexe sexe , Integer weight ) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.sexe = sexe;
		this.weight = weight;
	}


}
