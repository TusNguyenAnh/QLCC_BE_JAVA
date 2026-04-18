package com.mbs.qlcc.adapters.db.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaMediaFileRepository extends JpaRepository<MediaFileDataMapper, String> {
    @Query("SELECT m FROM MediaFileDataMapper m WHERE m.ownerId = :ownerId AND m.deletedAt IS NULL")
    List<MediaFileDataMapper> findAllByOwnerId(@Param("ownerId") String ownerId);
}
