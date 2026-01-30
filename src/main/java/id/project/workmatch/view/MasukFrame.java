package id.project.workmatch.view;
import id.project.workmatch.controller.Data;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MasukFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private ModernButton loginBtn;
    
    public MasukFrame() {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle("WorkMatch - Masuk");
        setSize(420, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header Panel with Gradient
        GradientPanel header = new GradientPanel();
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new GridBagLayout());
        
        JLabel title = new JLabel("WORKMATCH");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        header.add(title);
        
        add(header, BorderLayout.NORTH);
        
        // Center Panel
        JPanel center = new JPanel();
        center.setLayout(null);
        center.setBackground(Color.WHITE);
        
        JLabel ulabel = new JLabel("Nama:");
        ulabel.setBounds(50, 30, 100, 25);
        center.add(ulabel);
        
        userField = new JTextField();
        userField.setBounds(160, 30, 200, 25);
        center.add(userField);
        
        JLabel plabel = new JLabel("Sandi:");
        plabel.setBounds(50, 70, 100, 25);
        center.add(plabel);
        
        passField = new JPasswordField();
        passField.setBounds(160, 70, 200, 25);
        center.add(passField);
        
        // Modern Button with gradient
        loginBtn = new ModernButton("Login");
        loginBtn.setBounds(160, 120, 200, 50);
        center.add(loginBtn);
        
        add(center, BorderLayout.CENTER);
        
        // Event Listeners
        loginBtn.addActionListener(e -> doLogin());
        
        // Enter key support
        passField.addActionListener(e -> doLogin());
    }
    
    private void doLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();
        
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi Nama & Sandi.");
            return;
        }
        
        if (Data.getInstance().authenticate(user, pass)) {
            new LowonganFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal Masuk. Periksa Nama & Sandi.");
        }
    }
    
    // Inner class for gradient header panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Gradient background same as button
            GradientPaint gradient = new GradientPaint(0, 0, new Color(91, 192, 222), width, 0, new Color(52, 152, 219));
            g2.setPaint(gradient);
            g2.fillRect(0, 0, width, height);
            
            g2.dispose();
        }
    }
    
    // Inner class for modern gradient button (without icon)
    class ModernButton extends JButton {
        private Color color1 = new Color(91, 192, 222); // Light cyan
        private Color color2 = new Color(52, 152, 219); // Darker blue
        private Color hoverColor1 = new Color(101, 202, 232);
        private Color hoverColor2 = new Color(62, 162, 229);
        private boolean isHovered = false;
        
        public ModernButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(getFont().deriveFont(Font.BOLD, 18f));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Add hover effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Shadow effect
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillRoundRect(2, 4, width - 4, height - 4, 30, 30);
            
            // Gradient background
            GradientPaint gradient;
            if (isHovered) {
                gradient = new GradientPaint(0, 0, hoverColor1, width, 0, hoverColor2);
            } else {
                gradient = new GradientPaint(0, 0, color1, width, 0, color2);
            }
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, width - 4, height - 6, 30, 30);
            
            g2.dispose();
            super.paintComponent(g);
        }
    }
}