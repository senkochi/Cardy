package com.cardy.learningServer.consumer;

import com.cardy.learningServer.domain.LearnProgress;
import com.cardy.learningServer.dto.LearnProgressDTO;
import com.cardy.learningServer.repository.LearningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LearnConsumer {

    private final LearningRepository learningRepository;

    @RabbitListener(queues = "learn.queue")
    @Transactional
    public void handleLearnProgress(LearnProgressDTO dto) {
        System.out.println("--- Nhận tin nhắn học tập từ RabbitMQ ---");
        System.out.println("User: " + dto.getUserId() + " | Card: " + dto.getCardSetId());

        LearnProgress progress = learningRepository.findByUserIdAndCardSetId(dto.getUserId(), dto.getCardSetId())
                .orElse(LearnProgress.builder()
                        .userId(dto.getUserId())
                        .cardSetId(dto.getCardSetId())
                        .easinessFactor(2.5)
                        .intervalDay(0)
                        .repetitions(0)
                        .build());

        // Thuật toán SM-2 đơn giản (Logic cốt lõi)
        // Interval: Số ngày sẽ gặp lại thẻ này
        // EF (Easiness Factor): Độ dễ của thẻ (mặc định 2.5)

        calculateSM2(progress, dto.getResult());

        progress.setNextReviewDate(LocalDate.now().plusDays(progress.getIntervalDay()));
        progress.setLastUpdate(LocalDateTime.now());

        learningRepository.save(progress);

        System.out.println("Đã cập nhật tiến độ cho Card: " + dto.getCardSetId()
                + ". Ngày học tiếp theo: " + progress.getNextReviewDate());
    }

    private void calculateSM2(LearnProgress p, int q) {
        if (q >= 3) { // User nhớ bài
            if (p.getRepetitions() == 0) p.setIntervalDay(1);
            else if (p.getRepetitions() == 1) p.setIntervalDay(6);
            else p.setIntervalDay((int) Math.round(p.getIntervalDay() * p.getEasinessFactor()));

            p.setRepetitions(p.getRepetitions() + 1);
        } else { // User quên bài
            p.setRepetitions(0);
            p.setIntervalDay(1);
        }

        // Cập nhật Easiness Factor (EF)
        double newEf = p.getEasinessFactor() + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02));
        p.setEasinessFactor(Math.max(1.3, newEf));
    }
}
