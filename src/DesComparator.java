import java.util.Comparator;

public class DesComparator implements Comparator<Appointment>{

    @Override
    public int compare(Appointment a1, Appointment a2) {
        int descriptionComparison = a1.getDescription().compareTo(a2.getDescription());
        if (descriptionComparison != 0) {
            return descriptionComparison;
        }

        int startDateComparison = a1.getStartDate().compareTo(a2.getStartDate());
        if (startDateComparison != 0) {
            return startDateComparison;
        }

        return a1.getEndDate().compareTo(a2.getEndDate());
    }
}
