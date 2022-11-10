package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.exception.NotFoundException;
import com.example.musicstorecatalog.model.Album;
import com.example.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumRepository albumRepository;

    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Album createNewAlbum(@RequestBody Album album){
        return albumRepository.save(album);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Album> findAllAlbums(){
        List<Album> albumList = albumRepository.findAll();
        return albumList;
    }

    @GetMapping("/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Album> getAlbumByAlbumId(@PathVariable Integer albumId){
        if (albumRepository.findById(albumId).isPresent()){
            return albumRepository.findById(albumId);
        } else{
            throw new NotFoundException("unable to find this album id");
        }
    }

    @PutMapping("/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumByAlbumId(@RequestBody Album album, @PathVariable Integer albumId){
        if (album.getAlbumId().equals(albumId) == false) {

            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + albumId +"; Request body: " + album.getAlbumId());
        }
        album.setAlbumId(albumId);
        albumRepository.save(album);
    }


    @DeleteMapping("/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumByAlbumId(@PathVariable Integer albumId){
        albumRepository.deleteByAlbumId(albumId);
    }



}
