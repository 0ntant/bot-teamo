package app.redqueen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "user_teamo_block")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserTeamoBlock
{
    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserTeamo user;

    @Column(name="is_blocking")
    private Boolean isBlocking;

    @Column(name="reason")
    private String reason;

    @Column(name="teamo_code")
    private Integer teamoCode;

    @Column(name = "block_check_date")
    private Date blockDate;

    public UserTeamoBlock(UserTeamo user, boolean isBlocking, Date blockingDate)
    {
        this.user = user;
        this.isBlocking = isBlocking;
        this.blockDate = blockingDate;
    }

}