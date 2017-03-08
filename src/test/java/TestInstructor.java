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
    public void test_assignHomework_default() {
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        assertTrue(this.instructor.homeworkExists("TestA", 2017, "Hw1"));
    }

    @Test
    public void test_assignHomework_otherInstructor() {
        //test if other instructor can assign homework
        this.instructor.addHomework("InstructorA", "TestA", 2017, "Hw1", "Make Hello World");
        assertFalse(this.instructor.homeworkExists("TestA", 2017, "Hw1"));
    }

    @Test
    public void test_assignHomework_classDoesntExist() {
        //test if class doesn't exist
        this.instructor.addHomework("InstructorA", "TestB", 2017, "Hw1", "Make Hello World");
        assertFalse(this.instructor.homeworkExists("TestB", 2017, "Hw1"));
    }

    @Test
    public void test_assignHomework_pastClass() {
        //test if add homework to past class
        this.instructor.addHomework("InstructorA", "TestB", 2016, "Hw1", "Make Hello World");
        assertFalse(this.instructor.homeworkExists("TestB", 2016, "Hw1"));
    }

    //assigning grades
    @Test
    public void test_assignGradeInstructor_homeworkNotAssigned() {
        //homework has not been assigned
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student1", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);
    }

    @Test
    public void test_assignGradeInstructor_correctInstructor() {
        //instructor is teaching the class when assigning grade
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        this.instructor.assignGrade("InstructorA", "TestA", 2017, "Hw1", "Student1", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);
    }

    @Test
    public void test_assignGradeInstructor_studentSubmit() {
        //instructor assigns grade before student submits
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student1", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);
    }

    @Test
    public void test_assignGradeInstructor_default() {
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        this.student.submitHomework("Student1", "Hw1", "I did Hello World", "TestA", 2017);
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student1", 2);
        assertTrue(this.instructor.getGrade("TestA", 2017, "Hw1", "Student1") == 2);
    }

    @Test
    public void test_assignGradeInstructor_studentNotInClass() {
        //assign grade for a student not in the class
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        this.student.submitHomework("Student2", "Hw1", "I did Hello World", "TestA", 2017);
        this.instructor.assignGrade("Instructor", "TestA", 2017, "Hw1", "Student2", 2);
        assertFalse(this.instructor.getGrade("TestA", 2017, "Hw1", "Student2") == 2);
    }
}
