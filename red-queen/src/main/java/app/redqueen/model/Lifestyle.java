package app.redqueen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="lifestyle")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Lifestyle
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserTeamo user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="lifestyle_type_id")
    private LifestyleType lifestyleType;

    @Override
    public String toString()
    {
        return String.format(
                "id=%s, body=%s",
                this.getId(),
                this.getBody()
        );
    }
}
