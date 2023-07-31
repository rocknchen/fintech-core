package com.hthk.fintech.event.utils;

import com.hthk.fintech.enumration.CSVModel;
import com.hthk.fintech.model.event.IEvent;
import com.hthk.fintech.serialize.ModelDeserializeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class EventUtils {

    protected final static Logger logger = LoggerFactory.getLogger(EventUtils.class);
    public static final String BuildKey(IEvent event) {
        return event.getDomain() + "-" + event.getGroup() + "-" + event.getType() + "-" + Optional.ofNullable(event.getSubType()).map(t -> t.name()).orElse(DEFAULT_NA_STRING);
    }

    public static <T> T deserialize(List<String> headerList, List<String> fieldList, Class<T> clz) {

        Class<? extends ModelDeserializeController> controllerClz = clz.getAnnotation(CSVModel.class).deserializeController();
        try {
            ModelDeserializeController controller = controllerClz.getConstructor().newInstance();
            return (T) controller.process(headerList, fieldList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
