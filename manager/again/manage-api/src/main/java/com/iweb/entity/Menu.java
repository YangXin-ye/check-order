package com.iweb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName menu
 */
@TableName(value ="menu")
public class Menu implements Serializable {
    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 标签
     */
    private String label;

    /**
     * 图标
     */
    private String icon;

    /**
     * 页面链接
     */
    private String url;

    /**
     * 链接
     */
    private String path;

    /**
     * 父级Id，没有父级为0
     */
    private Integer parent;


    @TableField(exist = false)
    private List<Menu> children = null;

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    /**
     * id
     */
    public Integer getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 标签
     */
    public String getLabel() {
        return label;
    }

    /**
     * 标签
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 页面链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 页面链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 链接
     */
    public String getPath() {
        return path;
    }

    /**
     * 链接
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 父级Id，没有父级为0
     */
    public Integer getParent() {
        return parent;
    }

    /**
     * 父级Id，没有父级为0
     */
    public void setParent(Integer parent) {
        this.parent = parent;
    }
}