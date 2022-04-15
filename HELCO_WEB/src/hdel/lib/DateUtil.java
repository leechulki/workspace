package hdel.lib;

import java.util.Calendar;

public class DateUtil {

	public int getRunningDays(Calendar calendar, int workingDays){
		if(workingDays == 0) return 0;

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); 

		int runningDays = 0;
		int portion = 0;

		switch(dayOfWeek) {
		case Calendar.MONDAY:
			break;
		case Calendar.SATURDAY:
			runningDays = 2;
			break;
		case Calendar.SUNDAY:
			runningDays = 1;
			break;
		default:
			workingDays = workingDays + (dayOfWeek - Calendar.MONDAY); 
			runningDays = Calendar.MONDAY - dayOfWeek;
			break;
		}

		portion = (int)Math.floor(workingDays / 5);
		
		runningDays = runningDays + workingDays;
		runningDays = runningDays + portion * 2;	//Saturday, Sunday

		return runningDays;
	}
}
