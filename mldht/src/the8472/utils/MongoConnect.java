package the8472.utils;

import com.alibaba.fastjson.JSON;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adinimistrator on 2017/7/14.
 */
public class MongoConnect {


    private volatile static MongoConnect connect = null;

    private MongoClient client = null;

    private MongoConnect(){
        if(client == null) {
            //String uri = String.format("mongodb://%s:%s@%s/", "kangwenlong", "kangwenlonG0", "47.93.47.183");

            ServerAddress serverAddress = new ServerAddress("47.93.47.183",27017);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            MongoCredential credential = MongoCredential.createScramSha1Credential("kangwenlong", "t_torrent", "kangwenlonG0".toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            client = new MongoClient(addrs,credentials);
        }

    }

    public static MongoConnect getConnect(){
       if(connect == null){
           synchronized (MongoConnect.class){
               if(connect == null){
                   connect = new MongoConnect();
               }
           }
       }
       return connect;
    }
    /*private MongoDatabase getDb(String dbName) {
        return client.getDatabase(dbName);
    }

    private MongoCollection<Document> getCollection(String dbName,String collection){
        return client.getDatabase(dbName).getCollection(collection);
    }*/

    public void insert(String dbName,String collection,Document document){
        client.getDatabase(dbName).getCollection(collection).insertOne(document);
    }

    @Test
    public void test(){
        String value = "{\n" +
                "  \"info\":{\n" +
                "    \"pieces\":\"0xA0A5EE3BB44BF9FFB39E1AE7793ED5135875992F12DE3331207C8198F4DFEE32…(16720)\", \n" +
                "    \"name\":\"The.Originals.S04E06.HDTV.x264-SVA[rarbg]\", \n" +
                "    \"files\":[\n" +
                "      {\n" +
                "        \"path\":[\"RARBG.txt\"], \n" +
                "        \"length\":30\n" +
                "      }, {\n" +
                "        \"path\":[\"The.Originals.S04E06.HDTV.x264-SVA.mkv\"], \n" +
                "        \"length\":218998223\n" +
                "      }, {\n" +
                "        \"path\":[\"the.originals.s04e06.hdtv.x264-sva.nfo\"], \n" +
                "        \"length\":57\n" +
                "      }\n" +
                "    ], \n" +
                "    \"piece length\":262144\n" +
                "  }\n" +
                "}\n";

        Document document = new Document(JSON.parseObject(value));
        MongoConnect connect = getConnect();
        insert("t_torrent","torrent",document);
    }

}
