package com.mbs.qlcc.repository.MediaFile;
import com.mbs.qlcc.domain.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMediaFileRepository extends JpaRepository<MediaFile, String> {
    @Query("SELECT m FROM MediaFile m WHERE m.ownerId = :ownerId AND m.deletedAt IS NULL")
    List<MediaFile> findAllByOwnerId(@Param("ownerId") String ownerId);
}
