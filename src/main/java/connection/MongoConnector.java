package connection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.util.Map;
// TODO: исключить локальное подключение
public class MongoConnector {
    private MongoClient mongoClient;
    private DB database;
    private DBCollection collection;
    private Map siteMap;

    public MongoConnector(String host, int port) {
        setMongoClient(host, port);
        setDatabase("prod");
        setCollection("hosts");
    }

    private void setMongoClient(String host, int port) {
        this.mongoClient = new MongoClient(host, port);
    }

    private void setDatabase(String dbName){
        this.database = mongoClient.getDB(dbName);
    }

    public void setCollection(String collectionName){
        this.collection = database.getCollection(collectionName);
    }

    public void setWebSite(String internalName){
        BasicDBObject qur = new BasicDBObject();
        qur.put("internalName", internalName);
        this.siteMap = (Map) collection.find(qur).next();
    }

    public Map getApplicationProperties(){
        return (Map) ((Map) this.siteMap.get("config")).get("application");
    }
}
