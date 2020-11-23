package javakadai_portfolio.utuutu_calender;

import ajd4jp.AJD;
import ajd4jp.AJDException;
import ajd4jp.Holiday;
import ajd4jp.Month;
import ajd4jp.OffProvider;
import ajd4jp.Week;

/**
 * AJD4JP(ライブラリ)に関するクラス
 * 1.翌月頭～定年までの休日数を取得する
 * 2.今後の残り休日数を取得する
 * @author たかくら
 *
 */
public class UtuutuHoliday {

	/** 祝日と毎週土日を休日とする */
	public static OffProvider myCompanyOff = new OffProvider(true, Week.SATURDAY, Week.SUNDAY);

	/** 翌月の頭~定年までの休日数をカウントする変数cntを初期化 */
	public static int cnt = 0;

	/** 今月の残り休日数をカウントする変数sumを初期化 */
	public static int sum = 0;

	/**
	 * 翌月の頭~定年までの休日数を取得する
	 * @param thisYear
	 * 		現在の年
	 * @param nextMonth
	 * 		翌月
	 * @param nextMonthsBetween
	 * 		翌月頭～定年までの月数(繰り返し処理で使用)
	 * @return
	 * 		翌月の頭~定年までの休日数
	 */
	public static int getHoliday(int thisYear,int nextMonth, int nextMonthsBetween) throws AJDException {

		// yyyy/MM を設定。
		Month mon = new Month(thisYear, nextMonth);
		print(mon);

		// 翌月の頭から定年までの月数だけループを回す。
		for (int i = 1; i <= nextMonthsBetween; i++) {
			print(mon.add(i));
		}

		// 翌月の頭~定年までの休日数をreturnする
		return cnt;
	}

	/**
	 * 今月の残り休日数を取得する
	 * @param thisYear
	 * 		現在の年
	 * @param thisMonth
	 * 		今月
	 * @param days
	 * 		現在の日にち(1日~31日のいずれかが入る想定。変数「days」日付以降の休日数をカウントする為に使用する)
	 * @return
	 * 		今月の休日数
	 */
	public static int getHolidayLeft(int thisYear, int thisMonth, int days) throws AJDException {

		// yyyy/MM を設定。
		Month mon = new Month(thisYear, thisMonth);
		print(mon, days);

		// 今月だけの休日数を調べたいのでadd(0)する
		print(mon.add(0));

		// 今月の休日数をreturnする
		return sum;
	}


	/**
	 * 翌月～定年までの休日の数を変数cntカウントする
	 * @param mon
	 * 		年月(翌月)
	 */
	private static void print(Month mon) {

		for (AJD day : mon.getDays()) {

			// static変数から「祝日と毎週土日を休日」を取得する
			OffProvider.Off off = myCompanyOff.getOff(day);

			// もしも、「祝日と毎週土日を休日」がnullではない場合
			if (off != null) {
				if (off instanceof Holiday) {
					// もしも変数「off」が祝日だった場合は+1する
					cnt++;
				} else if (off instanceof Week) {
					// もしも変数「off」が土日だった場合は+1する
					cnt++;
				} else {
					// もしも変数「off」が「祝日」と「土日」以外場合は+1する(つまり、12/29等のカレンダー上休日に含まないもの)
					cnt++;
				}
			}
		}

	}


	/**
	 * 今月の残り休日数を変数sumにカウントする(オーバーロード(多重定義))
	 * @param mon
	 * 		年月(今月)
	 * @param days
	 *			今日の日にち(1日～31日のどれか)
	 */
	private static void print(Month mon, int days) {

		for (AJD day : mon.getDays()) {

			// static変数から「祝日と毎週土日を休日」を取得する
			OffProvider.Off off = myCompanyOff.getOff(day);

			// もしも、「祝日と毎週土日を休日」がnullではない場合
			if (off != null) {
				// もしも変数「off」がHoliday型のインスタンスだった場合は
				if (off instanceof Holiday) {
					// もしも変数「off」が祝日だった場合、&&、今日の日付以上の場合は+1する
					if (day.getDay() >= days) {sum++;};
				} else if (off instanceof Week) {
					// もしも変数「off」が土日だった場合、&&、今日の日付以上の場合は+1する
					if (day.getDay() >= days) {sum++;};
				} else {
					// もしも変数「off」が「祝日」と「土日」以外だった場合、&&、今日の日付以上の場合は+1する(つまり、12/29等のカレンダー上休日に含まないもの)
					if (day.getDay() >= days) {sum++;};
				}
			}
		}
	}

}
