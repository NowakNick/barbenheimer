package org.barbenheimer;

import com.google.cloud.storage.*;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;

import java.nio.file.Files;
import java.nio.file.Paths;



@Path("/storage")
@RegisterForReflection
public class GSCService {

    @Inject
    Storage storage;


    public RestResponse uploadFileToGCS(FileUploadInput input){
        try{
            Bucket bucket = storage.get("barbenheimer");
            bucket.create(input.name, Files.readAllBytes(Paths.get(input.media.filePath().toString())));
            return RestResponse.status(200);
        }catch (Exception e){
            return RestResponse.status(404);
        }

    }
    @GET
    @Path("test/{fileName}")
    public String getSingleFileFromGCS(String fileName) {
        Blob blob = storage.get(BlobId.of("barbenheimer", fileName));
        if (blob != null) {
            return "blob.signUrl(15, TimeUnit,Storage.SignUrlOption.withV4Signature()).toString();";
        } else {
            return null;
        }
    }

    @DELETE
    @Path("test/{fileName}")
    public RestResponse deleteFileFromGCS(String fileName) {
        BlobId blobId = BlobId.of("barbenheimer", fileName);
        boolean deleted = storage.delete(blobId);
        if (deleted) {
            return RestResponse.status(200);
        } else {
            return RestResponse.status(404);
        }
    }

}
