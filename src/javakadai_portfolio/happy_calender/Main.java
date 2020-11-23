package javakadai_portfolio.happy_calender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

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
		LocalDate teinenDate = HappyCalender.getTeinenDate(birthdate);
		// 現在の日付を取得する
		LocalDate nowDate = LocalDate.now();

		/**************************** 現在から定年までの出勤日数を取得する処理 ****************************/
		// 現在から定年までの残り月数を取得する
		long localDiffMonths = ChronoUnit.MONTHS.between(nowDate, teinenDate);
		System.out.println("現在から定年の残り月数は" + localDiffMonths);

		// 現在から定年までの残り年数を取得。(例)42.666666666666664…年
		double DiffYear = (double) localDiffMonths / 12;

		// 整数と小数を分割
		BigDecimal bigDecimal = new BigDecimal(String.valueOf(DiffYear));

		// 分割された整数を取得。 (例)42
		// 原因はここの+1と+2の整合性である(あとで修正)
		int intValue = bigDecimal.intValue();

		// 今月を取得
		int thisMonth = nowDate.getMonthValue();
		//thisMonth = 1;
		intValue += (thisMonth == 1) ? 1 : 2;

		// 生涯給料を格納する変数「paySum」を初期化
		int paySum = 0;
		// 生涯ボーナスを格納する変数「bonusSummerSum」を初期化
		int bonusSummerSum = 0;
		// 生涯ボーナスを格納する変数「bonusWinterSum」を初期化
		int bonusWinterSum = 0;
		int age = 23; // 後で計算して代入
		// 社会人年数をカウントする
		int cnt = 1;
		int sum = 0;

		for (int i = 1; i <= intValue; i++) {
			for (int j = 1; j <= 12; j++) {
				// もしも現在が1月以外であれば、繰り返し変数「j」に今月を代入する
				if (thisMonth != 1)
					j = thisMonth;
				// もしも 現在の年齢が「65歳」&& 繰り返し変数「j(4月)」だった場合、ループを抜ける
				if (age == 65 && j == 4)
					break;
				// 4月に昇給
				if (j == 4)
					age += 1;
				// 現在~定年までループの分だけ、毎月の給料を合計する
				paySum += age;

				// 毎年6月にボーナス支給(年齢 + 10)
				if (j == 6)
					bonusSummerSum += (age + 10);
				// 毎年12月にボーナス支給(年齢 + 10)
				if (j == 12)
					bonusWinterSum += (age + 15);
				System.out.println("あなたの" + j + "月の給料は" + age + "万円です。");

				// 初期化
				thisMonth = 1;

				sum++;
				System.out.println(sum + "回です");

				// 社会人年数をカウント
				if (j == 4)
					cnt++;
			}
			// 確認用
			//			System.out.println("社会人生活" + cnt + "年目"+ age + "歳");
			//			System.out.println("あなたの夏ボーナス" +bonusSummerSum);
			//			System.out.println("あなたの冬ボーナス" +bonusWinterSum);

		}

		System.out.println("あなたの生涯給料は" + paySum);
		System.out.println("あなたの生涯夏ボーナスは" + bonusSummerSum);
		System.out.println("あなたの生涯冬ボーナスは" + bonusWinterSum);

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
