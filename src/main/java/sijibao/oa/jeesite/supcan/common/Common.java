package sijibao.oa.jeesite.supcan.common;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.springframework.util.CollectionUtils;
import sijibao.oa.jeesite.supcan.common.fonts.Font;
import sijibao.oa.jeesite.supcan.common.properties.Properties;
import sijibao.oa.jeesite.utils.IdGen;

/**
 * 硕正Common
 * 
 * @author WangZhen
 * @version 2013-11-04
 */
public class Common {

	/**
	 * 属性对象
	 */
	@XStreamAlias("Properties")
	protected Properties properties;

	/**
	 * 字体对象
	 */
	@XStreamAlias("Fonts")
	protected List<Font> fonts;

	public Common() {
		properties = new Properties(IdGen.uuid());
		if (CollectionUtils.isEmpty(fonts)) {
			fonts = new ArrayList<>();
		}
		fonts.add(new Font("宋体", "134", "-12"));
		fonts.add(new Font("宋体", "134", "-13", "700"));
	}

	public Common(Properties properties) {
		this();
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public List<Font> getFonts() {
		return fonts;
	}

	public void setFonts(List<Font> fonts) {
		this.fonts = fonts;
	}

}
