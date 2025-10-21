import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class HotelReservationSystem extends JFrame implements ActionListener {
    private JTextField nameField, phoneField, checkInField, checkOutField;
    private JComboBox<String> roomTypeBox;
    private JButton addButton, resetButton, exitButton;
    private JTable table;
    private DefaultTableModel model;

    public HotelReservationSystem() {
        setTitle("Hotel Reservation System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== Input Panel =====
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Booking Details"));

        inputPanel.add(new JLabel("Guest Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Room Type:"));
        String[] roomTypes = { "Single", "Double", "Suite" };
        roomTypeBox = new JComboBox<>(roomTypes);
        inputPanel.add(roomTypeBox);

        inputPanel.add(new JLabel("Check-In Date (dd/mm/yyyy):"));
        checkInField = new JTextField();
        inputPanel.add(checkInField);

        inputPanel.add(new JLabel("Check-Out Date (dd/mm/yyyy):"));
        checkOutField = new JTextField();
        inputPanel.add(checkOutField);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        addButton = new JButton("Add Reservation");
        resetButton = new JButton("Reset");
        exitButton = new JButton("Exit");

        addButton.addActionListener(this);
        resetButton.addActionListener(this);
        exitButton.addActionListener(this);

        buttonPanel.add(addButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);

        // ===== Table =====
        String[] columns = { "Guest Name", "Phone", "Room Type", "Check-In", "Check-Out", "Total Days", "Amount" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Reservations"));

        // ===== Combine Panels =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String roomType = (String) roomTypeBox.getSelectedItem();
                String checkIn = checkInField.getText();
                String checkOut = checkOutField.getText();

                if (name.isEmpty() || phone.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int totalDays = calculateDays(checkIn, checkOut);
                double pricePerDay = (roomType.equals("Single")) ? 1000 : (roomType.equals("Double")) ? 1800 : 3000;
                double totalAmount = totalDays * pricePerDay;

                model.addRow(new Object[] { name, phone, roomType, checkIn, checkOut, totalDays, "₹" + totalAmount });
                clearFields();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Use dd/mm/yyyy", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetButton) {
            clearFields();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private int calculateDays(String checkIn, String checkOut) {
        // Simple date difference (for demo)
        // Format: dd/mm/yyyy
        String[] in = checkIn.split("/");
        String[] out = checkOut.split("/");
        int d1 = Integer.parseInt(in[0]);
        int d2 = Integer.parseInt(out[0]);
        int m1 = Integer.parseInt(in[1]);
        int m2 = Integer.parseInt(out[1]);

        // assuming same month for simplicity
        return Math.max((d2 - d1), 1);
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        checkInField.setText("");
        checkOutField.setText("");
        roomTypeBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelReservationSystem().setVisible(true));
    }
}