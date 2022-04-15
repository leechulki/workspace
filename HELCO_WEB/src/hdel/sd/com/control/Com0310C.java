package hdel.sd.com.control;

import com.tobesoft.platform.data.Dataset;
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0310;
import hdel.sd.com.domain.Com0310ParamVO;
import hdel.sd.com.service.Com0310S;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("com0310")
public class Com0310C
{
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0310S service;
	
	@RequestMapping(value="find")
    public View Com0310FindView(MipParameterVO paramVO, Model model)
    {
        Logger logger = Logger.getLogger(getClass());
        Dataset dsCond = paramVO.getDataset("ds_cond");
        Dataset dsList = paramVO.getDataset("ds_list");
        
        try
        {
            Com0310ParamVO param = new Com0310ParamVO();
            service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
            param.setMandt(paramVO.getVariable("G_MANDT"));
            param.setPspid(DatasetUtility.getString(dsCond, "PSPID"));
            logger.debug((new StringBuilder("eunha debugging mandt -> ")).append(param.getMandt()).toString());
            logger.debug((new StringBuilder("eunha debugging pspid -> ")).append(param.getPspid()).toString());
            List findList = service.find(param);
            for(int iRow = 0; iRow < findList.size(); iRow++)
            {
                dsList.appendRow();
                for(int iCol = 0; iCol < dsList.getColumnCount(); iCol++)
                {
                	dsList.setColumn(iRow, "CHCK", 0);
                    dsList.setColumn(iRow, "JQPRNUM", ((Com0310)findList.get(iRow)).getJqprnum());
                    dsList.setColumn(iRow, "JQPRNO", ((Com0310)findList.get(iRow)).getJqprno());
                    dsList.setColumn(iRow, "PSPID", ((Com0310)findList.get(iRow)).getPspid());
                    dsList.setColumn(iRow, "HOGI", ((Com0310)findList.get(iRow)).getHogi());
                    dsList.setColumn(iRow, "REJTXT", ((Com0310)findList.get(iRow)).getRejtxt());
                    dsList.setColumn(iRow, "OCCDT", ((Com0310)findList.get(iRow)).getOccdt());
                    dsList.setColumn(iRow, "IMPKTL", ((Com0310)findList.get(iRow)).getImpktl());
                    dsList.setColumn(iRow, "IMPKTLNAME", ((Com0310)findList.get(iRow)).getImpktlName());
                    dsList.setColumn(iRow, "IMPKTL2", ((Com0310)findList.get(iRow)).getImpktl2());
                    dsList.setColumn(iRow, "IMPKTL2NAME", ((Com0310)findList.get(iRow)).getImpktl2Name());
                    dsList.setColumn(iRow, "IMPKTL3", ((Com0310)findList.get(iRow)).getImpktl3());
                    dsList.setColumn(iRow, "IMPKTL3NAME", ((Com0310)findList.get(iRow)).getImpktl3Name());
                    dsList.setColumn(iRow, "IWBTR", ((Com0310)findList.get(iRow)).getIwbtr());
                    dsList.setColumn(iRow, "WAERS", ((Com0310)findList.get(iRow)).getWaers());
                    dsList.setColumn(iRow, "IWBTRORG", ((Com0310)findList.get(iRow)).getIwbtrorg());
                    dsList.setColumn(iRow, "IWBTRCHG", ((Com0310)findList.get(iRow)).getIwbtrChg());
                    dsList.setColumn(iRow, "MAXSEQ", ((Com0310)findList.get(iRow)).getMaxSeq());
                    
                    String iwbtr =  dsList.getColumnAsString(iRow, "IWBTR");
                    String iwbtrchg =  dsList.getColumnAsString(iRow, "IWBTRCHG");
                    String maxSeq = dsList.getColumnAsString(iRow, "MAXSEQ");

                    if(iwbtr != null && iwbtrchg != null ){
                        if(!iwbtr.equals(iwbtrchg)){
                        	dsList.setColumn(iRow, "IWBTR", iwbtrchg);
                        	dsList.setColumn(iRow, "IWBTRCHG", iwbtr);
                        	dsList.setColumn(iRow, "MAXSEQ", maxSeq==null?"0":Integer.parseInt(maxSeq)+1+"");
                        }else dsList.setColumn(iRow, "IWBTRCHG", "");
                    }else dsList.setColumn(iRow, "IWBTRCHG", "");
                }
            }

            MipResultVO resultVO = new MipResultVO();
            resultVO.addDataset(dsList);
            model.addAttribute("resultVO", resultVO);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return view;
    }
}
