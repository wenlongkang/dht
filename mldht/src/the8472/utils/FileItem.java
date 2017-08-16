package the8472.utils;

/**
 * Created by kang on 2017/8/13.
 */
public class FileItem {

    public String fileName = null;
    public int size = 0;

    public String getFileName(){
        return fileName;
    }
    @Override
    public String toString() {
        return "FileItem{" +
                "fileName='" + fileName + '\'' +
                ", size=" + size +
                '}';
    }
}
