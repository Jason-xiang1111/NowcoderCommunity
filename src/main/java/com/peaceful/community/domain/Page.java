package com.peaceful.community.domain;

/*
*   封装分页相关的信息
* */
public class Page {
    // 当前页码
    private int current = 1;
    // 显示上限
    private int limit = 10;
    // 数量总数（用于计算页面总数）
    private int rows;
    // 查询路径（复用分页路径连接）
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1)
            this.current = current;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100)
            this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0)
            this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    /**
     * 获取当前页面的起始行
     * @return
     */
    public int getOffset(){
        return (current-1)*limit;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotal(){
        // rows / limit
        if(rows % limit == 0){
            return rows/limit;
        }else
            return rows/limit + 1;
    }

    /**
     * 获取起始页码
     * @return
     */
    public int getFrom(){
        int from = current-2;
        return Math.max(from, 1);
    }

    /**
     * 获取结束页码
     * @return
     */
    public int getTo(){
        int to = current + 2;
        return Math.min(to, getTotal());
    }
}
