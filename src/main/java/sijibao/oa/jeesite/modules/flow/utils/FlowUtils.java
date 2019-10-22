package sijibao.oa.jeesite.modules.flow.utils;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.IdGen;

/**
 * 流程工具类
 * @author Petter
 */
public class FlowUtils {

	/**
	 * 生成流水号
	 * @return
	 */
	public static String genFlowCode(){
		return DateUtils.getDate("yyMMddHHmmss") + IdGen.getRandomNumByLength(4);
	}
	
}
