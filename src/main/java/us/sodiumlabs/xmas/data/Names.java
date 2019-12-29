package us.sodiumlabs.xmas.data;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * @author Alex on 11/4/2015.
 */
public class Names {
    private final String[] names;

    public Names(final String[] names) {
        this.names = requireNonNull(names);
    }

    public String[] getNames() {
        return Arrays.copyOf(names, names.length);
    }
}
