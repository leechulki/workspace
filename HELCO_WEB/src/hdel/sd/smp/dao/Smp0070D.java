package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0070ParamVO;
import hdel.sd.smp.domain.Smp0070VO;

import java.util.List;

/**
 * 이동계획 오픈/마감
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Smp0070D {

	public List<Smp0070VO> Select(Smp0070ParamVO param);				// 조회
	public int InsertCopyOrder(Smp0070ParamVO param);					// copy - 수주
	public int InsertCopyOrderRepair(Smp0070ParamVO param);				// copy - 수주(보수)
	public int InsertCopySales(Smp0070ParamVO param);					// copy - 매출
	public int InsertCopySalesRepair(Smp0070ParamVO param);				// copy - 매출(보수)
	public int InsertCopyCharge(Smp0070ParamVO param);					// copy - 청구
	public int InsertCopyChargeRepair(Smp0070ParamVO param);			// copy - 청구(보수)
	public int InsertCopyCollection(Smp0070ParamVO param);				// copy - 수금
	public int InsertCopyCollectionRepair(Smp0070ParamVO param);		// copy - 수금(보수)
	public int DeleteCancelOrder(Smp0070ParamVO param);					// cancel - 수주
	public int DeleteCancelOrderRepair(Smp0070ParamVO param);			// cancel - 수주(보수)
	public int DeleteCancelSales(Smp0070ParamVO param);					// cancel - 매출
	public int DeleteCancelSalesRepair(Smp0070ParamVO param);			// cancel - 매출(보수)
	public int DeleteCancelCharge(Smp0070ParamVO param);				// cancel - 청구
	public int DeleteCancelChargeRepair(Smp0070ParamVO param);			// cancel - 청구(보수)
	public int DeleteCancelCollection(Smp0070ParamVO param);			// cancel - 수금
	public int DeleteCancelCollectionRepair(Smp0070ParamVO param);		// cancel - 수금(보수)
	public int MergeCopyNext(Smp0070ParamVO param);						// 마감 후 저장
	public int MergeCancelNextUpd(Smp0070ParamVO param);				// 취소 후 저장 - 갱신
	public int MergeCancelNextDel(Smp0070ParamVO param);				// 취소 후 저장 - 삭제
}
