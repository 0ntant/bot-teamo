package app.redqueen.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "photo")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Photo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="url")
    private String teamoUrl;

    @Column(name="f_path")
    private String path;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTeamo user;

    public Photo(long id, String teamoUrl, UserTeamo user)
    {
        this.id = id;
        this.teamoUrl = teamoUrl;
        this.user = user;
    }

    public Photo(long id, String teamoUrl, String path)
    {
        this.id = id;
        this.teamoUrl = teamoUrl;
        this.path = path;
    }
}