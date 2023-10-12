package org.barbenheimer;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/media")
public class MediaResource {

    @Inject
    MediaService mediaService;

    @GET
    public List<Media> list() {
        return mediaService.list();
    }

    @POST
    public List<Media> add(Media media) {
        mediaService.add(media);
        return list();
    }
}