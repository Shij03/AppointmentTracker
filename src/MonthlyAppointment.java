import java.time.LocalDate;

public class MonthlyAppointment extends Appointment{
    public MonthlyAppointment(String d, LocalDate sD, LocalDate eD) {
        super(d, sD, eD);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return !date.isBefore(getStartDate()) && !date.isAfter(getEndDate()) && date.getDayOfMonth() == getStartDate().getDayOfMonth();
    }
}
