package com.travischenn.platform.domain.DO.relation;

import javax.persistence.*;

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
@Table(name = "member_role_relation", schema = "springboot-starter")
public class MemberRoleRelation {
    private int id;
    private int memId;
    private int roleId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "mem_id")
    public int getMemId() {
        return memId;
    }

    public void setMemId(int memId) {
        this.memId = memId;
    }

    @Basic
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberRoleRelation that = (MemberRoleRelation) o;

        if (id != that.id) return false;
        if (memId != that.memId) return false;
        if (roleId != that.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + memId;
        result = 31 * result + roleId;
        return result;
    }
}
