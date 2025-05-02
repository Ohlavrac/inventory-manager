package com.ohlavrac.inventory_manager.domain.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "category")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "category_name", unique = true)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<ProductEntity> products;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp
    private Date updated_at;
}