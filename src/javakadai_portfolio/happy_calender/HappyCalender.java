package javakadai_portfolio.happy_calender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * 鬱々カレンダーに関するクラス
 *
 * @author たかくら
 */
public class HappyCalender {

	/** フォーマットを指定する */
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	/**
	 * 定年日をLocalDate型で生成する
	 * @param birthdate
	 * 		生年月日
	 * @return
	 * 		定年日(yyyy-MM--dd)。LocalDate型で返ってくる
	 */
	public static LocalDate getTeinenDate(String birthdate) throws DateTimeParseException {

		// 生年月日の日付を取得する(LocalDate型)
		LocalDate localBirthdate = LocalDate.parse(birthdate, formatter);
		// 現在の日付を取得する
		LocalDate nowDate = LocalDate.now();

		// 残り労働年数 = 66歳 - 現在の年齢
		long between = 66 - ChronoUnit.YEARS.between(localBirthdate, nowDate);
		// もしも生年月日が4月生まれ以上の場合は1プラスする
		if (localBirthdate.getMonthValue() >= 4 ){between += 1;}
		// 定年を西暦で取得する(例)1997年が入力された場合は、「2063」を取得する
		int betweenNum = nowDate.plusYears(between).getYear();


		// 定年年を取得する(例)2063
		String betweenDate = Integer.toString(betweenNum);

		// 定年日の次の日にちを生成する (例)"2063/04/01"
		String s = betweenDate + "/03/31";
		// String型→localDate型に変換する
		//LocalDate teinenDateNextDay = LocalDate.parse(s, formatter);

		// 定年日を取得する(定年日 = 定年日の次の日 - 1) (例)2063-04-01をplusDays()で-1する
		LocalDate teinenDate = LocalDate.parse(s, formatter);


		return teinenDate;

	}

}
