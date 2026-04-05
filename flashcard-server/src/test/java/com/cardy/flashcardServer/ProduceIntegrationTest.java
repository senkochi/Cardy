package com.cardy.flashcardServer;

import com.cardy.flashcardServer.service.CardSetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProduceIntegrationTest extends BaseIntegrationTest {

    @MockitoBean
    private CardSetService cardSetService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSendProgress_Success() throws Exception {
        // 1. Giả lập: Khi check cardSetId "123" thì báo là CÓ tồn tại
        when(cardSetService.isCardSetExists("123")).thenReturn(true);

        // 2. Gọi API thật sự qua HTTP giả lập
        String json = "{\"userId\":\"user1\", \"cardSetId\":\"123\", \"result\":\"5\"}";
        mockMvc.perform(post("/learn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()); // Mong đợi trả về 200 OK

        // 3. Hệ thống lúc này đã tự chạy rabbitTemplate.convertAndSend vào Docker RabbitMQ rồi!
    }
}
