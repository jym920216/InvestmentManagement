package com.winsigns.investment.inventoryService.capital.generalCapital;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.inventoryService.model.Capital;

@Entity
@Relation(value = "capital", collectionRelation = "capitals")
@DiscriminatorValue("CHINA_GENERAL_CAPITAL_ACCOUNT")
public class GeneralCapital extends Capital {

}
