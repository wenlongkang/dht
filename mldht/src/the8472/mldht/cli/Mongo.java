package the8472.mldht.cli;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;
import the8472.utils.MongoConnect;

/**
 * Created by adinimistrator on 2017/7/14.
 */
public class Mongo {







    @Test
    public void testt(){

        String uri = String.format("mongodb://%s:%s@%s/", "kangwenlong", "kangwenlonG0", "47.93.47.183");
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        MongoDatabase database = mongoClient.getDatabase("t_torrent");

        MongoCollection<Document> collection = database.getCollection("torrent");
        System.out.println(collection.count());
        JSONObject jsonObject = JSON.parseObject("{\"kang\":\"wen\"}");
        Document document = new Document(jsonObject);
        collection.insertOne(document);
        System.out.println(collection.count());
        mongoClient.close();



    }

    @Test
    public void gg(){
        String value = "{\n" +
                "  \"info\":{\n" +
                "    \"pieces\":0x4E2671BF1A312D37E6F568B7D2A26341A80F441CC8F55CDB7047571372492B2C…(45140), \n" +
                "    \"publisher.utf-8\":\"zgome@18p2p\", \n" +
                "    \"name.utf-8\":\"对白淫荡有趣的母子乱伦对著化妆镜草妈妈射了好多 对白淫荡有趣的母子乱伦儿子性冲动把妈妈的肉丝撕破了草逼 露脸怒操武汉大奶MM 性感粉嫩\", \n" +
                "    \"name\":\"对白淫荡有趣的母子乱伦对著化妆镜草妈妈射了好多 对白淫荡有趣的母子乱伦儿子性冲动把妈妈的肉丝撕破了草逼 露脸怒操武汉大奶MM 性感粉嫩\", \n" +
                "    \"files\":[\n" +
                "      {\n" +
                "        \"path\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼\", \n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼.jpg\"\n" +
                "        ], \n" +
                "        \"ed2k\":0x21E28C0A55444A3EAB8691B95FB0CEC6, \n" +
                "        \"length\":169000, \n" +
                "        \"filehash\":0x410A896221F06CAB4DB033723A1467C798D88061, \n" +
                "        \"path.utf-8\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼\", \n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼.jpg\"\n" +
                "        ]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_0_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":93144, \n" +
                "        \"path.utf-8\":[\"_____padding_file_0_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼\", \n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼.mp4\"\n" +
                "        ], \n" +
                "        \"ed2k\":0x686EAA5609D4B7498A324875145876CF, \n" +
                "        \"length\":280426826, \n" +
                "        \"filehash\":0xBB55B16616E834F2F51F56597123FA5B765185EC, \n" +
                "        \"path.utf-8\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼\", \n" +
                "          \"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼.mp4\"\n" +
                "        ]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_1_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":67254, \n" +
                "        \"path.utf-8\":[\"_____padding_file_1_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多\", \n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多.jpg\"\n" +
                "        ], \n" +
                "        \"ed2k\":0x9E2938F21500B5DBFB58996B2446101A, \n" +
                "        \"length\":191424, \n" +
                "        \"filehash\":0x6C7FC8F3EE5031D436BBE252EA9915C1815D7544, \n" +
                "        \"path.utf-8\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多\", \n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多.jpg\"\n" +
                "        ]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_2_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":70720, \n" +
                "        \"path.utf-8\":[\"_____padding_file_2_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多\", \n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多.mp4\"\n" +
                "        ], \n" +
                "        \"ed2k\":0xC43CA193A39CDDE1D1C41EB409903AF1, \n" +
                "        \"length\":249104278, \n" +
                "        \"filehash\":0x8E2B92459FBB77825083F1F3AECAB9BAE07D869F, \n" +
                "        \"path.utf-8\":[\n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多\", \n" +
                "          \"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多.mp4\"\n" +
                "        ]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_3_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":194666, \n" +
                "        \"path.utf-8\":[\"_____padding_file_3_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\n" +
                "          \"露臉怒操武漢大奶MM\", \n" +
                "          \"露臉怒操武漢大奶MM.jpg\"\n" +
                "        ], \n" +
                "        \"ed2k\":0x287ED126391A8D0A4E4E464025ED2FAE, \n" +
                "        \"length\":174409, \n" +
                "        \"filehash\":0x154391AA5D1213CB13C5B878012418CD57CDE6F4, \n" +
                "        \"path.utf-8\":[\n" +
                "          \"露臉怒操武漢大奶MM\", \n" +
                "          \"露臉怒操武漢大奶MM.jpg\"\n" +
                "        ]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_4_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":87735, \n" +
                "        \"path.utf-8\":[\"_____padding_file_4_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\n" +
                "          \"露臉怒操武漢大奶MM\", \n" +
                "          \"露臉怒操武漢大奶MM.mp4\"\n" +
                "        ], \n" +
                "        \"ed2k\":0xDA3D8ACF0E6FA352FE6F0BC3FBF99419, \n" +
                "        \"length\":57749711, \n" +
                "        \"filehash\":0x016F0EC9F06A409AED8BF622DD8E571491986377, \n" +
                "        \"path.utf-8\":[\n" +
                "          \"露臉怒操武漢大奶MM\", \n" +
                "          \"露臉怒操武漢大奶MM.mp4\"\n" +
                "        ]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_5_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":184113, \n" +
                "        \"path.utf-8\":[\"_____padding_file_5_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"zghome@www.t66y.com.txt\"], \n" +
                "        \"ed2k\":0xA5758D1912649611BE67C1EF817D4460, \n" +
                "        \"length\":44, \n" +
                "        \"filehash\":0x0B93285C47682162EFE771649DBD5031E9AAA2DC, \n" +
                "        \"path.utf-8\":[\"zghome@www.t66y.com.txt\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_6_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":262100, \n" +
                "        \"path.utf-8\":[\"_____padding_file_6_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"zghome@草榴地址.url\"], \n" +
                "        \"ed2k\":0x8221D99236DCFB8040828D30EC0FF3C0, \n" +
                "        \"length\":143, \n" +
                "        \"filehash\":0x17FCF388C5A61BC80B01A81B0C34CBAA39C94358, \n" +
                "        \"path.utf-8\":[\"zghome@草榴地址.url\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_7_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":262001, \n" +
                "        \"path.utf-8\":[\"_____padding_file_7_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"zghome@草榴社區最新地址發布頁.html\"], \n" +
                "        \"ed2k\":0xE88C461E320AC7AD1D9794017D7F565F, \n" +
                "        \"length\":34023, \n" +
                "        \"filehash\":0x5DD30EBC38EA00F4635EDE33A5622CD979C5F8DE, \n" +
                "        \"path.utf-8\":[\"zghome@草榴社區最新地址發布頁.html\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_8_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":228121, \n" +
                "        \"path.utf-8\":[\"_____padding_file_8_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"zgome@ sex8.cc.txt\"], \n" +
                "        \"ed2k\":0x84E4D0B08E590BA77D582A1E957FBAD0, \n" +
                "        \"length\":21, \n" +
                "        \"filehash\":0x9EB6614D023644C5DEDDF053A3D267E06907CB06, \n" +
                "        \"path.utf-8\":[\"zgome@ sex8.cc.txt\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_9_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":262123, \n" +
                "        \"path.utf-8\":[\"_____padding_file_9_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"zgome@WK綜合論壇.url\"], \n" +
                "        \"ed2k\":0x610378CFA741390CC8DD757C381C2AAA, \n" +
                "        \"length\":223, \n" +
                "        \"filehash\":0xDD1CC7CD324CA694B1E5425A823AC157995CBB66, \n" +
                "        \"path.utf-8\":[\"zgome@WK綜合論壇.url\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_10_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":261921, \n" +
                "        \"path.utf-8\":[\"_____padding_file_10_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"zgome@dioguitar23.net.url\"], \n" +
                "        \"ed2k\":0x97A4F8FB671C4D0640329A7837A5BB05, \n" +
                "        \"length\":126, \n" +
                "        \"filehash\":0xB0F2E2487F1D3C452969FBB6F61E51B74F73D021, \n" +
                "        \"path.utf-8\":[\"zgome@dioguitar23.net.url\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_11_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":262018, \n" +
                "        \"path.utf-8\":[\"_____padding_file_11_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"免费试看最新影片天天更新.mht\"], \n" +
                "        \"ed2k\":0x37CA5910739BCF7988EBB23AFD007361, \n" +
                "        \"length\":477, \n" +
                "        \"filehash\":0xF0C9F77E9B583EB55F236B6324E14246346F5641, \n" +
                "        \"path.utf-8\":[\"免费试看最新影片天天更新.mht\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_12_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":261667, \n" +
                "        \"path.utf-8\":[\"_____padding_file_12_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"台湾妹纸.mht\"], \n" +
                "        \"ed2k\":0x93B7CFE733CAF25596EA9F282E6C594E, \n" +
                "        \"length\":481, \n" +
                "        \"filehash\":0x8B2D63FAF90ADF91AC3EBE13460ACC330401FD66, \n" +
                "        \"path.utf-8\":[\"台湾妹纸.mht\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_13_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":261663, \n" +
                "        \"path.utf-8\":[\"_____padding_file_13_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"國產自拍免空合集.mht\"], \n" +
                "        \"ed2k\":0x5C4E4080309CB0BBEAA78C202B1A60C9, \n" +
                "        \"length\":469, \n" +
                "        \"filehash\":0x08B98C1F69E3DB367FB25F7047166215CE07D87F, \n" +
                "        \"path.utf-8\":[\"國產自拍免空合集.mht\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_14_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":261675, \n" +
                "        \"path.utf-8\":[\"_____padding_file_14_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼.jpg\"], \n" +
                "        \"ed2k\":0x21E28C0A55444A3EAB8691B95FB0CEC6, \n" +
                "        \"length\":169000, \n" +
                "        \"filehash\":0x410A896221F06CAB4DB033723A1467C798D88061, \n" +
                "        \"path.utf-8\":[\"對白淫蕩有趣的母子亂倫兒子性沖動把媽媽的肉絲撕破了草逼.jpg\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_15_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":93144, \n" +
                "        \"path.utf-8\":[\"_____padding_file_15_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多.jpg\"], \n" +
                "        \"ed2k\":0x9E2938F21500B5DBFB58996B2446101A, \n" +
                "        \"length\":191424, \n" +
                "        \"filehash\":0x6C7FC8F3EE5031D436BBE252EA9915C1815D7544, \n" +
                "        \"path.utf-8\":[\"對白淫蕩有趣的母子亂倫對著化妝鏡草媽媽射了好多.jpg\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"_____padding_file_16_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"], \n" +
                "        \"length\":70720, \n" +
                "        \"path.utf-8\":[\"_____padding_file_16_如果您看到此文件，请升级到BitComet(比特彗星)0.85或以上版本____\"]\n" +
                "      }, {\n" +
                "        \"path\":[\"露臉怒操武漢大奶MM.jpg\"], \n" +
                "        \"ed2k\":0x287ED126391A8D0A4E4E464025ED2FAE, \n" +
                "        \"length\":174409, \n" +
                "        \"filehash\":0x154391AA5D1213CB13C5B878012418CD57CDE6F4, \n" +
                "        \"path.utf-8\":[\"露臉怒操武漢大奶MM.jpg\"]\n" +
                "      }\n" +
                "    ], \n" +
                "    \"publisher\":\"zgome@18p2p\", \n" +
                "    \"piece length\":262144, \n" +
                "    \"publisher-url\":\"http://18p2p.com\", \n" +
                "    \"publisher-url.utf-8\":\"http://18p2p.com\"\n" +
                "  }\n" +
                "}\n";

        //int length = value.length();
        //System.out.println(length);
        JSONObject jsonObject = JSON.parseObject(value);
    }

    @Test
    public void aaaaaaa(){
        JSONObject jsonObject = JSON.parseObject("{\"kang\":\"wevvvvn\"}");
        Document document = new Document(jsonObject);
        MongoConnect.getConnect().insert("t_torrent","torrent",document);
    }

    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject("{\"kangbbb\":\"wen.bbb\"}");
        Document document = new Document(jsonObject);
        MongoConnect.getConnect().insert("t_torrent","torrent",document);
    }

}
