package org.barbenheimer;

import com.google.cloud.firestore.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class MediaService {

    @Inject
    Firestore firestore;
    @Inject
    GSCService gscService;

    public List<Media> getFirestoreMedia() throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot2 = getCollection().get().get();
        List<Media> list = new ArrayList<>();

        for (QueryDocumentSnapshot document : querySnapshot2.getDocuments()) {
            Media media = documentToMedia(document);
            list.add(media);
        }
        return list;
    }

    public List<Media> getSingleMedia(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot querySnapshot = getCollection().document(id).get().get();
        if(querySnapshot.getData() != null) {
            List<Media> list = new ArrayList<>();
            Media media = documentToMedia(querySnapshot);
            list.add(media);
            return list;
        }
        return null;
    }

    private Media documentToMedia(DocumentSnapshot document) {
        Media media = new Media();
        String imgString = Base64.getEncoder().encodeToString(gscService.getSingleFileFromGCS(document.getString("name")));
        media.setId(document.getId());
        media.setName(document.getString("name"));
        media.setDate(document.getString("date"));
        media.setContentType(document.getString("content-type"));
        media.setMediaName(document.getString("media-name"));
        media.setMedia(imgString);
        media.setTags((List<Long>) document.get("tags"));
        return media;
    }

    public RestResponse addMedia(FileUploadInput input) throws ExecutionException, InterruptedException {
        if (input.media != null) {
            gscService.uploadFileToGCS(input);
        } else {
            return RestResponse.status(400);
        }

        Document document = new Document()
                .append("name", input.name)
                .append("date", input.date)
                .append("content-type", input.media.contentType())
                .append("media-name", input.media.fileName())
                .append("tags", input.tags);

        String objectIdFirestore = getCollection().add(document).get().getId();
        document.append("id", objectIdFirestore);

        return RestResponse.ok(document);
    }

    public RestResponse deleteMedia(String id) {
        try {
            gscService.deleteFileFromGCS(getSingleMedia(id).get(0).getName());
            getCollection().document(id).delete();
            return RestResponse.status(200);
        } catch (Exception e) {
            return RestResponse.status(400);
        }
    }

    private CollectionReference getCollection(){
        return firestore.collection("barbenheimer");
    }
}
