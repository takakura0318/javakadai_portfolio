package javakadai_portfolio.blackjack.util;

/**
 * プレイヤーの入力値をチェックするクラス
 *
 * @author たかくら
 *
 */
public class InputValidatorUtil {

	// staticメソッドのクラスなのでインスタンス化させない
	private InputValidatorUtil() {

	}

	/**
	 * Cpuの数を「1」「2」「3」のどれかか入力されているかをチェックする
	 *
	 * @return true:正しい値である false:間違った値である
	 */
	public static boolean isCpuNum(String inputNumberOfCpu) {
		// もしも入力された値が「1」「2」「3」のどれかだった場合はtrue
		if (inputNumberOfCpu.equals("1") || inputNumberOfCpu.equals("2") || inputNumberOfCpu.equals("3"))
			return true;

		return false;
	}

	/**
	 * Playerの入力値に2(スタンド)が入力されているかをチェックする
	 *
	 * @return true:正しい値である false:間違った値である
	 */
	public static boolean isStand(String stand) {

		if (stand.equals("2"))
			return true;

		return false;

	}

	/**
	 * Playerの入力値に1(ヒット)が入力されているかをチェックする
	 *
	 * @return true:正しい値である false:間違った値である
	 */
	public static boolean isHit(String hit) {

		if (hit.equals("1"))
			return true;

		return false;

	}

	/**
	 * 入力値が「表」もしくは「裏」のどちらかが入力されているかをチェックするメソッド
	 *
	 * @param coinInput
	 *            入力値(「表」か「裏」のどらか)
	 * @return コインの予想(「表」か「裏」のどらか)
	 */
	public static boolean isHeadsOrTails(String coinInput) {
		// もしも入力された値が「表」もしくは「裏」だった場合
		if (coinInput.equals("表") || coinInput.equals("裏"))
			return true;

		return false;
	}

}
