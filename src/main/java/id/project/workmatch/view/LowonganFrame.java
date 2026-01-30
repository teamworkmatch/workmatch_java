package id.project.workmatch.view;

import id.project.workmatch.controller.Data;
import id.project.workmatch.model.Lowongan;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LowonganFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    
    // Modern colors matching login
    private static final Color PRIMARY = new Color(46, 134, 193);
    private static final Color SUCCESS = new Color(39, 174, 96);
    private static final Color DANGER = new Color(231, 76, 60);
    private static final Color WARNING = new Color(243, 156, 18);
    private static final Color INFO = new Color(52, 152, 219);
    private static final Color BG = new Color(236, 240, 241);

    public LowonganFrame() {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle("WorkMatch - Lowongan");
        setSize(900, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(BG);

        // Header with gradient
        GradientPanel header = new GradientPanel();
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new GridBagLayout());
        
        JLabel title = new JLabel("WORKMATCH - DAFTAR LOWONGAN");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        header.add(title);
        
        add(header, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(BG);
        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setBounds(20, 20, 840, 60);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(buttonPanel);

        // Buttons with gradient
        JButton tambah = createGradientButton("Tambah", SUCCESS, 10, 10, e -> onTambah());
        JButton edit = createGradientButton("Ubah", INFO, 150, 10, e -> onEdit());
        JButton hapus = createGradientButton("Hapus", DANGER, 290, 10, e -> onHapus());
        JButton impor = createGradientButton("Impor", WARNING, 430, 10, e -> onImport());
        JButton export = createGradientButton("Ekspor", PRIMARY, 570, 10, e -> onExport());
        
        buttonPanel.add(tambah);
        buttonPanel.add(edit);
        buttonPanel.add(hapus);
        buttonPanel.add(impor);
        buttonPanel.add(export);

        // Table
        String[] cols = {"ID", "Judul", "Perusahaan", "Lokasi", "Deskripsi"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(189, 195, 199));
        table.setSelectionBackground(new Color(174, 214, 241));
        table.setSelectionForeground(new Color(33, 47, 61));
        table.setForeground(new Color(33, 47, 61));
        table.setBackground(Color.WHITE);
        
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 100, 840, 390);
        sp.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true));
        mainPanel.add(sp);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = getWidth() - 60;
                buttonPanel.setBounds(20, 20, w, 60);
                sp.setBounds(20, 100, w, getHeight() - 210);
            }
        });

        // Help menu
        JMenuBar menuBar = new JMenuBar();
        JMenu infoMenu = new JMenu("Info");
        JMenuItem infoItem = new JMenuItem("Cara Menggunakan");
        infoItem.addActionListener(e -> onInfo());
        infoMenu.add(infoItem);
        menuBar.add(infoMenu);
        setJMenuBar(menuBar);

        refreshTable();
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
            
            // Gradient background same as login button
            GradientPaint gradient = new GradientPaint(0, 0, new Color(91, 192, 222), width, 0, new Color(52, 152, 219));
            g2.setPaint(gradient);
            g2.fillRect(0, 0, width, height);
            
            g2.dispose();
        }
    }

    private JButton createGradientButton(String text, Color baseColor, int x, int y, ActionListener al) {
        JButton b = new JButton(text) {
            private boolean isHovered = false;
            
            {
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
                g2.fillRoundRect(2, 2, width - 2, height - 2, 10, 10);
                
                // Gradient background
                Color color1 = baseColor;
                Color color2 = baseColor.darker();
                
                if (isHovered) {
                    color1 = baseColor.brighter();
                    color2 = baseColor;
                }
                
                GradientPaint gradient = new GradientPaint(0, 0, color1, width, 0, color2);
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, width - 2, height - 2, 10, 10);
                
                // Draw text
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), textX, textY);
                
                g2.dispose();
            }
        };
        
        b.setBounds(x, y, 130, 40);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(al);
        
        return b;
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Lowongan l : Data.getInstance().getAll()) {
            model.addRow(new Object[]{l.getId(), l.getJudul(), l.getPerusahaan(), l.getLokasi(), l.getDeskripsi()});
        }
    }

    private void onTambah() {
        LowonganDialog dlg = new LowonganDialog(this, "Tambah Lowongan", null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Data.getInstance().add(dlg.getJudul(), dlg.getPerusahaan(), dlg.getLokasi(), dlg.getDeskripsi());
            refreshTable();
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(this, "Pilih data untuk diubah."); 
            return; 
        }
        int id = (int) model.getValueAt(row, 0);
        Lowongan selected = Data.getInstance().getAll().stream()
            .filter(l -> l.getId() == id).findFirst().orElse(null);
        LowonganDialog dlg = new LowonganDialog(this, "Ubah Lowongan", selected);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Data.getInstance().update(id, dlg.getJudul(), dlg.getPerusahaan(), dlg.getLokasi(), dlg.getDeskripsi());
            refreshTable();
        }
    }

    private void onHapus() {
        int row = table.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(this, "Pilih data untuk dihapus."); 
            return; 
        }
        int id = (int) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Hapus lowongan ID " + id + "?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Data.getInstance().delete(id);
            refreshTable();
        }
    }

    private void onExport() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("Lowongan.xlsx"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Lowongan");
                Row header = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) 
                    header.createCell(i).setCellValue(model.getColumnName(i));
                for (int r = 0; r < model.getRowCount(); r++) {
                    Row row = sheet.createRow(r + 1);
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        Object v = model.getValueAt(r, c);
                        row.createCell(c).setCellValue(v == null ? "" : v.toString());
                    }
                }
                for (int i = 0; i < model.getColumnCount(); i++) sheet.autoSizeColumn(i);
                try (FileOutputStream out = new FileOutputStream(chooser.getSelectedFile())) { 
                    wb.write(out); 
                }
                JOptionPane.showMessageDialog(this, "Ekspor berhasil ke:\n" + 
                    chooser.getSelectedFile().getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal ekspor: " + ex.getMessage());
            }
        }
    }

    private void onImport() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Pilih File Excel untuk Impor");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (Workbook wb = new XSSFWorkbook(chooser.getSelectedFile())) {
                Sheet sheet = wb.getSheetAt(0);
                int importCount = 0;
                int skipCount = 0;
                
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    
                    String judul = getCellValue(row.getCell(1));
                    String perusahaan = getCellValue(row.getCell(2));
                    String lokasi = getCellValue(row.getCell(3));
                    String deskripsi = getCellValue(row.getCell(4));
                    
                    if (!judul.isEmpty() && !perusahaan.isEmpty()) {
                        Data.getInstance().add(judul, perusahaan, lokasi, deskripsi);
                        importCount++;
                    } else {
                        skipCount++;
                    }
                }
                
                refreshTable();
                JOptionPane.showMessageDialog(this, 
                    "Import selesai!\n\n" +
                    "Berhasil: " + importCount + " data\n" +
                    "Dilewati: " + skipCount + " data (data tidak lengkap)", 
                    "Impor Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Gagal impor: " + ex.getMessage() + "\n\n" +
                    "Pastikan file Excel sesuai format ekspor.", 
                    "Kesalahan impor", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "";
        }
    }

    private void onInfo() {
        JOptionPane.showMessageDialog(this, 
            "• Tambah → Tambah lowongan baru\n" +
            "• Ubah → Ubah lowongan terpilih\n" +
            "• Hapus → Hapus lowongan terpilih\n" +
            "• Impor → Impor data dari Excel\n" +
            "• Ekspor → Ekspor data ke Excel\n\n" +
            "Klik baris tabel untuk memilih data.\n\n" +
            "Format Import Excel:\n" +
            "Kolom 1: ID (akan diabaikan)\n" +
            "Kolom 2: Judul (wajib)\n" +
            "Kolom 3: Perusahaan (wajib)\n" +
            "Kolom 4: Lokasi\n" +
            "Kolom 5: Deskripsi", 
            "Cara Menggunakan WorkMatch", JOptionPane.INFORMATION_MESSAGE);
    }

    // Dialog Add/Edit
    private static class LowonganDialog extends JDialog {
        private JTextField judulField, perusahaanField, lokasiField;
        private JTextArea deskArea;
        private boolean saved = false;

        public LowonganDialog(JFrame parent, String title, Lowongan edit) {
            super(parent, title, true);
            setSize(500, 480);
            setLayout(new BorderLayout());
            setLocationRelativeTo(parent);
            getContentPane().setBackground(new Color(236, 240, 241));

            // Header with gradient
            GradientDialogPanel header = new GradientDialogPanel();
            header.setPreferredSize(new Dimension(0, 60));
            header.setLayout(new GridBagLayout());
            
            JLabel headerTitle = new JLabel(title.toUpperCase());
            headerTitle.setForeground(Color.WHITE);
            headerTitle.setFont(headerTitle.getFont().deriveFont(Font.BOLD, 18f));
            header.add(headerTitle);
            
            add(header, BorderLayout.NORTH);

            // Form panel
            JPanel formPanel = new JPanel(null);
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            addField(formPanel, "Judul:", judulField = new JTextField(), 20, 20);
            addField(formPanel, "Perusahaan:", perusahaanField = new JTextField(), 20, 90);
            addField(formPanel, "Lokasi:", lokasiField = new JTextField(), 20, 160);
            
            JLabel lbl = new JLabel("Deskripsi:");
            lbl.setBounds(20, 230, 400, 20);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            formPanel.add(lbl);
            
            deskArea = new JTextArea();
            deskArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            deskArea.setLineWrap(true);
            deskArea.setWrapStyleWord(true);
            JScrollPane sp = new JScrollPane(deskArea);
            sp.setBounds(20, 255, 420, 100);
            sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            formPanel.add(sp);

            add(formPanel, BorderLayout.CENTER);

            // Button panel
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
            btnPanel.setBackground(new Color(236, 240, 241));
            
            JButton save = createDialogGradientButton("Simpan", new Color(39, 174, 96));
            JButton cancel = createDialogGradientButton("Batal", new Color(149, 165, 166));
            
            btnPanel.add(save);
            btnPanel.add(cancel);
            add(btnPanel, BorderLayout.SOUTH);

            if (edit != null) {
                judulField.setText(edit.getJudul());
                perusahaanField.setText(edit.getPerusahaan());
                lokasiField.setText(edit.getLokasi());
                deskArea.setText(edit.getDeskripsi());
            }

            save.addActionListener(e -> {
                if (judulField.getText().trim().isEmpty() || perusahaanField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Judul & Perusahaan harus diisi.");
                    return;
                }
                saved = true; 
                setVisible(false);
            });
            cancel.addActionListener(e -> { 
                saved = false; 
                setVisible(false); 
            });
        }

        // Inner class for dialog gradient panel
        class GradientDialogPanel extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                
                GradientPaint gradient = new GradientPaint(0, 0, new Color(91, 192, 222), width, 0, new Color(52, 152, 219));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, width, height);
                
                g2.dispose();
            }
        }

        private void addField(JPanel p, String label, JTextField field, int x, int y) {
            JLabel lbl = new JLabel(label);
            lbl.setBounds(x, y, 400, 20);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            p.add(lbl);
            
            field.setBounds(x, y + 25, 420, 35);
            field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            p.add(field);
        }

        private JButton createDialogGradientButton(String text, Color baseColor) {
            JButton b = new JButton(text) {
                private boolean isHovered = false;
                
                {
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
                    g2.fillRoundRect(2, 2, width - 2, height - 2, 10, 10);
                    
                    // Gradient background
                    Color color1 = baseColor;
                    Color color2 = baseColor.darker();
                    
                    if (isHovered) {
                        color1 = baseColor.brighter();
                        color2 = baseColor;
                    }
                    
                    GradientPaint gradient = new GradientPaint(0, 0, color1, width, 0, color2);
                    g2.setPaint(gradient);
                    g2.fillRoundRect(0, 0, width - 2, height - 2, 10, 10);
                    
                    // Draw text
                    g2.setColor(getForeground());
                    FontMetrics fm = g2.getFontMetrics();
                    int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                    int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                    g2.drawString(getText(), textX, textY);
                    
                    g2.dispose();
                }
            };
            
            b.setPreferredSize(new Dimension(130, 40));
            b.setFont(new Font("Segoe UI", Font.BOLD, 13));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            b.setOpaque(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            return b;
        }

        public boolean isSaved() { return saved; }
        public String getJudul() { return judulField.getText().trim(); }
        public String getPerusahaan() { return perusahaanField.getText().trim(); }
        public String getLokasi() { return lokasiField.getText().trim(); }
        public String getDeskripsi() { return deskArea.getText().trim(); }
    }
}