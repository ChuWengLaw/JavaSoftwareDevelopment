package ControlPanel.schedule.ScheduleTests;

import Server.Request.CreateBBRequest;
import Server.Request.ScheduleBillboardRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScheduleBillboard {
    // Session token for testing
    SessionToken token = new SessionToken("token", LocalDateTime.now());


    @Test
    public void TestScheduleRequestBillboardName() {
        ScheduleBillboardRequest scheduleBillboardRequest = new ScheduleBillboardRequest("BillboardName", "2020-05-31 12:00:00", "20", "3", "60", token, "admin");
        assertEquals("BillboardName", scheduleBillboardRequest.getBillboardName());
    }

    @Test
    public void TestScheduleRequestDateTime() {
        ScheduleBillboardRequest scheduleBillboardRequest = new ScheduleBillboardRequest("BillboardName", "2020-05-31 12:00:00", "20", "3", "60", token, "admin");
        assertEquals("2020-05-31 12:00:00", scheduleBillboardRequest.getScheduledTime());
    }

    @Test
    public void TestScheduleRequestDuration() {
        ScheduleBillboardRequest scheduleBillboardRequest = new ScheduleBillboardRequest("BillboardName", "2020-05-31 12:00:00", "20", "3", "60", token, "admin");
        assertEquals(20, scheduleBillboardRequest.getDuration());
    }

    @Test
    public void TestScheduleRequestCreator() {
        ScheduleBillboardRequest scheduleBillboardRequest = new ScheduleBillboardRequest("BillboardName", "2020-05-31 12:00:00", "20", "3", "60", token, "admin");
        assertEquals("admin", scheduleBillboardRequest.getUserName());
    }
}