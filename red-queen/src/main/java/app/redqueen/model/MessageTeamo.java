package app.redqueen.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Table(name="messages")
@Data
@Entity
public class MessageTeamo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name="body")
    private String body;

    @Column(name="create_date")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private UserTeamo userSender;

    @ManyToOne
    @JoinColumn(name="receiver_id")
    private UserTeamo userReceiver;

    @ManyToOne
    @JoinColumn(name = "bot_phrases_type_id")
    private BotPhraseType botPhraseType;

    public MessageTeamo(){}

    public MessageTeamo(String body,
                        Date createDate)
    {
        this.body = body;
        this.createDate = createDate;
    }

    public MessageTeamo(String body,
                        Date createDate,
                        UserTeamo userSender,
                        UserTeamo userReceiver)
    {
        this.body = body;
        this.createDate = createDate;
        this.userSender = userSender;
        this.userReceiver = userReceiver;
    }
}
