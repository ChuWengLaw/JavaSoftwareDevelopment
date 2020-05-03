//package Main;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.*;
//
///* The following tests are for the basic functionality of
// * the Billboard class
// *
// * Here we have added comments to explain what each test
// * obliges you to do during Test-Driven Development
// */
//
//public class BillboardTest {
//
//    /* Test 0: Declaring Billboard object
//     * [This test obliges you to create a class called "Billboard".]
//     */
//    Billboard myBillboard;
//
//
//    /* Test 1: Constructing a Billboard object
//     * [This test obliges you to add a parameterless constructor
//     * to Billboard class.]
//     */
//    @BeforeEach @Test
//    public void setUpBillboard() {
//        myBillboard = new Billboard();
//    }
//
//    /* Test 2: Create a new billboard to the list
//     * [This test obliges you to add a method called "CreateBillboard",
//     * It will return sql code in string if the
//     * billboard is successfully created.)]
//     */
//    @Test
//    public void createABillboard() throws BillboardException {
//        String newBillboard = myBillboard.CreateBillboard("Star Wars");
//        assertEquals("SQL CODE", newBillboard,
//                "Failed to create billboard");
//    }
//
//    /* Test 3: Delete an existing billboard from the list
//     * [This test obliges you to add a method called "DeleteBillboard"
//     * It will return sql code in string if the
//     * billboard is successfully deleted.)]
//     */
//    @Test
//    public void deleteABillboard() throws BillboardException {
//        myBillboard.CreateBillboard("Toy Story");
//        String deleteBillboard = myBillboard.DeleteBillboard("Toy Story");
//        assertEquals("SQL CODE", deleteBillboard,
//                "Failed to delete billboard");
//    }
//
//    /* Test 4: Can't delete unknown billboard
//     * [This test obliges method "DeleteBillboard" to throw an exception
//     * if it is deleting a non-exiting billboard.]
//     */
//    @Test
//    public void deleteUnknownBillboard() throws BillboardException {
//        assertThrows(BillboardException.class, () -> {
//            myBillboard.CreateBillboard("Toy Story");
//            myBillboard.DeleteBillboard("Harry Potter");
//        });
//    }
//
//    /* Test 5: List all the billboards in the list
//     * [This test obliges you to add a method called "ListBillboards"
//     * It will return sql code in string if all the
//     * billboards are successfully retrieved.)]
//     */
//    @Test
//    public void listAllBillboards() throws BillboardException {
//        myBillboard.CreateBillboard("Toy Story");
//        myBillboard.CreateBillboard("Star Wars");
//        myBillboard.CreateBillboard("Harry Potter");
//        String listBillboard = myBillboard.ListBillboards();
//        assertEquals("SQL CODE", listBillboard,
//                "Failed to list all billboards");
//    }
//
//    /* Test 6: Retrieve an existing billboard content from the list
//     * [This test obliges you to add a method called "GetBillboard"
//     * It will return sql code in string if the
//     * billboard is successfully retrieved.)]
//     */
//    @Test
//    public void getABillboard() throws BillboardException { //TODO: add contents in CreateBillboard
//        myBillboard.CreateBillboard("Toy Story");
//        String getBillboard = myBillboard.GetBillboard("Toy Story");
//        assertEquals("SQL CODE", getBillboard,
//                "Failed to retrieve the billboard info");
//    }
//
//    /* Test 7: Can't retrieve unknown billboard
//     * [This test obliges method "GetBillboard" to throw an exception
//     * if it is retrieving a non-exiting billboard.]
//     */
//    @Test
//    public void getUnknownBillboard() throws BillboardException {
//        assertThrows(BillboardException.class, () -> {
//            myBillboard.CreateBillboard("Toy Story");
//            myBillboard.GetBillboard("Harry Potter");
//        });
//    }
//
////    View Schedule
////    Schedule Billboard
////    Remove Billboard from Schedule
//}
