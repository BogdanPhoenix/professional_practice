package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "index_task")
    @NonNull
    private TaskTesting taskTesting;

    @ManyToOne
    @JoinColumn(name = "index_performer")
    @NonNull
    private Employee performer;

    @Column(name = "comment_text", unique = true, length = 750)
    @NonNull
    private String commentText;

    @Column(name = "date_time_create")
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "current_data")
    private boolean currentData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentTask that = (CommentTask) o;

        if (currentData != that.currentData) return false;
        return commentText.equals(that.commentText);
    }

    @Override
    public int hashCode() {
        return commentText.hashCode();
    }

    @Override
    public String toString() {
        return commentText;
    }
}
