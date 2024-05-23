package app.redqueen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "general_attribute")
@Data
@Entity
public class GeneralAttribute
{
    @Id
    @Column(name="value")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer value;

    @Column(name ="name")
    private String name;

    @Column(name="value_text")
    private String valueText;

    @ManyToMany
    @JoinTable(name = "user_teamo_has_general_attribute",
            joinColumns = @JoinColumn(name = "general_attribute_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserTeamo> users;

    public GeneralAttribute(){}

    public GeneralAttribute(String name, String valueText)
    {
        this.name = name;
        // this.value = value;
        this.valueText = valueText;
    }

    @Override
    public String toString(){
        return String.format("value=%s, name=%s", this.value, this.name);
    }

}
