package cn.cdipcc.aerolite.server.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    private List<T> list;

    private Long total;

    private Integer pageNum;

    private Integer pageSize;

    public PageDTO(PageInfo<T> pageInfo) {
        this.list = pageInfo.getList();
        this.total = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }
}
