package org.barbenheimer;

import com.google.cloud.storage.*;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class GSCResource {

    @Inject
    Storage storage;

    public RestResponse uploadFileToGCS(String bucketName, String localFilePath, String gcsFileName){
        try{
            Bucket bucket = storage.get(bucketName);
            bucket.create(gcsFileName, Files.readAllBytes(Paths.get(localFilePath)));
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
