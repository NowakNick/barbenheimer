package org.barbenheimer;

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
import java.util.List;

@ApplicationScoped
public class MediaService {
    @Inject
    MongoClient mongoClient;
    @Inject
    GSCService gscService;

    public RestResponse<List<Media>> getMedia() {
        try {
            List<Media> list = new ArrayList<>();
            MongoCursor<Document> cursor = getCollection().find().iterator();
            createDocumentList(cursor, list);
            return RestResponse.ok(list);
        } catch (Exception e) {
            return RestResponse.status(400, "Getting all documents failed.");
        }
    }

    public RestResponse<List<Media>> getSingleMedia(String id) {
        try {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));
            List<Media> list = new ArrayList<>();
            MongoCursor<Document> cursor = getCollection().find(query).iterator();
            createDocumentList(cursor, list);
            return RestResponse.ok(list);
        } catch (Exception e) {
            return RestResponse.status(400, "Getting single document failed.");
        }
    }

    public RestResponse addMedia(FileUploadInput input) {
        if (input.media != null) {
            return RestResponse.status(400, "Uploaded File is null.");
        }

        if (!gscService.uploadFileToGCS(input)) {
            return RestResponse.status(400, "Upload file to GCS failed.");
        }

        Document document = new Document()
                .append("name", input.name)
                .append("date", input.date)
                .append("content-type", input.media.contentType())
                .append("media-name", input.media.fileName())
                .append("tags", input.tags);

        try {
            InsertOneResult insertId = getCollection().insertOne(document);
            document.append("id", insertId.getInsertedId().asObjectId().getValue().toString());
        } catch (Exception e) {
            try {
                BasicDBObject query = new BasicDBObject();
                query.put("_id", new ObjectId(input.name));
                gscService.deleteFileFromGCS(getSingleMedia(input.name).get(0).getName());
            } catch (Exception e2) {
                return RestResponse.status(400,
                        "Inerst into Mongo failed, but after that the delete of the file from gcs also faild.");
            }
            return RestResponse.status(400, "Insert into Mongo failed");
        }

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
                media.setMedia(gscService.getSingleFileFromGCS(document.getString("name")).toString());
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
        } catch (Exception e) {
            return RestResponse.status(400, "Delete of media in gcs failed.");
        }

        try {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));
            getCollection().findOneAndDelete(query);
        } catch (Exception e) {
            return RestResponse.status(400, "Delete of media from mongo failed.");
        }

        return RestResponse.ok();

    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("media").getCollection("media");
    }
}
