package com.songguesser.conector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDto {
    private long trackId;
    private String artistName;
    private String trackName;
    private String collectionName;
    private String primaryGenreName;
    private String releaseDate;
    private String artworkUrl100;
    private String previewUrl;
}
