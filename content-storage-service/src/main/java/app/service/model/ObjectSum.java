package app.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "object_sum")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ObjectSum
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_hash")
    private String objectHash;

    @OneToOne(mappedBy = "objectSum")
    ImageAvatar imageAvatar;
}
