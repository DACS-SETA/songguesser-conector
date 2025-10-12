package com.songguesser.conector.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItunesResponse {
    private int resultCount;
    private List<SongDto> results;
}
