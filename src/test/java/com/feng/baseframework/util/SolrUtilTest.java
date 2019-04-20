package com.feng.baseframework.util;

import com.feng.baseframework.common.JunitBaseTest;
import com.feng.baseframework.model.SolrUpdateLog;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CursorMarkParams;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@ActiveProfiles("dev")
public class SolrUtilTest extends JunitBaseTest {

	private SolrUpdateLog solrUpdateLog;
	private String collection;
	private String auditId;
	private SolrQuery solrQuery;

	@Before
	public void setUp() {
		collection = "1_1545634851850";
		auditId = "38818219521545634867968960";
	}

	@Test
	public void addDoc() {
	}

	@Test
	public void addDocs() {
	}

	@Test
	public void updateIndex() {
		solrUpdateLog = new SolrUpdateLog();
		solrUpdateLog.setAuditId(auditId);
		solrUpdateLog.setField("review");
		solrUpdateLog.setFieldValue("y");
		solrUpdateLog.setCollection(collection);

		SolrUtil.updateIndex(solrUpdateLog);
	}

	@Test
	public void getDoc() {
		SolrDocument doc = SolrUtil.getDoc(collection, auditId);
		System.out.println(doc.toString());
	}

	@Test
	public void getDocs() {
		collection = "1_1547605293500";
		solrQuery = new SolrQuery();
		solrQuery.set("q", "optime:* AND hour:*");//logon_time:[2019-01-17T13:45:00.00Z TO *]
		solrQuery.set("fl", "id,hour,optime");//logon_time,optime
		solrQuery.setStart(0);
		solrQuery.setRows(100);
		solrQuery.addSort("id", SolrQuery.ORDER.desc);

		SolrUtil.loadCore(collection);

		QueryResponse queryResponse = SolrUtil.getDocs(collection, solrQuery, true);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		String cursorMark = queryResponse.getNextCursorMark();
		List<SolrDocument> docs = solrDocumentList.subList(0, solrDocumentList.size());
		long count = solrDocumentList.getNumFound();
		System.out.println("sum:" + count);
		printTimeResult(docs);
		int loopTime = (int) (count % 10000 == 0 ? count / 10000 : count / 10000 + 1);
		for (int i = 1; i < loopTime; i++) {
			solrQuery.set(CursorMarkParams.CURSOR_MARK_PARAM, cursorMark);
			queryResponse = SolrUtil.getDocs(collection, solrQuery, true);
			cursorMark = queryResponse.getNextCursorMark();
			solrDocumentList = queryResponse.getResults();
			printTimeResult(solrDocumentList.subList(0, solrDocumentList.size()));
		}
	}

	private void printTimeResult(List<SolrDocument> docs) {
		String hour = "";
		String id = "";
		Object logonTimeFiled;
		String logonTime = "";
		if (docs != null && !docs.isEmpty()) {
			for (SolrDocument doc : docs) {
				try {
					id = doc.getFieldValue("id").toString();
					hour = doc.getFieldValue("hour").toString();
					logonTimeFiled = doc.getFieldValue("optime");
					logonTime = DateUtil.dateToString((Date) logonTimeFiled, "HHmm", TimeZone.getTimeZone("GMT"));

					if (!hour.equals(logonTime)) {
						System.out.println(id);
						System.out.println(hour);
						System.out.println(logonTime);
					}
				} catch (Exception e) {
					System.out.println(id);
				}
			}
		}
	}
}