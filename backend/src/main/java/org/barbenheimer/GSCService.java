package org.barbenheimer;

import com.google.cloud.storage.*;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
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
            return RestResponse.ok();
        }catch (Exception e){
            return RestResponse.status(404);
        }

    }
    @GET
    @Path("test/{fileName}")
    public byte[] getSingleFileFromGCS(String fileName) {
        Blob blob = storage.get(BlobId.of("barbenheimer", fileName));
        if (blob != null) {
            return blob.getContent();
        } else {
            return null;
        }
    }

    public RestResponse deleteFileFromGCS(String fileName) {
        BlobId blobId = BlobId.of("barbenheimer", fileName);
        boolean deleted = storage.delete(blobId);
        if (deleted) {
            return RestResponse.ok();
        } else {
            return RestResponse.status(404);
        }
    }

}
