package com.mbs.qlcc.repository.MediaFile;

import com.mbs.qlcc.domain.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMediaFileRepository extends JpaRepository<MediaFile, String> {
    @Query("SELECT m FROM MediaFile m WHERE m.ownerId IN :ownerId AND m.deletedAt IS NULL")
    List<MediaFile> findAllByOwnerId(@Param("ownerId") List<String> ownerId);

    List<MediaFile> findByOwnerTypeAndOwnerIdAndDeletedAtIsNull(String ownerType, String ownerId);

    List<MediaFile> findByOwnerTypeAndOwnerIdInAndDeletedAtIsNull(String ownerType, List<String> ownerIds);

    Optional<MediaFile> findFirstByOwnerTypeAndOwnerIdAndDeletedAtIsNull(String ownerType, String ownerId);
}

