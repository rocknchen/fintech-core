package com.hthk.fintech.service.impl;

import org.junit.Test;

/**
 * @Author: Rock CHEN
 * @Date: 2024/1/3 15:15
 */
public class ExtLibsPomFileBuilderImplTest {

    ExtLibsPomFileBuilderImpl builder = new ExtLibsPomFileBuilderImpl();

    @Test
    public void testBuild_GENERAL() {

        String srcFolder = "C:/Rock/Datas/IT/DEV/ws/WorkSpaceIDEAHTSFICC/agent-service-calypso-v17/calypso_libs";
        String ext = "jar";

        String output = builder.generate(srcFolder, ext);
        System.out.println(output);
    }

}