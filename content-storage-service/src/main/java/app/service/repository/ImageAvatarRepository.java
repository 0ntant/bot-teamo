package app.service.repository;

import app.service.model.ImageAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageAvatarRepository extends JpaRepository<ImageAvatar, Long>
{
    List<ImageAvatar> findByGender(String gender);
}
