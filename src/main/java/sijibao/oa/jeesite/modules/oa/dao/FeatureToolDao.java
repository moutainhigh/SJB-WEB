package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.FeatureTools;

@MyBatisDao
public interface FeatureToolDao  extends CrudDao<FeatureTools> {


    List<FeatureTools> queryFlowData(FeatureTools featureTools);

    List<FeatureTools> queryFunctionData(FeatureTools featureTools);

    List<FeatureTools> queryOwnData(FeatureTools featureTools);

    List<FeatureTools> queryOwnUnData(FeatureTools featureTools);
}
