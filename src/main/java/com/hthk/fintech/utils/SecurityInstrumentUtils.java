package com.hthk.fintech.utils;

import com.hthk.fintech.enumration.SecurityGroupEnum;
import com.hthk.fintech.model.instrument.ISecurity;
import com.hthk.fintech.model.instrument.Security;

public class SecurityInstrumentUtils {

    public static ISecurity build(SecurityGroupEnum secGroup, String name) {

        Security security = new Security();
        security.setSecurityGroup(secGroup);
        security.setName(name);
        return security;
    }

    public static ISecurity buildUnknownGroup(String name) {
        return build(SecurityGroupEnum.UNKNOWN, name);
    }

}
