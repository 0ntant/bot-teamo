package app.redqueen.service.database;

import app.redqueen.model.Dislike;
import app.redqueen.repository.DislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DislikeService
{
    @Autowired
    DislikeRepository dislikeRep;

    public boolean isExistsById(long id)
    {
        return dislikeRep.findById(id)
                .isPresent();
    }

    public boolean isExitsByNameAndText(String name, String text)
    {
        return dislikeRep.findByNameAndText(name, text)
                .isPresent();
    }

    public Dislike findByNameAndText(String name, String text)
    {
        return dislikeRep.findByNameAndText(name, text)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Dislike name %s text %s doesn't exists",
                                name,
                                text
                        )
                ));
    }

    public Dislike findById(long id)
    {
        return dislikeRep.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Dislike id %s doesn't exits", id)
                ));
    }
}
