package org.barbenheimer;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.RestResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@ApplicationScoped
public class MediaService {
    @Inject
    MongoClient mongoClient;
    @Inject
    GSCService gscService;

    public List<Media> getMedia() {
        List<Media> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        createDocumentList(cursor, list);
        return list;
    }

    public List<Media> getSingleMedia(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        List<Media> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find(query).iterator();

        createDocumentList(cursor, list);

        return list;
    }

    public RestResponse addMedia(FileUploadInput input) throws IOException {
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
        InsertOneResult insertId = getCollection().insertOne(document);
        document.append("id", insertId.getInsertedId().asObjectId().getValue().toString());

        return RestResponse.ok(document);
    }

    private void createDocumentList(MongoCursor<Document> cursor, List<Media> list) {
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Media media = new Media();
                media.setName(document.getString("name"));
                media.setId(document.get("_id").toString());
                media.setDate(document.getString("date"));
                media.setContentType(document.getString("content-type"));
                media.setMediaName(document.getString("media-name"));
                media.setMedia(document.getString("media"));
                media.setTags((List<Integer>) document.get("tags"));
                list.add(media);
            }
        } finally {
            cursor.close();
        }
    }

    public RestResponse deleteMedia(String id) {
        try {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));
            gscService.deleteFileFromGCS(getSingleMedia(id).get(0).getName());
            getCollection().findOneAndDelete(query);
            return RestResponse.status(200);
        } catch (Exception e) {
            return RestResponse.status(400);
        }
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("media").getCollection("media");
    }
}
