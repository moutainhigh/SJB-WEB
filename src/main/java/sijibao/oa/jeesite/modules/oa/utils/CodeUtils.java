package sijibao.oa.jeesite.modules.oa.utils;

import com.sijibao.oa.common.utils.IdGen;

public class CodeUtils {

	public static String genCode(String pre, String context, int randomNum){
		return pre + context + IdGen.getRandomNumByLength(randomNum);
	}
	
	public static void main(String[] args) {
		System.out.println(genCode("P", "20180417", 3));
	}
	
}
