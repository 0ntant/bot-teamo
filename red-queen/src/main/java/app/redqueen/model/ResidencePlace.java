package app.redqueen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "residence_place")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ResidencePlace
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    String title;

    @Column(name="timezone")
    String timezone;

    @Column(name = "active")
    boolean active;
}
