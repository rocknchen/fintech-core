package com.hthk.fintech.utils;

import com.hthk.fintech.enumration.InstrumentGroup;
import com.hthk.fintech.enumration.SecurityGroupEnum;
import com.hthk.fintech.model.instrument.ISecurity;
import com.hthk.fintech.model.instrument.Security;

public class SecurityInstrumentUtils {

    public static ISecurity build(String name) {

        Security security = new Security();
        security.setSecurityGroup(SecurityGroupEnum.LISTED.SECURITY);
        security.setName(name);
        return security;
    }

}
