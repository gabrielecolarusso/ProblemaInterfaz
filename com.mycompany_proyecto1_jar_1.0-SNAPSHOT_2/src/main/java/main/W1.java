package main;

import primitivas.*;
import classes.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class W1 extends javax.swing.JFrame {
    public Semaphore onPlay;
    public Semaphore onPlayClock;
    public List readyList;
    public List allProcessList;
    public UtilityGraph w2;
    private Dispatcher dispatcher;
    private MetricsCollector metrics;
    
    private void loadConfig() {
        String filePath = "configuracion.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            if (line != null) {
                String[] values = line.split(",");
                if (values.length < 3) {
                    System.out.println("Error: El archivo de configuración no tiene el formato correcto.");
                    return;
                }

                int selectedAlgorithm = Integer.parseInt(values[0]);
                int numberOfInstructions = Integer.parseInt(values[1]);
                int quantum = Integer.parseInt(values[2]);

                selectDispatcher.setSelectedIndex(selectedAlgorithm);
                timeSlider.setValue(numberOfInstructions);
                quantumSlider.setValue(quantum);
                this.instructionTime.setText(this.timeSlider.getValue() + " ms");
                this.quantumLabel.setText("Quantum: " + this.quantumSlider.getValue());
                System.out.println("Configuración cargada desde CSV.");
            }
        } catch (IOException e) {
            System.out.println("No se encontró el archivo de configuración. Se usarán valores por defecto.");
        }
    }
    
    public W1(Semaphore onPlay, Semaphore onPlay1, List readyList, List allProcess, MetricsCollector metrics) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.onPlay = onPlay;
        this.onPlayClock = onPlay1;
        this.readyList = readyList;
        this.allProcessList = allProcess;
        this.metrics = metrics;
        w2 = new UtilityGraph("CPU usage");
        
        loadConfig();
        this.updatePCBs();
        this.updateMetrics(metrics);
    }

    public W1() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    public synchronized void createNewProcess(List list, String name, String type, int duration, int id){
        ProcessImage newProcess = new ProcessImage(list, type, id, "ready", name, 1, 0, duration);
        readyList.appendLast(newProcess);
        allProcessList.appendLast(newProcess);
        updatePCBs();
    }

    public void updatePCBs(){
        NodoList pAux = readyList.getHead();
        String display = "";
        while(pAux != null){
            ProcessImage process = (ProcessImage) pAux.getValue();
            display += this.makeString(process);
            pAux = pAux.getpNext();
        }
        this.updateProcess(display);
    }

    private String makeString(ProcessImage currentProcess){
        String display = Dispatcher.makeString(currentProcess);
        return display;
    }

    public void updateCPU(String input){
        this.cpuTextArea.setText(input);
    }

    public synchronized void updateDataset(int instruction, String type){
        w2.updateDataset(instruction, type);
    }
    
    public int getSelectAlgorithm(){
        return this.selectDispatcher.getSelectedIndex();
    }

    public void updateReady(String text){
        this.readyTextArea.setText(text);
    } 

    public void updateBlock(String text){
        this.blockedTextArea.setText(text);
    }

    public void updateExit(String text){
        this.exitTextArea.setText(text);
    }

    public void updateSuspended(String text){
        this.suspendedTextArea.setText(text);
    }
    
    public void updateProcess(String text){
        this.pcbTextArea.setText(text);
    }

    public int getTime(){
        return this.timeSlider.getValue();
    }

    public int getQuantum(){
        return this.quantumSlider.getValue();
    }

    public void setDispatcher(Dispatcher d){
        this.dispatcher = d;
    }

    public void updateCycle(int in){
        this.cycleTextField.setText(in + "");
    }

    public void updateMetrics(MetricsCollector metrics){
        this.throughputLabel.setText(String.format("%.4f p/c", metrics.getThroughput()));
        this.cpuUtilizationLabel.setText(String.format("%.2f%%", metrics.getCpuUtilization()));
        this.avgWaitingTimeLabel.setText(String.format("%.2f c", metrics.getAverageWaitingTime()));
        this.avgResponseTimeLabel.setText(String.format("%.2f c", metrics.getAverageResponseTime()));
        this.fairnessLabel.setText(String.format("%.2f%%", metrics.getFairness()));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cpuPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cpuTextArea = new javax.swing.JTextArea();
        readyPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        readyTextArea = new javax.swing.JTextArea();
        suspendedPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        suspendedTextArea = new javax.swing.JTextArea();
        blockedPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        blockedTextArea = new javax.swing.JTextArea();
        exitPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        exitTextArea = new javax.swing.JTextArea();
        pcbPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pcbTextArea = new javax.swing.JTextArea();
        metricsPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        throughputLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cpuUtilizationLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        avgWaitingTimeLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        avgResponseTimeLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        fairnessLabel = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        timeSlider = new javax.swing.JSlider();
        instructionTime = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        quantumSlider = new javax.swing.JSlider();
        quantumLabel = new javax.swing.JLabel();
        actionsPanel = new javax.swing.JPanel();
        createProcessButton = new javax.swing.JButton();
        showUsageButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        showLogButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        schedulerPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cycleTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        selectDispatcher = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Process Scheduler Simulator");

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cpuPanel.setBackground(new java.awt.Color(255, 255, 255));
        cpuPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CPU", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(51, 51, 51)));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        cpuTextArea.setEditable(false);
        cpuTextArea.setBackground(new java.awt.Color(255, 255, 255));
        cpuTextArea.setColumns(20);
        cpuTextArea.setFont(new java.awt.Font("Monospaced", 0, 11));
        cpuTextArea.setRows(8);
        cpuTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane2.setViewportView(cpuTextArea);

        javax.swing.GroupLayout cpuPanelLayout = new javax.swing.GroupLayout(cpuPanel);
        cpuPanel.setLayout(cpuPanelLayout);
        cpuPanelLayout.setHorizontalGroup(
            cpuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        cpuPanelLayout.setVerticalGroup(
            cpuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );

        jPanel1.add(cpuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 160, 150));

        readyPanel.setBackground(new java.awt.Color(255, 255, 255));
        readyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ready Queue", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(0, 204, 153)));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        readyTextArea.setEditable(false);
        readyTextArea.setBackground(new java.awt.Color(255, 255, 255));
        readyTextArea.setColumns(20);
        readyTextArea.setFont(new java.awt.Font("Dialog", 0, 11));
        readyTextArea.setRows(5);
        readyTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane1.setViewportView(readyTextArea);

        javax.swing.GroupLayout readyPanelLayout = new javax.swing.GroupLayout(readyPanel);
        readyPanel.setLayout(readyPanelLayout);
        readyPanelLayout.setHorizontalGroup(
            readyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        readyPanelLayout.setVerticalGroup(
            readyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );

        jPanel1.add(readyPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 180, 150));

        suspendedPanel.setBackground(new java.awt.Color(255, 255, 255));
        suspendedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Suspended", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(153, 153, 153)));

        jScrollPane6.setBorder(null);
        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        suspendedTextArea.setEditable(false);
        suspendedTextArea.setBackground(new java.awt.Color(255, 255, 255));
        suspendedTextArea.setColumns(20);
        suspendedTextArea.setFont(new java.awt.Font("Dialog", 0, 11));
        suspendedTextArea.setRows(5);
        suspendedTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane6.setViewportView(suspendedTextArea);

        javax.swing.GroupLayout suspendedPanelLayout = new javax.swing.GroupLayout(suspendedPanel);
        suspendedPanel.setLayout(suspendedPanelLayout);
        suspendedPanelLayout.setHorizontalGroup(
            suspendedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        suspendedPanelLayout.setVerticalGroup(
            suspendedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
        );

        jPanel1.add(suspendedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 160, 280));

        blockedPanel.setBackground(new java.awt.Color(255, 255, 255));
        blockedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocked Queue", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(255, 153, 0)));

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        blockedTextArea.setEditable(false);
        blockedTextArea.setBackground(new java.awt.Color(255, 255, 255));
        blockedTextArea.setColumns(20);
        blockedTextArea.setFont(new java.awt.Font("Dialog", 0, 11));
        blockedTextArea.setRows(5);
        blockedTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane3.setViewportView(blockedTextArea);

        javax.swing.GroupLayout blockedPanelLayout = new javax.swing.GroupLayout(blockedPanel);
        blockedPanel.setLayout(blockedPanelLayout);
        blockedPanelLayout.setHorizontalGroup(
            blockedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        blockedPanelLayout.setVerticalGroup(
            blockedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
        );

        jPanel1.add(blockedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 180, 140));

        exitPanel.setBackground(new java.awt.Color(255, 255, 255));
        exitPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Completed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(51, 153, 51)));

        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        exitTextArea.setEditable(false);
        exitTextArea.setBackground(new java.awt.Color(255, 255, 255));
        exitTextArea.setColumns(20);
        exitTextArea.setFont(new java.awt.Font("Dialog", 0, 11));
        exitTextArea.setRows(5);
        exitTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane4.setViewportView(exitTextArea);

        javax.swing.GroupLayout exitPanelLayout = new javax.swing.GroupLayout(exitPanel);
        exitPanel.setLayout(exitPanelLayout);
        exitPanelLayout.setHorizontalGroup(
            exitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        exitPanelLayout.setVerticalGroup(
            exitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );

        jPanel1.add(exitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 180, 120));

        pcbPanel.setBackground(new java.awt.Color(255, 255, 255));
        pcbPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Process Control Blocks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(0, 102, 204)));

        jScrollPane5.setBorder(null);
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pcbTextArea.setEditable(false);
        pcbTextArea.setBackground(new java.awt.Color(255, 255, 255));
        pcbTextArea.setColumns(20);
        pcbTextArea.setFont(new java.awt.Font("Monospaced", 0, 10));
        pcbTextArea.setRows(5);
        pcbTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane5.setViewportView(pcbTextArea);

        javax.swing.GroupLayout pcbPanelLayout = new javax.swing.GroupLayout(pcbPanel);
        pcbPanel.setLayout(pcbPanelLayout);
        pcbPanelLayout.setHorizontalGroup(
            pcbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );
        pcbPanelLayout.setVerticalGroup(
            pcbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
        );

        jPanel1.add(pcbPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 180, 450));

        metricsPanel.setBackground(new java.awt.Color(255, 255, 255));
        metricsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Performance Metrics", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 51, 153)));

        jLabel11.setText("Throughput:");

        throughputLabel.setFont(new java.awt.Font("Dialog", 1, 12));
        throughputLabel.setText("0.0000 p/c");

        jLabel12.setText("CPU Util:");

        cpuUtilizationLabel.setFont(new java.awt.Font("Dialog", 1, 12));
        cpuUtilizationLabel.setText("0.00%");

        jLabel13.setText("Avg Wait:");

        avgWaitingTimeLabel.setFont(new java.awt.Font("Dialog", 1, 12));
        avgWaitingTimeLabel.setText("0.00 c");

        jLabel14.setText("Avg Resp:");

        avgResponseTimeLabel.setFont(new java.awt.Font("Dialog", 1, 12));
        avgResponseTimeLabel.setText("0.00 c");

        jLabel15.setText("Fairness:");

        fairnessLabel.setFont(new java.awt.Font("Dialog", 1, 12));
        fairnessLabel.setText("100.00%");

        javax.swing.GroupLayout metricsPanelLayout = new javax.swing.GroupLayout(metricsPanel);
        metricsPanel.setLayout(metricsPanelLayout);
        metricsPanelLayout.setHorizontalGroup(
            metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(metricsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(20, 20, 20)
                .addGroup(metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(throughputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cpuUtilizationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avgWaitingTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avgResponseTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fairnessLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        metricsPanelLayout.setVerticalGroup(
            metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(metricsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(throughputLabel))
                .addGap(18, 18, 18)
                .addGroup(metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cpuUtilizationLabel))
                .addGap(18, 18, 18)
                .addGroup(metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(avgWaitingTimeLabel))
                .addGap(18, 18, 18)
                .addGroup(metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(avgResponseTimeLabel))
                .addGap(18, 18, 18)
                .addGroup(metricsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(fairnessLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(metricsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 200, 180));

        controlPanel.setBackground(new java.awt.Color(255, 255, 255));
        controlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Simulation Controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 51, 153)));

        jLabel3.setText("Instruction Time");

        timeSlider.setMaximum(5000);
        timeSlider.setMinimum(1);
        timeSlider.setValue(5000);
        timeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                timeSliderStateChanged(evt);
            }
        });

        instructionTime.setFont(new java.awt.Font("Dialog", 1, 12));
        instructionTime.setText("5000 ms");

        jLabel10.setText("Quantum (RR)");

        quantumSlider.setMaximum(10);
        quantumSlider.setMinimum(1);
        quantumSlider.setValue(5);
        quantumSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                quantumSliderStateChanged(evt);
            }
        });

        quantumLabel.setFont(new java.awt.Font("Dialog", 1, 12));
        quantumLabel.setText("Quantum: 5");

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(timeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instructionTime, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(quantumSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(instructionTime)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quantumSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quantumLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(controlPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 200, 250));

        actionsPanel.setBackground(new java.awt.Color(255, 255, 255));
        actionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 51, 153)));

        createProcessButton.setText("Create Process");
        createProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createProcessButtonActionPerformed(evt);
            }
        });

        showUsageButton.setText("Show Usage");
        showUsageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showUsageButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        showLogButton.setText("Show Log");
        showLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLogButtonActionPerformed(evt);
            }
        });

        playButton.setBackground(new java.awt.Color(0, 204, 153));
        playButton.setFont(new java.awt.Font("Dialog", 1, 14));
        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actionsPanelLayout = new javax.swing.GroupLayout(actionsPanel);
        actionsPanel.setLayout(actionsPanelLayout);
        actionsPanelLayout.setHorizontalGroup(
            actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createProcessButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showUsageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showLogButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        actionsPanelLayout.setVerticalGroup(
            actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createProcessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showUsageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showLogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(actionsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 220, 210, 250));

        schedulerPanel.setBackground(new java.awt.Color(255, 255, 255));
        schedulerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Scheduler", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 51, 153)));

        jLabel7.setText("Global Cycle:");

        cycleTextField.setEditable(false);
        cycleTextField.setFont(new java.awt.Font("Dialog", 1, 14));
        cycleTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cycleTextField.setText("0");

        jLabel8.setText("Scheduling Algorithm");

        selectDispatcher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FCFS", "Round Robin", "SPN", "SRT", "HRR", "Feedback" }));

        javax.swing.GroupLayout schedulerPanelLayout = new javax.swing.GroupLayout(schedulerPanel);
        schedulerPanel.setLayout(schedulerPanelLayout);
        schedulerPanelLayout.setHorizontalGroup(
            schedulerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schedulerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(schedulerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectDispatcher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(schedulerPanelLayout.createSequentialGroup()
                        .addGroup(schedulerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(cycleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 64, Short.MAX_VALUE)))
                .addContainerGap())
        );
        schedulerPanelLayout.setVerticalGroup(
            schedulerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schedulerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cycleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectDispatcher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(schedulerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 210, 180));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
        onPlay.release(1);
        onPlayClock.release();
        ProcessImageCSV.saveProcessesToCSV(readyList, "procesos.csv");
        this.createProcessButton.setEnabled(false);
        this.playButton.setEnabled(false);
    }

    private void createProcessButtonActionPerformed(java.awt.event.ActionEvent evt) {
        CreateProcess newProcess = new CreateProcess(this);
        newProcess.setVisible(true);
    }

    private void timeSliderStateChanged(javax.swing.event.ChangeEvent evt) {
        this.instructionTime.setText(this.timeSlider.getValue() + " ms");
    }

    private void quantumSliderStateChanged(javax.swing.event.ChangeEvent evt) {
        this.quantumLabel.setText("Quantum: " + this.quantumSlider.getValue());
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedAlgorithm = selectDispatcher.getSelectedIndex();
        int numberOfInstructions = timeSlider.getValue();
        int quantum = quantumSlider.getValue();

        String filePath = "configuracion.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(selectedAlgorithm + "," + numberOfInstructions + "," + quantum);
            writer.newLine();
            
            JOptionPane.showMessageDialog(this, "Configuración guardada en " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la configuración");
        }
    }

    private void showUsageButtonActionPerformed(java.awt.event.ActionEvent evt) {
        w2.setSize(800, 400);
        w2.setLocationRelativeTo(null);
        w2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        w2.setVisible(true);
    }

    private void showLogButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LogViewer logViewer = new LogViewer();
        logViewer.setVisible(true);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(W1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(W1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(W1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(W1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                W1 w1 = new W1();
                w1.setVisible(true);
            }
        });
    }

    private javax.swing.JPanel actionsPanel;
    private javax.swing.JLabel avgResponseTimeLabel;
    private javax.swing.JLabel avgWaitingTimeLabel;
    private javax.swing.JPanel blockedPanel;
    private javax.swing.JTextArea blockedTextArea;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel cpuPanel;
    private javax.swing.JTextArea cpuTextArea;
    private javax.swing.JLabel cpuUtilizationLabel;
    private javax.swing.JButton createProcessButton;
    private javax.swing.JTextField cycleTextField;
    private javax.swing.JPanel exitPanel;
    private javax.swing.JTextArea exitTextArea;
    private javax.swing.JLabel fairnessLabel;
    private javax.swing.JLabel instructionTime;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel metricsPanel;
    private javax.swing.JPanel pcbPanel;
    private javax.swing.JTextArea pcbTextArea;
    private javax.swing.JButton playButton;
    private javax.swing.JLabel quantumLabel;
    private javax.swing.JSlider quantumSlider;
    private javax.swing.JPanel readyPanel;
    private javax.swing.JTextArea readyTextArea;
    private javax.swing.JButton saveButton;
    private javax.swing.JPanel schedulerPanel;
    private javax.swing.JComboBox<String> selectDispatcher;
    private javax.swing.JButton showLogButton;
    private javax.swing.JButton showUsageButton;
    private javax.swing.JPanel suspendedPanel;
    private javax.swing.JTextArea suspendedTextArea;
    private javax.swing.JLabel throughputLabel;
    private javax.swing.JSlider timeSlider;
}