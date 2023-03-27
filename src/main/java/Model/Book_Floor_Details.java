package Model;

import Enums.Categories;
import Enums.FloorNumber;

public class Book_Floor_Details{
  
	FloorNumber FloorNo;
	int ShelveNo;
	Categories Department;
	
	public FloorNumber getFloorNo() {
		return FloorNo;
	}
	public void setFloorNo(FloorNumber floorNo) {
		FloorNo = floorNo;
	}
	public int getShelveNo() {
		return ShelveNo;
	}
	public void setShelveNo(int shelveNo) {
		ShelveNo = shelveNo;
	}
	public Categories getDepartment() {
		return Department;
	}
	public void setDepartment(Categories department) {
		Department = department;
	}
}
