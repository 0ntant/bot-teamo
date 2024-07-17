package app.redqueen.repository;

import app.redqueen.model.UserTeamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserTeamoRepository extends JpaRepository<UserTeamo, Long>
{
    @Query("""
            SELECT u FROM UserTeamo u
                LEFT JOIN u.messagesToThisUser as s
                LEFT JOIN u.messagesFromThisUser as m
            WHERE (m.userReceiver=:user OR s.userSender=:user) AND u!=:user AND u.block.isBlocking!=true
            GROUP BY u.id
            """)
    List<UserTeamo> findChatPartners(@Param("user") UserTeamo user);

    @Query("""
            SELECT u FROM UserTeamo u
            WHERE size(u.blackList) != 0
            """)
    List<UserTeamo> findBlackListOwners();

    @Query("""
             SELECT u FROM UserTeamo u
             WHERE u.token is not null and u.block.isBlocking!=true
            """)
    List<UserTeamo> findUserWithTokenAndNotBlocking();

    @Query("""
             SELECT u FROM UserTeamo u
             WHERE u.token is not null
            """)
    List<UserTeamo> findUserWithToken();

    Page<UserTeamo> findAll(Pageable pageable);

    List<UserTeamo> findByGender(String gender);

    @Query("""
            SELECT u FROM UserTeamo u
            WHERE u.token is not null and u.block.isBlocking!=true and u.city=:city
            """)
    List<UserTeamo> findUserWithTokenAndCity(@Param("city") String city);
}
