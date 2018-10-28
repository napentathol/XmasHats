package us.sodiumlabs.xmas.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex on 11/7/2015.
 */

public class OrderMaintainer {
    private final List<String> names = new ArrayList<>();

    public List<String> getNames() {
        return Collections.unmodifiableList(names);
    }

    public void addName(final String n) {
        names.add(n);
    }
}
