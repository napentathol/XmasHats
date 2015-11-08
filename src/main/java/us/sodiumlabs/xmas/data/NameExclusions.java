package us.sodiumlabs.xmas.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Alex on 11/4/2015.
 */
@Data
public class NameExclusions {
    private final Map<String, List<String>> lastYears;

    private final Map<String, List<String>> misc;

    public NameExclusions(final Map<String, List<String>> lastYears, final Map<String, List<String>> misc) {
        this.lastYears = lastYears;
        this.misc = misc;
    }
}
