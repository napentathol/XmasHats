package us.sodiumlabs.xmas.matcher;

import us.sodiumlabs.xmas.data.XmasPayload;
import us.sodiumlabs.xmas.data.XmasResult;

/**
 * @author Alex on 11/4/2015.
 */
public interface Matcher {
    XmasResult match(XmasPayload payload);
}
