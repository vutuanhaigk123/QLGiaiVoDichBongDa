package model;

import java.util.Vector;

public class Regulation {
	public static Vector<Priority> priorityList;
	public static Vector<RegulationList> regulationList;
	
	public static int getValueOf(int regulationId){
		return Integer.parseInt(regulationList.get(regulationId - 1).getValue());
	}
	
	public static void setValueOf(int regulationId, String value){
		regulationList.get(regulationId - 1).setValue(value);
	}
	
}
