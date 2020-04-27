package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.telegram.telegrambots.ApiContextInitializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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

    @Mock
    Page <Message> messages;

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
        when(messageRepository.findById(anyLong())).thenReturn(java.util.Optional.of(message));
        messageService.deleteMessage(0L);
        verify(messageRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void updateMessageWhenMessageNotFound() {
        Message message = new Message();
        message.setId(0L);
        when(messageRepository.findById(message.getId())).thenReturn(java.util.Optional.of(message));
        assertThrows(IllegalArgumentException.class, ()-> messageService.updateMessage(1L));
    }

    @Test
    void updateMessage() {
        Message message = new Message();
        message.setId(0L);
        when(messageRepository.findById(anyLong())).thenReturn(java.util.Optional.of(message));
        messageService.updateMessage(message.getId());
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    @SuppressWarnings("unchecked")
    void getMessageByChatIdValue() {
        messages =  Mockito.mock(Page.class);
        Mockito.when(this.messageRepository.findAllByChatId(anyLong(),
                org.mockito.Matchers.isA(Pageable.class))).thenReturn(messages);
        assertEquals(messages, messageService.getMessageByChatId(1L, Pageable.unpaged()));
    }

    @Test
    void getMessageByChatId() {
        Pageable pageable = PageRequest.of(1, 25, Sort.by("id").descending());
        messageService.getMessageByChatId(1L, pageable);
        verify(messageRepository, times(1)).findAllByChatId(1L, pageable);
    }
}
