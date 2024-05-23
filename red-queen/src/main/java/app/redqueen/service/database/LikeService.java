package app.redqueen.service.database;

import app.redqueen.model.Like;
import app.redqueen.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LikeService
{
    @Autowired
    LikeRepository likeRep;

    public boolean isExistsById(long id)
    {
        return likeRep.findById(id)
                .isPresent();
    }

    public Like findById(long id)
    {
        return likeRep.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Like id %s doesn't exits", id
                        ))
                );
    }

    public boolean isExitsByNameAndText(String name, String text)
    {
        return likeRep.findByNameAndText(name, text)
                .isPresent();
    }

    public Like findByNameAndText(String name, String text)
    {
        return  likeRep.findByNameAndText(name, text)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Like name %s text %s doesn't exits",
                                name,
                                text)
                ));
    }
}
