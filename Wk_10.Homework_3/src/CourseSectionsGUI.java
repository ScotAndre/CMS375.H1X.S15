/*
 *  CourseSectionsGUI.java
 *  
 *  SELECT course_name, section_num, semester
 *  FROM course, section
 *  WHERE course.course_id = section.course_id
 *  AND course.course_id = 10;
 */
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CourseSectionsGUI extends JFrame {
	private String SQL1 = "SELECT * FROM course";
	private String SQL2 = "SELECT course_name, section_num, semester FROM course, section WHERE course.course_id = section.course_id AND course_id = ";
	
	public CourseSectionsGUI() {
		super("Courses");
		AnySelect as = new AnySelect();
		String courseString = as.execute(SQL1);
		makeCourseList(courseString);

		// Build the main window
		JPanel mainFrame;
		setBounds(100, 100, 425, 425);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame = new JPanel();
		mainFrame.setLayout(new GridLayout(2,1));
		setContentPane(mainFrame);

		String[] myChoices = {"Choice 1", "Choice 2", "Choice 3"};
		// top panel
		JPanel topPanel = new JPanel();
		mainFrame.add(topPanel);
		JComboBox courses = new JComboBox(myChoices);
		topPanel.setLayout(new GridLayout(2,1));
		topPanel.add(courses);
		
	}// end constructor
	
	public ArrayList<Course> makeCourseList(String courseString){
		ArrayList<Course> courses = new ArrayList<Course>();
		BufferedReader reader = new BufferedReader(new StringReader(courseString));
		String line = "";
		try {
			while((line = reader.readLine()) != null){
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return courses;
	}// end makeCourseList()
}// end class
