import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pikachu on 3/7/2017.
 */
public class TestAdmin {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    //create class
    @Test
    public void test_createClass_exist() {
        //check if the class exists after creation
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test1", 2017));

        assertFalse(this.admin.classExists("TestB", 2017));
    }

    @Test
    public void test_createClass_past() {
        //check if class can be made in past
        this.admin.createClass("Test2", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test2", 2016));
    }

    @Test
    public void test_createClass_capacityCheck_zero() {
        //check capacity cannot be zero
        this.admin.createClass("Test3", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    @Test
    public void test_createClass_capacityCheck() {
        //check capacity cannot be negative
        this.admin.createClass("Test4", 2017, "Instructor", -100);
        assertFalse(this.admin.classExists("Test4", 2017));
    }

    @Test
    public void test_createClass_instructorTwoClasses() {
        //check instructor can only teach two classes
        this.admin.createClass("TestA", 2017, "Instructor", 15);
        this.admin.createClass("TestB", 2017, "Instructor", 15);
        this.admin.createClass("TestC", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("TestC", 2017));
    }

    @Test
    public void test_createClass_twoInstructorsOneClass() {
        //check two instructors for one class
        this.admin.createClass("TestA", 2017, "Instructor", 15);
        this.admin.createClass("TestA", 2017, "InstructorA", 15);
        assertEquals("InstructorA", this.admin.getClassInstructor("TestA", 2017));
    }

    //check about changing capacity
    @Test
    public void test_capacitySuccessfullyChanged_same() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        //check capacity changed to the same thing
        this.admin.changeCapacity("Test", 2017, 15);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 15);
    }

    @Test
    public void test_capacitySuccessfullyChanged_higher() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        int changedCapacity = 20;
        this.admin.changeCapacity("Test1", 2017, changedCapacity);
        assertTrue(this.admin.getClassCapacity("Test1", 2017) == changedCapacity);
    }

    @Test
    public void test_capacitySuccessfullyChanged_lower() {
        this.admin.createClass("SmallClass", 2017, "Instructor", 2);
        this.student.registerForClass("Student1", "SmallClass", 2017);
        this.student.registerForClass("Student2", "SmallClass", 2017);
        int changedCapacity2 = 1;
        this.admin.changeCapacity("SmallClass", 2017, changedCapacity2);
        assertTrue(this.admin.getClassCapacity("SmallClass", 2017) != changedCapacity2);
    }

}
