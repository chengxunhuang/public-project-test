package com.project.test.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.test.server.exception.validator.SaveGroup;
import com.project.test.server.exception.validator.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: wh
 * @Date: 2022/06/18/14:55
 * @Description:
 */
@TableName("t_pay_order")
public class PayOrder implements Serializable {
    @TableId
    @NotNull(groups = {UpdateGroup.class}, message = "更新时主键不能为空")
    private String payOrderId;
    private String mchNo;
    private String isvNo;
    @NotBlank(groups = {SaveGroup.class, UpdateGroup.class}, message = "appId不能为空")
    private String appId;
    private String mchName;
    private Byte mchType;
    private String mchOrderNo;
    private String ifCode;
    private String wayCode;
    private Long amount;
    private BigDecimal mchFeeRate;
    private Long mchFeeAmount;
    private String currency;
    private String state;
    private Byte notifyState;
    private String clientIp;
    private String subject;
    private String body;
    private String channelExtra;
    private String channelUser;
    private String channelOrderNo;
    private Byte refundState;
    private Integer refundTimes;
    private Long refundAmount;
    private Byte divisionMode;
    private Byte divisionState;
    private Date divisionLastTime;
    private String errCode;
    private String errMsg;
    private String extParam;
    private String notifyUrl;
    private String returnUrl;
    private Date expiredTime;
    private Date successTime;
    private Date createdAt;
    private Date updatedAt;
    private String posId;
    private String mktId;
    private Long buyerPayAmount;
    private Long discountAmount;
    private Long mDiscountAmount;
    private String discountInfo;

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getMchNo() {
        return mchNo;
    }

    public void setMchNo(String mchNo) {
        this.mchNo = mchNo;
    }

    public String getIsvNo() {
        return isvNo;
    }

    public void setIsvNo(String isvNo) {
        this.isvNo = isvNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public Byte getMchType() {
        return mchType;
    }

    public void setMchType(Byte mchType) {
        this.mchType = mchType;
    }

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public String getIfCode() {
        return ifCode;
    }

    public void setIfCode(String ifCode) {
        this.ifCode = ifCode;
    }

    public String getWayCode() {
        return wayCode;
    }

    public void setWayCode(String wayCode) {
        this.wayCode = wayCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getMchFeeRate() {
        return mchFeeRate;
    }

    public void setMchFeeRate(BigDecimal mchFeeRate) {
        this.mchFeeRate = mchFeeRate;
    }

    public Long getMchFeeAmount() {
        return mchFeeAmount;
    }

    public void setMchFeeAmount(Long mchFeeAmount) {
        this.mchFeeAmount = mchFeeAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Byte getNotifyState() {
        return notifyState;
    }

    public void setNotifyState(Byte notifyState) {
        this.notifyState = notifyState;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getChannelExtra() {
        return channelExtra;
    }

    public void setChannelExtra(String channelExtra) {
        this.channelExtra = channelExtra;
    }

    public String getChannelUser() {
        return channelUser;
    }

    public void setChannelUser(String channelUser) {
        this.channelUser = channelUser;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public Byte getRefundState() {
        return refundState;
    }

    public void setRefundState(Byte refundState) {
        this.refundState = refundState;
    }

    public Integer getRefundTimes() {
        return refundTimes;
    }

    public void setRefundTimes(Integer refundTimes) {
        this.refundTimes = refundTimes;
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Byte getDivisionMode() {
        return divisionMode;
    }

    public void setDivisionMode(Byte divisionMode) {
        this.divisionMode = divisionMode;
    }

    public Byte getDivisionState() {
        return divisionState;
    }

    public void setDivisionState(Byte divisionState) {
        this.divisionState = divisionState;
    }

    public Date getDivisionLastTime() {
        return divisionLastTime;
    }

    public void setDivisionLastTime(Date divisionLastTime) {
        this.divisionLastTime = divisionLastTime;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getMktId() {
        return mktId;
    }

    public void setMktId(String mktId) {
        this.mktId = mktId;
    }

    public Long getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(Long buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getmDiscountAmount() {
        return mDiscountAmount;
    }

    public void setmDiscountAmount(Long mDiscountAmount) {
        this.mDiscountAmount = mDiscountAmount;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }
}
