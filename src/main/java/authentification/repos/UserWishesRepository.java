package authentification.repos;

import authentification.domain.UserWishes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWishesRepository extends JpaRepository<UserWishes,Long> {
}
