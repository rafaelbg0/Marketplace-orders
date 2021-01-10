package com.exercise.orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exercise.orders.connection.ConnectionFactory;
import com.exercise.orders.util.IntervalUtils;
import com.exercise.orders.util.ValidateArgs;

@SpringBootApplication
public class MarketplaceApplication {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		
//		String[] simulation = {"2018-01-01 00:00:00", "2019-01-01 00:00:00", "1-2", "2-3", ">4"};
//		List<String> argsList =  new ArrayList<String>(Arrays.asList(simulation));
		
		List<String> argsList =  new ArrayList<String>(Arrays.asList(args));
		List<String> intervalList = new ArrayList();
		
		
		if(ValidateArgs.isValid(argsList)) {

			LocalDateTime firstDate = LocalDateTime.parse(argsList.get(0), dateFormatter);
			LocalDateTime secondDate = LocalDateTime.parse(argsList.get(1), dateFormatter);
			
			if(argsList.size()>2) {
				for (int i=2; i<argsList.size(); i++) {
					intervalList.add(argsList.get(i));
				}				
			}else {
				intervalList.add("1-3");
				intervalList.add("4-6");
				intervalList.add("7-12");
				intervalList.add(">12");
			}

			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement stm = null;
			ResultSet rs = null;

			int intervalSize = 0;
			int nextMonth = 1;

			for(String interval : intervalList) {

				intervalSize = IntervalUtils.getSize(interval);
				List<LocalDateTime> dates = IntervalUtils.stringToDate(intervalSize, secondDate);

				int ordersAmountByPeriod = 0;

				try {

					stm = conn.prepareStatement("SELECT COUNT(DISTINCT(`order`.`id`)) as `result` FROM `marketplace`.`order` AS `order` "
							+ "INNER JOIN `marketplace`.`item` AS `item` ON `order`.`id` = `item`.`order_id` "
							+ "LEFT JOIN `marketplace`.`product` AS `prod` ON `item`.`product_id` = `prod`.`id` where `prod`.`creation_date` BETWEEN ? AND ?");

					stm.setString(1, dates.get(0).format(dateFormatter));
					stm.setString(2, dates.get(1).format(dateFormatter));

					rs = stm.executeQuery();

					while(rs.next()) {
						ordersAmountByPeriod = rs.getInt("result");						
					}

				} catch (SQLException e) {
					throw new RuntimeException("Connection error.", e);
				}				

				System.out.println(printMessage(interval, nextMonth, ordersAmountByPeriod));

				secondDate = dates.get(0).minusMonths(1);
			}

			ConnectionFactory.closeConnection(conn, stm, rs);

		}else {
			System.out.println("Invalid arguments.");
		}
		
	}

	private static String printMessage(String interval, int nextMonth, int amount) {
		String msg = interval + " months: " + amount;
		msg+= (amount<=1) ? " order" : " orders";  
		return msg;
	}

}
