package com.blog.blog.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false, length = 255)
    private String name;

    @Column(name = "Description", nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "post" ,cascade = CascadeType.ALL)
    private List<Comment> comments ;

}
