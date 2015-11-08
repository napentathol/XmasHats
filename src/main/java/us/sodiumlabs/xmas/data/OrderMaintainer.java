package us.sodiumlabs.xmas.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex on 11/7/2015.
 */
@Data
public class OrderMaintainer {
    final List<String> names = new ArrayList<>();
}
