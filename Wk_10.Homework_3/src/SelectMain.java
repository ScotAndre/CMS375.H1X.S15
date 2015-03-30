import java.awt.EventQueue;



public class SelectMain {

	public static void main(String[] args) {
//		AnySelect as = new AnySelect();
//		String SQL1 = "SELECT course_name AS Course FROM course";
//		String SQL2 = "SELECT course_name AS Course, section_num as Section, semester WHERE course.course_id = section.course_id AND course_name LIKE 'INTRO TO JAVA'";
//		System.out.println(as.execute(SQL1));
//		System.out.println("\n\n" + as.execute(SQL2));
		// run GUI
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CourseSectionsGUI frame = new CourseSectionsGUI();
					frame.setTitle("Courses");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}// end main()
}// end class
