package the8472;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import the8472.utils.FileItem;
import the8472.utils.MongoConnect;
import the8472.utils.io.TorrentItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kang on 2017/8/13.
 */
public class SolrTest {


    @Test
    public void testSolr() throws IOException, SolrServerException {

        List<Document> all = MongoConnect.getConnect().findAll("t_torrent", "torrent",1,10);


        String urlString = "http://47.93.47.183:8983/solr/mycollection";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();



        for (Document doc:all
             ) {
            TorrentItem torrentItem = new TorrentItem();

            String torrentName = String.valueOf(doc.get("name"));//0xddffff.torrent
            String substring = torrentName.substring(0, torrentName.lastIndexOf("."));
            torrentItem.id = substring;

            JSONObject data = JSON.parseObject(String.valueOf(doc.get("data")));//info
            int totalFiles = getTotalFiles(data);
            torrentItem.totalFiles = totalFiles;

            int totalSize = getTotalSize(data);
            torrentItem.totalSize = totalSize;

            // 添加创建日期
            //TODO
            String info = data.getString("info");
            String s = info.replaceAll("\\n", "");
            String s1 = s.replaceAll("\\\\", "");

            data = JSONObject.parseObject(s1);// torrent data



            String name = null;

            List<FileItem> fileList = new ArrayList<>();
            if(data.containsKey("name.utf-8")){// 优选utf-8
                name = data.getString("name.utf-8");
            }else {
                name = data.getString("name");
            }

            torrentItem.name = name;

            if(data.containsKey("piece length")){
                String kblength = data.getString("piece length");
                torrentItem.size = Integer.valueOf(kblength);
            }
            if(data.containsKey("files")){// 此字段可能没有
                JSONArray files = data.getJSONArray("files");
                for(int i=0;i<files.size();i++){
                    JSONObject file = files.getJSONObject(i);
                    if(file.containsKey("path.utf-8")){
                        JSONArray path = file.getJSONArray("path.utf-8");
                        Object o = path.get(path.size() - 1);
                        FileItem fileItem = new FileItem();
                        fileItem.fileName = String.valueOf(o);
                        Integer length = file.getInteger("length");
                        if(length == null){
                            length = 0;
                        }
                        fileItem.size = length;
                        fileList.add(fileItem);

                    }else {
                        JSONArray path = file.getJSONArray("path");
                        Object o = path.get(path.size() - 1);
                        FileItem fileItem = new FileItem();
                        fileItem.fileName = String.valueOf(o);
                        Integer length = file.getInteger("length");
                        if(length == null){
                            length = 0;
                        }
                        fileItem.size = length;
                        fileList.add(fileItem);
                    }

                }
                torrentItem.files = fileList;
            }else{// 没有files字段
                FileItem fileItem = new FileItem();
                fileItem.size = torrentItem.size;
                fileItem.fileName = torrentItem.name;
                FileItem[] items = new FileItem[]{fileItem};
                List<FileItem> fileItems = Arrays.asList(items);
                torrentItem.files = fileItems;
            }
            System.out.print(torrentItem.toString());
            System.out.print("\n");


            SolrInputDocument document = new SolrInputDocument();
            document.addField("name",torrentItem.name);
            document.addField("files",torrentItem.getNameList());
            document.addField("size",torrentItem.size);
            document.addField("id",torrentItem.id);
            UpdateResponse response = solr.add(document);

        }



        solr.commit();



    }

    private int getTotalSize(JSONObject data) {

        return 0;
    }

    private int getTotalFiles(JSONObject data) {

        String info = data.getString("info");
        String s = info.replaceAll("\\n", "");
        String s1 = s.replaceAll("\\\\", "");

        JSONObject jsonObject = JSONObject.parseObject(s1);// torrent data

        if(jsonObject.containsKey("files")){// 此字段可能没有
           //
            JSONArray files = jsonObject.getJSONArray("files");
            return files.size();
        }else{// 没有files字段
            return 1;
        }
    }


    @Test
    public void test(){
    // 关键词提取
    // https://github.com/hankcs/HanLP
        //String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
        CustomDictionary.add("中出");
        CustomDictionary.add("肉便器");
        CustomDictionary.add("n0055");
        String content = "龍王@18P2P@N0310-3穴輪姦直腸直撃流し込み-- 杜泉 --";
        List<String> keywordList = HanLP.extractKeyword(content,10);
        System.out.println(keywordList);

    }

    @Test
    public void testDemo() {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences =
                new String[] {"gavyvy@18p2p@ID-23044早乙女ゆいSPECIAL BEST 4時間"};
        for (String sentence : sentences) {
            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
        }
    }
}
