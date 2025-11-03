package com.songguesser.conector.controller;

import com.songguesser.conector.dto.SongDto;
import com.songguesser.conector.service.ItunesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/itunes")
public class ItunesController {

    private final ItunesService itunesService;

    public ItunesController(ItunesService itunesService) {
        this.itunesService = itunesService;
    }

    @GetMapping(value = "/random", produces = "application/json")
    public SongDto getRandom() {
        log.info("Fetching random song...");
        return itunesService.getRandomSong();
    }

    @GetMapping("/random/{genre}")
    public SongDto getRandomByGenre(@PathVariable String genre) {
        log.info("Fetching random song for genre: {}", genre);
        return itunesService.getRandomSongByGenre(genre);
    }

    @GetMapping("/artist/{artist}")
    public List<SongDto> getByArtist(@PathVariable String artist) {
        log.info("Fetching songs by artist: {}", artist);
        return itunesService.getSongsByArtist(artist);
    }

    @GetMapping("/id/{id}")
    public SongDto getById(@PathVariable long id) {
        log.info("Fetching song by ID: {}", id);
        return itunesService.getSongById(id);
    }

    @GetMapping("/search")
    public List<SongDto> searchSongs(@RequestParam("term") String term) {
        log.info("Buscando canciones en iTunes para term='{}'", term);
        return itunesService.searchSongs(term);
    }
    
    // ANC: Just for populate database with builded json
    @GetMapping("/popular")
    public Object getPopularSongsRaw() {
        log.info("Fetching popular songs (raw JSON)...");
        return itunesService.getPopularSongsRaw();
    }

}
