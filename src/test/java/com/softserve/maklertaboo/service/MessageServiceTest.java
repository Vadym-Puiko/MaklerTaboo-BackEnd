package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.ApiContextInitializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@PrepareForTest(MessageService.class)
class MessageServiceTest {


    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageService messageService;

    @BeforeAll
    static void beforeAll(){
        ApiContextInitializer.init();
    }

    @Test
    void addMessage() {
        Message message = new Message();
        message.setId(1L);
        when(messageRepository.save(message)).thenReturn(message);
        assertEquals(message, messageService.addMessage(message));
    }


    @Test
    void deleteMessage() {
        Message message = new Message();
        message.setId(0L);
        when(messageRepository.findById(0L)).thenReturn(java.util.Optional.of(message));
        messageService.deleteMessage(0L);
        verify(messageRepository, times(1)).deleteById(anyLong());
    }


}
