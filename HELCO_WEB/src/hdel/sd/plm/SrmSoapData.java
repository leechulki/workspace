package hdel.sd.plm;

import java.util.List;
import java.util.Map;

interface SrmSoapData {

	/** 
	 * SRM 입력 XML 데이터
	 * @param
	 * @return "String"
	 * @throws Exception
	 */
	public String getInputPutXml() throws Exception;

	/** 
	 * plm_ds_header(헤더정보)를 Hashmap으로 변환하여 리턴한다.
	 * @param 선택행
	 * @return Map<String, String>
	 * @throws Exception
	 */
	public Map<String, String> getDsHeaderMap(int indexRow) throws Exception;

	/** 
	 * plm_ds_template(영업사양)를 Hashmap으로 변환하여 리턴한다.
	 * @param 선택행
	 * @return Map<String, String>
	 * @throws Exception
	 */
	public Map<String, String> getDsTemplateMap(int indexRow)  throws Exception;

	/** 
	 * 리스트 plm_ds_template(영업사양)를 Hashmap List 변환하여 리턴한다.
	 * @param 
	 * @return List<Map<String, String>>
	 * @throws Exception
	 */
	public List<Map<String, String>> getDsHeaderList() throws Exception;
	

	/** 
	 * 멀티 영업사양 체크 시 로우별 Duty Call 수행 후 특성코드 기본 사양값을 입력한다.
	 * @param 선택행, 영업특성, 특성값
	 * @return void
	 * @throws Exception
	 */
	public void setPRHValue(int rowIndex, String prh, String prd) throws Exception;

	/** 
	 * Duty Call 수행 후 특성코드 기본 사양값 HashMap 데이터를 입력한다.
	 * @param 선택행, 영업특성결과Map
	 * @return void
	 * @throws Exception
	 */
	public void setPRHValueMap(int rowIndex, Map<String, String> dsTemplateMap) throws Exception;

	/** 
	 * 층별사양 Header 결과 입력
	 * @param 선택행, 영업특성결과Map
	 * @return void
	 * @throws Exception
	 */
	public void setFloorHeadList(int rowIndex, List<Map<String, String>> outHeaderList) throws Exception;
	
	/** 
	 * 에러코드와 에러 메시지를 입력한다.
	 * @param 에러코드, 에러메시지
	 * @return void
	 * @throws Exception
	 */
	public void setDsError(String errCode, String errMsg);

	/** 
	 * Srm 정합성 체크 결과 XML을 생성한다.
	 * @param 
	 * @return Xml 정합성 체크 데이터
	 * @throws Exception
	 */
	public String getBase64OutPutXml();

}
