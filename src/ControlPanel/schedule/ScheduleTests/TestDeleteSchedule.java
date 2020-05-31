package ControlPanel.schedule.ScheduleTests;

import Server.Request.CreateBBRequest;
import Server.Request.DeleteScheduleRequest;
import Server.Request.ScheduleBillboardRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeleteSchedule {
    // Session token for testing
    SessionToken token = new SessionToken("token", LocalDateTime.now());


    //public DeleteScheduleRequest(String ScheduledName, String ScheduledTime, SessionToken sessionToken) {
    @Test
    public void TestDeleteScheduleBillboardName() {
        DeleteScheduleRequest deleteScheduleRequest = new DeleteScheduleRequest("BillboardName", "2020-05-31 12:00:00", token);
        assertEquals("BillboardName", deleteScheduleRequest.getScheduledName());
    }
    @Test
    public void TestDeleteScheduleDateTime() {
        DeleteScheduleRequest deleteScheduleRequest = new DeleteScheduleRequest("BillboardName", "2020-05-31 12:00:00", token);
        assertEquals("2020-05-31 12:00:00", deleteScheduleRequest.getScheduledTime());
    }

    @Test
    public void TestDeleteScheduleToken() {
        DeleteScheduleRequest deleteScheduleRequest = new DeleteScheduleRequest("BillboardName", "2020-05-31 12:00:00", token);
        assertEquals(token, deleteScheduleRequest.getSessionToken());
    }
}