package javakadai_portfolio.babanuki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		// Cpuの人数を決める
		int numberOfCpu = inputNumberOfCpu();
		// トランプカードを取得する
		List<String> deck = Card.createCard();

		// ゲームの準備を行う
		GameMaster master = new GameMaster();
		master.prepareGame(numberOfCpu, deck);

		// ゲームを開始する
		master.startGame();

	}

	private static int inputNumberOfCpu() {
		// Cpuの数を初期化
		String inputNumberOfCpu = null;
		/** 標準入力 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Cpuの数「1」「2」「3」のどれかか入力されるまで入力させる
		try {
			while (true) {
				System.out.println(String.format("Cpuの数を選びなさい(1～3で選ぶ)"));
				inputNumberOfCpu = reader.readLine();
				// cpuの数が「1」「2」「3」のどれかが入力されているかチェックする。
				if (isCpuNum(inputNumberOfCpu)) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Cpuの数「1」「2」「3」のどれかをint型で返す
		return Integer.parseInt(inputNumberOfCpu);
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

}
