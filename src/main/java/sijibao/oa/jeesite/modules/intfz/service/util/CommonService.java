package sijibao.oa.jeesite.modules.intfz.service.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sijibao.oa.modules.sys.entity.Area;
import com.sijibao.oa.modules.sys.service.AreaService;

/**
 * 公共类
 * @author mbdn
 *
 */
@Service
public class CommonService {
	
	@Autowired
	private AreaService areaInfoService;
	
	/**
	 * 设置业务单据的明细城市信息
	 * @param startPoint
	 * @param endPoint
	 * @return areaMap
	 */
	public Map<String,String[]> getBusinessArea(String startPoint,String endPoint){
		Map<String,String[]> areaMap = new HashMap<String,String[]>();
		//处理开始城市信息
		Area start = new Area();
		start.setCode(startPoint);
		start = areaInfoService.getForCode(start);
		String[] startArea = new String[1];
		String[] startAreaName = new String[1];
		if(start != null){
			if("1".equals(start.getType())){
				startArea[0] = start.getCode();
				startAreaName[0] = start.getName();
			}else{
				startArea = new String[2];
				startAreaName = new String[2];
				Area parentStart = areaInfoService.get(start.getParentId());
				startArea[0] = parentStart.getCode();
				startAreaName[0] = parentStart.getName();
				startArea[1] = start.getCode();
				startAreaName[1] = start.getName();
			}
			areaMap.put("startArea", startArea);
			areaMap.put("startAreaName", startAreaName);
		}
		//处理结束城市信息
		Area end = new Area();
		end.setCode(endPoint);
		end = areaInfoService.getForCode(end);
		String[] endArea = new String[1];
		String[] endAreaName = new String[1];
		if(end != null){
			if("1".equals(end.getType())){
				endArea[0] = end.getCode();
				endAreaName[0] = end.getName();
			}else{
				endArea = new String[2];
				endAreaName = new String[2];
				Area parentEnd = areaInfoService.get(end.getParentId());
				if(parentEnd != null){
					endArea[0] = parentEnd.getCode();
					endAreaName[0] = parentEnd.getName();
					endArea[1] = end.getCode();
					endAreaName[1] = end.getName();
				}else{
					endArea = new String[1];
					endAreaName = new String[1];
					endArea[0] = end.getCode();
					endAreaName[0] = end.getName();
				}
			}
			areaMap.put("endArea", endArea);
			areaMap.put("endAreaName", endAreaName);
		}
		return areaMap;
	}
}
