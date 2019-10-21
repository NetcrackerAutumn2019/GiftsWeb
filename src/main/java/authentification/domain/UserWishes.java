package authentification.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table
@Data
public class UserWishes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    private String wish;

}
