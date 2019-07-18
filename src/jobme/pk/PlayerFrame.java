/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobme.pk;
import jaco.mp3.player.MP3Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.Timer;
import javax.swing.UIManager;
import org.tritonus.share.sampled.file.TAudioFileFormat;



/**
 *
 * @author dell
 */
public class PlayerFrame extends javax.swing.JFrame {

    /**
     * Creates new form PlayerFrame
     */
    
    static MP3Player player;
    
    static File songFile;
    
    static String currentDirectory = "E:\\Darpan\\farewell tracks";
    
    static String currentPath, imagePath;
    
    static String appName = "BEAT THE BOX!!!";
    
    static boolean repeat = false;
    
    static boolean windowCollapsed = false;
    
    static int xMouse,yMouse;
    
    static boolean fvol = false , nvol = false;
    
    static boolean on = false;
    
    static int count = 0;
    
    static int n_id = 0;
    
    static int totalTime = 0;
    
    static Timer timer ;

    static void playFromMost(String addr) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        songFile =  new File(addr); // path to be copied
        
        if(on == false)
        {
            player.play();
            System.out.println("on=" + on);
        }
        else
        {
            timer.cancel();
            System.out.println("on=" + on);
        }
        
        player.addToPlayList(songFile);
        player.skipForward();
            
            String image =  currentPath+imagePath+"\\play_enabled.png";
            playBtn.setIcon(new ImageIcon(image));
        
            image =  currentPath+imagePath+"\\pause.png";
            pauseBtn.setIcon(new ImageIcon(image));
        
            image =  currentPath+imagePath+"\\stop.png";
            stopBtn.setIcon(new ImageIcon(image));
            
            //Add code for the Progress Bar...
            count=0;
            try {
                getDurationWithMp3Spi(songFile);
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            on = true;
            timer = new Timer();
            progressTime();
            
            currentDirectory = songFile.getAbsolutePath();
            songNameDisplay.setText("Playing now ...|"+songFile.getName());
       
        String songName = songFile.getName();
        String songPath = songFile.getAbsolutePath();
        System.out.println(songName);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","12345678");
            //String sql = "SELECT S_NAME FROM MOST_PLAYED WHERE EXISTS(SELECT S_NAME FROM MOST_PLAYED WHERE MOST_PLAYED.S_NAME = songName)";
            String sql="SELECT * FROM MOST_PLAYED WHERE S_NAME = (?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, songName);
            ResultSet a = st.executeQuery();
            System.out.println("a = " + a);
            if(a.next()){//if track is already present in the DB, then increment.
                System.out.println(1);
                sql = "UPDATE MOST_PLAYED SET COUNT=COUNT+1 WHERE S_NAME = (?)";
                st = con.prepareStatement(sql);
                st.setString(1, songName);
                boolean execute = st.execute();
            }
            else{//if track is not present in the DB, then add it and initialise count to 1.
                System.out.println(0);
                st = con.prepareStatement("insert into MOST_PLAYED (S_NAME,S_ADD,COUNT) values (?,?,?)");
                st.setString(1, songName);
                st.setString(2, songPath);
                st.setInt(3, 1);
                boolean execute = st.execute();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Connection Con=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    
    public PlayerFrame() throws UnsupportedAudioFileException, IOException, ClassNotFoundException, SQLException {
        initComponents();
        
        appTitle.setText(appName);
        
        songFile =  new File("E:\\Darpan\\farewell tracks\\punjabi\\3 pegg.mp3"); // path to be copied
        
        String fileName = songFile.getName();
        
        songNameDisplay.setText(fileName);
        
        player = mp3Player();
        
        player.addToPlayList(songFile);
        
        currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        
        //System.out.println(currentPath);
        
        imagePath = "\\images"; 
        
        
        getDurationWithMp3Spi(songFile);
        
        pBar.setMinimum(0);
        pBar.setMaximum(totalTime);
        pBar.setStringPainted(true);
        UIManager.put("ProgressBar.background", Color.BLACK);
        UIManager.put("ProgressBar.foreground", Color.RED);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        songNameDisplay = new javax.swing.JLabel();
        appTitle = new javax.swing.JLabel();
        quitBtn = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        repeatBtn = new javax.swing.JLabel();
        pauseBtn = new javax.swing.JLabel();
        playBtn = new javax.swing.JLabel();
        stopBtn = new javax.swing.JLabel();
        openBtn = new javax.swing.JLabel();
        volumeDownBtn = new javax.swing.JLabel();
        volumeupBtn = new javax.swing.JLabel();
        volumeFullBtn = new javax.swing.JLabel();
        muteBtn = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pBar = new javax.swing.JProgressBar();
        startTime = new javax.swing.JLabel();
        endTime = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(7, 63, 88));

        jPanel2.setBackground(new java.awt.Color(36, 93, 116));

        songNameDisplay.setBackground(new java.awt.Color(7, 63, 88));
        songNameDisplay.setFont(new java.awt.Font("Segoe Script", 3, 18)); // NOI18N
        songNameDisplay.setForeground(new java.awt.Color(34, 202, 237));
        songNameDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        songNameDisplay.setText("PLAYING");
        songNameDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(34, 202, 234)));

        appTitle.setBackground(new java.awt.Color(36, 93, 116));
        appTitle.setFont(new java.awt.Font("Segoe Script", 1, 36)); // NOI18N
        appTitle.setForeground(new java.awt.Color(34, 202, 237));
        appTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appTitle.setText("BEAT THE BOX");
        appTitle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                appTitleMouseDragged(evt);
            }
        });
        appTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                appTitleMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                appTitleMousePressed(evt);
            }
        });

        quitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/quit.png"))); // NOI18N
        quitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitBtnMouseClicked(evt);
            }
        });

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(songNameDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(appTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minimize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(appTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(minimize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(songNameDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(7, 63, 88));

        repeatBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/repeat.png"))); // NOI18N
        repeatBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repeatBtnMouseClicked(evt);
            }
        });

        pauseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/pause.png"))); // NOI18N
        pauseBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pauseBtnMouseClicked(evt);
            }
        });

        playBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/play.png"))); // NOI18N
        playBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playBtnMouseClicked(evt);
            }
        });

        stopBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/stop.png"))); // NOI18N
        stopBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopBtnMouseClicked(evt);
            }
        });

        openBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/open.png"))); // NOI18N
        openBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openBtnMouseClicked(evt);
            }
        });

        volumeDownBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/volume_down.png"))); // NOI18N
        volumeDownBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volumeDownBtnMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                volumeDownBtnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                volumeDownBtnMouseReleased(evt);
            }
        });

        volumeupBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/volume_up.png"))); // NOI18N
        volumeupBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volumeupBtnMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                volumeupBtnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                volumeupBtnMouseReleased(evt);
            }
        });

        volumeFullBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/volume_full.png"))); // NOI18N
        volumeFullBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volumeFullBtnMouseClicked(evt);
            }
        });

        muteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jobme/pk/images/mute.png"))); // NOI18N
        muteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                muteBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(repeatBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pauseBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volumeDownBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volumeupBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volumeFullBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(muteBtn))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playBtn)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pauseBtn)
                            .addComponent(repeatBtn)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openBtn)
                            .addComponent(stopBtn)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(volumeupBtn)
                            .addComponent(volumeDownBtn)
                            .addComponent(volumeFullBtn)
                            .addComponent(muteBtn))))
                .addGap(254, 254, 254))
        );

        jPanel4.setBackground(new java.awt.Color(7, 63, 88));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(34, 202, 237)));

        pBar.setForeground(new java.awt.Color(34, 202, 237));
        pBar.setString("");

        startTime.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        startTime.setForeground(new java.awt.Color(34, 202, 237));
        startTime.setText("0:00");

        endTime.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        endTime.setForeground(new java.awt.Color(34, 202, 237));
        endTime.setText("4:00");

        jButton2.setBackground(new java.awt.Color(7, 63, 88));
        jButton2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(34, 202, 237));
        jButton2.setText("Add to Favourites");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addComponent(startTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pBar, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endTime)
                .addGap(168, 168, 168))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(startTime))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(endTime, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jButton3.setBackground(new java.awt.Color(7, 63, 88));
        jButton3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(34, 202, 237));
        jButton3.setText("Favourites");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(7, 63, 88));
        jButton4.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(34, 202, 237));
        jButton4.setText("Most Played");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(7, 63, 88));
        jButton5.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(34, 202, 237));
        jButton5.setText("List of Songs");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void playFromFav(String addr){
        songFile =  new File(addr); // path to be copied
        
        if(on == false)
        {
            player.play();
            System.out.println("on=" + on);
        }
        else
        {
            timer.cancel();
            System.out.println("on=" + on);
        }
        
        player.addToPlayList(songFile);
        player.skipForward();
            
            String image =  currentPath+imagePath+"\\play_enabled.png";
            playBtn.setIcon(new ImageIcon(image));
        
            image =  currentPath+imagePath+"\\pause.png";
            pauseBtn.setIcon(new ImageIcon(image));
        
            image =  currentPath+imagePath+"\\stop.png";
            stopBtn.setIcon(new ImageIcon(image));
            
            //Add code for the Progress Bar...
            count=0;
            try {
                getDurationWithMp3Spi(songFile);
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            on = true;
            timer = new Timer();
            progressTime();
            
            currentDirectory = songFile.getAbsolutePath();
            songNameDisplay.setText("Playing now ...|"+songFile.getName());
       /*
        player = mp3Player();
        
        player.addToPlayList(songFile);
        player.skipForward();
        
        currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        player.play();
       
        String image =  currentPath+imagePath+"\\play_enabled.png";
        playBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\pause.png";
        pauseBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\stop.png";
        stopBtn.setIcon(new ImageIcon(image));
        
        on = true;
        timer = new Timer();
        progressTime();
        */
        String songName = songFile.getName();
        String songPath = songFile.getAbsolutePath();
        System.out.println(songName);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","12345678");
            //String sql = "SELECT S_NAME FROM MOST_PLAYED WHERE EXISTS(SELECT S_NAME FROM MOST_PLAYED WHERE MOST_PLAYED.S_NAME = songName)";
            String sql="SELECT * FROM MOST_PLAYED WHERE S_NAME = (?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, songName);
            ResultSet a = st.executeQuery();
            System.out.println("a = " + a);
            if(a.next()){//if track is already present in the DB, then increment.
                System.out.println(1);
                sql = "UPDATE MOST_PLAYED SET COUNT=COUNT+1 WHERE S_NAME = (?)";
                st = con.prepareStatement(sql);
                st.setString(1, songName);
                boolean execute = st.execute();
            }
            else{//if track is not present in the DB, then add it and initialise count to 1.
                System.out.println(0);
                st = con.prepareStatement("insert into MOST_PLAYED (S_NAME,S_ADD,COUNT) values (?,?,?)");
                st.setString(1, songName);
                st.setString(2, songPath);
                st.setInt(3, 1);
                boolean execute = st.execute();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    private void playBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playBtnMouseClicked
        // TODO add your handling code here:
        //count = 0;
        player.play();
        
        String image =  currentPath+imagePath+"\\play_enabled.png";
        playBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\pause.png";
        pauseBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\stop.png";
        stopBtn.setIcon(new ImageIcon(image));
        
        on = true;
        timer = new Timer();
        progressTime();
        
        String songName = songFile.getName();
        String songPath = songFile.getAbsolutePath();
        System.out.println(songName);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","12345678");
            //String sql = "SELECT S_NAME FROM MOST_PLAYED WHERE EXISTS(SELECT S_NAME FROM MOST_PLAYED WHERE MOST_PLAYED.S_NAME = songName)";
            String sql="SELECT * FROM MOST_PLAYED WHERE S_NAME = (?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, songName);
            ResultSet a = st.executeQuery();
            System.out.println("a = " + a);
            if(a.next()){//if track is already present in the DB, then increment.
                System.out.println(1);
                sql = "UPDATE MOST_PLAYED SET COUNT=COUNT+1 WHERE S_NAME = (?)";
                st = con.prepareStatement(sql);
                st.setString(1, songName);
                boolean execute = st.execute();
            }
            else{//if track is not present in the DB, then add it and initialise count to 1.
                System.out.println(0);
                st = con.prepareStatement("insert into MOST_PLAYED (S_NAME,S_ADD,COUNT) values (?,?,?)");
                st.setString(1, songName);
                st.setString(2, songPath);
                st.setInt(3, 1);
                boolean execute = st.execute();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }//GEN-LAST:event_playBtnMouseClicked

    private void stopBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopBtnMouseClicked
        // TODO add your handling code here:
        player.stop();
        
        String image =  currentPath+imagePath+"\\play.png";
        playBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\pause.png";
        pauseBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\stop_enabled.png";
        stopBtn.setIcon(new ImageIcon(image));
        
        on = false;
        count = 0;
        timer.cancel();
        
    }//GEN-LAST:event_stopBtnMouseClicked

    private void pauseBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseBtnMouseClicked
        // TODO add your handling code here:
        player.pause();
        
        String image =  currentPath+imagePath+"\\play.png";
        playBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\pause_enabled.png";
        pauseBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\stop.png";
        stopBtn.setIcon(new ImageIcon(image));
        
        on = false;
        timer.cancel();
    }//GEN-LAST:event_pauseBtnMouseClicked

    private void repeatBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repeatBtnMouseClicked
        // TODO add your handling code here:
        if(repeat == false){
            repeat = true;
            player.setRepeat(repeat);
            
            String image =  currentPath+imagePath+"\\repeat_enabled.png";
            
            repeatBtn.setIcon(new ImageIcon(image));
        }
        else if(repeat == true)
        {
            repeat = false;
            player.setRepeat(repeat);
            
            String image =  currentPath+imagePath+"\\repeat.png";
            
            repeatBtn.setIcon(new ImageIcon(image));
        }
    }//GEN-LAST:event_repeatBtnMouseClicked

    private void appTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appTitleMousePressed
        // TODO add your handling code here:
        
        xMouse  = evt.getX();
        yMouse  = evt.getY();
    }//GEN-LAST:event_appTitleMousePressed

    private void appTitleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appTitleMouseDragged
        // TODO add your handling code here:
        
        int x  = evt.getXOnScreen();
        int y  = evt.getYOnScreen();
        
        this.setLocation(x-xMouse, y-yMouse);
    }//GEN-LAST:event_appTitleMouseDragged

    private void quitBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitBtnMouseClicked
        // TODO add your handling code here:
        if(on)
        timer.cancel();
        this.dispose();
    }//GEN-LAST:event_quitBtnMouseClicked

    private void openBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openBtnMouseClicked
        // TODO add your handling code here:
        
        JFileChooser openFileChooser = new JFileChooser(currentDirectory);
        openFileChooser.setFileFilter(new FileTypeFilter(".mp3","open mp3 files only"));
        
        int result = openFileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION)
        {
            songFile = openFileChooser.getSelectedFile();
            String songName = songFile.getName();
            String songPath = songFile.getAbsolutePath();
            if(on == false)
            {
                player.play();
            }
            else
            {
                timer.cancel();
            }
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","12345678");
                    //String sql = "SELECT S_NAME FROM MOST_PLAYED WHERE EXISTS(SELECT S_NAME FROM MOST_PLAYED WHERE MOST_PLAYED.S_NAME = songName)";
                String sql="SELECT * FROM MOST_PLAYED WHERE S_NAME = (?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, songName);
                ResultSet a = st.executeQuery();
                System.out.println("a = " + a);
                if(a.next()){//if track is already present in the DB, then increment.
                    System.out.println(1);
                    sql = "UPDATE MOST_PLAYED SET COUNT=COUNT+1 WHERE S_NAME = (?)";
                    st = con.prepareStatement(sql);
                    st.setString(1, songName);
                    boolean execute = st.execute();
                    }
                else{//if track is not present in the DB, then add it and initialise count to 1.
                    System.out.println(0);
                    st = con.prepareStatement("insert into MOST_PLAYED (S_NAME,S_ADD,COUNT) values (?,?,?)");
                    st.setString(1, songName);
                    st.setString(2, songPath);
                    st.setInt(3, 1);
                    boolean execute = st.execute();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            player.addToPlayList(songFile);
            player.skipForward();
            
            String image =  currentPath+imagePath+"\\play_enabled.png";
            playBtn.setIcon(new ImageIcon(image));
        
            image =  currentPath+imagePath+"\\pause.png";
            pauseBtn.setIcon(new ImageIcon(image));
        
            image =  currentPath+imagePath+"\\stop.png";
            stopBtn.setIcon(new ImageIcon(image));
            
            //Add code for the Progress Bar...
            count=0;
            try {
                getDurationWithMp3Spi(songFile);
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            on = true;
            timer = new Timer();
            progressTime();
            
            currentDirectory = songFile.getAbsolutePath();
            songNameDisplay.setText("Playing now ...|"+songFile.getName());
        }
    }//GEN-LAST:event_openBtnMouseClicked

    private void appTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appTitleMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2)
        {
            if(windowCollapsed == false)
            {
                windowCollapsed = true;
                this.setSize(new Dimension(this.getSize().width,50));
                appTitle.setFont(new Font("Nirmala UI",0,12));
                appTitle.setText("Playing now ...|"+songFile.getName());
            }
            else if(windowCollapsed == true){
                windowCollapsed = false;
                this.setSize(new Dimension(this.getSize().width,320));
                appTitle.setFont(new Font("Nirmala UI",0,18));
                appTitle.setText(appName);
            }
        }
    }//GEN-LAST:event_appTitleMouseClicked

    private void volumeDownBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeDownBtnMouseClicked
        // TODO add your handling code here:
        volumeDownControl(0.1);
        if(fvol==true)
        {
           String image =  currentPath+imagePath+"\\volume_full.png";
            volumeFullBtn.setIcon(new ImageIcon(image));
        }
    }//GEN-LAST:event_volumeDownBtnMouseClicked

    private void volumeupBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeupBtnMouseClicked
        // TODO add your handling code here:
        volumeUpControl(0.1);
        if(nvol==true)
        {
            String image =  currentPath+imagePath+"\\mute.png";
            muteBtn.setIcon(new ImageIcon(image));
        }
    }//GEN-LAST:event_volumeupBtnMouseClicked

    private void volumeFullBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeFullBtnMouseClicked
        // TODO add your handling code here:
        volumeControl(1.0);
        
        fvol = true;
        nvol = false;
        
        String image =  currentPath+imagePath+"\\mute.png";
        muteBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\volume_full_enabled.png";
        volumeFullBtn.setIcon(new ImageIcon(image));
    }//GEN-LAST:event_volumeFullBtnMouseClicked

    private void muteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_muteBtnMouseClicked
        // TODO add your handling code here:
        volumeControl(0.0);
        
        fvol = false;
        nvol = true;
        
        String image =  currentPath+imagePath+"\\mute_enabled.png";
        muteBtn.setIcon(new ImageIcon(image));
        
        image =  currentPath+imagePath+"\\volume_full.png";
        volumeFullBtn.setIcon(new ImageIcon(image));
    }//GEN-LAST:event_muteBtnMouseClicked

    private void volumeDownBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeDownBtnMousePressed
        // TODO add your handling code here:
        String image =  currentPath+imagePath+"\\volume_down_enabled.png";
        volumeDownBtn.setIcon(new ImageIcon(image));
    }//GEN-LAST:event_volumeDownBtnMousePressed

    private void volumeDownBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeDownBtnMouseReleased
        // TODO add your handling code here:
        String image =  currentPath+imagePath+"\\volume_down.png";
        volumeDownBtn.setIcon(new ImageIcon(image));
    }//GEN-LAST:event_volumeDownBtnMouseReleased

    private void volumeupBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeupBtnMousePressed
        // TODO add your handling code here:
        String image =  currentPath+imagePath+"\\volume_up_enabled.png";
        volumeupBtn.setIcon(new ImageIcon(image));
    }//GEN-LAST:event_volumeupBtnMousePressed

    private void volumeupBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeupBtnMouseReleased
        // TODO add your handling code here:
        String image =  currentPath+imagePath+"\\volume_up.png";
        volumeupBtn.setIcon(new ImageIcon(image));
    }//GEN-LAST:event_volumeupBtnMouseReleased

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here: //Add to Favourites
        String songName = songFile.getName();
        String songPath = songFile.getAbsolutePath();
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","12345678");
            //PreparedStatement st = con.prepareStatement("insert into FAVOURITES(S_NAME,S_ADD) values(?,?)");
            //st.setString(1, songName);
            //st.setString(2, songPath);
            //boolean execute = st.execute();
            String sql = "SELECT * FROM FAVOURITES WHERE S_NAME = (?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, songName);
            ResultSet a = st.executeQuery();
            if(!a.next()){//if track is not present in the DB, then add it and initialise count to 1.
                System.out.println(0);
                st = con.prepareStatement("insert into FAVOURITES(S_NAME,S_ADD) values (?,?)");
                st.setString(1, songName);
                st.setString(2, songPath);
                boolean execute = st.execute();
            }
            else{//if track is already present in the DB, then increment.
                System.out.println(1);
                JOptionPane.showMessageDialog(rootPane, "The song is already added to the playlist!");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here: list of songs
        new disp().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here: (for favourites)
            new fav().setVisible(true);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // TODO add your handling code here:
            new MostPlayed().setVisible(true);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new PlayerFrame().setVisible(true);
            } catch (UnsupportedAudioFileException | IOException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(PlayerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appTitle;
    private static javax.swing.JLabel endTime;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel minimize;
    private static javax.swing.JLabel muteBtn;
    private static javax.swing.JLabel openBtn;
    private static javax.swing.JProgressBar pBar;
    private static javax.swing.JLabel pauseBtn;
    private static javax.swing.JLabel playBtn;
    private javax.swing.JLabel quitBtn;
    private static javax.swing.JLabel repeatBtn;
    private static javax.swing.JLabel songNameDisplay;
    private static javax.swing.JLabel startTime;
    private static javax.swing.JLabel stopBtn;
    private static javax.swing.JLabel volumeDownBtn;
    private static javax.swing.JLabel volumeFullBtn;
    private static javax.swing.JLabel volumeupBtn;
    // End of variables declaration//GEN-END:variables

    private MP3Player mp3Player(){
        MP3Player mp3Player = new MP3Player();
        return mp3Player;
    }
    
    
    private void volumeDownControl(Double valueToPlusMinus)
    {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        
        for(Mixer.Info mixerInfo: mixers)
        {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            
            for(Line.Info lineInfo: lineInfos)
            {
                Line line = null;
                
                boolean opened = true;
                
                try{
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    
                    if(!opened)
                    {
                        line.open();
                        
                    }
                    
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    
                    float currentVolume = volControl.getValue();
                    
                    Double volumeToCut  = valueToPlusMinus;
                    
                    float changedCalc = (float) ((float)currentVolume - (double)volumeToCut);
                    
                    volControl.setValue(changedCalc);
                    
                    //return currentVolume;
                    
                }
                catch(LineUnavailableException | IllegalArgumentException lineException){ 
                }
                finally{
                    if(line != null && !opened){
                        line.close();
                    }
                    
                }
            }
        }
    }
    
    private void volumeUpControl(Double valueToPlusMinus)
    {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        
        for(Mixer.Info mixerInfo: mixers)
        {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            
            for(Line.Info lineInfo: lineInfos)
            {
                Line line = null;
                
                boolean opened = true;
                
                try{
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    
                    if(!opened)
                    {
                        line.open();
                        
                    }
                    
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    
                    float currentVolume = volControl.getValue();
                    
                    Double volumeToCut  = valueToPlusMinus;
                    
                    float changedCalc = (float) ((float)currentVolume + (double)volumeToCut);
                    
                    volControl.setValue(changedCalc);
                    
                }
                catch(LineUnavailableException | IllegalArgumentException lineException){ 
                }
                finally{
                    if(line != null && !opened){
                        line.close();
                    }
                    
                }
            }
        }
    }
    
    
    private void volumeControl(Double valueToPlusMinus)
    {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        
        for(Mixer.Info mixerInfo: mixers)
        {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            
            for(Line.Info lineInfo: lineInfos)
            {
                Line line = null;
                
                boolean opened = true;
                
                try{
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    
                    if(!opened)
                    {
                        line.open();
                        
                    }
                    
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    
                    float currentVolume = volControl.getValue();
                    
                    Double volumeToCut  = valueToPlusMinus;
                    
                    float changedCalc = (float) ((double)volumeToCut);
                    
                    volControl.setValue(changedCalc);
                    
                }
                catch(LineUnavailableException | IllegalArgumentException lineException){ 
                }
                finally{
                    if(line != null && !opened){
                        line.close();
                    }
                    
                }
            }
        }
    }
    
    private static void getDurationWithMp3Spi(File file) throws UnsupportedAudioFileException, IOException {

    AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
    if (fileFormat instanceof TAudioFileFormat) {
        Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
        String key = "duration";
        String key2 = "author";
        String key3 = "album";
        Long microseconds = (Long) properties.get(key);
        String artistName = (String) properties.get(key2);
        String albumName = (String) properties.get(key3);
        int mili = (int) (microseconds / 1000);
        int sec = (mili / 1000) % 60;
        int min = (mili / 1000) / 60;
        System.out.println("time = " + min + ":" + sec);
        System.out.println("author = "+artistName);
        System.out.println("album = "+albumName);
        if(sec<10)
        endTime.setText(Integer.toString(min)+":0"+Integer.toString(sec));
        else
        endTime.setText(Integer.toString(min)+":"+Integer.toString(sec));
        totalTime = (min*60)+sec;
        
    } else {
        throw new UnsupportedAudioFileException();
    }

}
    
    
    public static void progressTime()
    {
        timer.schedule(new TimerTask() 
        {
            @Override
            public void run() 
            {
                // Increase value here...
                if(on)
                count++;
                pBar.setValue(count);
                //System.out.println(count);
                if(count==totalTime)
                {
                    timer.cancel();
                    on = false;
                    count = 0;
                    String image =  currentPath+imagePath+"\\play.png";
                    playBtn.setIcon(new ImageIcon(image));
                }
            }
        }, 0, 1000);
    }
    
}

