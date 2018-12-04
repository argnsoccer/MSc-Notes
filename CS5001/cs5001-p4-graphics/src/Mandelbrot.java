import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

public class Mandelbrot extends JPanel implements ActionListener{

    static int counter = 0;
    private static int originalCopy[][] = new int[600][600];
    private int zoomCopy[][] = new int[600][600];
    JFrame zoomFrame = new JFrame("Mandelbrot Zoom Estimate");
    protected static final double INITIAL_MIN_REAL = -2.0;
    protected static final double INITIAL_MAX_REAL = 0.7;
    protected static final double INITIAL_MIN_IMAGINARY = -1.25;
    protected static final double INITIAL_MAX_IMAGINARY = 1.25;
    protected static int INITIAL_MAX_ITERATIONS = 50;
    protected static final double DEFAULT_RADIUS_SQUARED = 4.0;
    private MandelbrotCalculator mandelCalc = new MandelbrotCalculator();
    private JTextField panAmt = new JTextField(3);
    private JLabel panLabel = new JLabel();
    private JLabel panLabel2 = new JLabel();
    private static JPanel iterPanel = new JPanel();
    private JLabel iterLabel = new JLabel();
    private JTextField iterAmt = new JTextField(3);
    private  JButton undo;
    private  JButton redo;
    private static JPanel buttons;
    private static JPanel panPanel = new JPanel();
    private double panNum = 0.1;
    private static int iterNum = 50;
    private static GridBagConstraints c = new GridBagConstraints();
    private int[][] mandelbrotData;
    private boolean zoomFlag=false;
    private int zoomStartX = 0;
    private int zoomStartY = 0;
    private int zoomEndX = 0;
    private int zoomEndY = 0;
    private double minRealPan = INITIAL_MIN_REAL;
    private double maxRealPan = INITIAL_MAX_REAL;
    private double minImaginaryPan = INITIAL_MIN_IMAGINARY;
    private double maxImaginaryPan = INITIAL_MAX_IMAGINARY;
    private JButton zoomConfirm;
    private static Map<String, int[][]> mandelbrots = new HashMap<String, int[][]>();
    private double minRealScaled, maxRealScaled, minImaginaryScaled, maxImaginaryScaled;


    private ZoomMouseListener mouseListener = new ZoomMouseListener();
    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch(keyCode) {
                case KeyEvent.VK_UP:

                    pan(0);
                    break;
                case KeyEvent.VK_DOWN:

                    pan(1);
                    break;
                case KeyEvent.VK_LEFT:

                    pan(2);
                    break;
                case KeyEvent.VK_RIGHT :

                    pan(3);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    MouseListener undoListener = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {

        }

        public void mouseReleased(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                int temp = counter-1;
                String key = "original" + temp;
                for(int i = 0; i < mandelbrots.get("original").length; i++){
                    for(int j = 0; j < mandelbrots.get("original")[i].length; j++){
                        mandelbrotData[i][j] = mandelbrots.get("original")[i][j];
                        repaint();
                    }
                }
                minRealPan = INITIAL_MIN_REAL;
                maxRealPan = INITIAL_MAX_REAL;
                minImaginaryPan = INITIAL_MIN_IMAGINARY;
                maxImaginaryPan = INITIAL_MAX_IMAGINARY;

            }
        }
    };

    MouseListener redoListener = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {

        }

        public void mouseReleased(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                int temp = counter-1;
                String key = "zoom"+temp;
                for(int i = 0; i < mandelbrots.get("original").length; i++){
                    for(int j = 0; j < mandelbrots.get("original")[i].length; j++){

                        mandelbrotData[i][j] = mandelbrots.get(key)[i][j];
                        repaint();
                    }
                }


            }
        }
    };

    //https://stackoverflow.com/questions/21744672/set-color-dynamically-based-on-value-java-swing
    private static double normalize(double min, double max, double value) {
        return (value - min) / (max - min);
    }
    //https://stackoverflow.com/questions/21744672/set-color-dynamically-based-on-value-java-swing
    private static Color colorFor(double value) {
        value = Math.max(0, Math.min(1, value));
        int red = (int)(value * 255);
        return new Color(red,0,0);
    }

    class ZoomMouseListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            requestFocusInWindow();
            zoomStartX = e.getX();
            zoomStartY = e.getY();

        }

        public void mouseReleased(MouseEvent e){
            zoomEndX = e.getX();
            zoomEndY = e.getY();
            if(zoomEndX - zoomStartX > 0 && zoomEndY - zoomStartY > 0){
                if(!zoomFlag){
                    zoom(iterNum);
                }

            }

        }
    }

    MouseListener buttonListener = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {

        }

        public void mouseReleased(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                for(int i = 0; i < mandelbrotData.length; i++){
                    for(int j = 0; j < mandelbrotData[i].length; j++){
                        String key = "zoom"+counter;
                        mandelbrotData[i][j] = mandelbrots.get(key)[i][j];
                    }
                }
                zoomFrame.repaint();
                counter++;
                zoomFlag = false;


                zoomFrame.dispose();
                zoomFrame = null;
                repaint();
            }
        }
    };

    public Mandelbrot(int xRes, int yRes, double minReal, double maxReal, double minImaginary, double maxImaginary, int maxIterations, boolean isZoom){
        this.mandelbrotData = mandelCalc.calcMandelbrotSet(xRes, yRes, minReal, maxReal, minImaginary, maxImaginary, maxIterations, DEFAULT_RADIUS_SQUARED);

        if(isZoom){
            this.zoomFlag = true;
        }
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addKeyListener(keyListener);
        setFocusable(true);

        panAmt.addActionListener(this);
        iterAmt.addActionListener(this);
        panLabel.setText("Use arrow keys to pan ");
        panLabel2.setText("set distance here (default = 0.1): ");
        iterLabel.setText("Set max iterations amount here (default = 50): ");
        panPanel.setLayout(new GridLayout(1,3));
        panPanel.add(panLabel);
        panPanel.add(panLabel2);
        panPanel.add(panAmt);
        iterPanel.setLayout(new GridLayout(1,2));
        iterPanel.add(iterLabel);
        iterPanel.add(iterAmt);
        buttons = new JPanel(new GridLayout(2,1));
        undo = new JButton();
        undo.setText("Undo");
        redo = new JButton();
        redo.setText("Redo");
        undo.addMouseListener(undoListener);
        redo.addMouseListener(redoListener);
        buttons.add(undo);
        buttons.add(redo);
        repaint();

    }

    public void actionPerformed(ActionEvent evt) {
        String pan = panAmt.getText();
        if(pan.length() >= 1){
            panNum = Double.parseDouble(pan);
        }
        String iterations = iterAmt.getText();
        if(iterations.length() >= 1){
            iterNum = Integer.parseInt(iterations);
        }


    }




    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < mandelbrotData.length; i++){
            for(int j = 0; j < mandelbrotData[i].length; j++){
//                if(mandelbrotData[i][j] >= iterNum){
//                    g.drawLine(j,i,j,i);
//                }
                  g.setColor(colorFor(normalize(0, (double)(iterNum), mandelbrotData[i][j])));
                  g.drawLine(j,i,j,i);
            }
        }

    }

    public void pan(int dir){

        switch(dir){
            case 0:
                minImaginaryPan += panNum;
                maxImaginaryPan += panNum;
                mandelbrotData =  mandelCalc.calcMandelbrotSet(600, 600, minRealPan, maxRealPan, minImaginaryPan, maxImaginaryPan, iterNum, DEFAULT_RADIUS_SQUARED);
                repaint();
                break;

            case 1:
                minImaginaryPan -= panNum;
                maxImaginaryPan -= panNum;
                mandelbrotData =  mandelCalc.calcMandelbrotSet(600, 600, minRealPan, maxRealPan, minImaginaryPan, maxImaginaryPan, iterNum, DEFAULT_RADIUS_SQUARED);
                repaint();
                break;

            case 2:
                minRealPan += panNum;
                maxRealPan += panNum;
                mandelbrotData =  mandelCalc.calcMandelbrotSet(600, 600, minRealPan, maxRealPan, minImaginaryPan, maxImaginaryPan, iterNum, DEFAULT_RADIUS_SQUARED);
                repaint();
                break;

            case 3:
                minRealPan -= panNum;
                maxRealPan -= panNum;
                mandelbrotData =  mandelCalc.calcMandelbrotSet(600, 600, minRealPan, maxRealPan, minImaginaryPan, maxImaginaryPan, iterNum, DEFAULT_RADIUS_SQUARED);
                repaint();
                break;

        }
    }

    public void zoom(int maxIterations){
        zoomFrame = new JFrame("Mandelbrot Zoom Estimate");
        minRealScaled = minRealPan + ((zoomStartX/600.0)*(maxRealPan-minRealPan));
        //double minRealScaled = INITIAL_MIN_REAL + ((zoomStartX/600.0)*(INITIAL_MAX_REAL-INITIAL_MIN_REAL));

        maxRealScaled = minRealPan + ((zoomEndX/600.0)*(maxRealPan-minRealPan));


        minImaginaryScaled = minImaginaryPan + ((zoomStartY/600.0)*(maxImaginaryPan-minImaginaryPan));


        maxImaginaryScaled = minImaginaryPan + ((zoomEndY/600.0)*(maxImaginaryPan-minImaginaryPan));
        minRealPan = minRealScaled;
        maxRealPan = maxRealScaled;
        minImaginaryPan = minImaginaryScaled;
        maxImaginaryPan = maxImaginaryScaled;
        zoomFrame.setSize(600, 600);
        zoomFrame.setLayout(new BorderLayout());
        zoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        zoomFrame.setBackground(Color.white);
        Mandelbrot zoomPanel = new Mandelbrot(600, 600, minRealScaled, maxRealScaled, minImaginaryScaled, maxImaginaryScaled, maxIterations, true);
        zoomPanel.repaint();
        for(int i = 0; i < zoomPanel.mandelbrotData.length; i++){
            for(int j = 0; j < zoomPanel.mandelbrotData[i].length; j++){
                zoomCopy[i][j] = zoomPanel.mandelbrotData[i][j];
            }
        }
        String key = "zoom"+counter;

        mandelbrots.put(key, zoomPanel.mandelbrotData);
        mandelbrots.put("zoom", zoomCopy);
        zoomConfirm = new JButton("Take me in");
        zoomConfirm.setVerticalTextPosition(AbstractButton.CENTER);
        zoomConfirm.setHorizontalTextPosition(AbstractButton.CENTER);
        zoomConfirm.addMouseListener(buttonListener);

        zoomFrame.add(zoomPanel, BorderLayout.CENTER);
        zoomPanel.repaint();
        zoomFrame.add(zoomConfirm, BorderLayout.PAGE_END);
        zoomFrame.setVisible(true);

    }

    public static void main(String args[]){

        JFrame frame = new JFrame("Mandelbrot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);

        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        Mandelbrot panel = new Mandelbrot(600, 600, INITIAL_MIN_REAL, INITIAL_MAX_REAL, INITIAL_MIN_IMAGINARY, INITIAL_MAX_IMAGINARY, iterNum, false);
        String key = "original"+counter;

        mandelbrots.put(key, panel.mandelbrotData);
        for(int i = 0; i < panel.mandelbrotData.length; i++){
            for(int j = 0; j < panel.mandelbrotData[i].length; j++){
                originalCopy[i][j] = panel.mandelbrotData[i][j];
            }
        }
        mandelbrots.put("original", originalCopy);



        frame.add(panPanel, BorderLayout.PAGE_START);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(iterPanel, BorderLayout.PAGE_END);
        frame.add(buttons, BorderLayout.EAST);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }




}
