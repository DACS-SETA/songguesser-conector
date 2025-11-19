package com.songguesser.conector.api.client;

import com.songguesser.conector.dto.ItunesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "itunesClient",
        url = "https://itunes.apple.com",
        configuration = com.songguesser.conector.config.FeignConfig.class
)
public interface ItunesClient {

    @GetMapping("/search")
    ItunesResponse search(
            @RequestParam("term") String term,
            @RequestParam(value = "media", defaultValue = "music") String media,
            @RequestParam(value = "entity", defaultValue = "song") String entity,
            @RequestParam(value = "limit", defaultValue = "50") int limit
    );

    @GetMapping("/lookup")
    ItunesResponse lookup(@RequestParam("id") long id);


}
