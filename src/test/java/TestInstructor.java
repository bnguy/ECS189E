import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pikachu on 3/7/2017.
 */
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
        this.admin.createClass("TestA", 2017, "Instructor", 15);
        this.student.registerForClass("Student1", "TestA", 2017);
    }

    //check if can assign homework
    @Test
    public void test_assignHomework() {
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        assertTrue(this.instructor.homeworkExists("TestA", 2017, "Hw1"));

        //test if other instructor can assign homework
        this.instructor.addHomework("InstructorA", "TestA", 2017, "Hw1", "Make Hello World");
        assertFalse(this.instructor.homeworkExists("TestA", 2017, "Hw1"));

        //test if class doesn't exist
        this.instructor.addHomework("InstructorA", "TestB", 2017, "Hw1", "Make Hello World");
        assertFalse(this.instructor.homeworkExists("TestB", 2017, "Hw1"));

        //test if add homework to past class
        this.instructor.addHomework("InstructorA", "TestB", 2016, "Hw1", "Make Hello World");
        assertFalse(this.instructor.homeworkExists("TestB", 2016, "Hw1"));
    }

    //assigning grades
    @Test
    public void test_assignGradeInstructor() {
        //homework has not been assigned
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student1", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);

        //instructor is teaching the class when assigning grade
        this.instructor.assignGrade("InstructorA", "TestA", 2017, "Hw1", "Student1", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);

        //instructor assigns grade before student submits
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student1", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);

        this.student.submitHomework("Student1", "Hw1", "I did Hello World", "TestA", 2017);
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student1", 2);
        assertTrue(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);
        //assign grade for a student not in the class
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student2", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student2") == 2);
        //assign grade for homework that does not exist
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw2", "Student1", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw2", "Student1") == 2);
    }
}
