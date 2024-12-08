package app.redqueen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dislike dislike = (Dislike) o;
        return Objects.equals(text, dislike.text)
                && Objects.equals(name, dislike.name) ;
    }
}
