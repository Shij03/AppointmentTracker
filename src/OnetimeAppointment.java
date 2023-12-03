import java.time.LocalDate;

public class OnetimeAppointment extends Appointment{
    public OnetimeAppointment(String d, LocalDate date) {
        super(d, date, date);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return getStartDate().equals(date);
    }
}
