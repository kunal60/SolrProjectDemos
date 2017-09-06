
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

public class SolrjPopulator {
	static SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/DemoCore").build();

	public static void main(String[] args) throws IOException, SolrServerException {

		client.deleteByQuery("*");
		client.commit();
		try {
			HashMap<String, String> map = DocumentsParser.parse("Example.pdf", "pdf");
			addingToDocument(map);
			System.out.println("pdf done");
			HashMap<String, String> map1 = DocumentsParser.parse("Example.jpg", "jpg");
			addingToDocument(map1);
			System.out.println("jpg done");
			HashMap<String, String> map2 = DocumentsParser.parse("Example.xlsx", "xlsx");
			addingToDocument(map2);
			System.out.println("xls done");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void addingToDocument(HashMap<String, String> map) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		Set<String> keySet = map.keySet();
		Iterator<String> keySetIterator = keySet.iterator();

		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			doc.addField(key, map.get(key).toString());
		}
		client.add(doc);
		client.commit();
	}
}
