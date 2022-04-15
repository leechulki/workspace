package hdel.lib.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class ZBCS_TIMESTAMP extends CommonDomain {
	private static final long serialVersionUID = 1L;
	
	private String erdat;
	private String erzet;
	private String ernam;
	private String aedat;
	private String aezet;
	private String aenam;

	public ZBCS_TIMESTAMP() {
		setTimeStamp("");
	}
	/**
	 * @return the erdat
	 */
	public String getErdat() {
		return erdat == null ? getAedat() : String.format("%08d", NumberUtils.toInt(erdat,0));
	}
	/**
	 * @param erdat the erdat to set
	 */
	public void setErdat(String erdat) {
		this.erdat = String.format("%08d", NumberUtils.toInt(erdat, 0));
	}
	/**
	 * @return the erzet
	 */
	public String getErzet() {
		return erzet == null ? getAezet() : String.format("%06d", NumberUtils.toInt(erzet,0));
	}
	/**
	 * @param erzet the erzet to set
	 */
	public void setErzet(String erzet) {
		this.erzet = String.format("%06d", NumberUtils.toInt(erzet, 0));
	}
	/**
	 * @return the ernam
	 */
	public String getErnam() {
		return ernam == null ? getAenam() : StringUtils.defaultString(ernam, "");
	}
	/**
	 * @param ernam the ernam to set
	 */
	public void setErnam(String ernam) {
		this.ernam = StringUtils.defaultString(ernam, "");
	}
	/**
	 * @return the aedat
	 */
	public String getAedat() {
		return String.format("%08d", NumberUtils.toInt(aedat, 0));
	}
	/**
	 * @param aedat the aedat to set
	 */
	public void setAedat(String aedat) {
		this.aedat = String.format("%08d", NumberUtils.toInt(aedat, 0));
	}
	/**
	 * @return the aezet
	 */
	public String getAezet() {
		return String.format("%06d", NumberUtils.toInt(aezet, 0));
	}
	/**
	 * @param aezet the aezet to set
	 */
	public void setAezet(String aezet) {
		this.aezet = String.format("%06d", NumberUtils.toInt(aezet, 0));
	}
	/**
	 * @return the aenam
	 */
	public String getAenam() {
		return StringUtils.defaultString(aenam, "");
	}
	/**
	 * @param aenam the aenam to set
	 */
	public void setAenam(String aenam) {
		this.aenam = StringUtils.defaultString(aenam, "");
	}

	public void setTimeStamp(String user) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		Calendar calendar = Calendar.getInstance();
		setAedat(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
		setAezet(new SimpleDateFormat("HHmmss").format(calendar.getTime()));
		setAenam(user);
	}
}