package Main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/* The following tests are for the basic functionality of
 * the Billboard class
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class BillboardTest {

    /* Test 0: Declaring Billboard object
     * [This test obliges you to create a class called "Billboard".]
     */
    Billboard mybillboard;


    /* Test 1: Constructing a Billboard object
     * [This test obliges you to add a parameterless constructor
     * to Billboard class.]
     */
    @BeforeEach @Test
    public void setUpBillboard() {
        mybillboard = new Billboard();
    }

    /* Test 2: Create a new billboard to the list
     * [This test obliges you to add a method called "CreateBillboard",
     * It will return sql code in string if the
     * billboard is successfully created.)]
     */
    @Test
    public void createABillboard() throws BillboardException {
        String newBillboard = mybillboard.CreateBillboard("Star Wars");
        assertEquals("SQL CODE", newBillboard,
                "Failed to create billboard");
    }

    /* Test 3: Can't create the same billboard twice
     * [This test obliges method "CreateBillboard" to throw an exception
     * if it is given the same billboard name more than once.]
     */
    @Test
    public void createDuplicateBillboard() {
        assertThrows(BillboardException.class, () -> {
            mybillboard.CreateBillboard("What Does The Fox Say?");
            mybillboard.CreateBillboard("What Does The Fox Say?");
        });
    }

    /* Test 4: Edit an existing billboard in the list
     * [This test obliges you to add a method called "EditBillboard"
     * It will return sql code in string if the
     * billboard is successfully edited.)]
     */
    @Test
    public void editABillboard() throws BillboardException {
        mybillboard.CreateBillboard("Toy Story");
        String editBillboard = mybillboard.EditBillboard("Toy Story", "There's a snake in my boot!");
        assertEquals("SQL CODE", editBillboard,
                "Failed to edit billboard");
    }

    /* Test 5: Can't edit unknown billboard
     * [This test obliges method "EditBillboard" to throw an exception
     * if it is editing a non-exiting billboard.]
     */
    @Test
    public void editUnknownBillboard() throws BillboardException {
        assertThrows(BillboardException.class, () -> {
            mybillboard.CreateBillboard("Toy Story");
            mybillboard.EditBillboard("Toy Story", "There's a snake in my boot!");
        });
    }

    /* Test 6: Delete an existing billboard from the list
     * [This test obliges you to add a method called "DeleteBillboard"
     * It will return sql code in string if the
     * billboard is successfully deleted.)]
     */
    @Test
    public void deleteABillboard() throws BillboardException {
        mybillboard.CreateBillboard("Toy Story");
        String deleteBillboard = mybillboard.DeleteBillboard("Toy Story");
        assertEquals("SQL CODE", deleteBillboard,
                "Failed to delete billboard");
    }

    /* Test 7: Can't delete unknown billboard
     * [This test obliges method "DeleteBillboard" to throw an exception
     * if it is deleting a non-exiting billboard.]
     */
    @Test
    public void deleteUnknownBillboard() throws BillboardException {
        assertThrows(BillboardException.class, () -> {
            mybillboard.CreateBillboard("Toy Story");
            mybillboard.DeleteBillboard("Harry Potter");
        });
    }

    /* Test 8: List all the billboards in the list
     * [This test obliges you to add a method called "ListBillboards"
     * It will return sql code in string if all the
     * billboards are successfully retrieved.)]
     */
    @Test
    public void listAllBillboards() throws BillboardException {
        mybillboard.CreateBillboard("Toy Story");
        mybillboard.CreateBillboard("Star Wars");
        mybillboard.CreateBillboard("Harry Potter");
        String listBillboard = mybillboard.ListBillboards();
        assertEquals("SQL CODE", listBillboard,
                "Failed to list all billboards");
    }

    /* Test 9: Retrieve an existing billboard content from the list
     * [This test obliges you to add a method called "GetBillboard"
     * It will return sql code in string if the
     * billboard is successfully retrieved.)]
     */
    @Test
    public void getABillboard() throws BillboardException { //TODO: add contents in CreateBillboard
        mybillboard.CreateBillboard("Toy Story");
        String getBillboard = mybillboard.GetBillboard("Toy Story");
        assertEquals("SQL CODE", getBillboard,
                "Failed to retrieve the billboard info");
    }

    /* Test 10: Can't retrieve unknown billboard
     * [This test obliges method "GetBillboard" to throw an exception
     * if it is retrieving a non-exiting billboard.]
     */
    @Test
    public void getUnknownBillboard() throws BillboardException {
        assertThrows(BillboardException.class, () -> {
            mybillboard.CreateBillboard("Toy Story");
            mybillboard.GetBillboard("Harry Potter");
        });
    }

//    View Schedule
//    Schedule Billboard
//    Remove Billboard from Schedule
}
