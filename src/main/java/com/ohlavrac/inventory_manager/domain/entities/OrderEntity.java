package com.ohlavrac.inventory_manager.domain.entities;

import java.util.UUID;

import org.hibernate.annotations.SecondaryRow;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "order")
@Entity
@Getter
@SecondaryRow
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String orderName;
    private int quantOrder;
    
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productOrder;
}
