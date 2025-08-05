package com.side.positivehabit.config.batch.writer;

import com.side.positivehabit.config.batch.NotificationMessage;
import com.side.positivehabit.config.batch.NotificationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationWriter implements ItemWriter<NotificationMessage> {

    private final NotificationMessageRepository notificationMessageRepository;

    @Override
    public void write(List<? extends NotificationMessage> items) throws Exception {
        log.info("Writing {} notification messages to database", items.size());

        try {
            // 알림 메시지들을 데이터베이스에 저장
            for (NotificationMessage message : items) {
                // 상태를 PENDING으로 설정 (아직 발송되지 않음)
                message.setStatus(NotificationStatus.PENDING);

                NotificationMessage savedMessage = notificationMessageRepository.save(message);
                log.debug("Saved notification message: ID={}, Recipient={}",
                        savedMessage.getId(), savedMessage.getRecipient());
            }

            log.info("Successfully wrote {} notification messages", items.size());

        } catch (Exception e) {
            log.error("Error writing notification messages: {}", e.getMessage(), e);
            throw e;
        }
    }
}