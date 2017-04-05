package com.winsigns.investment.webGateway.http.entity;

/**
 * Created by wfl on 17-4-4.
 */
public class Instruction {
    //ID
    private Long id;

    // 投资组合
    private Long portfolioId;

    //基金产品
    private String fundName;

    //产品账户
    private String fundAccName;

    //投资组合
    private String portfolioName;

    // 投资标的
    private Long securityId;

    // 标的代码
    private String securityCode;

    // 标的名称
    private String securityName;

    // 投资服务
    private String investSvc;

    // 投资方向
    private String investDirection;

    // 成本价
    private Double costPrice;

    // 指令数量
    private Double quantity;

    //是否编辑状态
    private boolean editer;

    // 指令金额
    private Double amount;

    //
    private boolean selected;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundAccName() {
        return fundAccName;
    }

    public void setFundAccName(String fundAccName) {
        this.fundAccName = fundAccName;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public Long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Long securityId) {
        this.securityId = securityId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getInvestSvc() {
        return investSvc;
    }

    public void setInvestSvc(String investSvc) {
        this.investSvc = investSvc;
    }

    public String getInvestDirection() {
        return investDirection;
    }

    public void setInvestDirection(String investDirection) {
        this.investDirection = investDirection;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public boolean getEditer() {
        return editer;
    }

    public void setEditer(boolean editer) {
        this.editer = editer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
