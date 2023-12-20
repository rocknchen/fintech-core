package com.hthk.fintech.position.asset.service.impl;

import com.hthk.fintech.model.finance.config.ProductTypeEnum;
import com.hthk.fintech.model.finance.position.config.PositionConfiguration;
import com.hthk.fintech.model.finance.position.liquidation.LiquidationInfo;
import com.hthk.fintech.model.finance.position.liquidation.LiquidationKey;
import com.hthk.fintech.model.finance.trade.ITrade;
import com.hthk.fintech.model.finance.trade.criteria.ICriteriaTrade;
import com.hthk.fintech.model.position.asset.AssetPositionEntity;
import com.hthk.fintech.position.asset.service.AssetPositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/19 16:16
 */
@Service
public class AssetPositionServiceDefaultImpl implements AssetPositionService {

    @Override
    public AssetPositionEntity build(
            List<ITrade> tradeList,
            @Nullable Set<ICriteriaTrade> acceptCriteriaSet,
            @Nullable Set<ICriteriaTrade> rejectCriteriaSet,
            boolean isCrossBook,
            Map<LiquidationKey, LiquidationInfo> liquidationInfoMap,
            Map<ProductTypeEnum, PositionConfiguration> positionConfigMap
    ) {
        return null;

    }
}
