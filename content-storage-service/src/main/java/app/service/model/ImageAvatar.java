package app.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "t_img_avatar")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageAvatar
{
    @Id
    @Column(name = "object_sum_id")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "gender")
    private String gender;

    @Column(name = "sys_create_date")
    private Date sysCreateDate;

    @Column(name="create_source")
    String createSource;

    @OneToOne
    @MapsId
    @JoinColumn(name = "object_sum_id")
    private ObjectSum objectSum;
}
