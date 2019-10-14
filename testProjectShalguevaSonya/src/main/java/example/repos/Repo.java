package example.repos;

import example.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface Repo extends CrudRepository<User, Integer> {
}
