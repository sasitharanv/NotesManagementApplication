package com.notesManagement.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull(message = "Title can not be null")
    private String title;

    @Column(length = 1000)
    private String content;

    private LocalDateTime creationDate =LocalDateTime.now();
    private  LocalDateTime lastEditedDate;

    private boolean isArchived=false;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userId")
    @NotNull(message = "User must be specified")
    private User user;
}
