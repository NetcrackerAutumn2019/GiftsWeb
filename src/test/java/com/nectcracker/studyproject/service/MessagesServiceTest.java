package com.nectcracker.studyproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-for-chat-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
@Sql(value = {"/delete-after-chat.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("b")
public class MessagesServiceTest {
    @Autowired
    private MessagesService messagesService;
    @Test
    public void testIfAllMessagesAreFound() {
        Map<String, String> messages = messagesService.findMessagesForChat(1L);
        assertThat(messages.size(), is(2));
        assertThat(messages.toString(), containsString("first message"));
        assertThat(messages.toString(), containsString("second message"));
    }

    @Test
    public void testIfWeCanReceiveMessage() {
        messagesService.receiveMessage("test message", 1L);
        Map<String, String> messages = messagesService.findMessagesForChat(1L);
        assertThat(messages.size(), is(3));
        assertThat(messages.toString(), containsString("test message"));
    }
}
