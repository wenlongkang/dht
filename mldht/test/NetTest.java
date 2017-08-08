import org.junit.Test;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by adinimistrator on 2017/6/9.
 */
public class NetTest {

    @Test
    public void aaa(){


        try
        {
            for (NetworkInterface iface : Collections.list(NetworkInterface.getNetworkInterfaces()))
            {
                if(!iface.isUp() || iface.isLoopback())
                    continue;
                for (InterfaceAddress ifaceAddr : iface.getInterfaceAddresses())
                {
                    Inet4Address addr = (Inet4Address) ifaceAddr.getAddress();
                    if(addr instanceof Inet4Address && addr.getAddress()[0] == 0){
                        System.out.println("return false");
                    }
                    boolean anyLocalAddress = addr.isAnyLocalAddress();
                    boolean linkLocalAddress = addr.isLinkLocalAddress();
                    boolean loopbackAddress = addr.isLoopbackAddress();
                    boolean multicastAddress = addr.isMulticastAddress();
                    boolean siteLocalAddress = addr.isSiteLocalAddress();
                    if(anyLocalAddress || linkLocalAddress || loopbackAddress || multicastAddress || siteLocalAddress){


                        System.out.println("return true");
                    }else {
                        System.out.println("return false");
                    }

                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void test() throws UnsupportedEncodingException {
        String msg = URLEncoder.encode("{\"status\":{\"message\":\"\\u6210\\u529f\",\"result\":true},\"info\":{\"id\":\"1\",\"formid\":\"7\",\"siteid\":\"kf_3004\",\"fields\":\"[{\\\"name\\\": \\\"name\\\", \\\"label\\\": \\\"\\u59d3\\u540d\\\",\\\"type\\\": \\\"input\\\",\\\"defaultText\\\": \\\"\\\",\\\"rule\\\": \\\"\\u4e2d\\u6587\\u6216\\u82f1\\u6587\\u4e14\\u4e0d\\u5305\\u62ec\\u7279\\u6b8a\\u5b57\\u7b26\\uff0c20\\u5b57\\u7b26\\u4ee5\\u5185\\\",\\\"requed\\\": 0},{\\\"name\\\": \\\"phone\\\",\\\"label\\\": \\\"\\u7535\\u8bdd\\\",\\\"type\\\": \\\"input\\\",\\\"defaultText\\\": \\\"\\\",\\\"rule\\\": \\\"\\u965011\\u4e2a\\u6570\\u5b57\\uff0c\\\",\\\"requed\\\": 1 },{\\\"name\\\": \\\"email\\\", \\\"label\\\": \\\"\\u90ae\\u7bb1\\\",\\\"type\\\": \\\"input\\\",\",\"enable\":\"1\",\"formname\":\"\\u804a\\u7a97\\u53d1\\u9001\\u8868\\u5355\",\"createtime\":\"1496888130\",\"desc\":null,\"submiturl\":\"http:\\/\\/release-ts2.ntalker.com\\/index.php?c=form_deal&m=formInsert&userid=kf_3004_ISME9754_T2D_ralf&token=f9aad16ed927c47f4bfc163b86efeaf0&siteid=kf_3004&formid=7\"}}", "utf-8");
        System.out.println(msg);
    }

    @Test
    public void testaaa() throws UnsupportedEncodingException {

        String tpath = "H:\\torrents\\12\\80\\12808A7D7D5FCE0B1D2AB4F9F05E2738902CC135.torrent";
        Path path = Paths.get(tpath);
        String s = path.toString();

        byte[] bytes = "康文龙".getBytes("utf-8");
        System.out.println(bytes.length);

        UniversalDetector detector = new UniversalDetector(null);
        //开始给一部分数据，让学习一下啊，官方建议是1000个byte左右（当然这1000个byte你得包含中文之类的）
        detector.handleData(bytes, 0, bytes.length);
        //识别结束必须调用这个方法
        detector.dataEnd();
        //神奇的时刻就在这个方法了，返回字符集编码。
        String detectedCharset = detector.getDetectedCharset();

        byte[] bytesc = "1康文龙".getBytes("utf-8");
        System.out.println(bytesc.length);


        byte[] bytess = "康文龙".getBytes("GBK");

        UniversalDetector detector1 = new UniversalDetector(null);
        //开始给一部分数据，让学习一下啊，官方建议是1000个byte左右（当然这1000个byte你得包含中文之类的）
        detector1.handleData(bytess, 0, bytess.length);
        //识别结束必须调用这个方法
        detector1.dataEnd();
        //神奇的时刻就在这个方法了，返回字符集编码。
        String detectedCharset1 = detector1.getDetectedCharset();

        System.out.println(bytess.length);


        byte[] bytes1 = "1".getBytes("utf-8");
        System.out.println(bytes1.length);
        byte[] bytess2 = "1".getBytes("GBK");
        System.out.println(bytess2.length);


        byte[] bytes11 = ",".getBytes("utf-8");
        System.out.println(bytes11.length);
        byte[] bytess22 = ",".getBytes("GBK");
        System.out.println(bytess22.length);

        System.out.println(s);

        byte[] gbks = "6-19 新作37連發".getBytes("BIG5");
        byte[] utf8 = "6-19 新作37連發".getBytes("utf-8");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    @Test
    public void ssss(){
        String path = NetTest.class.getResource("/").getPath();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
