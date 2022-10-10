package com.qiren.project.ui;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.LoggingCenter;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatePickerPanel extends JPanel {

    private JFrame frame;
    private OnSaveTimeListener listener;

    private JPanel centerPanel;
    private JPanel southPanel;

    private List<String> weekDays = new ArrayList<>();
    private List<LocalTime> fromTimes = new ArrayList<>();
    private List<LocalTime> toTimes = new ArrayList<>();

    public void setListener(OnSaveTimeListener listener) {
        this.listener = listener;
    }

    public DatePickerPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 4, 20, 20));

        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        JButton resetButton = new JButton("Reset All");
        resetButton.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(
                    frame, "Are you sure to reset all?");
            if (res != 0) {
                return;
            }
            centerPanel.removeAll();
            centerPanel.revalidate();
            init();
            if (null != listener) {
                listener.onReset();
            }
            weekDays.clear();
            fromTimes.clear();
            toTimes.clear();
        });
        southPanel.add(new JLabel(" "));
        southPanel.add(resetButton);

        // setLayout(new GridLayout(0, 4, 20, 20));
        init();
    }

    private void init() {
//        JPanel southPanel = new JPanel();
//        southPanel.setLayout(new GridLayout(0, 3));

        JLabel dateLabel = new JLabel("Pick Date");
        JLabel fromTimeLabel = new JLabel("Pick From Time");
        JLabel toTimeLabel = new JLabel("Pick To Time");

        centerPanel.add(dateLabel);
        centerPanel.add(fromTimeLabel);
        centerPanel.add(toTimeLabel);
        centerPanel.add(new JLabel(" "));

        addPicker();
    }

    private void addPicker() {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setLocale(Locale.CANADA);
        settings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.use24HourClockFormat();

        // add(new DatePicker(settings));
        JComboBox<DayOfWeek> weekDayCombo = new JComboBox<>();
        weekDayCombo.addItem(DayOfWeek.MONDAY);
        weekDayCombo.addItem(DayOfWeek.TUESDAY);
        weekDayCombo.addItem(DayOfWeek.WEDNESDAY);
        weekDayCombo.addItem(DayOfWeek.THURSDAY);
        weekDayCombo.addItem(DayOfWeek.FRIDAY);
        // do not add non-workdays

        TimePicker fromTime = new TimePicker(timeSettings);
        fromTime.setTime(LocalTime.NOON);
        TimePicker toTime = new TimePicker(timeSettings);
        toTime.setTime(LocalTime.NOON);

        centerPanel.add(weekDayCombo);
        centerPanel.add(fromTime);
        centerPanel.add(toTime);
        // JButton addButton = new JButton("Save and Add Weekday");
        // It will be very complex to add multiple time, so close for now.
        JButton addButton = new JButton("Save Weekday");
        addButton.addActionListener(e -> {
            // check if the time is valid:
            // first, check null
            if (null == fromTime.getTime() || null == toTime.getTime()) {
                CoreUtils.showErrorDialog(frame, "You must select time to save!");
                return;
            }
            // then, check self conflict
            if (fromTime.getTime().isAfter(toTime.getTime())
                    || fromTime.getTime().equals(toTime.getTime())) {
                CoreUtils.showErrorDialog(frame, "You cannot select toTime after fromTime!");
                return;
            }
            // then, check conflict (db conflict later)
            for (int i = 0; i < weekDays.size(); i++) {
                if (weekDayCombo.getSelectedItem().toString().equals(weekDays.get(i))) {
                    LocalTime tmpFrom = fromTimes.get(i);
                    LocalTime tmpTo = toTimes.get(i);
                    if ((fromTime.getTime().isAfter(tmpFrom) && fromTime.getTime().isBefore(tmpTo))
                            || (toTime.getTime().isBefore(tmpTo) && toTime.getTime().isAfter(tmpFrom))) {
                        CoreUtils.showErrorDialog(frame, "Current time is conflicting with your previous one!");
                        return;
                    }
                }
            }
            // last, warn if not regular time
            if (fromTime.getTime().isBefore(LocalTime.of(10, 0))
                    || fromTime.getTime().isAfter(LocalTime.of(18, 0))
                    || toTime.getTime().isBefore(LocalTime.of(10, 0))
                    || toTime.getTime().isAfter(LocalTime.of(18, 0))) {
                CoreUtils.showErrorDialog(frame, "Your are adding plan that is in the night!");
            }
            if (null != listener) {
                listener.onSaveDateTime(
                        weekDayCombo.getSelectedItem().toString(),
                        fromTime.getTime(),
                        toTime.getTime()
                );
                weekDays.add(weekDayCombo.getSelectedItem().toString());
                fromTimes.add(fromTime.getTime());
                toTimes.add(toTime.getTime());
            }
            // only save, not add here.
//            addPicker();
//            centerPanel.revalidate();
            LoggingCenter.info("Week Day Picked: " + weekDayCombo.getSelectedItem().toString());
            LoggingCenter.info("From Time Picked: " + fromTime.getTime().toString());
            LoggingCenter.info("To Time Picked: " + toTime.getTime().toString());
        });

        centerPanel.add(addButton);
    }

    interface OnSaveTimeListener {
        void onSaveDateTime(String weekDay, LocalTime fromTime, LocalTime toTime);

        void onReset();
    }
}
