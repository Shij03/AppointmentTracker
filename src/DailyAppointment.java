import java.time.LocalDate;

public class DailyAppointment extends Appointment{
    public DailyAppointment(String d, LocalDate sD, LocalDate eD) {
        super(d, sD, eD);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return !date.isBefore(getStartDate()) && !date.isAfter(getEndDate());
    }
}
