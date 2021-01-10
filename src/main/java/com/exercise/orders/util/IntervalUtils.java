package com.exercise.orders.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IntervalUtils {

	public static List<LocalDateTime> stringToDate(int size, LocalDateTime finalDate) {
		List<LocalDateTime> dateList = new ArrayList();
		if(size!=0) {
			dateList.add(finalDate.minusMonths(size));
		}else {
			dateList.add(finalDate.minusMonths(2400));
		}
		dateList.add(finalDate);		
		return dateList;
	}
	
	public static int getSize(String interval) {
		int size = 0;
		if(!interval.contains(">")) {
			String[] month = interval.split("-");
			size = Integer.parseInt(month[1]) - Integer.parseInt(month[0]); 
		}
		return size;
	}

}
