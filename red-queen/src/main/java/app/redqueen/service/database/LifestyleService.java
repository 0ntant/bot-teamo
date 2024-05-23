package app.redqueen.service.database;

import app.redqueen.model.Lifestyle;
import app.redqueen.repository.LifestyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LifestyleService
{
    @Autowired
    LifestyleRepository lifestyleRep;

    public boolean isExistsById(long id)
    {
        return lifestyleRep.findById(id)
                .isPresent();
    }

    public Lifestyle findById(long id)
    {
        return lifestyleRep.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Lifestyle id %s doesn't exists", id)
                ));
    }

    public List<Lifestyle> getAll()
    {
        return lifestyleRep.findAll();
    }
}
