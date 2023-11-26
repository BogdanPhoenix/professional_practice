package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "comment_task")
public class CommentTask implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_comment")
    private Long indexComment;

    @ManyToOne
    @JoinColumn(name = "index_task")
    @NonNull
    private TaskTesting taskTesting;

    @ManyToOne
    @JoinColumn(name = "index_performer")
    @NonNull
    private Employee performer;

    @Column(name = "comment_text", length = 750)
    @NonNull
    private String commentText;

    @Column(name = "date_time_create")
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "current_data")
    private boolean currentData = true;

    @Override
    public Long getId() {
        return indexComment;
    }
}
