package sijibao.oa.jeesite.modules.intfz.request.messageremind;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 消息提醒接收实体
 * @author: xgx
 * @create: 2019-09-04 14:11
 **/
@ApiModel(value="提醒管理服务--查询列表条件对象")
public class MainMessageRemindHandleListReq implements Serializable {

    private static final long serialVersionUID = -9027321523576011450L;
    @ApiModelProperty(value="列表页面带入id（所有详情页面只传Id）")
    private String id;
    @ApiModelProperty(value="提醒管理分类标识：0-代办提醒，1-审批提醒，2-其他")
    private String remindCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemindCategory() {
        return remindCategory;
    }

    public void setRemindCategory(String remindCategory) {
        this.remindCategory = remindCategory;
    }

    @Override
    public String toString() {
        return "MainMessageRemindHandleReq{" +
                "id='" + id + '\'' +
                ", remindCategory=" + remindCategory +
                '}';
    }
}
