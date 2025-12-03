package us.sodiumlabs.xmas.marshaller;

import us.sodiumlabs.xmas.data.OrderMaintainer;
import us.sodiumlabs.xmas.data.XmasResult;

/**
 * @author Alex on 11/5/2015.
 */
public interface OutputMarshaller<T> {
    boolean marshal(XmasResult xmasResult, OrderMaintainer maintainer, int year);
}
