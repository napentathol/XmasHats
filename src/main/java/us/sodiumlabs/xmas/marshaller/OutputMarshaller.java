package us.sodiumlabs.xmas.marshaller;

import us.sodiumlabs.xmas.data.OrderMaintainer;

/**
 * @author Alex on 11/5/2015.
 */
public interface OutputMarshaller<T> {
    public boolean marshal(T t, OrderMaintainer maintainer);
}
