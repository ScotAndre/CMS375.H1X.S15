
public class SelectMain {

	public static void main(String[] args) {
		AnySelect as = new AnySelect();
		String statement = "SELECT * FROM course";
		String statement2 = "SELECT course_name, section_num, semester FROM course, section WHERE course.course_id = section.course_id AND section.course_id = 10";
		System.out.println(as.execute(statement));
		System.out.println();
		System.out.println();
		System.out.println(as.execute(statement2));
	}// end main()
}// end class
