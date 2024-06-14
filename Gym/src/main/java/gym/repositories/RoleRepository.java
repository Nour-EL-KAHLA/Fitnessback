package gym.repositories;
import java.util.Optional;

import gym.models.ERole;
import gym.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
	Optional<Role> findByName(ERole name);
}
