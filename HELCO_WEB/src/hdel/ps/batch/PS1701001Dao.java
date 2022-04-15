package hdel.ps.batch;

import hdel.ps.domain.*;

import java.util.HashMap;
import java.util.List;

public interface PS1701001Dao {
    public void deleteZPSTW1713(PS17ParamVO param);

    public void deleteZPSTW1714(PS17ParamVO param);

    public void deleteZPSTW1716(PS17ParamVO param);

    public void deleteZPSTW1717(PS17ParamVO param);

    public void insertZPSTW1713(HashMap<String, Object> param);

    public void insertZPSTW1714(HashMap<String, Object> param);

    public void insertZPSTW1716(HashMap<String, Object> param);

    public void insertZPSTW1717(HashMap<String, Object> param);

    public List<HashMap<String, Object>> selectRawData(PS17ParamVO param);

    public ZPSTW1715 selectZPSTW1715ForUpdate(PS17ParamVO param);

    public void insertZPSTW1715(PS17ParamVO param);

    public void updateZPSTW1715ForStart(PS17ParamVO param);

    public void updateZPSTW1715ForEnd(PS17ParamVO param);

    public List<HashMap<String, Object>> selectHoliday(PS17ParamVO param);

    public List<ZPSTW1703> selectZPSTW1703(PS17ParamVO param);

    public List<ZPSTW1704> selectZPSTW1704(PS17ParamVO param);

    public List<ZPSTW1705> selectZPSTW1705(PS17ParamVO param);

    public List<ZPSTW1706> selectZPSTW1706(PS17ParamVO param);

    public List<ZPSTW1707> selectZPSTW1707(PS17ParamVO param);

    public List<ZPSTW1708> selectZPSTW1708(PS17ParamVO param);

    public List<ZPSTW1709> selectZPSTW1709(PS17ParamVO param);

    public List<ZPSTW1710> selectZPSTW1710(PS17ParamVO param);

    public List<ZPSTW1711> selectZPSTW1711(PS17ParamVO param);

    public List<ZPSTW1712> selectZPSTW1712(PS17ParamVO param);

    public HashMap<String, Object> selectZPSTW1718(HashMap<String, Object> param);

    public HashMap<String, Object> selectZPSTW1719(HashMap<String, Object> param);

    public HashMap<String, Object> selectZPSTW1720(HashMap<String, Object> param);

    public HashMap<String, Object> selectForES(HashMap<String, Object> param);

    public HashMap<String, Object> selectForMW(HashMap<String, Object> param);

    public HashMap<String, Object> selectInwonByTemno(HashMap<String, Object> param);
}
