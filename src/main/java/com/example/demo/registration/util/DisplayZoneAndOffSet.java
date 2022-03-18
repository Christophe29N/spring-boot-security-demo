package com.example.demo.registration.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DisplayZoneAndOffSet {

	public static final boolean SORT_BY_REGION = true;

	public static void main(String[] argv) {

		Map<String, String> sortedMap = new LinkedHashMap<>();

		Map<String, String> allZoneIdsAndItsOffSet = getAllZoneIdsAndItsOffSet();

		// sort map by key
		if (SORT_BY_REGION) {
			allZoneIdsAndItsOffSet.entrySet().stream().sorted(Map.Entry.comparingByKey())
					.forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
		} else {
			// sort by value, descending order
			allZoneIdsAndItsOffSet.entrySet().stream().sorted(Map.Entry.<String, String>comparingByValue().reversed())
					.forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
		}

		// print map
		sortedMap.forEach((k, v) -> {
			String out = String.format("%35s (UTC%s) %n", k, v);
			System.out.printf(out);
		});

		System.out.println("\nTotal Zone IDs " + sortedMap.size());
		
		
		String[] ids = TimeZone.getAvailableIDs();
		for (String id : ids) {
			System.out.println(displayTimeZone(TimeZone.getTimeZone(id)));
		}
		
		System.out.println("\nTotal TimeZone ID " + ids.length);
	}

	private static Map<String, String> getAllZoneIdsAndItsOffSet() {

		Map<String, String> result = new HashMap<>();

		LocalDateTime localDateTime = LocalDateTime.now();

		for (String zoneId : ZoneId.getAvailableZoneIds()) {

			ZoneId id = ZoneId.of(zoneId);

			// LocalDateTime -> ZonedDateTime
			ZonedDateTime zonedDateTime = localDateTime.atZone(id);

			// ZonedDateTime -> ZoneOffset
			ZoneOffset zoneOffset = zonedDateTime.getOffset();

			// replace Z to +00:00
			String offset = zoneOffset.getId().replaceAll("Z", "+00:00");

			result.put(id.toString(), offset);
		}
		return result;
	}
	
	private static String displayTimeZone(TimeZone tz) {

		long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
		// avoid -4:-30 issue
		minutes = Math.abs(minutes);

		String result = "";
		if (hours > 0) {
			result = String.format("(GMT+%d:%02d) %s", hours, minutes, tz.getID());
		} else {
			result = String.format("(GMT%d:%02d) %s", hours, minutes, tz.getID());
		}

		return result;

	}
}