package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class NavigationManager {
    private final JFrame frame;
    private final JPanel root;
    private final CardLayout cardLayout;
    private final Deque<String> history = new ArrayDeque<>();

    public NavigationManager(JFrame frame, JPanel root, CardLayout cardLayout) {
        this.frame = frame;
        this.root = root;
        this.cardLayout = cardLayout;
    }

    public void goTo(String screenName) {
        // push current (top) into history only if different
        if (!history.isEmpty() && history.peek().equals(screenName)) {
            cardLayout.show(root, screenName);
            return;
        }
        history.push(screenName);
        cardLayout.show(root, screenName);
        frame.revalidate();
        frame.repaint();
    }

    public void goBack() {
        if (history.size() <= 1) return; // nothing to go back to
        history.pop(); // pop current
        String previous = history.peek();
        cardLayout.show(root, previous);
        frame.revalidate();
        frame.repaint();
    }

    public boolean canGoBack() {
        return history.size() > 1;
    }
}
