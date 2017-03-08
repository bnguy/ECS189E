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
public class TestStudent {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {   
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
        this.admin.createClass("TestA", 2017, "Instructor", 15);
        this.admin.createClass("SmallClass", 2017, "Instructor", 2);
    }

    //register for class that doesnt exist or overcapacity
    @Test
    public void test_registerForClass() {
        this.student.registerForClass("Student1", "TestA", 2017);
        assertTrue(this.student.isRegisteredFor("Student1", "TestA", 2017));

        //register for a class that doesnt exist
        this.student.registerForClass("Student1", "TestB", 2017);
        assertFalse(this.student.isRegisteredFor("Student1", "TestB", 2017));

        //overcapacity class
        this.student.registerForClass("Student1", "SmallClass", 2017);
        this.student.registerForClass("Student2", "SmallClass", 2017);
        this.student.registerForClass("Student3", "SmallClass", 2017);
        assertFalse(this.student.isRegisteredFor("Student3", "SmallClass", 2017));
    }

    //check drop class
    @Test
    public void test_dropClass() {
        this.student.registerForClass("Student1", "TestA", 2017);
        this.student.dropClass("Student1", "TestA", 2017);
        assertFalse(this.student.isRegisteredFor("Student1", "TestA", 2017));
    }

    //check submit homework
    @Test
    public void test_submitHomework() {
        this.student.registerForClass("Student1", "TestA", 2017);
        this.instructor.addHomework("Instructor", "TestA", 2017, "Hw1", "Make Hello World");
        this.student.submitHomework("Student1", "Hw1", "answers", "TestA", 2017);
        assertTrue(this.student.hasSubmitted("Student1", "Hw1", "TestA", 2017));

        //submit for hw that does not exist
        this.student.submitHomework("Student1", "Hw2", "answers", "TestA", 2017);
        assertFalse(this.student.hasSubmitted("Student1", "Hw2", "TestA", 2017));

        //submit for class that does not exist
        this.student.submitHomework("Student1", "Hw1", "answers", "TestB", 2017);
        assertFalse(this.student.hasSubmitted("Student1", "Hw1", "TestB", 2017));

        //submit when student is not registered
        this.student.submitHomework("Student2", "Hw1", "answers", "TestA", 2017);
        assertFalse(this.student.hasSubmitted("Student2", "Hw1", "TestA", 2017));

        //submit for class in past year
        this.student.submitHomework("Student1", "Hw1", "answers", "TestA", 2016);
        assertTrue(this.student.hasSubmitted("Student1", "Hw1", "TestA", 2016));
    }
}
