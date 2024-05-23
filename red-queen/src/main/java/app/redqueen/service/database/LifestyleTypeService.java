package app.redqueen.service.database;

import app.redqueen.model.LifestyleType;
import app.redqueen.repository.LifestyleRepository;
import app.redqueen.repository.LifestyleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LifestyleTypeService
{
    @Autowired
    LifestyleTypeRepository lifestyleTypeRep;

    public boolean isExistsByLabel(String label)
    {
        return lifestyleTypeRep.findByLabel(label)
                .isPresent();
    }

    public boolean isExistsById(long id)
    {
        return lifestyleTypeRep.findById(id)
                .isPresent();
    }

    public LifestyleType findByLabel(String label)
    {
        return lifestyleTypeRep.findByLabel(label)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format(
                                "LifeStyleType label %s not found",
                                label)
                ));
    }

    public List<LifestyleType> getAll()
    {
        return lifestyleTypeRep.findAll();
    }
}
