package app.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "t_text_data")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TextData
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="text_content")
    String textContent;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "text_data_has_tag",
            joinColumns = @JoinColumn(name="text_data_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<DataTag> tags;

    @Column(name="sys_create_date")
    LocalDateTime sysCreateDate;

    @Column(name="create_source")
    String createSource;
}
