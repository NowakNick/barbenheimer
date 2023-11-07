package org.barbenheimer;

import com.google.cloud.storage.*;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Path("/storage")
@RegisterForReflection
public class GSCResource {

    @Inject
    Storage storage;

    @POST
    @Path("test")
    public RestResponse uploadFileToGCS(FileUploadInput input){
        try{
            Bucket bucket = storage.get("barbenheimer");
            bucket.create(input.name, Files.readAllBytes(Paths.get(input.media.filePath().toString())));
            return RestResponse.status(200);
        }catch (Exception e){
            return RestResponse.status(404);
        }

    }

    public byte[] getSingleFileFromGCS(String bucketName, String fileName) {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        if (blob != null) {
            return blob.getContent();
        } else {
            return null;
        }
    }

    public List<Blob> listImageFilesInBucket(String bucketName) {
        Bucket bucket = storage.get(bucketName);
        if (bucket != null) {
            return StreamSupport.stream(bucket.list().getValues().spliterator(), false)
                    .filter(blob -> blob.getName().toLowerCase().endsWith(".jpg") || blob.getName().toLowerCase().endsWith(".png") /* oder andere Bildformate */)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public RestResponse deleteFileFromGCS(String bucketName, String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        boolean deleted = storage.delete(blobId);
        if (deleted) {
            return RestResponse.status(200);
        } else {
            return RestResponse.status(404);
        }
    }

}
