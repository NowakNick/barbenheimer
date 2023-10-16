package org.barbenheimer;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.model.Filters;
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
            File media = new File(input.media.filePath().toString());
            FileInputStream fl = new FileInputStream(file);
            byte[] arr = new byte[(int) media.length()];
            fl.read(arr);
            fl.close();
            encodedString = Base64.getEncoder().encodeToString(arr);
        } else {
            return RestResponse.status(400);
        }

        Document document = new Document()
                .append("name", input.name)
                .append("date", input.date)
                .append("media", encodedString)
                .append("content-type", input.media.contentType())
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
            getCollection().findOneAndDelete(Filters.eq("id", id));
            return RestResponse.status(200);
        } catch (Exception e) {
            return RestResponse.status(400);
        }
    }

    public RestResponse updateMedia(String id, FileUploadInput input) {
        try {
            String encodedString;
            if (input.media != null) {
                File media = new File(input.media.filePath().toString());
                FileInputStream fl = new FileInputStream(media);
                byte[] arr = new byte[(int) media.length()];
                fl.read(arr);
                fl.close();
                encodedString = Base64.getEncoder().encodeToString(arr);
            } else {
                return RestResponse.status(400);
            }
            getCollection().findOneAndUpdate(Filters.eq(
                    "id", id),
                    Updates.set("name", input.name));
            getCollection().findOneAndUpdate(Filters.eq(
                    "id", id),
                    Updates.set("media", encodedString));
            getCollection().findOneAndUpdate(Filters.eq(
                    "id", id),
                    Updates.set("date", input.date));
            getCollection().findOneAndUpdate(Filters.eq(
                    "id", id),
                    Updates.set("tags", input.tags));
            getCollection().findOneAndUpdate(Filters.eq(
                    "id", id),
                    Updates.set("content-type", input.media.contentType()));
            return RestResponse.status(200);
        } catch (IOException e) {
            return RestResponse.status(400);
        }
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("media").getCollection("media");
    }
}