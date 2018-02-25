package com.taobao.dao.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Create by zhangpe0312@qq.com on 2018/2/24.
 * <p>
 * 篮球实体
 */
@Entity
@Table(name = "basketball")
public class Basketball {


    private String basketballID;

    //是否损坏
    private int isBad;

    //是否出租
    private int isRent;

    //篮球型号
    private String model;

    //这个篮球正常使用的压力值
    private int pressure;

    //上架时间
    private Date createTime;

    //一个篮球只有一个出租规则 一个出租规则由多个篮球  多方 维护端
    private Rent rent;

    //一个篮球属于一个订单 被维护端
    private Order order;

    //set and get

    @Id
    @Column(name = "id", unique = true, length = 32, nullable = false)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    public String getBasketballID() {
        return basketballID;
    }

    public void setBasketballID(String basketballID) {
        this.basketballID = basketballID;
    }

    @Column(name = "isBad", nullable = false)
    public int getIsBad() {
        return isBad;
    }

    public void setBad(int bad) {
        isBad = bad;
    }

    @Column(name = "isRent", nullable = false)
    public int getIsRent() {
        return isRent;
    }

    public void setRent(int rent) {
        isRent = rent;
    }

    @Column(name = "model", nullable = false, length = 10)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "pressure", nullable = false, length = 10)
    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    @Column(name = "createTime", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @OneToOne(mappedBy = "basketball", cascade = CascadeType.ALL)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setIsBad(int isBad) {
        this.isBad = isBad;
    }

    public void setIsRent(int isRent) {
        this.isRent = isRent;
    }


    @ManyToOne(targetEntity = Rent.class)
    @JoinColumn(name="rent")
    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
}
