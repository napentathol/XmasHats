package us.sodiumlabs.xmas.data;

import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

/**
 * @author Alex on 11/4/2015.
 */
@SodiumStyle
@Value.Immutable
public abstract class NameExclusions {
    public abstract Map<String, List<String>> getLastYears();

    public abstract Map<String, List<String>> getMisc();

    public static ImmutableNameExclusions.Builder builder() {
        return new ImmutableNameExclusions.Builder();
    }
}
