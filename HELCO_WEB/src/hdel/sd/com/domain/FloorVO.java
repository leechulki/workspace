package hdel.sd.com.domain;

public class FloorVO {
	
	private int iFloor    = 0;
	private String floor   = "";
	private String floorNm = "";

	public int getiFloor() {
		return iFloor;
	}

	public void setiFloor(int iFloor) {
		this.iFloor = iFloor;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getFloorNm() {
		return floorNm;
	}

	public void setFloorNm(String floorNm) {
		this.floorNm = floorNm;
	}

	@Override
	public String toString() {
		return "FloorVO [iFloor=" + iFloor + ", floor=" + floor + ", floorNm="
				+ floorNm + "]";
	}
}
