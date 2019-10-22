package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.modules.oa.dao.FeatureToolDao;
import com.sijibao.oa.modules.oa.entity.FeatureTools;


@Service
@Transactional(readOnly = false)
public class FeatureToolService  extends BaseService {
    @Autowired
    private FeatureToolDao featureToolDao;

    public List<FeatureTools> queryFlowData(FeatureTools featureTools) {
        return featureToolDao.queryFlowData(featureTools);
    }

    public List<FeatureTools> queryFunctionData(FeatureTools featureTools) {
        return featureToolDao.queryFunctionData(featureTools);
    }

    public List<FeatureTools> queryOwnData(FeatureTools featureTools) {
        return featureToolDao.queryOwnData(featureTools);
    }

    public List<FeatureTools> queryOwnUnData(FeatureTools featureTools) {
        return featureToolDao.queryOwnUnData(featureTools);
    }
}
