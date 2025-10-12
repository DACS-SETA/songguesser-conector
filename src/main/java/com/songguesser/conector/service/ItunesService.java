package com.songguesser.conector.service;

import com.songguesser.conector.api.client.ItunesClient;
import com.songguesser.conector.dto.ItunesResponse;
import com.songguesser.conector.dto.SongDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ItunesService {

    @Autowired
    private ItunesClient itunesClient;

    private final Random random = new Random();

    public SongDto getRandomSong() {
        String[] genres = {"rock", "pop", "hiphop", "jazz", "classical"};
        String genre = genres[random.nextInt(genres.length)];
        return getRandomSongByGenre(genre);
    }

    public SongDto getRandomSongByGenre(String genre) {
        ItunesResponse response = itunesClient.search(genre, "music", "song", 50);
        if (response != null && !response.getResults().isEmpty()) {
            List<SongDto> songs = response.getResults();
            return songs.get(random.nextInt(songs.size()));
        }
        return null;
    }

    public List<SongDto> getSongsByArtist(String artist) {
        ItunesResponse response = itunesClient.search(artist, "music", "song", 25);
        return response != null ? response.getResults() : List.of();
    }

    public SongDto getSongById(long id) {
        ItunesResponse response = itunesClient.lookup(id);
        if (response != null && !response.getResults().isEmpty()) {
            return response.getResults().get(0);
        }
        return null;
    }

    public List<SongDto> searchSongs(String term) {
        ItunesResponse response = itunesClient.search(term, "music", "song", 25);
        return response != null ? response.getResults() : List.of();
    }
}
