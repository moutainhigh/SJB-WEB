package sijibao.oa.jeesite.modules.intfz.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.modules.intfz.response.expense.SubInfoResponse;

/**
 * 需求申请流程服务层
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class IntfzCommonService extends BaseService {
	
	/**
	 *  设置默认的二级科目
	 * @param subInfolist
	 * @return
	 */
	public List<SubInfoResponse> setSecondSub(List<SubInfoResponse> subInfolist){
		List<SubInfoResponse> addList = new ArrayList<SubInfoResponse>();
		for(SubInfoResponse s1:subInfolist){
			int count = 0;
			for(SubInfoResponse s2:subInfolist){
				if((!"0".equals(s1.getParent()) || ("0".equals(s1.getParent()) && s1.getValue().equals(s2.getParent())))){
					count++;
				}
			}
			if(count == 0){
				SubInfoResponse add = new SubInfoResponse();
				add.setName(s1.getName());
				add.setParent(s1.getValue());
				add.setValue("no_"+s1.getValue());
				add.setCreateTime(s1.getCreateTime());
				addList.add(add);
			}
		}
		subInfolist.addAll(addList);
		return subInfolist;
	}
	
	/**
	 * 设置附件下载url
	 * @param uploadUrl
	 * @param fileName
	 * @return
	 */
	public String setFileDownLoadUrl(String uploadUrl,String fileName){
		String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
		String fileUrl = serverUrl+uploadUrl;
		String url ="/f/commonInfo/downloadFiles?url="+fileUrl+"&fileName="+fileName;
		return url;
	}
}
