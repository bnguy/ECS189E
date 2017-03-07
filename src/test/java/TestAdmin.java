import api.IAdmin;
import api.core.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pikachu on 3/7/2017.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void test_makeClassSuccess() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        //check the class exists
        assertTrue(this.admin.classExists("Test", 2017));
        //check that class not created does not exist
        assertFalse(this.admin.classExists("TestB", 2017));
    }

    @Test
    public void test_yearInPast() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        //check can't make class in the past
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void test_capacityGreaterThanZero() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        //check capacity cannot be zero
        assertFalse(this.admin.classExists("Test", 2016));
        this.admin.createClass("Test", 2017, "Instructor", -100);
        //check capacity cannot be negative
        assertFalse(this.admin.classExists("Test", 2016));
    }

    /*@Test
    public void test_capacitySuccessfullyChanged() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        int changedCapacity = 10;
        this.admin.changeCapacity("Test", 2017, changedCapacity);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == changedCapacity);
    }
    @Test
    public void test_yearInPast() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    @Test
    public void test_yearInPast() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    @Test
    public void test_yearInPast() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }*/
}
