package the8472.mldht.cli;

import static the8472.utils.Functional.typedGet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.bson.Document;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import the8472.bencode.PrettyPrinter;
import the8472.bencode.Tokenizer.BDecodingException;
import the8472.bt.TorrentUtils;
import the8472.utils.MongoConnect;
import the8472.utils.concurrent.SerializedTaskExecutor;

import lbms.plugins.mldht.kad.Key;
import lbms.plugins.mldht.kad.utils.ThreadLocalUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TorrentInfo {
	
	Path source;
	ByteBuffer raw;
	Map<String, Object> root;
	Map<String, Object> info;
	Charset encoding = StandardCharsets.UTF_8;
	boolean truncate = true;
	//

	public static Logger logger = LoggerFactory.getLogger(TorrentInfo.class);

	public TorrentInfo(Path source) {
		this.source = source;
	}

	public TorrentInfo(){

	}
	
	void readRaw() {
		if(raw != null)
			return;
		try(FileChannel chan = FileChannel.open(source, StandardOpenOption.READ)) {
			raw = chan.map(MapMode.READ_ONLY, 0, chan.size());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	void decode() {
		if(root != null)
			return;
		readRaw();
		root = ThreadLocalUtils.getDecoder().decode(raw.duplicate());
		typedGet(root, "info", Map.class).ifPresent(i -> info = i);
		if(info != null) {
			String charset = typedGet(info, "encoding", byte[].class).map(b -> new String(b, StandardCharsets.ISO_8859_1)).orElse(null);

			//Optional<byte[]> encoding = typedGet(info, "encoding", byte[].class);
			//encoding.map()
			/*typedGet(info, "encoding", byte[].class).map(new Function<byte[], Object>() {

				@Override
				public Object apply(byte[] bytes) {
					return new String();
				}

				@Override
				public <V> Function<V, Object> compose(Function<? super V, ? extends byte[]> before) {
					return null;
				}

				@Override
				public <V> Function<byte[], V> andThen(Function<? super Object, ? extends V> after) {
					return null;
				}
			});*/
			Object name = info.get("name");
			String namev = null;
			if(name instanceof String){
				System.out.println("111");
			}else if(name instanceof byte[]){
//-----------
				UniversalDetector detector = new UniversalDetector(null);
				//开始给一部分数据，让学习一下啊，官方建议是1000个byte左右（当然这1000个byte你得包含中文之类的）
				detector.handleData((byte[]) name, 0, ((byte[]) name).length);
				//识别结束必须调用这个方法
				detector.dataEnd();
				//神奇的时刻就在这个方法了，返回字符集编码。
				String detectedCharset = detector.getDetectedCharset();
				if(detectedCharset == null){
					return;
				}
				if(detectedCharset.equalsIgnoreCase("koi8-r")){//gbk
					try {
						namev = new String((byte[]) name,"GBK");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					byte [] newname = null;
					try {
						newname = namev.getBytes("utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					info.put("name",newname);
				}




//-----------




			}

			if(charset != null) {
				try {
					this.encoding = Charset.forName(charset);
				} catch (Exception e) {
					System.err.println("Charset " + charset + "not supported, falling back to " + this.encoding.name());
				}
			}
		}
	}
	
	Key infoHash() {
		return TorrentUtils.infohash(raw);
	}
	
	Optional<String> name() {
		decode();
		Optional<String> name = typedGet(info, "name.utf-8", byte[].class).map(b -> new String(b, StandardCharsets.UTF_8));
		if(!name.isPresent()) {
			name = typedGet(info, "name", byte[].class).map(b -> new String(b, encoding));
		}
		
		return name;
	}
	
	List<Map<String, Object>> files() {
		return typedGet(info, "files", List.class).map((List l) -> {
			return (List<Map<String, Object>>)l.stream().filter(Map.class::isInstance).collect(Collectors.toList());
		}).orElse(Collections.emptyList());
	}
	
	String raw() {
		decode();
		PrettyPrinter p = new PrettyPrinter();
		p.indent("  ");
		p.guessHumanReadableStringValues(true);
		p.truncateHex(truncate);
		p.append(root);
		return p.toString();
	}
	
	
	public static void getDetailInfo(String tpath) {
		/*String tpath = "H:\\torrents\\12\\80\\12808A7D7D5FCE0B1D2AB4F9F05E2738902CC135.torrent";*/


		List<String> args = new ArrayList<>();
		args.add(tpath);
		
		boolean printRaw = true;//ParseArgs.extractBool(args, "-raw");
		boolean noTrunc = false;//ParseArgs.extractBool(args, "-notrunc");
		boolean recursive = false;//ParseArgs.extractBool(args, "-r");
		boolean printLargest = false;//ParseArgs.extractBool(args, "-largest");
		
		
		Stream<Path> torrents = args.parallelStream().unordered().map(Paths::get).filter(Files::exists).flatMap(p -> {
			//logger.info("11111111");
			try {
				return Files.find(p, recursive ? Integer.MAX_VALUE :  1 , (f, attr) -> {
					return attr.isRegularFile() && attr.size() > 0;
				},  FileVisitOption.FOLLOW_LINKS);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}); //.collect(Collectors.toSet()).parallelStream().unordered();

		
		Consumer<String> printer = SerializedTaskExecutor.runSerialized((String s) -> {
			// save db
			//System.out.println(s);

			logger.info("GET!--> " + s);
			try {
				JSONObject torrent = JSON.parseObject(s);
                String name = torrent.getString("name");
                Document document = MongoConnect.getConnect().find("t_torrent", "torrent", "name", name);
                if(document != null){
                	logger.info("find ! return");
                    return;
                }else {
                	logger.info("save " + name + "to mongo");
                    document = new Document(torrent);
                    MongoConnect.getConnect().insert("t_torrent","torrent",document);
                }
				//MongoConnect.getConnect().

			}catch (Exception e){
				StackTraceElement[] stackTrace = e.getStackTrace();
				if(stackTrace != null && stackTrace.length > 0){
					for(int i=0;i<stackTrace.length;i++){
						logger.error(stackTrace[i].toString());
					}
				}
			}


		});
		
		String newline = "\\u000a|\\u000b|\\u000c|\\u000d|\\u0085|\\u2028|\\u2029";
		
		torrents.map(p -> {

			//logger.info("caoni" + p.toString());
			TorrentInfo ti = new TorrentInfo(p);
			try {
				ti.decode();
			} catch(BDecodingException ex) {
				return p.toString() + " does not appear to be a bencoded file: " + ex.getMessage();
			}

			if(printRaw) {
				ti.truncate = !noTrunc;
				//return "\n" + ti.raw() + '\n';
				String raw = ti.raw();
				//logger.info("raw : " + raw);
				//JSONObject json = JSON.parseObject(raw);
				JSONObject json = new JSONObject();
				String s = p.toString();
				logger.info(s);

				json.put("name",s.substring(s.lastIndexOf("/") + 1,s.length()));
				json.put("data",raw);
				return json.toString();
			}
				
			
			if(ti.info == null)
				return p.toString() + " does not contain an info dictionary";
			
			long length = typedGet(ti.info, "length", Long.class).orElse(0L);
			long largestSize = length;
			int numFiles = 1;
			
			StringBuilder result = new StringBuilder();
			Optional<String> name = ti.name();
			
			if(!name.isPresent()) {
				return p.toString() + " does not contain a name field";
			}
			
			String largestFile = "";
			
			List<Map<String, Object>> files = ti.files();
			
			if(!files.isEmpty()) {
				length = files.stream().mapToLong(e -> typedGet(e, "length", Long.class).orElse(0L)).sum();
				numFiles = files.size();
				Map<String, Object> largest = files.stream().max(Comparator.comparing(e -> typedGet(e, "length", Long.class).orElse(0L))).get();
				largestSize = typedGet(largest, "length", Long.class).orElse(0L);
				
				List<?> path = typedGet(largest, "path.utf-8", List.class).orElse(null);
				if(path == null)
					path = typedGet(largest, "path", List.class).orElse(null);
				
				largestFile = path.stream().filter(byte[].class::isInstance).map(b -> new String((byte[]) b, StandardCharsets.UTF_8)).collect(Collectors.joining("/"));
				largestFile = largestFile.replaceAll(newline, " ");
			}
			
			
			result.append(p.toString());
			result.append(" ");
			ti.name().map(s -> s.replaceAll(newline, " ")).ifPresent(result::append);
			
			if(printLargest) {
				if(numFiles > 1) {
					result.append('/');
					result.append(largestFile);
				}

				result.append(" size:");
				result.append(largestSize);
				result.append('/');
				result.append(length);
				result.append(" files:");
				result.append(numFiles);
			} else {
				result.append(" size:");
				result.append(length);
				result.append(" files:");
				result.append(numFiles);
			}
			
			result.append(" ih:");
			result.append(ti.infoHash().toString(false));
			
			return result.toString();
		}).forEach(printer::accept);
		

	}

	public static void main(String[] args) throws IOException {
		//String value = "C:\\Users\\adinimistrator\\Desktop\\xxx\\FEED4CFE9F468F934F597C56B4DD6D90F23CF057.torrent";
//		String value = "C:\\Users\\adinimistrator\\Desktop\\xxx\\FFEBC7201CB3F448115B17896BF0C73710FA6EF4.torrent";
		//String value = "C:\\Users\\adinimistrator\\Desktop\\xxx\\FF9E461EC705474B83FD8711B9269BD52A170D90.torrent";
		String value = "C:\\Users\\adinimistrator\\Desktop\\xxx\\FDD64723E662814C3D4322ECD0E963FC962A3DE1.torrent";
		//FF9E461EC705474B83FD8711B9269BD52A170D90.torrent

		getDetailInfo(value);
		//HashMap<String, Object> info = new HashMap<>();
		//String[] array= {"111","222"};
		//info.put("encoding","utf-8");


		System.in.read();

	}






}
