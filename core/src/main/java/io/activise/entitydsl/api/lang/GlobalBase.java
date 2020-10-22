package io.activise.entitydsl.api.lang;

import java.util.List;
import java.util.Optional;

public interface GlobalBase {
    List<Option> getOptions();

    default Optional<Option> getOptionByKey(String key) {
        return getOptions().stream().filter(o -> o.getKey().equals(key)).findFirst();
    }

}