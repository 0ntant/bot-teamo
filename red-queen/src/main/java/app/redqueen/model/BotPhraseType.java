package app.redqueen.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Table(name="bot_phrases_type")
@Entity
public class BotPhraseType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @OneToMany(mappedBy = "botPhraseType")
    private List<BotPhrase> botPhrases;

    @OneToMany(mappedBy = "botPhraseType", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<MessageTeamo> messageList = new ArrayList<>();
}