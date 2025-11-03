package com.songguesser.conector.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songguesser.conector.api.client.ItunesClient;
import com.songguesser.conector.dto.ItunesResponse;
import com.songguesser.conector.dto.SongDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
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

    public Object getPopularSongsRaw() {
        String[] queries = {
                "Taylor Swift", "The Weeknd", "Bad Bunny", "Drake", "Adele",
                "Ed Sheeran", "Beyonce", "Coldplay", "Billie Eilish", "Bruno Mars",
                "Rihanna", "Post Malone", "Eminem", "Shakira", "Imagine Dragons"
        };

        List<Map<String, Object>> results = new ArrayList<>();

        for (String artist : queries) {
            try {
                String url = String.format(
                        "https://itunes.apple.com/search?term=%s&media=music&entity=song&limit=5",
                        URLEncoder.encode(artist, StandardCharsets.UTF_8)
                );

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> json = mapper.readValue(response.body(), Map.class);

                results.add(json);

                log.info("Fetched {} songs for artist {}", json.get("resultCount"), artist);
            } catch (Exception e) {
                log.error("Error fetching songs for artist {}: {}", artist, e.getMessage());
            }
        }

        return results;
    }
}
