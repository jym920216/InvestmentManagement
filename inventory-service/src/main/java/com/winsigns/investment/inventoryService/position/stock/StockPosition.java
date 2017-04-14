package com.winsigns.investment.inventoryService.position.stock;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.inventoryService.model.Position;

@Entity
@Table(name = "stock_position")
@Relation(value = "position", collectionRelation = "positions")
@DiscriminatorValue("stock-position")
public class StockPosition extends Position {


}
