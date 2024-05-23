package app.redqueen.repository;

import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageTeamoRepository extends JpaRepository<MessageTeamo, Long>
{
    @Query("""
            SELECT m from MessageTeamo m
            WHERE
                (m.userSender = :userBotParam and m.userReceiver = :userTeamoParam )
            OR
                (m.userSender = :userTeamoParam and m.userReceiver = :userBotParam )
            ORDER BY m.createDate ASC
            """)
    List<MessageTeamo> findChat(@Param("userTeamoParam") UserTeamo userBot,
                                @Param("userBotParam") UserTeamo userTeamo);

    @Query("""
              SELECT m from MessageTeamo m
              WHERE m.body = :body and m.createDate = :createDate
            """)
    Optional<MessageTeamo> findByBodyAndCreateDate(@Param("body") String body,
                                                   @Param("createDate") Date createDate);

    @Query("""
            SELECT m from MessageTeamo m
            WHERE
                m.userSender = :userTeamo or m.userReceiver = :userTeamo
            ORDER BY m.createDate ASC
            """)
    List<MessageTeamo> findAllMessagesByUser(@Param("userTeamo") UserTeamo userTeamo);

    @Query("""
            SELECT m from MessageTeamo m
            WHERE
                m.userSender = :userTeamo or m.userReceiver = :userTeamo
            ORDER BY m.createDate DESC
            LIMIT 1
            """)
    Optional<MessageTeamo> findLastMessageByUser(@Param("userTeamo") UserTeamo userTeamo);

    Page<MessageTeamo> findAll(Pageable pageable);
}
