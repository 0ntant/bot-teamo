package app.redqueen.repository;

import app.redqueen.model.GeneralAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralAttributeRepository extends JpaRepository<GeneralAttribute, Long>

{
    @Override
    @Query("""
            SELECT a from GeneralAttribute a
            WHERE a.value=:value
            """)
    Optional<GeneralAttribute> findById(@Param("value")Long aLong);

    @Query("""
            SELECT a from GeneralAttribute a
            WHERE a.name=:name
            """)
    Optional<GeneralAttribute> findByName(@Param("name") String name);

    @Query("""
            SELECT a from GeneralAttribute a
            WHERE a.valueText=:valueText
            """)
    Optional<GeneralAttribute> findByValueText(@Param("valueText") String valueText);

    @Query("""
            SELECT a from GeneralAttribute a
            WHERE a.valueText=:valueText and a.name=:name
            """)
    Optional<GeneralAttribute> findByValueTextAndName(@Param("valueText") String valueText,
                                                      @Param("name") String name);
}
