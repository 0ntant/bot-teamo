package app.redqueen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="looking_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LookingType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="label")
    private String label;

    @Column(name="title")
    private String title;
}
