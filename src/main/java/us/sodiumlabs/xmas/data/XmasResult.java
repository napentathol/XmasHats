package us.sodiumlabs.xmas.data;

import java.util.List;
import java.util.Map;

public record XmasResult(
    Map<String, String> matchMap,
    Map<String, List<String>> thisYearExclusion
) { }