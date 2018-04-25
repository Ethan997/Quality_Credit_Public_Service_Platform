package com.travischenn.platform.domain.DO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * **************************************************************
 * 公司名称    : 杭州质慧信息技术有限公司
 * 系统名称    : springboot-starter
 * 类 名 称    : NewsAnnex
 * 功能描述    : 新闻消息附件    [分离原因]: 由于图片上传形式为 Base 64 从而导致新闻内容十分巨大 , 于是将内容与文章本身分离
 * 作 者 名    : @Author TravisChenn (陈齐康)
 * 开发日期    : 2018/1/11 15:05
 * Created    : IntelliJ IDEA
 * **************************************************************
 * 修改日期    :
 * 修 改 者    :
 * 修改内容    :
 * **************************************************************
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "news_annex", schema = "springboot-starter", catalog = "")
public class NewsAnnex {
    private int id;
    private String content;
    private String img;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsAnnex newsAnnex = (NewsAnnex) o;

        if (id != newsAnnex.id) return false;
        if (content != null ? !content.equals(newsAnnex.content) : newsAnnex.content != null) return false;
        if (img != null ? !img.equals(newsAnnex.img) : newsAnnex.img != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        return result;
    }
}
