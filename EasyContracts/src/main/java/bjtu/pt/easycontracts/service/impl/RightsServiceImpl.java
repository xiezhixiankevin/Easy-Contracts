package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.pojo.table.Rights;
import bjtu.pt.easycontracts.service.RightsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <Description> RightsServiceImpl
 *
 * @author 26802
 * @version 1.0
 * @ClassName RightsServiceImpl
 * @taskId
 * @see bjtu.pt.easycontracts.service.impl
 */
@Service
public class RightsServiceImpl implements RightsService {
    @Override
    public int allocationRights(int userId, List<Rights> rights) {
        return 0;
    }

    @Override
    public int allocationRights(String username, List<Rights> rights) {
        return 0;
    }

    @Override
    public int allocationRights(int userId, Rights rights) {
        return 0;
    }

    @Override
    public int allocationRights(String username, Rights rights) {
        return 0;
    }

    @Override
    public int deleteRights(int userId) {
        return 0;
    }

    @Override
    public List<Rights> listRights(int userId) {
        return null;
    }

    @Override
    public Rights ifUserHasRights(int userId, int rightsType) {
        return null;
    }

    @Override
    public List<Rights> createRightList(List<Integer> rights) {
        return null;
    }
}
