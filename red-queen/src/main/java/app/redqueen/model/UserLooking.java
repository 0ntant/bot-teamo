package app.redqueen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="user_looking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserLooking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserTeamo user;

    @ManyToOne
    @JoinColumn(name="looking_type_id")
    private LookingType lookingType;

    @Override
    public String toString()
    {
        return String.format("id=%s, body=%s", this.getId(), this.getBody());
    }
}