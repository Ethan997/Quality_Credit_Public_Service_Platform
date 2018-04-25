package com.travischenn.platform.domain.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    :
 * 功能描述    :
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/7 20:16
 * Created    : IntelliJ IDEA
 * ***************************************************************/
@Entity
@DynamicUpdate
public class Member {
    private int id;
    private Integer orgId;
    private String username;
    private String password;
    private String realname;
    private String gender;
    private String mobile;
    private String createTime;
    private String lastLoginTime;
    private String lastLoginIp;
    private String loginCount;
    private String enable;
    private String locked;
    private String delete;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "org_id")
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "realname")
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "last_login_time")
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Basic
    @Column(name = "login_count")
    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }

    @Basic
    @Column(name = "enable")
    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "locked")
    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "delete")
    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (id != member.id) return false;
        if (orgId != null ? !orgId.equals(member.orgId) : member.orgId != null) return false;
        if (username != null ? !username.equals(member.username) : member.username != null) return false;
        if (password != null ? !password.equals(member.password) : member.password != null) return false;
        if (realname != null ? !realname.equals(member.realname) : member.realname != null) return false;
        if (gender != null ? !gender.equals(member.gender) : member.gender != null) return false;
        if (mobile != null ? !mobile.equals(member.mobile) : member.mobile != null) return false;
        if (createTime != null ? !createTime.equals(member.createTime) : member.createTime != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(member.lastLoginTime) : member.lastLoginTime != null)
            return false;
        if (lastLoginIp != null ? !lastLoginIp.equals(member.lastLoginIp) : member.lastLoginIp != null) return false;
        if (loginCount != null ? !loginCount.equals(member.loginCount) : member.loginCount != null) return false;
        if (enable != null ? !enable.equals(member.enable) : member.enable != null) return false;
        if (locked != null ? !locked.equals(member.locked) : member.locked != null) return false;
        if (delete != null ? !delete.equals(member.delete) : member.delete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + (loginCount != null ? loginCount.hashCode() : 0);
        result = 31 * result + (enable != null ? enable.hashCode() : 0);
        result = 31 * result + (locked != null ? locked.hashCode() : 0);
        result = 31 * result + (delete != null ? delete.hashCode() : 0);
        return result;
    }
}
