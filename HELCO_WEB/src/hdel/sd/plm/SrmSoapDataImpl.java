package hdel.sd.plm;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.PlatformData;

public class SrmSoapDataImpl implements SrmSoapData {
	private boolean isDebug = false;
	private StringBuffer xmlData = new StringBuffer();
	private DatasetList dsInputList = null;
	private DatasetList outDsList = new DatasetList();
	private Map<String, String> indexMap = new HashMap<String, String>();

	// 데이터 출력을 위한 처리
	private Dataset ds_template = null;
	private Dataset ds_error = null;

	public SrmSoapDataImpl(String jsonData) {
		initData(jsonData, false);
	}

	public SrmSoapDataImpl(String jsonData, boolean isDebug) {
		initData(jsonData, isDebug);
	}

	private void initData(String jsonData, boolean isDebug) {
		this.isDebug = isDebug;
		// 데이터 변환 처리
		if(jsonData != null) {
			Decoder decoder = Base64.getDecoder();
			byte[] decodedBytes = decoder.decode(jsonData.getBytes());
			try {
				xmlData.append(new String(decodedBytes, "EUC-KR"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}

		// 마이플랫폼 형식으 데이터 수신 처리
		receiveData();

		// 출력용 데이터셑 정의
		this.ds_template = new Dataset("plm_out_ds_template");
		this.ds_template.addColumn("IDX", (short)1, 256);
		this.ds_template.addColumn("PRH", (short)1, 256);
		this.ds_template.addColumn("PRD", (short)1, 256);
		this.outDsList.addDataset(this.ds_template);
		
		this.ds_error = new Dataset("ds_error");
		this.ds_error.addColumn("ERRCODE", (short)1, 256);
		this.ds_error.addColumn("ERRMSG", (short)1, 256);
		this.outDsList.addDataset(this.ds_error);
	}
	
	/** 
	 * SRM 마이플랫폼 XML 전제 데이터를 수신한다.
	 * @throws Exception
	 */
	private void receiveData() {
		try {
			StringReader reader = new StringReader(xmlData.toString());
			PlatformRequest pReq = new PlatformRequest(reader);
			pReq.receiveData();
			this.dsInputList = pReq.getDatasetList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * SRM 입력 XML 데이터
	 * @param
	 * @return "String"
	 * @throws Exception
	 */
	@Override
	public String getInputPutXml() throws Exception {
		return this.xmlData.toString();
	}

	/** 
	 * plm_ds_header(헤더정보)를 Hashmap으로 변환하여 리턴한다.
	 * @param 선택행
	 * @return Map<String, String>
	 * @throws Exception
	 */
	@Override
	public Map<String, String> getDsHeaderMap(int indexRow) throws Exception {
		Map<String, String> dsHeaderMap = new HashMap<String, String>();
		Dataset ds_header = this.dsInputList.getDataset("plm_ds_header");
		for(int i=0; i < ds_header.getColumnCount(); i++) {
			String columnId = ds_header.getColumnId(i);
			String value = ds_header.getColumnAsString(indexRow, columnId);
			dsHeaderMap.put(columnId, value);
		}
		if(this.isDebug) {
			System.out.println("dsHeaderMap("+indexRow+"):"+dsHeaderMap);
		}
		return dsHeaderMap;
	}

	/** 
	 * plm_ds_template(영업사양)를 Hashmap으로 변환하여 리턴한다.
	 * @param 선택행
	 * @return Map<String, String>
	 * @throws Exception
	 */
	@Override
	public Map<String, String> getDsTemplateMap(int indexRow) throws Exception {
		Map<String, String> dsTemplateMap = new HashMap<String, String>();
		Dataset ds_template = this.dsInputList.getDataset("plm_ds_template");
		Dataset ds_header = this.dsInputList.getDataset("plm_ds_header");
        int startIndex = new Integer(ds_header.getColumnAsString(indexRow, "START_IDX")).intValue();
        int endIndex = new Integer(ds_header.getColumnAsString(indexRow, "END_IDX")).intValue();
        for(int j = startIndex; j <= endIndex; j++) {
			String prh = ds_template.getColumnAsString(j, "PRH");
			String prd = ds_template.getColumnAsString(j, "PRD");
			String idx = ds_template.getColumnAsString(j, "IDX");
			dsTemplateMap.put(prh, prd);
			indexMap.put(prh+indexRow, idx);
		}
		if(this.isDebug) {
			System.out.println("dsTemplateMap("+indexRow+"):"+dsTemplateMap);
		}

		return dsTemplateMap;
	}

	/** 
	 * 리스트 plm_ds_template(영업사양)를 Hashmap List 변환하여 리턴한다.
	 * @param 
	 * @return List<Map<String, String>>
	 * @throws Exception
	 */
	public List<Map<String, String>> getDsHeaderList() throws Exception {
		List<Map<String, String>> dsHeaderList = new ArrayList<Map<String, String>>();
		Dataset ds_header = this.dsInputList.getDataset("plm_ds_header");
		for(int i=0; i < ds_header.getRowCount(); i++ ) {
			Map<String, String> dsHeaderMap = getDsHeaderMap(i);
			dsHeaderList.add(dsHeaderMap);
		}
		if(this.isDebug) {
			System.out.println("dsHeaderList:"+dsHeaderList);
		}
		return dsHeaderList;
	}

	/** 
	 * 멀티 영업사양 체크 시 로우별 Duty Call 수행 후 특성코드 기본 사양값을 입력한다.
	 * @param 선택행, 영업특성, 특성값
	 * @return void
	 * @throws Exception
	 */
	public void setPRHValue(int rowIndex, String prh, String prd) throws Exception {
		String idx = null;
		int row = ds_template.appendRow();
		String rsWord = "ERRMSG_";
        int prhIndex = prh.indexOf(rsWord);
		if(prhIndex > -1) {
			idx = indexMap.get(prh.substring(prhIndex+rsWord.length())+rowIndex);
		} else {
			idx = indexMap.get(prh+rowIndex);
		}
		
		ds_template.setColumn(row, "IDX", idx);
		ds_template.setColumn(row, "PRH", prh);
		ds_template.setColumn(row, "PRD", prd);
		if(this.isDebug) {
			System.out.println("rowIndex:"+rowIndex+", idx:"+idx+", prh:"+prh+", prd:"+prd);
		}
	}

	/** 
	 * Duty Call 수행 후 특성코드 기본 사양값 HashMap 데이터를 입력한다.
	 * @param 선택행, 영업특성결과Map
	 * @return void
	 * @throws Exception
	 */
	public void setPRHValueMap(int rowIndex, Map<String, String> dsTemplateMap) throws Exception {
		Iterator<String> keys = dsTemplateMap.keySet().iterator();
		while( keys.hasNext() ) {
			String prh = keys.next();
			String prd = dsTemplateMap.get(prh);
			setPRHValue(rowIndex, prh, prd);
		}
	}

	/** 
	 * 에러코드와 에러 메시지를 입력한다.
	 * @param 에러코드, 에러메시지
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void setDsError(String errCode, String errMsg) {
		int row = ds_error.appendRow();
		ds_error.setColumn(row, "ERRCODE", errCode);
		ds_error.setColumn(row, "ERRMSG", errMsg);
		if(this.isDebug) {
			System.out.println("errCode:"+errCode+", errMsg:"+errMsg);
		}
	}

	/** 
	 * Srm 정합성 체크 결과 XML을 생성한다.
	 * @param 
	 * @return Xml 정합성 체크 데이터
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@Override
	public String getBase64OutPutXml() {
		String outputXml = null;
		PlatformData platformdata = new PlatformData();
		platformdata.setCharset("UTF-8");
		platformdata.setDatasetList(this.outDsList);
		outputXml = platformdata.toXml();
		if(this.isDebug) {
			System.out.println("outputXml:"+outputXml);
		}
		Encoder encoder = Base64.getEncoder();
		// UTF-8 선언해서 데이터를 수신해 보자
		byte[] encodedBytes;
		try {
			// PLM 서버
			// PLM 한글 처리
			encodedBytes = encoder.encode(outputXml.getBytes("EUC-KR"));
			//encodedBytes = encoder.encode(new String(outputXml.getBytes("EUC-KR")).getBytes("UTF-8"));
			outputXml = new String(encodedBytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return outputXml;
	}

	/**
	 * SRM 층별사양확인 헤더 정보를 생성한다.
	 * @param 
	 * @return 층별사양확인 헤더 정보 XML
	 * @throws Exception
	 */
	@Override
	public void setFloorHeadList(int rowIndex, List<Map<String, String>> outHeaderList) throws Exception {
		int row = -1;
		Dataset dsFloorHeader = new Dataset("plm_out_ds_header");
		dsFloorHeader.addColumn("HEAD", (short)1, 256);
		dsFloorHeader.addColumn("TIT", (short)1, 256);
		this.outDsList.addDataset(dsFloorHeader);

		if(this.isDebug) {
			System.out.println("rowIndex:"+rowIndex+", outHeaderList:"+outHeaderList);
		}

		for(int i=0; i < outHeaderList.size(); i++) {
			Map<String, String> headMap = outHeaderList.get(i);
			row = dsFloorHeader.appendRow();
			dsFloorHeader.setColumn(row, "HEAD", headMap.get("HEAD"));
			dsFloorHeader.setColumn(row, "TIT", headMap.get("TIT"));
		}
	}
}
