package com.hthk.fintech.position.asset.service;

import javax.annotation.Nullable;

/**
 * @Author: Rock CHEN
 * @Date: 2023/12/19 15:53
 */
public interface AssetPositionService {

    AssetPositionEntity build(
            Set<Book> bookSet,
            boolean isCrossBook,
            @Nullable Set<ProductTypeEnum> productTypeNotInScopeSet,
            LiquidationInfoMap<LiquidationKey, LiquidationInfo> liquidationInfoMap,
            PositionConfigMap<ProductTypeEnum, PositionConfiguration> positionConfigMap
    );

}
