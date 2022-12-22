import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener {

    SchoolSystemSwing sss;

    public EventHandler(SchoolSystemSwing sss) {
        this.sss = sss;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(sss.admin))
            sss.setStartPage();
    }
}
