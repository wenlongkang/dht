package lbms.plugins.mldht.kad;

import static lbms.plugins.mldht.kad.NodeFactory.fillTable;
import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import lbms.plugins.mldht.DHTConfiguration;
import lbms.plugins.mldht.kad.DHT.DHTtype;

public class KClosestNodeSearchTest {
	
	Node node;
	
	@Before
	public void setup() {
		DHT dht = new DHT(DHTtype.IPV6_DHT);
		dht.setScheduler(Executors.newScheduledThreadPool(0));
		dht.populate();
		dht.config = new DHTConfiguration() {
			
			@Override
			public boolean noRouterBootstrap() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isPersistingID() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Path getStoragePath() {
				return Paths.get("./does.not.exist");
			}
			
			@Override
			public int getListeningPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean allowMultiHoming() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		node = dht.getNode();
		node.initKey(dht.config);
		node.registerId();
		//node.registerServer(null);
		//node.registerServer(null);
	}
	
	@Test
	public void testOrdering() {
		fillTable(node);
		// KNS only accepts pinged entries
		node.table().stream().forEach(b -> {
			b.getBucket().entriesStream().forEach(e -> {
				e.signalResponse(1);
			});
		});
		
		Key k = Key.createRandomKey();
		
		int targetSize = 32;
		
		Comparator<KBucketEntry> comp = new KBucketEntry.DistanceOrder(k);
		
		KClosestNodesSearch search = new KClosestNodesSearch(k, targetSize, node.getDHT());
		
		search.fill();
		List<KBucketEntry> result = search.getEntries();
		
		List<KBucketEntry> reference = node.table().stream().flatMap(b -> b.getBucket().entriesStream()).filter(KBucketEntry::eligibleForNodesList).sorted(comp).limit(targetSize).collect(Collectors.toList());
		
		
		List<KBucketEntry> sortedResult = new ArrayList<>(result);
		sortedResult.sort(comp);
		
		assertEquals(targetSize, result.size());
		
		/*
		List<RoutingTableEntry> entries = node.getBuckets();

		System.out.println(k.toBinString()+"\n");
		
		
		List<Prefix> ps = reference.stream().map(e -> {
			int idx = Node.findIdxForId(entries, e.getID());
			Node.RoutingTableEntry re = entries.get(idx);
			return re.prefix;
		}).distinct().collect(Collectors.toList());
		
		ps.stream().forEachOrdered(p -> {
			System.out.println(new Prefix(k.distance(p), p.depth));
		});
		
		System.out.println(" ");
		
		ps.stream().forEachOrdered(p -> {
			System.out.println(p);
		});
		
		reference.forEach(e -> {
			System.out.println(e.getID().toBinString()+" "+k.distance(e.getID()).toBinString());
		});
		System.out.println(" ");
		sortedResult.forEach(e -> {
			System.out.println(e.getID().toBinString()+" "+k.distance(e.getID()).toBinString());
		});
		
		Diagnostics d = new Diagnostics();
		d.formatRoutingTable(System.out, node);
		*/
		

		
		assertEquals(reference, sortedResult);
		
		
	}

}
