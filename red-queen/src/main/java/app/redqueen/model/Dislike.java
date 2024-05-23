package app.redqueen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "t_dislikes")
@Data
@Entity
public class Dislike
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "f_type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name="text")
    private String text;

    @ManyToMany
    @JoinTable(name = "user_teamo_has_t_dislikes",
            joinColumns = @JoinColumn(name="t_dislikes_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserTeamo> users;

    public Dislike(){}

    public Dislike( String type, String name, String text)
    {
        this.type = type;
        this.name = name;
        this.text = text;
    }
}
