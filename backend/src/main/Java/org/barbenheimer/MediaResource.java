package org.barbenheimer;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("")
public class MediaResource {

    @Inject
    MediaService mediaService;

    @Path("getMedia")
    @GET
    public List<Media> getMedia() {
        return mediaService.getMedia();
    }

    @Path("addMedia")
    @POST
    public List<Media> addMedia(Media media) {
        mediaService.addMedia(media);
        return getMedia();
    }
}