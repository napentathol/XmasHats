package us.sodiumlabs.xmas.data;

import lombok.Data;
import us.sodiumlabs.xmas.data.NameExclusions;
import us.sodiumlabs.xmas.data.Names;

/**
 * @author Alex on 11/4/2015.
 */
@Data
public class XmasPayload {
    private final Names names;
    private final NameExclusions nameExclusions;
}
