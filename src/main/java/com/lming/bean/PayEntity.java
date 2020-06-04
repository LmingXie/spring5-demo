package com.lming.bean;

/**
 * @description: 支付实体
 * @program: TestSpringboot
 * @author: Lming
 * @create: 2020-05-21 11:24
 **/
public class PayEntity {
    private Integer type;
    private Integer amount;
    public PayEntity(){
        System.out.println("正在执行PayEntity的构造方法");
    }
    
    public Integer getType() {
        return type;
    }
    
    public PayEntity setType(Integer type) {
        this.type = type;
        return this;
    }
    
    public Integer getAmount() {
        return amount;
    }
    
    public PayEntity setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
    
    @Override
    public String toString() {
        return "PayEntity{" +
                "type=" + type +
                ", amount=" + amount +
                '}';
    }
}
