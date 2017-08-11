package the8472;

/**
 * Created by adinimistrator on 2017/8/11.
 */
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bson.Document;
import org.junit.Test;
import the8472.utils.MongoConnect;

public class MongoDBJDBC {
    public static void main(String[] args){



        MongoClientURI uri = new MongoClientURI("mongodb://kangwenlong:kangwenlonG0@127.0.0.1/?authSource=t_torrent");
        int i = Security.addProvider(new BouncyCastleProvider());

        MongoClient mongoClient = new MongoClient(uri);
        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("t_torrent");
        MongoCollection<Document> torrent = mongoDatabase.getCollection("torrent");
        FindIterable<Document> documents = torrent.find();
        for (Document doc:documents
                ) {
            String s = doc.toString();
            System.out.println(s);
        }

    }












    @Test
    public void testaaa(){
        // create index
        //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("47.93.47.183",27017);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential("kangwenlong", "t_torrent", "kangwenlonG0".toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs,credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("t_torrent");
        MongoCollection<Document> torrent = mongoDatabase.getCollection("torrent");
        //torrent.createIndex(Indexes.ascending("locationn"));
        //
        //torrent.createIndex(Indexes.text("locationn"));
        //Document first = torrent.find(Filters.lt("locationn", "FF9E461EC705474B83FD8711B9269BD52A170D90.torrent")).first();

        Document document = MongoConnect.getConnect().find("t_torrent", "torrent", "locationn", "FF9E461EC705474B83FD8711B9269BD52A170D90.torrent");
        System.out.println(document);
        //eq("i", 71)
    }

    @Test
    public void tfffffestaaa(){
        String name = "\\adfafasff.torrent";
        String substring = name.substring(name.lastIndexOf("\\")+1, name.length());
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}