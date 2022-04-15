package hdel.ps.control;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.ps.domain.PS17ParamVO;
import hdel.ps.service.PS17Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import tit.util.DatasetUtility;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
@Controller
@RequestMapping("ps17")
public class PS17Control {
    @Autowired
    private SrmView view;

    @Autowired
    private PS17Service service;

    @Autowired
    private SrmSqlSession sqlSession;

    private View list(MipParameterVO paramVO, Model model) {
        Dataset ds_cond = paramVO.getDataset("ds_cond");
        Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
        MipResultVO resultVO = new MipResultVO();

        try {
            String sysId = paramVO.getVariable("G_SYSID");

            service.createDao(sqlSession.getSqlSessionBySysid(sysId));
           

            String req = paramVO.getVariable("req");
            String mandt = paramVO.getVariable("G_MANDT");
            String mdate = DatasetUtility.getString(ds_cond, "MDATE");	// 조회일
            String actss = DatasetUtility.getString(ds_cond, "ZZACTSS");	// 지사
            String lifnr = DatasetUtility.getString(ds_cond, "ZZLIFNR");	// 협력업체
            String temno = DatasetUtility.getString(ds_cond, "ZZPMNUM");	// 소장
            String chk = DatasetUtility.getString(ds_cond, "CHK");	// 소장 담당현장 다보기 기능(체크박스)

            PS17ParamVO param = new PS17ParamVO();
            param.setReq(req);
            param.setMandt(mandt);
            param.setMdate(mdate);
            param.setZzactss(actss);
            param.setZzlifnr(lifnr);
            param.setZzpmnum(temno);
            param.setChk(chk);

            // 다음~ 달로 조회할 경우,
            int diffNthNext = service.getDiffNthNext(mdate);
            param.setDiffNthNext(diffNthNext);
            if (diffNthNext <= 0) {
                param.setLoad("LOD");
                param.setL0("L0");
                param.setL1("L1");
                param.setL2("L2");
            } else {
                param.setLoad("L" + diffNthNext);
                param.setL0("L" + diffNthNext);
                param.setL1("L" + (diffNthNext + 1));
                param.setL2("L" + (diffNthNext + 2));
            }

            List<HashMap<String, Object>> list;
            if (param.getReq().equals("groupByTeam")) {
                list = service.groupByTeam(param);
            } else if (param.getReq().equals("groupByActss")) {
                list = service.groupByActss(param);
            } else if (param.getReq().equals("groupByLifnr")) {
                list = service.groupByLifnr(param);
            } else {
                list = service.groupByTemno(param);
            }
            Dataset dsOutput = new Dataset();
            service.list2Ds(list, dsOutput);
            dsOutput.setId("ds_list_tmp");

            resultVO.addDataset(dsOutput);
        } catch (Exception e) {
            e.printStackTrace();
            CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        }

        ds_error.setId("ds_error");
        resultVO.addDataset(ds_error);
        model.addAttribute("resultVO", resultVO);
        return view;
    }

    @RequestMapping(value = "groupByTemno")
    public View groupByTemno(MipParameterVO paramVO, Model model) {
        VariableList variableList = paramVO.getVariableList();
        variableList.add("req", "groupByTemno");
        paramVO.setVariableList(variableList);
        return list(paramVO, model);
    }

    @RequestMapping(value = "groupByLifnr")
    public View groupByLifnr(MipParameterVO paramVO, Model model) {
        VariableList variableList = paramVO.getVariableList();
        variableList.add("req", "groupByLifnr");
        paramVO.setVariableList(variableList);
        return list(paramVO, model);
    }

    @RequestMapping(value = "groupByActss")
    public View groupByActss(MipParameterVO paramVO, Model model) {
        VariableList variableList = paramVO.getVariableList();
        variableList.add("req", "groupByActss");
        paramVO.setVariableList(variableList);
        return list(paramVO, model);
    }

    @RequestMapping(value = "groupByTeam")
    public View groupByTeam(MipParameterVO paramVO, Model model) {
        VariableList variableList = paramVO.getVariableList();
        variableList.add("req", "groupByTeam");
        paramVO.setVariableList(variableList);
        return list(paramVO, model);
    }
}
