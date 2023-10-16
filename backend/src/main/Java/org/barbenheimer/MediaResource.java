package org.barbenheimer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.io.IOException;
import java.util.List;

@Path("")
public class MediaResource {

    @Inject
    MediaService mediaService;

    @POST
    @Path("addMedia")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResponse addMedia(FileUploadInput input) throws IOException {
        return mediaService.addMedia(input);
    }

    @GET
    @Path("getMedia")
    public List<Media> getMedia() {
        return mediaService.getMedia();
    }

    @GET
    @Path("getSingleMedia/{id}")
    public List<Media> getSingleMedia(String id) {
        return mediaService.getSingleMedia(id);
    }

    @DELETE
    @Path("deleteMedia")
    public RestResponse deleteMedia(String id){
        return mediaService.deleteMedia(id);
    }
}


