package us.sodiumlabs.xmas.data;

import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

/**
 * @author Alex on 11/4/2015.
 */
@SodiumStyle
@Value.Immutable
public abstract class XmasResult {
    public abstract Map<String, String> getMatchMap();

    public abstract Map<String, List<String>> getThisYearExclusion();

    public static ImmutableXmasResult.Builder builder() {
        return new ImmutableXmasResult.Builder();
    }
}
