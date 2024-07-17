package app.redqueen.service.database;

import app.redqueen.model.ResidencePlace;
import app.redqueen.repository.ResidencePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidencePlaceService
{
    @Autowired
    ResidencePlaceRepository resRepository;

    public List<ResidencePlace> findActive()
    {
        return resRepository.findByActiveTrue();
    }
}
