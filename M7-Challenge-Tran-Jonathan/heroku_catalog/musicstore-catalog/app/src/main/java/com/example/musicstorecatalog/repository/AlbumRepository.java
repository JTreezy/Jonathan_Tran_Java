package com.example.musicstorecatalog.repository;

import com.example.musicstorecatalog.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByTitle(String string);
    void deleteByAlbumId(Integer albumId);

}
