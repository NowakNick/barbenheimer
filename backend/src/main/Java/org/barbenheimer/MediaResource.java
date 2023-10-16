package org.barbenheimer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.io.IOException;
import java.util.List;

@Path("")
public class MediaResource {

    @Inject
    MediaService mediaService;

    @POST
    @Path("addMedia")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ResponseStatus(200)
    public void addMedia(FileUploadInput input) throws IOException {
        mediaService.addMedia(input);
    }

    @GET
    @Path("getMedia")
    public List<Media> getMedia() {
        return mediaService.getMedia();
    }

    @DELETE
    @Path("deleteMedia")
    public boolean deleteMedia(String id){
        return mediaService.deleteMedia(id);
    }
}


