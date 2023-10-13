package org.barbenheimer;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.util.List;

@Path("")
public class MediaResource {

    @Inject
    MediaService mediaService;


    @POST
    @Path("addMedia")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addMedia(@MultipartForm FileUploadInput input) throws Exception {
        mediaService.addMedia(input);
    }

    @GET
    @Path("getMedia")
    public List<Media> getMedia() {
        return mediaService.getMedia();
    }
}


