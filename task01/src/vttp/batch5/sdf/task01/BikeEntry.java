package vttp.batch5.sdf.task01;

public class BikeEntry {
    
    int position;

    String season;
    String weekday;
    String month;
    int totalCyclists;
    String weather;
    boolean isHoliday;


    public BikeEntry(String season, String weekday, String month, int totalCyclists, String weather,
            boolean isHoliday) {
        this.position = -1;
        this.season = season;
        this.weekday = weekday;
        this.month = month;
        this.totalCyclists = totalCyclists;
        this.weather = weather;
        this.isHoliday = isHoliday;
    }


    

    public int getPosition() {
        return position;
    }

    public String getSeason() {
        return season;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getMonth() {
        return month;
    }

    public int getTotalCyclists() {
        return totalCyclists;
    }

    public String getWeather() {
        return weather;
    }

    public boolean isHoliday() {
        return isHoliday;
    }


    



    public void setPosition(int position) {
        this.position = position;
    }




    @Override
    public String toString() {
        return "BikeEntry [position=" + position + ", season=" + season + ", weekday=" + weekday + ", month=" + month
                + ", totalCyclists=" + totalCyclists + ", weather=" + weather + ", isHoliday=" + isHoliday + "]";
    }


}
