package hdel.sd.com.domain;

import java.util.HashMap;
import java.util.List;

import org.jsn.jdf.Logger;

//=========================================================================================
//  클래스명 : 사양서 체크 변수
//  기능ID   : UF003
//  개선내역 : 사양 기준정보 및 사양 체크 디테일 처리 로직 개선
//  HISTORY  : 2016.01.28 최초 코딩 박수근
//=========================================================================================
public class DutyObj {
	HashMap<String, Object> dutyObj;
    List<Duty> dutyList;
    List<DutyDet> dutyDList;
    String mandt = "";
    String gtype = "";
    String zflg = "";

    public DutyObj() {
    	dutyObj = new HashMap<String, Object>();
    }

    public boolean getDataExists(String mandt, String gtype) {
        boolean isData = false;
        if(mandt != null && gtype != null) {
    		if( this.mandt.equals(mandt) && this.gtype.equals(gtype) ) {
    			if( this.dutyList != null ) {
        			if( this.dutyList.size() > 0 ) {
        			    isData = true;
        			}
    			}
    		}
    	}
        
    	if( !isData ) {
		    if( dutyList != null ) {
		    	dutyList.clear();
		    }

		    if( dutyDList != null ) {
		    	dutyDList.clear();
		    }
    	}

    	return isData;
    }

    public HashMap<String, Object> getDutyObj() {
        return dutyObj;
    }

    public int getDustyObjSize() {
        return dutyObj.size();
    }

    public List<Duty> getDutyList() {
        return this.dutyList;
    }
    
    public void setDutyList(List<Duty> dutyList) {
        this.dutyList = dutyList;
    }

    public void setDutyList(List<Duty> dutyList, String mandt, String gtype, String zflg) {
    	this.mandt = mandt;
    	this.gtype = gtype;
    	this.zflg = zflg;
        this.dutyList = dutyList;
    }

    public List<DutyDet> getBlockDutyDList(String blockno) {
    	@SuppressWarnings("unchecked")
		List<DutyDet> DutyDList = (List<DutyDet>) this.dutyObj.get(blockno);
        return DutyDList;
    }

    public void  setBlockDutyDList(String blockno, List<DutyDet> dutyDList) {
        this.dutyObj.put(blockno, dutyDList);
    }
}