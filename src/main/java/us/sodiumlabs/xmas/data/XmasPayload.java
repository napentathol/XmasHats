package us.sodiumlabs.xmas.data;

import org.immutables.value.Value;

/**
 * @author Alex on 11/4/2015.
 */
@SodiumStyle
@Value.Immutable
public abstract class XmasPayload {
    public abstract Names getNames();
    public abstract NameExclusions getNameExclusions();

    public static ImmutableXmasPayload.Builder builder() {
        return new ImmutableXmasPayload.Builder();
    }
}
