package org.barbenheimer;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.io.IOException;
import java.util.List;

@Path("")
@RegisterForReflection
public class MediaResource {

    private static final Logger LOG = Logger.getLogger(MediaResource.class);

    @Inject
    MediaService mediaService;

    @GET
    @Path("test")
    public String getTest() {
        LOG.info("tesst");
        System.out.println("helloworld");
        return "hello world";
    }

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
    @Path("deleteMedia/{id}")
    public RestResponse deleteMedia(String id){
        return mediaService.deleteMedia(id);
    }
}


