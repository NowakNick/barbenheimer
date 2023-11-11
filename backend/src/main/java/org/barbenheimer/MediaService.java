package org.barbenheimer;

import com.google.cloud.firestore.*;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class MediaService {

    @Inject
    Firestore firestore;
    //MongoDB
    @Inject
    MongoClient mongoClient;
    @Inject
    GSCService gscService;

    public List<Media> getFirestoreMedia() throws ExecutionException, InterruptedException {
        CollectionReference mediaCollection = firestore.collection("barbenheimer");
        QuerySnapshot querySnapshot2 = mediaCollection.get().get();
        List<Media> list = new ArrayList<>();

        for (QueryDocumentSnapshot document : querySnapshot2.getDocuments()) {
            Media media = documentToMedia(document);
            list.add(media);
        }
        return list;
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

    public List<Media> getMedia() {
        List<Media> list = new ArrayList<>();
        //MongoDB
        MongoCursor<Document> cursor = getCollection().find().iterator();

        createDocumentList(cursor, list);
        return list;
    }

    public List<Media> getSingleMedia(String id) {
        //MongoDB
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        List<Media> list = new ArrayList<>();
        //MongoDB
        MongoCursor<Document> cursor = getCollection().find(query).iterator();

        createDocumentList(cursor, list);

        return list;
    }

    public RestResponse addMedia(FileUploadInput input){
        String encodedString;
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
        //MongoDB
        InsertOneResult insertId = getCollection().insertOne(document);
        document.append("id", insertId.getInsertedId().asObjectId().getValue().toString());

        return RestResponse.ok(document);
    }

    private void createDocumentList(MongoCursor<Document> cursor, List<Media> list) {
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Media media = new Media();
                String imgString = Base64.getEncoder().encodeToString(gscService.getSingleFileFromGCS(document.getString("name")));
                media.setName(document.getString("name"));
                media.setId(document.get("_id").toString());
                media.setDate(document.getString("date"));
                media.setContentType(document.getString("content-type"));
                media.setMediaName(document.getString("media-name"));
                media.setMedia(imgString);
                media.setTags((List<Long>) document.get("tags"));
                list.add(media);
            }
        } finally {
            cursor.close();
        }
    }

    public RestResponse deleteMedia(String id) {
        try {
            //MongoDB
            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));
            gscService.deleteFileFromGCS(getSingleMedia(id).get(0).getName());
            getCollection().findOneAndDelete(query);
            return RestResponse.status(200);
        } catch (Exception e) {
            return RestResponse.status(400);
        }
    }

    //MongoDB
    private MongoCollection getCollection() {
        return mongoClient.getDatabase("media").getCollection("media");
    }
}
