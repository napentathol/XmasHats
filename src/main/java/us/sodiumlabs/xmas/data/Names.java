package us.sodiumlabs.xmas.data;

import lombok.Data;

import java.util.Arrays;

/**
 * @author Alex on 11/4/2015.
 */
@Data
public class Names {
    private final String[] names;

    public Names(final String[] names) {
        this.names = names;
    }

    public String[] getNames() {
        return Arrays.copyOf(names, names.length);
    }
}
