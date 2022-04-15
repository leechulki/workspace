package hdel.ps.dao;

import hdel.ps.domain.*;

import java.util.HashMap;
import java.util.List;

public interface PS1701002Dao {
    public List<HashMap<String, Object>> selectGroupByTemno(PS17ParamVO param);

    public List<HashMap<String, Object>> selectGroupByActss(PS17ParamVO param);

    public List<HashMap<String, Object>> selectGroupByLifnr(PS17ParamVO param);

    public List<HashMap<String, Object>> selectGroupByTeam(PS17ParamVO param);

    public List<HashMap<String, Object>> selectGrupByActss(PS17ParamVO param);

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

    public List<HashMap<String, Object>> selectGroupByTeamPlusMinus(PS17ParamVO param);
}
