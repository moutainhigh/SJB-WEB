/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.mapper.JsonMapper;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.modules.sys.dao.DictDao;
import com.sijibao.oa.modules.sys.entity.Dict;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}

    /**
     * 传入单个type，查询对应的字典
     * @param type
     * @return
     */
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CacheUtils.SYS_CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CacheUtils.SYS_CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}

    /**
     * 传入type列表，查询对应的字典
     * 如果入参列表不含元素，查询返回全部字典
     * @param typeList
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Dict> getDictList(List<String> typeList){
        Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CacheUtils.SYS_CACHE_DICT_MAP);
        if (dictMap==null){
            dictMap = Maps.newHashMap();
            for (Dict dict : dictDao.findAllList(new Dict())){
                List<Dict> dictList = dictMap.get(dict.getType());
                if (dictList != null){
                    dictList.add(dict);
                }else{
                    dictMap.put(dict.getType(), Lists.newArrayList(dict));
                }
            }
            CacheUtils.put(CacheUtils.SYS_CACHE_DICT_MAP, dictMap);
        }

        List<Dict> dictList = new LinkedList<>();
        Collection<List<Dict>> valueList = dictMap.values();

        if(typeList.size() == 0){
            for(List<Dict> l : valueList){
                dictList.addAll(l);
            }
        }else {
            for(String type : typeList){
                dictList.addAll(dictMap.get(type));
            }
        }

        return dictList;
    }
	
//	public static List<Dict> getAllDictListForIntfz(){
//		List<Dict> dictList = dictDao.findAllList(new Dict());
//		return dictList;
//	}
	
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}

	/**
	 * 获取name
	 * @param list
	 * @param type
	 * @return
	 */
	public static String getName(List<Dict> list,String type){
		for(Dict dict : list){
			if(dict.getValue().equals(type)){
				return dict.getLabel();
			}
		}
		return "";
	}
	
}
