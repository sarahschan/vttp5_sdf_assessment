package vttp.batch5.sdf.task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Use this class as the entry point of your program

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {


		// Prepare where to store the read data
		List<BikeEntry> bikeEntryList = new ArrayList<>();
		
		// Read the csv file
		String filePath = "day.csv";
		FileReader fr = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
		// System.out.println("Able to read file");

		String line = "";
		boolean isFirstLine = true;
		
		while ((line = br.readLine()) != null) {

			// skip the first line
			if (isFirstLine) {
				isFirstLine = false;
				continue;
			}

			// From second line onwards
			// 1. Extract essential information
			String[] info = line.split(",");
				int sEASON = Integer.parseInt(info[0]);
				String season = Utilities.toSeason(sEASON);

				int mONTH = Integer.parseInt(info[1]);
				String month = Utilities.toMonth(mONTH);

				int iSHOLIDAY = Integer.parseInt(info[2]);
				Boolean isHoliday = setHoliday(iSHOLIDAY);

				int wEEDAY = Integer.parseInt(info[3]);
				String weekday = toWeekday(wEEDAY);

				int wEATHER = Integer.parseInt(info[4]);
				String weather = setWeather(wEATHER);

				int cASUAL = Integer.parseInt(info[8]);
				int rEGISTERED = (Integer.parseInt(info[9]));
				int totalCyclists = countCyclists(cASUAL, rEGISTERED);

				// 2. Add to list
				BikeEntry entry = new BikeEntry(season, weekday, month, totalCyclists, weather, isHoliday);
				bikeEntryList.add(entry);
				
		}

		// for (BikeEntry entry : bikeEntryList) {
		// 	System.out.println(entry);
		// }


		// Sort the list by highest totalCyclists
		List<BikeEntry> sortedEntries = bikeEntryList.stream()
			.sorted(Comparator.comparing(BikeEntry::getTotalCyclists).reversed())
			.collect(Collectors.toList());

		// for (BikeEntry entry : sortedEntries) {
		// 	System.out.println(entry);
		// }


		// Create list for top 5
		List<BikeEntry> topFive = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			topFive.add(sortedEntries.get(i));
		}

		// Set the position
		for (int i = 0; i < topFive.size(); i++) {
			topFive.get(i).setPosition(i + 1);
		}

		// for (BikeEntry entry : topFive) {
		// 	System.out.println(entry);
		// }


		for (BikeEntry entry : topFive) {
			System.out.printf("The %s (position) recorded number of cyclists was in %s (season), on a %s (day) in the month of %s (month).\nThere were a total of %d (total) cyclists. The weather was %s (weather).\n%s (day) was %s.",
										convertPosition(entry.getPosition()),
										entry.getSeason(),
										entry.getWeekday(),
										entry.getMonth(),
										entry.getTotalCyclists(),
										entry.getWeather(),
										entry.getWeekday(),
										entry.isHoliday() ? "a holiday" : "not a holiday");
			System.out.println();
			System.out.println();
		}


	}

	private static String convertPosition(int position) {
		switch (position) {
			case 1:
				return "highest";
			case 2:
				return "second highest";
			case 3:
				return "third highest";
			case 4:
				return "fourth highest";
			case 5:
				return "fifth highest";
			default:
				return "invalid";
		}
	}

	private static int countCyclists(int cASUAL, int rEGISTERED) {
		int totalCyclists = cASUAL + rEGISTERED;
		return totalCyclists;
	}

	private static String setWeather(int wEATHER) {
		switch (wEATHER) {
			case 1: 
				return "Clear, Few clouds, Partly cloudy, Partly cloudy";

			case 2: 
				return "Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist";
			
			case 3:
				return "Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds";

			case 4:
				return "Heavy Rain + Ice Pallets + Thunderstorm + Mist, Snow + Fog";

			default:
				return "Unknown weather";

		}
	}

	private static Boolean setHoliday(int iSHOLIDAY) {
		if (iSHOLIDAY == 1) {
			return true;

		} else {
			return false;
		}
	}

	public static String toWeekday(int weekday) {
		switch (weekday) {
			case 0:
				return "Sunday";
			case 1:
				return "Monday";
			case 2:
				return "Tuesday";
			case 3:
				return "Wednesday";
			case 4:
				return "Thursday";
			case 5:
				return "Friday";
			case 6:
				return "Saturday";

			default:
				return "incorrect day";
		}
	}
	
	
}
