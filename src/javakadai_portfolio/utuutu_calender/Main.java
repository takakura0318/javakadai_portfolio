package javakadai_portfolio.utuutu_calender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import ajd4jp.AJDException;

/**
 * 鬱々カレンダーを実行するMainクラス
 * @author たかくら
 *
 */
public class Main {
	/**
	 * 鬱々カレンダーを実行するMainメソッド
	 *
	 */
	public static void main(String[] args) {

		// 生年月日の入力を受け取る(1997/03/18)
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String birthdate = null;

		try {

			while (true) {
				System.out.println("生年月日を入力してください(yyyy/MM/dd形式)");
				birthdate = reader.readLine();
				if (checkDate(birthdate))
					break;
			}

		} catch (IOException e) { // マルチキャッチ
			e.printStackTrace();
		}

		// 定年日をCalenderKadaiクラスのgetTeinenDate()メソッドから取得する
		LocalDate teinenDate = CalenderKadai.getTeinenDate(birthdate);
		// 現在の日付を取得する
		LocalDate nowDate = LocalDate.now();

		/**************************** 現在から定年までの出勤日数を取得する処理 ****************************/
		// 休日日数を格納する為の変数holidayCntを初期化
		int holidayCnt = 0;
		// 今月の休日日数を格納する為の変数holidayCntを初期化
		int thisholidayCnt = 0;

		// 翌月の〇月1日を生成するメソッド
		StringBuilder sb = createNextMonth(nowDate.getYear(), nowDate.plusMonths(1).getMonthValue());

		// フォーマットを指定する
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		// 翌月の〇月1日をLocalDate型で取得する
		LocalDate nextDateHead = LocalDate.parse(sb, formatter);

		// 現在から定年までの月数を集計する
		int nextMonthsBetween = (int) ChronoUnit.MONTHS.between(nextDateHead, teinenDate);

		try {
			// 翌月の頭～定年までの休日日数を取得する
			holidayCnt = UtuutuHoliday.getHoliday(nextDateHead.getYear(),
					nowDate.plusMonths(1).getMonthValue(), nextMonthsBetween);
			// 今月の残り休日日数を取得する
			thisholidayCnt = UtuutuHoliday.getHolidayLeft(nowDate.getYear(), nowDate.getMonthValue(),
					nowDate.getDayOfMonth());

		} catch (AJDException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//nowDate = LocalDate.of(2063, 04, 30);
		// 現在から定年までの日数を取得する
		int allDays = (int) ChronoUnit.DAYS.between(nowDate, teinenDate);
		int workingDays = allDays - holidayCnt - thisholidayCnt;

		if (allDays > 0) {
			System.out.println("【鬱々カレンダーはここから下】");
			System.out.println("あなたの働く日数は" + workingDays + "日です");
		} else if (allDays == 0) {
			System.out.println("【鬱々カレンダーはここから下】");
			System.out.println("本日が定年日です");
		} else {
			System.out.println("【鬱々カレンダーはここから下】");
			System.out.println("あなたは定年を過ぎています");
		}
		/**************************** 現在から定年までの出勤日数を取得する処理ここまで ****************************/

	}

	/**
	 * 生年月日入力チェック
	 * @param birthdate
	 * 		生年月日
	 * @return
	 * 		正しい書式で入力されている場合はtrueを返す
	 * 		間違った書式で入力されている場合はfalseを返す
	 */
	private static boolean checkDate(String birthdate) {

		// 書式化文字列を指定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		// 日付/時刻解析を厳密に行うかどうかを設定する。
		sdf.setLenient(false);

		try {
			sdf.parse(birthdate);
			formatter.parse(birthdate);
			// 正しい書式で入力されている場合はtrue

			// 生年月日の日付を取得する(LocalDate型)
			LocalDate localBirthdate = LocalDate.parse(birthdate, formatter);
			// 現在の日付を取得する
			LocalDate nowDate = LocalDate.now();

			// もしも未来だった場合はfalseを返す
			if (ChronoUnit.DAYS.between(localBirthdate, nowDate) < 0) {
				System.out.println("未来の生年月日は入力できません。");
				return false;
			}

			// 正しい書式で入力されている場合はtrue
			return true;
		} catch (DateTimeParseException | ParseException e) {
			System.out.println("正しい書式で入力してください");

			return false;
		}
	}

	/**
	 * 翌月の○○○○年〇〇月01日を生成するメソッド
	 * @param thisYear
	 * 		現在の年
	 * @param nextMonth
	 * 		翌月
	 * @return
	 * 		現在の年/翌月/01 (String型)
	 */
	private static StringBuilder createNextMonth(int thisyear, int nextMonth) {

		// 翌月の〇月1日を作る
		int thisYear = thisyear;
		// テスト
		//nextMonth = 10;
		String s = thisYear + "/" + nextMonth + "/01";

		// 10月より小さい場合は文字列連結する。(1月～9月の接頭辞に「0」を連結する)
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		// 1月～9月の場合は、接頭辞に0をつける
		if (nextMonth < 10) {
			sb.insert(5, "0");
		}

		return sb;

	}

}
