import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Appointment implements Comparable<Appointment>{
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Appointment(String d, LocalDate sD, LocalDate eD){
        this.description = d;
        this.startDate = sD;
        this.endDate = eD;
    }

    public String getDescription(){
        return description;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public abstract boolean occursOn(LocalDate date);

    @Override
    public int compareTo(Appointment a){
        int startDateComparison = this.startDate.compareTo(a.startDate);
        if (startDateComparison != 0) {
            return startDateComparison;
        }

        int endDateComparison = this.endDate.compareTo(a.endDate);
        if (endDateComparison != 0) {
            return endDateComparison;
        }

        return this.description.compareTo(a.description);
    }

    // Sources:
    // https://www.geeksforgeeks.org/introduction-to-java-swing/
    // https://www.tutorialspoint.com/swing/swing_action_listener.htm
    // https://www.tutorialspoint.com/swing/swing_action_event.htm
    public static void appointmentUI(ArrayList<Appointment> appointments) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField typeInput = new JTextField(20);
        JTextField descriptionInput = new JTextField(20);
        JTextField startDateInput = new JTextField(20);
        JTextField endDateInput = new JTextField(20);

        panel.add(new JLabel("Appointment Type (One Time, Daily, Monthly):"));
        panel.add(typeInput);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionInput);
        panel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        panel.add(startDateInput);
        panel.add(new JLabel("End Date (YYYY-MM-DD):"));
        panel.add(endDateInput);

        JButton addButton = new JButton("Add Appointment");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String description = descriptionInput.getText();
                LocalDate startDate = LocalDate.parse(startDateInput.getText(), DateTimeFormatter.ISO_DATE);
                LocalDate endDate = LocalDate.parse(endDateInput.getText(), DateTimeFormatter.ISO_DATE);
                String appointmentType = typeInput.getText().toLowerCase();

                if (endDate.isBefore(startDate)) {
                    JOptionPane.showMessageDialog(frame, "End date cannot be before the start date!");
                    return;
                }

                Appointment newAppointment = null;
                switch (appointmentType) {
                    case "one time":
                        newAppointment = new OnetimeAppointment(description, startDate);
                        break;
                    case "daily":
                        newAppointment = new DailyAppointment(description, startDate, endDate);
                        break;
                    case "monthly":
                        newAppointment = new MonthlyAppointment(description, startDate, endDate);
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame, "Invalid appointment type!");
                        return;
                }
                appointments.add(newAppointment);

            }
        });

        JButton displayAllButton = new JButton("Display All Appointments");
        displayAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAppointments(appointments);
            }
        });

        panel.add(addButton);
        panel.add(displayAllButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void displayAppointments(ArrayList<Appointment> appointments) {
        JTextArea displayArea = new JTextArea(20, 40);
        displayArea.setEditable(false);

        JFrame displayFrame = new JFrame("All Appointments");
        displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayFrame.add(new JScrollPane(displayArea));

        StringBuilder sb = new StringBuilder();
        for (Appointment appointment : appointments) {
            sb.append("Description: ").append(appointment.getDescription()).append("\n");
            sb.append("Start Date: ").append(appointment.getStartDate()).append("\n");
            sb.append("End Date: ").append(appointment.getEndDate()).append("\n");
            sb.append("------------------------------\n");
        }

        displayArea.setText(sb.toString());

        displayFrame.pack();
        displayFrame.setVisible(true);
    }

    public static class AppointmentGUI extends JFrame {
        private ArrayList<Appointment> appointments = new ArrayList<>();
    }

    public static void main(String[] args) {
        AppointmentGUI app = new AppointmentGUI();
        app.setVisible(true);
        Appointment.appointmentUI(app.appointments);
    }
}