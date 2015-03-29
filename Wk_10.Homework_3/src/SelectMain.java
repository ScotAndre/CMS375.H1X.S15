
public class SelectMain {

	public static void main(String[] args) {
		AnySelect as = new AnySelect();
		String statement = "SELECT * FROM student";
		System.out.println(as.execute(statement));
	}

}
