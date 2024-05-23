package app.redqueen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name="lifestyle_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LifestyleType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="label")
    private String label;

    @OneToMany(mappedBy = "lifestyleType")
    private List<Lifestyle> lifestyleList;
}

