package co.com.tokenization.tokenization_api.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void shouldSendEmailSuccessfully() {
        // Arrange
        String to = "test@example.com";
        String subject = "Hello";
        String text = "This is a test";

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendSimpleMessage(to, subject, text);

        // Assert
        verify(mailSender).send(captor.capture());
        SimpleMailMessage sent = captor.getValue();

        assertEquals("tokenizationapi@gmail.com", sent.getFrom());
        assertEquals(to, sent.getTo()[0]);
        assertEquals(subject, sent.getSubject());
        assertEquals(text, sent.getText());
    }
}
