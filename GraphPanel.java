package com.Raspi;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Rodrigo
 */
public class GraphPanel extends JPanel implements Scrollable {

    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(48, 230, 212, 252);
    private Color lineColor2 = new Color(255, 0, 0, 252);
    private Color lineColor3 = new Color(0, 255, 0, 252);
    private Color lineColor4 = new Color(200, 150, 100, 252);
    private Color lineColor5 = new Color(214, 0, 133, 252);


    private Color pointColor = new Color(0, 32, 255, 255);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 30;
    private List<List<Double>> tempeList;
    int tempListSize;
    public int yScaleSize = 30;
    int MIN_BAR_WIDTH = 30;
    private boolean serverFlag []=new boolean[5];


    public GraphPanel(List<List<Double>> tempeList,boolean flags []) {
        this.serverFlag=flags;
        this.tempeList = tempeList;
        for (int i = 0; i < tempeList.size(); i++) {
            tempeList.get(i);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(this.serverFlag[i]);
        }
        this.tempListSize = tempeList.get(0).size();
    }

    @Override
    public Dimension getPreferredSize() {
        int width = (50 * MIN_BAR_WIDTH) + 11;
        return new Dimension(width, 256);
    }

    @Override
    public Dimension getMinimumSize() {
        int width = (50) + 11;
        return new Dimension(width, 512);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (tempListSize - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / yScaleSize;
      //  System.out.println(xScale + "\n" + yScale);
      //  System.out.println(getHeight());

        List<Point> graphPoints1;
        List<Point> graphPoints2;

        List<Point> graphPoints3;
        List<Point> graphPoints4;
        List<Point> graphPoints5;


//        for (int i = 0; i < tempListSize; i++) {
//            int x1 = (int) (i * xScale + padding + labelPadding);
//            int y1 = (int) ((55 - tempeList.get(i)) * yScale + padding);
//            graphPoints.add(new Point(x1, y1));
//            System.out.println(graphPoints.get(i));
//        }
        graphPoints1 = findPoints(xScale, yScale, tempListSize, tempeList, 0);

        graphPoints2 = findPoints(xScale, yScale, tempListSize, tempeList, 1);

        graphPoints3 = findPoints(xScale, yScale, tempListSize, tempeList, 2);
        graphPoints4 = findPoints(xScale, yScale, tempListSize, tempeList, 3);
        graphPoints5 = findPoints(xScale, yScale, tempListSize, tempeList, 4);


        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (tempListSize > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = i + 30 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < tempListSize; i++) {
            if (tempListSize > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (tempListSize - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((tempListSize / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        //line1
        if (serverFlag[0] == true) {
            g2.setColor(lineColor);
            g2.setStroke(GRAPH_STROKE);
            drawLineGraph(graphPoints1, g2);
        }
        //line2
        if (serverFlag[1] == true) {
            g2.setColor(lineColor2);
            g2.setStroke(GRAPH_STROKE);
            drawLineGraph(graphPoints2, g2);
        }
        //line3
        if (serverFlag[2] == true) {
            g2.setColor(lineColor3);
            g2.setStroke(GRAPH_STROKE);
            drawLineGraph(graphPoints3, g2);
        }
        //line4
        if (serverFlag[3] == true) {
            g2.setColor(lineColor4);
            g2.setStroke(GRAPH_STROKE);
            drawLineGraph(graphPoints4, g2);
        }
        //line5
        if (serverFlag[4] == true) {
            g2.setColor(lineColor5);
            g2.setStroke(GRAPH_STROKE);
            drawLineGraph(graphPoints5, g2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
//        for (int i = 0; i < graphPoints.size(); i++) {
//            int x = graphPoints.get(i).x - pointWidth / 2;
//            int y = graphPoints.get(i).y - pointWidth / 2;
//            int ovalW = pointWidth;
//            int ovalH = pointWidth;
//            g2.fillOval(x, y, ovalW, ovalH);
//        }
        if (serverFlag[0] == true) {

            drawDots(graphPoints1, g2);
        }
        if (serverFlag[1] == true) {

            drawDots(graphPoints2, g2);
        }
        if (serverFlag[2] == true) {

            drawDots(graphPoints3, g2);
        }
        if (serverFlag[3] == true) {

            drawDots(graphPoints4, g2);
        }
        if (serverFlag[4] == true) {

            drawDots(graphPoints5, g2);
        }

    }

    public List<Point> findPoints(double xScale, double yScale, int tempListSize, List<List<Double>> tempeList, int arrayIndex) {
        List<Point> graphPoints = new ArrayList<>();

        for (int i = 0; i < tempListSize; i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((60 - tempeList.get(arrayIndex).get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
          //  System.out.println(graphPoints.get(i));
        }
        return graphPoints;
    }

    public void drawLineGraph(List<Point> graphPoints, Graphics2D g2) {
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public void drawDots(List<Point> graphPoints, Graphics2D g2) {
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }

    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(width, heigth);
//    }
//
//    private double getMinScore() {
//        double minScore = Double.MAX_VALUE;
//        for (Double score : tempeList) {
//            minScore = Math.min(minScore, score);
//        }
//        return minScore;
//    }
//
//    private double getMaxScore() {
//        double maxScore = Double.MIN_VALUE;
//        for (Double score : tempeList) {
//            maxScore = Math.max(maxScore, score);
//        }
//        return maxScore;
//    }

//    public void settempeList(List<Double> tempeList) {
//        this.tempeList = tempeList;
//        invalidate();
//        this.repaint();
//    }


    public void createAndShowGui() {
        List<Double> tempeList = new ArrayList<>();
        Random random = new Random();
        int maxDataPoints = 40;
        int maxScore = 10;
//        GraphPanel mainPanel = new GraphPanel(temperature);
//        mainPanel.setPreferredSize(new Dimension(800, 600));
//        JFrame frame = new JFrame("DrawGraph");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(mainPanel);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);


    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(1000, 512);
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return getPreferredSize().width
                <= getParent().getSize().width;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return getPreferredSize().height
                <= getParent().getSize().height;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGui();
//            }
//        });
//    }
}
