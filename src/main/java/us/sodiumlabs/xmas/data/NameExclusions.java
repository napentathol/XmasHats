package us.sodiumlabs.xmas.data;

import java.util.List;
import java.util.Map;

public record NameExclusions(
    Map<String, List<String>> lastYears,
    Map<String, List<String>> misc
) { }
