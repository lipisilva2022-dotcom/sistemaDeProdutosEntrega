import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class RotacenterGUI extends JFrame {

    private final DatabaseManager database;

    private final Color BACKGROUND = new Color(242, 245, 249);
    private final Color SIDEBAR = new Color(25, 39, 52);
    private final Color GREEN = new Color(32, 160, 105);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(45, 55, 65);

    private DefaultTableModel tableModel;

    private JLabel totalPedidos;
    private JLabel pendentes;
    private JLabel faturados;
    private JLabel entregues;

    public RotacenterGUI(DatabaseManager database) {

        this.database = database;

        setTitle("ROTACENTER - Sistema de Gestão");
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        criarMenuLateral();
        criarPainelPrincipal();

        atualizarDados();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    database.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar conexão: "
                            + ex.getMessage());
                }
            }
        });
    }

    // ==========================================
    // MENU LATERAL
    // ==========================================

    private void criarMenuLateral() {

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  ROTACENTER");

        logo.setFont(new Font("Arial", Font.BOLD, 23));
        logo.setForeground(WHITE);
        logo.setBorder(
                BorderFactory.createEmptyBorder(30, 10, 35, 10)
        );

        sidebar.add(logo);

        sidebar.add(criarBotaoMenu("Painel inicial"));
        sidebar.add(criarBotaoMenu("Pedidos"));
        sidebar.add(criarBotaoMenu("Clientes"));
        sidebar.add(criarBotaoMenu("Produtos"));
        sidebar.add(criarBotaoMenu("Relatórios"));

        sidebar.add(Box.createVerticalGlue());

        JLabel versao = new JLabel("  ROTACENTER v1.0");

        versao.setForeground(Color.LIGHT_GRAY);
        versao.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 20, 10)
        );

        sidebar.add(versao);

        add(sidebar, BorderLayout.WEST);
    }

    private JButton criarBotaoMenu(String texto) {

        JButton botao = new JButton(texto);

        botao.setMaximumSize(new Dimension(230, 48));
        botao.setPreferredSize(new Dimension(230, 48));

        botao.setForeground(WHITE);
        botao.setBackground(SIDEBAR);

        botao.setFont(new Font("Arial", Font.PLAIN, 15));
        botao.setHorizontalAlignment(SwingConstants.LEFT);

        botao.setBorder(
                BorderFactory.createEmptyBorder(10, 25, 10, 10)
        );

        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addActionListener(e -> {

            if (texto.equals("Painel inicial")) {

                atualizarDados();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "A seção " + texto + " será implementada.",
                        "ROTACENTER",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        return botao;
    }

    // ==========================================
    // PAINEL PRINCIPAL
    // ==========================================

    private void criarPainelPrincipal() {

        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));

        contentPanel.setBackground(BACKGROUND);

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.setOpaque(false);

        JLabel titulo = new JLabel("Painel de Controle");

        titulo.setFont(new Font("Arial", Font.BOLD, 27));
        titulo.setForeground(TEXT);

        JButton atualizar = new JButton("Atualizar dados");

        atualizar.addActionListener(e -> atualizarDados());

        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(atualizar, BorderLayout.EAST);

        contentPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 20));

        centerPanel.setOpaque(false);

        centerPanel.add(criarCards(), BorderLayout.NORTH);
        centerPanel.add(criarTabelaPedidos(), BorderLayout.CENTER);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    // ==========================================
    // INDICADORES
    // ==========================================

    private JPanel criarCards() {

        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0));

        cards.setOpaque(false);

        totalPedidos = new JLabel("0");
        pendentes = new JLabel("0");
        faturados = new JLabel("0");
        entregues = new JLabel("0");

        cards.add(criarCard(
                "TOTAL DE PEDIDOS",
                totalPedidos,
                new Color(48, 110, 190)
        ));

        cards.add(criarCard(
                "PENDENTES",
                pendentes,
                new Color(225, 160, 45)
        ));

        cards.add(criarCard(
                "FATURADOS",
                faturados,
                new Color(115, 95, 190)
        ));

        cards.add(criarCard(
                "ENTREGUES",
                entregues,
                GREEN
        ));

        return cards;
    }

    private JPanel criarCard(String titulo, JLabel valor, Color cor) {

        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 5, 0, 0, cor
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        JLabel nome = new JLabel(titulo);

        nome.setFont(new Font("Arial", Font.BOLD, 12));
        nome.setForeground(Color.GRAY);

        valor.setFont(new Font("Arial", Font.BOLD, 30));
        valor.setForeground(TEXT);

        card.add(nome);
        card.add(Box.createVerticalStrut(15));
        card.add(valor);

        return card;
    }

    // ==========================================
    // TABELA DE PEDIDOS
    // ==========================================

    private JPanel criarTabelaPedidos() {

        JPanel panel = new JPanel(new BorderLayout(10, 15));

        panel.setBackground(WHITE);

        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel titulo = new JLabel("Pedidos recentes");

        titulo.setFont(new Font("Arial", Font.BOLD, 19));
        titulo.setForeground(TEXT);

        String[] colunas = {
                "ID",
                "CLIENTE",
                "DATA DA COMPRA",
                "DATA DE RETIRADA",
                "STATUS"
        };

        tableModel = new DefaultTableModel(colunas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(tableModel);

        tabela.setRowHeight(38);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));

        tabela.setSelectionBackground(
                new Color(220, 240, 230)
        );

        tabela.setSelectionForeground(TEXT);

        JTableHeader header = tabela.getTableHeader();

        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(235, 240, 245));
        header.setForeground(TEXT);

        header.setPreferredSize(new Dimension(0, 40));

        JScrollPane scroll = new JScrollPane(tabela);

        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ==========================================
    // CONSULTA AO BANCO DE DADOS
    // ==========================================

    private void atualizarDados() {

        try {

            ArrayList<Order> pedidos = database.findAllOrders();

            int total = pedidos.size();
            int pendentesCount = 0;
            int faturadosCount = 0;
            int entreguesCount = 0;

            tableModel.setRowCount(0);

            for (Order pedido : pedidos) {

                switch (pedido.getStatus()) {

                    case "PENDENTE":
                        pendentesCount++;
                        break;

                    case "FATURADO":
                        faturadosCount++;
                        break;

                    case "ENTREGUE":
                        entreguesCount++;
                        break;
                }

                String retirada = pedido.getPickupDate() != null
                        ? pedido.getPickupDate().toString()
                        : "Não definida";

                tableModel.addRow(new Object[]{
                        pedido.getId(),
                        pedido.getCliente().getNome(),
                        pedido.getPurchaseDate(),
                        retirada,
                        pedido.getStatus()
                });
            }

            totalPedidos.setText(String.valueOf(total));
            pendentes.setText(String.valueOf(pendentesCount));
            faturados.setText(String.valueOf(faturadosCount));
            entregues.setText(String.valueOf(entreguesCount));

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao consultar os pedidos:\n" + e.getMessage(),
                    "Erro no banco de dados",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // INICIALIZAÇÃO DO SISTEMA
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                DatabaseManager database = new DatabaseManager();

                TesteDatabase.executar(database);

                RotacenterGUI janela = new RotacenterGUI(database);

                janela.setVisible(true);

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Não foi possível conectar ao MySQL:\n"
                                + e.getMessage(),
                        "Falha na conexão",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}