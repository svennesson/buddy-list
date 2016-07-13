package se.svennesson.svennelist.model;

import javax.persistence.*;

@MappedSuperclass
public class AbstractEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false, updatable = false)
    protected Long id;

    public AbstractEntity() {}

    public Long getId() {
        return id;
    }
}
