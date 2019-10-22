/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.supcan.common.fonts;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import sijibao.oa.jeesite.supcan.annotation.common.fonts.SupFont;

/**
 * 硕正TreeList Properties
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("Font")
@Data
public class Font {

	/**
	 * 字体名称   微软雅黑  宋体
	 */
	@XStreamAsAttribute
	private String faceName;

	/**
	 * 字符集 134
	 */
	@XStreamAsAttribute
	private String charSet;

	/**
	 * Height(或size)是字体的尺寸，单位是字体的逻辑单位，通常采用小于0的数字，
	 * 如果大于0，则高度不包含文字的内部行距(internal-leading)。
	 * 常用的尺寸是-8, -9, -10, -11, -12, -14, -16, -18, -20, -22, -24, -26, -28, -36, -48, -72;
	 */
	@XStreamAsAttribute
	private String height;
	
	/**
	 * 字体加粗 weight=400/700 对应 非粗体/粗体；
	 */
	@XStreamAsAttribute
	private String weight;

	/**
	 * 字体宽度
	 */
	@XStreamAsAttribute
	private String width;
	
	/**
	 * 字体斜体
	 */
	@XStreamAsAttribute
	private String italic;
	
	/**
	 * 字体下划线
	 */
	@XStreamAsAttribute
	private String underline;
	
	public Font() {
		
	}

	public Font(SupFont supFont) {
		this();
		BeanUtils.copyProperties(this,supFont);
	}
	
	public Font(String faceName) {
		this();
		this.faceName = faceName;
	}
	
	public Font(String faceName, String charSet, String height) {
		this(faceName);
		this.charSet = charSet;
		this.height = height;
	}
	
	public Font(String faceName, String charSet, String height, String weight) {
		this(faceName, charSet, height);
		this.weight = weight;
	}
}
