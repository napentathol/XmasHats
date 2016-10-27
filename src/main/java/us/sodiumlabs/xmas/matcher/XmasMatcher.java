package us.sodiumlabs.xmas.matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.sodiumlabs.xmas.data.XmasPayload;
import us.sodiumlabs.xmas.data.XmasResult;

import java.util.*;

/**
 * @author Alex on 11/4/2015.
 */
public class XmasMatcher implements Matcher {
    private static final Logger logger = LogManager.getLogger(XmasMatcher.class);

    @Override
    public XmasResult match(final XmasPayload payload) {
        final Map<String, String> matches = createMatches(payload);

        final Map<String, List<String>> exclusions = createExclusions(payload, matches);

        return new XmasResult(matches, exclusions);
    }

    private Map<String,List<String>> createExclusions(final XmasPayload payload, final Map<String, String> matches) {
        final Map<String, List<String>> exclusions = payload.getNameExclusions().getLastYears();

        logger.info("Calculating exclusions using payload\n{} and matches\n{}", payload, matches);

        for(Map.Entry<String, List<String>> exclusion : exclusions.entrySet()) {

            exclusion.getValue().add( 0, StringUtils.stripToEmpty(matches.get( exclusion.getKey() )) );
        }

        return exclusions;
    }

    private Map<String, String> createMatches(final XmasPayload payload) {
        final String[] names = payload.getNames().getNames();
        final String[] matches = new String[names.length];

        for(final String toMatch : names) {
            final int matchPlace = (int)(Math.random() * names.length);

            placeMatch(matchPlace, toMatch, names, matches, payload);
        }

        final Map<String, String> matchMap = new HashMap<>();

        for(int i = 0; i < names.length; i++) {
            matchMap.put(names[i], matches[i]);
        }

        return matchMap;
    }

    private void placeMatch(final int place, final String toMatch, final String[] names, final String[] matches, final XmasPayload payload) {
        if(toMatch == null) return;

        for(int i = 0; i < names.length; i++) {
            final int p = (place + i) % names.length;

            if(validPlacement(p, toMatch, names, payload)) {
                final String tmp = matches[p];
                matches[p] = toMatch;

                placeMatch(p + 1, tmp, names, matches, payload);

                return;
            }
        }

        logger.fatal("Failed to place match!\r\nplace [{}];\r\ntoMatch [{}];\r\nnames [{}]\r\nmatches[{}]\r\npayload[{}]\r\n",
                place, toMatch, Arrays.toString(names), Arrays.toString(matches), payload);

        throw new RuntimeException("Failed to place match!");
    }

    private boolean validPlacement(final int i, final String toMatch, final String[] names, final XmasPayload payload) {
        return !(
                Objects.equals(names[i], toMatch) ||
                        isExcluded(names[i], toMatch, payload) ||
                        isExcluded(toMatch, names[i], payload)
        );
    }

    private boolean isExcluded(final String name, final String toCheck, final XmasPayload payload) {
        logger.info( String.format( "Name: %s\tTo check %s", name, toCheck ) );
        return payload.getNameExclusions().getLastYears().get(name).contains(toCheck) ||
                        payload.getNameExclusions().getMisc().get(name).contains(toCheck);
    }
}
