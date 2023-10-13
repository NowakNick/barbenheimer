package org.barbenheimer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.BSONObject;
import org.bson.Document;
import org.jboss.resteasy.reactive.MultipartForm;

import java.io.File;
import java.io.FileInputStream;
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

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Media media = new Media();
                media.setName(document.getString("name"));
                media.setId(document.get("_id").toString());
                media.setDate(document.getString("date"));
                media.setMedia(document.getString("media"));
                //media.setTags((List<String>) document.get("tags"));
                list.add(media);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void addMedia(@MultipartForm FileUploadInput input) throws Exception{
        File file = new File(input.file.getAbsolutePath());
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        fl.read(arr);
        fl.close();
        String encodedString = Base64.getEncoder().encodeToString(arr);


        Document document = new Document()
                .append("name", input.name)
                .append("media", encodedString)
                .append("date", input.date);
        getCollection().insertOne(document);
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("media").getCollection("media");
    }
}
