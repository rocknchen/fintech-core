package com.hthk.fintech.position.asset.service;

import com.hthk.fintech.model.finance.config.ProductTypeEnum;
import com.hthk.fintech.model.finance.position.config.PositionConfiguration;
import com.hthk.fintech.model.finance.position.liquidation.LiquidationInfo;
import com.hthk.fintech.model.finance.position.liquidation.LiquidationKey;
import com.hthk.fintech.model.finance.staticdata.Book;
import com.hthk.fintech.model.position.asset.AssetPositionEntity;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/19 15:53
 */
public interface AssetPositionService {

    AssetPositionEntity build(
            Set<Book> bookSet,
            boolean isCrossBook,
            @Nullable Set<ProductTypeEnum> productTypeNotInScopeSet,
            Map<LiquidationKey, LiquidationInfo> liquidationInfoMap,
            Map<ProductTypeEnum, PositionConfiguration> positionConfigMap
    );

}
