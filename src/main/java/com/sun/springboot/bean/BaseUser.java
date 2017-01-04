package com.sun.springboot.bean;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Table(name = "T_BASE_USER")
public class BaseUser {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal userId;

    @Column(name = "DEPT_ID")
    private BigDecimal deptId;

    @Column(name = "PURCHASER_ID")
    private BigDecimal purchaserId;

    @Column(name = "COMPANY_ID")
    private BigDecimal companyId;

    @Column(name = "COST_CENTER_ID")
    private BigDecimal costCenterId;

    @Column(name = "USER_LEVEL_ID")
    private BigDecimal userLevelId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMPLOYEE_NO")
    private String employeeNo;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "NATIONALITY")
    private BigDecimal nationality;

    @Column(name = "NAME_CN")
    private String nameCn;

    @Column(name = "PINYIN_FIRST")
    private String pinyinFirst;

    @Column(name = "PINYIN_FULL")
    private String pinyinFull;

    @Column(name = "NAME_EN_LAST")
    private String nameEnLast;

    @Column(name = "NAME_EN_MIDDLE")
    private String nameEnMiddle;

    @Column(name = "NAME_EN_FIRST")
    private String nameEnFirst;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "MOBILEPHONE")
    private String mobilephone;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "JOB")
    private String job;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IDC_NAME")
    private String idcName;

    @Column(name = "IDC_TYPE")
    private String idcType;

    @Column(name = "IDC_NO")
    private String idcNo;

    @Column(name = "IDC_VALIDITY_DATE")
    private String idcValidityDate;

    @Column(name = "IDC_NAME2")
    private String idcName2;

    @Column(name = "IDC_TYPE2")
    private String idcType2;

    @Column(name = "IDC_NO2")
    private String idcNo2;

    @Column(name = "IDC_VALIDITY_DATE2")
    private String idcValidityDate2;

    @Column(name = "IDC_NAME3")
    private String idcName3;

    @Column(name = "IDC_TYPE3")
    private String idcType3;

    @Column(name = "IDC_NO3")
    private String idcNo3;

    @Column(name = "IDC_VALIDITY_DATE3")
    private String idcValidityDate3;

    @Column(name = "STATE")
    private String state;

    @Column(name = "VISIT_NUM")
    private Long visitNum;

    @Column(name = "LAST_VISIT_TIME")
    private Date lastVisitTime;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "CREATE_USER")
    private BigDecimal createUser;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MODIFY_USER")
    private BigDecimal modifyUser;

    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;

    @Column(name = "LOGIN_FALSE_COUNT")
    private Long loginFalseCount;

    @Column(name = "TEMP_PASSWORD")
    private String tempPassword;

    @Column(name = "TEMP_VALID_TIME")
    private Date tempValidTime;

    @Column(name = "PAY_PASSWORD")
    private String payPassword;

    @Column(name = "TEMP_PAY_PASSWORD")
    private String tempPayPassword;

    @Column(name = "TEMP_VALID_PAY_TIME")
    private Date tempValidPayTime;

    @Column(name = "INV_CODE")
    private String invCode;

    @Column(name = "INV_CREATE_TIME")
    private Date invCreateTime;

    @Column(name = "IS_INV_USED")
    private Short isInvUsed;

    @Column(name = "IDENTIFY_CODE")
    private String identifyCode;

    @Column(name = "IDENTIFY_CREATE_TIME")
    private Date identifyCreateTime;

    @Column(name = "IDENTIFY_COUNT")
    private Short identifyCount;

    @Column(name = "SUPERIOR_USER_ID")
    private BigDecimal superiorUserId;

    @Column(name = "BANK_ID")
    private String bankId;

    @Column(name = "LAST_PREBOOK_TIME")
    private Date lastPrebookTime;

}