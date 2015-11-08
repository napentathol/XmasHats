package us.sodiumlabs.xmas.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Alex on 11/4/2015.
 */
@Data
public class XmasResult {
    private final Map<String, String> matchMap;

    private final Map<String, List<String>> thisYearExclusion;
}
