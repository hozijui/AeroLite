package cn.cdipcc.aerolite.server.dto;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "分页数据")
public class PageDTO<T> {
    @ApiModelProperty(value = "数据列表")
    private List<T> list;

    @ApiModelProperty(value = "总数")
    private Long total;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    public PageDTO(PageInfo<T> pageInfo) {
        this.list = pageInfo.getList();
        this.total = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }
}
