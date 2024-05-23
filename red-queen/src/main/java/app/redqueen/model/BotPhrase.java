package app.redqueen.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Table(name="bot_phrases")
@Entity
public class BotPhrase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="body")
    private String body;

    @Column(name="gender")
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "f_type_id")
    private BotPhraseType botPhraseType;
}
