package com.winsigns.investment.fundService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.fundService.model.Fund;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.Portfolio;
import com.winsigns.investment.fundService.resource.FundTreeResource.FundItem;

import lombok.Getter;

public class FundTreeResource extends HALResponse<FundItem> {
  @Getter
  protected String shortName;

  public FundTreeResource(FundItem content) {
    super(content);
  }

  public FundTreeResource(Fund fund) {
    this(new FundItem(fund));
    this.shortName = fund.getShortName();
  }

  static protected class Item {
    @Getter
    protected Long id;

    @Getter
    protected String name;
  }

  @Relation(value = "fund", collectionRelation = "funds")
  static protected class FundItem extends Item {

    public FundItem(Fund fund) {
      this.id = fund.getId();
      this.name = fund.getName();
    }

  }

  static protected class FundAccountItem extends Item {

    public FundAccountItem(FundAccount fundAccount) {
      this.id = fundAccount.getId();
      this.name = fundAccount.getName();
    }

  }

  static protected class FundAccountItemResource extends HALResponse<FundAccountItem> {

    public FundAccountItemResource(FundAccountItem content) {
      super(content);
    }

    public FundAccountItemResource(FundAccount fundAccount) {
      this(new FundAccountItem(fundAccount));
    }

  }

  static protected class PortfolioItem extends Item {

    public PortfolioItem(Portfolio portfolio) {
      this.id = portfolio.getId();
      this.name = portfolio.getName();
    }

  }

  static protected class PortfolioItemResource extends HALResponse<PortfolioItem> {

    public PortfolioItemResource(PortfolioItem content) {
      super(content);
    }

    public PortfolioItemResource(Portfolio portfolio) {
      this(new PortfolioItem(portfolio));
    }

  }

}
