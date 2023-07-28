package com.hthk.fintech.event.utils;

import com.hthk.common.utils.CSVFileUtils;
import com.hthk.fintech.enumration.CSVModel;
import com.hthk.fintech.serialize.ModelDeserializeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventUtils {

    protected final static Logger logger = LoggerFactory.getLogger(EventUtils.class);

    public static <T> T deserialize(List<String> headerList, List<String> fieldList, Class<T> clz) {

        Class<? extends ModelDeserializeController> controllerClz = clz.getAnnotation(CSVModel.class).deserializeController();
        try {
            ModelDeserializeController controller = controllerClz.getConstructor().newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return null;
    }

}
