package main;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
  private JFrame jframe;
  public GameWindow(GamePanel gamePanel) {

    jframe = new JFrame();

    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.add(gamePanel);
    jframe.setSize(1280,800);
    jframe.setResizable(false);
    jframe.pack();
    jframe.setLocationRelativeTo(null);
    jframe.setVisible(true);
    jframe.addWindowFocusListener(new WindowFocusListener() {

      @Override
      public void windowLostFocus(WindowEvent e) {
        gamePanel.getGame().windowFocusLost();
      }

      @Override
      public void windowGainedFocus(WindowEvent e) {
      }
    });
  }
}
