package sijibao.oa.jeesite.modules.sys.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.modules.sys.dao.SysMappingRelaInfoDao;
import com.sijibao.oa.modules.sys.entity.SysMappingRelaInfo;

/**
 * function: 系统映射参数缓存工具类
 * @author Petter
 * @version 2016年4月25日
 */
public class MappingRelaUtils {

	private static SysMappingRelaInfoDao mappingRelaDao = SpringContextHolder.getBean(SysMappingRelaInfoDao.class);
	
	public static final String CACHE_MAPPING_RELA_MAP = "mappingRelaMap";
	
	/**
	 * function: 根据类型和映射前的值获取映射对象
	 * @param mappingType 类型
	 * @param value 映射前的值
	 * @return
	 */
	public static SysMappingRelaInfo getByTypeNValue(String mappingType,String value){
		if (StringUtils.isNotBlank(mappingType) && StringUtils.isNotBlank(value)){
			for (SysMappingRelaInfo mappingRela : getMappingRelaList(mappingType)){
				if (mappingType.equals(mappingRela.getMappingType()) && value.equals(mappingRela.getMappingValue())){
					return mappingRela;
				}
			}
		}
		return null;
	}
	
	/**
	 * function: 根据传入参数获取对应的映射信息(value和name可有一个为空)
	 * @param mappingType 映射类型
	 * @param value 映射前的值
	 * @param name 映射前的名称
	 * @return
	 */
	public static SysMappingRelaInfo getMappingRelaByValueOrName(String mappingType,String value, String name){
		if (StringUtils.isNotBlank(mappingType)){
			if(StringUtils.isNotBlank(value)){
				for (SysMappingRelaInfo mappingRela : getMappingRelaList(mappingType)){
					if (mappingType.equals(mappingRela.getMappingType()) && value.equals(mappingRela.getMappingValue())){
						return mappingRela;
					}
				}
			}else if(StringUtils.isNotBlank(name)){
				for (SysMappingRelaInfo mappingRela : getMappingRelaList(mappingType)){
					if (mappingType.equals(mappingRela.getMappingType()) && name.equals(mappingRela.getMappingName())){
						return mappingRela;
					}
				}
			}else if(StringUtils.isNotBlank(value) && StringUtils.isNotBlank(name)){
				for (SysMappingRelaInfo mappingRela : getMappingRelaList(mappingType)){
					if (mappingType.equals(mappingRela.getMappingType()) && name.equals(mappingRela.getMappingName())
							 && value.equals(mappingRela.getMappingValue())){
						return mappingRela;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * function: 根据类型和映射前的值获取映射后的值
	 * @param value 映射前值
	 * @param mappingType 映射类型
	 * @param defaultLabel 默认值
	 * @return
	 */
	public static String getMappingRelaValue(String value, String mappingType, String defaultLabel){
		if (StringUtils.isNotBlank(mappingType) && StringUtils.isNotBlank(value)){
			for (SysMappingRelaInfo mappingRela : getMappingRelaList(mappingType)){
				if (mappingType.equals(mappingRela.getMappingType()) && value.equals(mappingRela.getMappingValue())){
					return mappingRela.getMappingToValue();
				}
			}
		}
		return defaultLabel;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SysMappingRelaInfo> getMappingRelaList(String mappingType){
		Map<String, List<SysMappingRelaInfo>> mappingRelaMap = (Map<String, List<SysMappingRelaInfo>>)CacheUtils.get(CACHE_MAPPING_RELA_MAP);
		if (mappingRelaMap==null){
			mappingRelaMap = Maps.newHashMap();
			for (SysMappingRelaInfo mappingRela : mappingRelaDao.findAllList(new SysMappingRelaInfo())){
				List<SysMappingRelaInfo> mappingRelaList = mappingRelaMap.get(mappingRela.getMappingType());
				if (mappingRelaList != null){
					mappingRelaList.add(mappingRela);
				}else{
					mappingRelaMap.put(mappingRela.getMappingType(), Lists.newArrayList(mappingRela));
				}
			}
			CacheUtils.put(CACHE_MAPPING_RELA_MAP, mappingRelaMap);
		}
		List<SysMappingRelaInfo> mappingList = mappingRelaMap.get(mappingType);
		if (mappingList == null){
			mappingList = Lists.newArrayList();
		}
		return mappingList;
	}
	
}
