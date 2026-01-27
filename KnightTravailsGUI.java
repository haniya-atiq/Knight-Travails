import java.awt.*;
import java.util.*;
import javax.swing.*;

public class KnightTravailsGUI extends JFrame {

    private JButton[][] board = new JButton[8][8];
    private Point start = null, end = null;

    private final int[] dx = {2, 2, -2, -2, 1, 1, -1, -1};
    private final int[] dy = {1, -1, 1, -1, 2, -2, 2, -2};

    public KnightTravailsGUI() {
        setTitle("Knight Travails - DSA Project");
        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel chessBoard = new JPanel(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);

                int x = i, y = j;
                btn.addActionListener(e -> selectCell(x, y));

                board[i][j] = btn;
                chessBoard.add(btn);
            }
        }

        JPanel controls = new JPanel();
        JButton findPath = new JButton("Find Path");
        JButton reset = new JButton("Reset");

        findPath.addActionListener(e -> findKnightPath());
        reset.addActionListener(e -> resetBoard());

        controls.add(findPath);
        controls.add(reset);

        add(chessBoard, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void selectCell(int x, int y) {
        if (start == null) {
            start = new Point(x, y);
            board[x][y].setBackground(Color.GREEN);
        } else if (end == null) {
            end = new Point(x, y);
            board[x][y].setBackground(Color.RED);
        }
    }

    private void findKnightPath() {
        if (start == null || end == null) {
            JOptionPane.showMessageDialog(this, "Select start and end positions!");
            return;
        }

        Point[][] parent = new Point[8][8];
        boolean[][] visited = new boolean[8][8];
        Queue<Point> queue = new LinkedList<>();

        queue.add(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            if (p.equals(end)) break;

            for (int i = 0; i < 8; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];

                if (isValid(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    parent[nx][ny] = p;
                    queue.add(new Point(nx, ny));
                }
            }
        }

        showPath(parent);
    }

    private void showPath(Point[][] parent) {

    ArrayList<Point> path = new ArrayList<>();
    Point p = end;

    // Build path from end to start
    while (p != null) {
        path.add(p);
        p = parent[p.x][p.y];
    }

    // Reverse path (start → end)
    Collections.reverse(path);

    // Display path
    for (int i = 0; i < path.size(); i++) {
        Point step = path.get(i);
        board[step.x][step.y].setText(String.valueOf(i));
        board[step.x][step.y].setBackground(Color.CYAN);
    }

    // Mark start and end clearly
    board[start.x][start.y].setBackground(Color.GREEN);
    board[end.x][end.y].setBackground(Color.RED);
}

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }

    private void resetBoard() {
        start = end = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].setText("");
                board[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }

    public static void main(String[] args) {
        new KnightTravailsGUI();
    }
}
