package app.redqueen.integration.rest.url;

import org.springframework.stereotype.Component;

@Component
public class FacesAPI
{
    private String matches;
    private String saveMatchVote;

    public FacesAPI()
    {
        this.matches = "https://api.teamo.ru/faces.getMatches";
        this.saveMatchVote = "https://api.teamo.ru/faces.saveMatchVote";
    }

    public String getMatches() {
        return matches;
    }

    public String getSaveMatchVote() {
        return saveMatchVote;
    }
}
