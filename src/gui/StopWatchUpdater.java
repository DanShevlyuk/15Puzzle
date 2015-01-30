package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopWatchUpdater implements ActionListener {

    private JLabel stopwatch;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    public StopWatchUpdater(JLabel label) {
        stopwatch = label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        seconds++;
        if (seconds == 60) {
            minutes++;
            seconds = 0;
        }
        if (minutes == 60) {
            hours++;
            minutes = 0;
        }

        stopwatch.setText((hours < 10 ? "0" + hours : hours) + ":" +
                (minutes < 10 ? "0" + minutes : minutes) + ":" +
                (seconds < 10 ? "0" + seconds : seconds));
    }

    public void drop () {
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public void setTime(String time) {
        this.seconds = Integer.parseInt(time.split(":")[2]);
        this.minutes = Integer.parseInt(time.split(":")[1]);
        this.hours = Integer.parseInt(time.split(":")[0]);
    }

    public String getTime() {
        return (hours < 10 ? "0" + hours : hours) + ":" +
                (minutes < 10 ? "0" + minutes : minutes) + ":" +
                (seconds < 10 ? "0" + seconds : seconds);
    }
}
