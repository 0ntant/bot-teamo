package app.redqueen.service.database;

import app.redqueen.model.GeneralAttribute;
import app.redqueen.repository.GeneralAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GeneralAttributeService
{
    @Autowired
    GeneralAttributeRepository generalAttributeRep;

    public boolean isExistsById(long id)
    {
        return generalAttributeRep.findById(id)
                .isPresent();
    }

    public boolean isExistsByName(String name)
    {
        return generalAttributeRep.findByName(name)
                .isPresent();
    }

    public boolean isExistsByValueText(String valueText)
    {
        return generalAttributeRep.findByValueText(valueText)
                .isPresent();
    }

    public boolean isExistsByValueTextAndName(String valueText, String name)
    {
        return generalAttributeRep.findByValueTextAndName(valueText, name)
                .isPresent();
    }


    public GeneralAttribute findById(long id)
    {
        return generalAttributeRep.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(
                        "GeneralAttribute id %s not exists", id)
                ));
    }

    public GeneralAttribute findByName(String name)
    {
        return generalAttributeRep.findByName(name)
                .orElseThrow(() -> new NoSuchElementException(String.format(
                        "GeneralAttribute name %s not exists", name)
                ));
    }

    public GeneralAttribute findByValueText(String valueText)
    {
        return generalAttributeRep.findByValueText(valueText)
                .orElseThrow(() -> new NoSuchElementException(String.format(
                        "GeneralAttribute valueText %s not exists", valueText)
                ));
    }

    public GeneralAttribute findByValueTextAndName( String valueText, String name)
    {
        return generalAttributeRep.findByValueTextAndName(valueText, name)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("GeneralAttribute valueText %s name %s doesn't exits",
                                valueText,
                                name
                        )
                ));
    }

}
