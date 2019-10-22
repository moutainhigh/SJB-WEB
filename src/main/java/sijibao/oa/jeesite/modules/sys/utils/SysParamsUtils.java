package sijibao.oa.jeesite.modules.sys.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.modules.sys.dao.SysParamsDao;
import com.sijibao.oa.modules.sys.entity.SysParams;

/**
 * function: 系统参数工具类
 * @author Petter
 * @version 2016年4月13日
 */
public class SysParamsUtils {

	private static SysParamsDao sysParamsDao = SpringContextHolder.getBean(SysParamsDao.class);

	public static String getParamValue(String paraName, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(paraName)){
			for (SysParams sysParams : getParamsList(type)){
				if (type.equals(sysParams.getParamType()) && paraName.equals(sysParams.getParamName())){
					return sysParams.getParamValue();
				}
			}
		}
		return defaultLabel;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SysParams> getParamsList(String type){
		Map<String, List<SysParams>> sysParamsMap = (Map<String, List<SysParams>>)CacheUtils.get(CacheUtils.SYS_CACHE_PARAMS_MAP);
		if (sysParamsMap==null){
			sysParamsMap = Maps.newHashMap();
			for (SysParams sysParams : sysParamsDao.findAllList(new SysParams())){
				List<SysParams> paramsList = sysParamsMap.get(sysParams.getParamType());
				if (paramsList != null){
					paramsList.add(sysParams);
				}else{
					sysParamsMap.put(sysParams.getParamType(), Lists.newArrayList(sysParams));
				}
			}
			CacheUtils.put(CacheUtils.SYS_CACHE_PARAMS_MAP, sysParamsMap);
		}
		List<SysParams> dictList = sysParamsMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
}
