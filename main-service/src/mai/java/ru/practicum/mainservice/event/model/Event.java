package ru.practicum.mainservice.event.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.user.model.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, length = 2000)
    String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;
    @Column(name = "confirmed_requests")
    Long confirmedRequests;
    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn;
    @Column(nullable = false, length = 7000)
    String description;
    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    User initiator;
    @Embedded
    Location location;
    @Column(nullable = false)
    boolean paid;
    @Builder.Default
    @Column(name = "participant_limit", nullable = false)
    Long participantLimit = 0L;
    @Column(name = "published_on")
    LocalDateTime publishedOn;
    @Builder.Default
    @Column(name = "request_moderation", nullable = false)
    boolean requestModeration = true;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    State state;
    @Column(nullable = false, length = 120)
    String title;
    Long views;
}