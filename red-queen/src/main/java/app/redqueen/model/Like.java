package app.redqueen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "t_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Like
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "f_type")
    private String type;

    @Column(name="name")
    private String name;

    @Column(name="text")

    private String text;
    @ManyToMany
    @JoinTable(name = "user_teamo_has_t_like",
            joinColumns = @JoinColumn(name = "t_like_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserTeamo> users;

    public Like(String type, String name, String text)
    {
        this.type = type;
        this.name = name;
        this.text = text;
    }

    public String toString()
    {
        return "id=" + id + " type=" + type + " name=" + name + " text=" + text;
    }
}