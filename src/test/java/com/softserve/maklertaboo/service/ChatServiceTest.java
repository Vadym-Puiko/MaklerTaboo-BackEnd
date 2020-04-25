package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.enums.UserStatus;
import com.softserve.maklertaboo.entity.user.User;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@PrepareForTest(ChatService.class)
class ChatServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    ChatService chatService;

    @BeforeAll
    static void beforeAll(){
        ApiContextInitializer.init();

    }

    private User user1 = new User(1L, "user1", "", "", "", "", UserRole.ROLE_USER, UserStatus.ACTIVATED );
    private User user2 = new User(1L, "user1", "", "", "", "", UserRole.ROLE_USER, UserStatus.ACTIVATED );
    private UserDto user1Dto = new UserDto(1L, "user1", "", "", "", "", "");
    private UserDto user2Dto = new UserDto(1L, "user1", "", "", "", "", "");

    @Test
    void getChatId() {
       // when(userService.findByUsername(anyString())).thenReturn(user1Dto);
       // when(userService.findUserById(anyLong())).thenReturn(user2Dto);
       // OngoingStubbing<Boolean> you_cant_chat_with_yourself = when(user2Dto.equals(user1Dto)).thenThrow(new IllegalArgumentException("YOU CANT CHAT WITH YOURSELF"));
        // assertEquals(, chatService.getChatId("user1", 1L));

    }
}
