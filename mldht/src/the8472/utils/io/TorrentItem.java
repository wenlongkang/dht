package the8472.utils.io;

import the8472.utils.FileItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kang on 2017/8/13.
 */
public class TorrentItem {

    public List<FileItem> files = null;
    public int size = 0;
    public String name = null;
    public String id = null;
    public List<String> getNameList(){

        if(files == null){
            return null;
        }
        List<String> collect = files.parallelStream().map(FileItem::getFileName).collect(Collectors.toList());
        return collect;
    }
    @Override
    public String toString() {
        return "TorrentItem{" +
                "files=" + files.toString() +
                ", size=" + size +
                ", name='" + name + '\'' +
                '}';
    }
}
