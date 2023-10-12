package org.barbenheimer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MediaService {
    @Inject
    MongoClient mongoClient;

    public List<Media> list() {
        List<Media> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Media media = new Media();
                media.setName(document.getString("name"));
                list.add(media);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void add(Media media) {
        Document document = new Document()
                .append("name", media.getName());
        getCollection().insertOne(document);
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("media").getCollection("media");
    }
}
