package com.winsigns.investment.inventoryService.capital.generalCapital;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

@Entity
@Relation(value = "capital-pool", collectionRelation = "capital-pools")
@DiscriminatorValue("general_capital_pool")
public class GeneralCapitalPool extends FundAccountCapitalPool {

}
