package author_hirase.calculator;

public class Main {

	public static void main(String[] args) {

		System.out.println("半角で入力してください。");
		System.out.println("負の値を使用するときは（）で囲ってください。");

		Calculator calc = new Calculator();

		calc.execute();

	}

}
