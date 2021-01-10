package com.exercise.orders.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ValidateArgs {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static boolean isValid(List<String> list) {
		boolean value = false;
		try {
			if(list.size()==2) {				
				LocalDateTime ldt1 = LocalDateTime.parse(list.get(0), dateFormatter);
				LocalDateTime ldt2 = LocalDateTime.parse(list.get(1), dateFormatter);
				if(ldt1.isBefore(ldt2)) {
					value = true;
				}
			}
			else if(list.size()>2) {

				LocalDateTime ldt1 = LocalDateTime.parse(list.get(0), dateFormatter);
				LocalDateTime ldt2 = LocalDateTime.parse(list.get(1), dateFormatter);

				if(ldt1.isBefore(ldt2)) {
					value = true;
				}else {
					value = false;
				}

				for(int i=2; i<list.size() && value; i++) {

					String[] val = list.get(i).split("-"); 
					if(val.length==1) {
						String[] val2 = list.get(i).split(">");
						int v1 = Integer.parseInt(val[1]);
						value = true;
					}
					else {
						int v1 = Integer.parseInt(val[0]);
						int v2 = Integer.parseInt(val[1]);

						if(v1<v2) {
							value = true;
						}else {
							value = false;
						}
					}
				}				
			}
		} catch (Exception e) {
			//args not valid
		}
		return value;
	}

}
