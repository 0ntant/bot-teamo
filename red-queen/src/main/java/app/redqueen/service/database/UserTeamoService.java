package app.redqueen.service.database;

import app.redqueen.model.UserTeamo;
import app.redqueen.repository.UserTeamoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserTeamoService
{
    @Autowired
    UserTeamoRepository userTeamoRepository;

    public List<UserTeamo> findUserWithTokenAndNotBlocking()
    {
        return userTeamoRepository.findUserWithTokenAndNotBlocking();
    }

    public List<UserTeamo> findUserWithToken()
    {
        return userTeamoRepository.findUserWithToken();
    }

    public UserTeamo findById(long id)
    {
        return userTeamoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("User id %s doesn't exists", id)
                ));
    }

    public void save(UserTeamo userToSave)
    {
        userTeamoRepository.save(userToSave);
    }

    public boolean isExistById(long id)
    {
        return userTeamoRepository.findById(id)
                .isPresent();
    }

    public List<UserTeamo> findChatPartners(UserTeamo user)
    {
        return userTeamoRepository.findChatPartners(user);
    }

    public Page<UserTeamo> findAllPages(Pageable pageable)
    {
        return userTeamoRepository.findAll(pageable);
    }

    public List<UserTeamo> getAll()
    {
        return userTeamoRepository.findAll();
    }

    public long getCount()
    {
        return userTeamoRepository.count();
    }

    public List<UserTeamo> findByGender(String gender)
    {
        return userTeamoRepository.findByGender(gender);
    }

    public List<UserTeamo> findBlackListOwners()
    {
        return userTeamoRepository.findBlackListOwners();
    }
}
