package sijibao.oa.jeesite.supcan.treelist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.springframework.util.CollectionUtils;
import sijibao.oa.jeesite.supcan.annotation.treelist.SupTreeList;
import sijibao.oa.jeesite.supcan.common.Common;
import sijibao.oa.jeesite.supcan.common.fonts.Font;
import sijibao.oa.jeesite.supcan.common.properties.Properties;

/**
 * 硕正TreeList
 * 
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("TreeList")
public class TreeList extends Common {

	/**
	 * 列集合
	 */
	@XStreamAlias("Cols")
	private List<Object> cols;

	public TreeList() {
		super();
	}

	public TreeList(Properties properties) {
		this();
		this.properties = properties;
	}

	public TreeList(SupTreeList supTreeList) {
		this();
		if (supTreeList != null) {
			if (supTreeList.properties() != null) {
				this.properties = new Properties(supTreeList.properties());
			}
			if (!CollectionUtils.isEmpty(Arrays.asList(supTreeList.fonts()))) {
				List<Font> fonts = JSON.parseArray(JSON.toJSONString(supTreeList.fonts()), Font.class);
				this.fonts.addAll(fonts);
			}
		}
	}

	public List<Object> getCols() {
		if (cols == null) {
			cols = new ArrayList<>();
		}
		return cols;
	}

	public void setCols(List<Object> cols) {
		this.cols = cols;
	}

}
