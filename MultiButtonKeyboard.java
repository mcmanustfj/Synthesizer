import javax.swing.*;
import javax.swing.GroupLayout.*;
import java.awt.event.*;
import java.awt.*;
import javax.sound.midi.*;
public class MultiButtonKeyboard {
    Synthesizer synth;
    MidiChannel[] channels;
    Instrument[] instr;
    JFrame frame;
    JButton[] buttons;
    protected JTextField textField1 = new JTextField("60 (Middle C)", 10);
    protected JTextField textField2 = new JTextField("1", 10);
    protected JComboBox instrBox = new JComboBox();

    int numKeys;
    int beginningNoteNum = 60; 
    int volume = 400;
    public static void main(String[] args) throws MidiUnavailableException {
        MultiButtonKeyboard m = new MultiButtonKeyboard(13);
    }

    public MultiButtonKeyboard(int numKeys)
    {
        this.numKeys = numKeys;
        initMidi();
        initGui();
    }

    public void initMidi() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            instr = synth.getDefaultSoundbank().getInstruments();
            synth.loadInstrument(instr[1]);
        } catch (MidiUnavailableException ex) {

        }
    }

    public void initGui() {
        frame = new JFrame("MIDIBUTTON");

        makeButtons();

        JPanel notePanel = new JPanel();
        notePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        for(JButton b : buttons) {
            notePanel.add(b);
        }

        JPanel setupPanel = new JPanel();

        JLabel jLabel1 = new JLabel();
        jLabel1.setText("Beginning Note Number: ");
        textField1.addFocusListener( new FocusListener() {
                @Override
                public void focusLost(FocusEvent e) {
                    String text = textField1.getText();
                    setBeginningNoteNum(Integer.parseInt(textField1.getText()));
                }

                @Override
                public void focusGained(FocusEvent e) {
                }
            });

        //setupPanel.add(jLabel1);
        //setupPanel.add(textField1);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("Instrument Number: ");
        textField2.addFocusListener( new FocusListener() 
            {
                @Override
                public void focusLost(FocusEvent e) {
                    String text = textField2.getText();
                    setInstrument(Integer.parseInt(textField2.getText()));
                }

                @Override
                public void focusGained(FocusEvent e) {
                }
            });

        //setupPanel.add(jLabel2);
        //setupPanel.add(textField2);

        JButton keyButton = new JButton("Keyboard Control");
        //setupPanel.add(keyButton);
        keyButton.setFocusable(true);

        keyButton.addKeyListener(new KeyListener() {
                @Override 
                public void keyTyped(KeyEvent e){
                }

                public void keyPressed(KeyEvent e)
                {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_Z: playNote(beginningNoteNum + 0);break;
                        case KeyEvent.VK_S: playNote(beginningNoteNum + 1);break;
                        case KeyEvent.VK_X: playNote(beginningNoteNum + 2);break;
                        case KeyEvent.VK_D: playNote(beginningNoteNum + 3);break;
                        case KeyEvent.VK_C: playNote(beginningNoteNum + 4);break;
                        case KeyEvent.VK_V: playNote(beginningNoteNum + 5);break;
                        case KeyEvent.VK_G: playNote(beginningNoteNum + 6);break;
                        case KeyEvent.VK_B: playNote(beginningNoteNum + 7);break;
                        case KeyEvent.VK_H: playNote(beginningNoteNum + 8);break;
                        case KeyEvent.VK_N: playNote(beginningNoteNum + 9);break;
                        case KeyEvent.VK_J: playNote(beginningNoteNum + 10);break;
                        case KeyEvent.VK_M: playNote(beginningNoteNum + 11);break;
                        case KeyEvent.VK_COMMA: playNote(beginningNoteNum + 12);break;
                        case KeyEvent.VK_LEFT: setBeginningNoteNum(beginningNoteNum - 12);
                        textField1.setText(Integer.toString(beginningNoteNum));
                        break;
                        case KeyEvent.VK_RIGHT: setBeginningNoteNum(beginningNoteNum + 12);
                        textField1.setText(Integer.toString(beginningNoteNum));
                        break;
                        case KeyEvent.VK_UP: volume += 40;break;
                        case KeyEvent.VK_DOWN: volume -= 40;break;                        
                    }
                }

                public void keyReleased(KeyEvent e) 
                {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_Z: noteOff(beginningNoteNum + 0);break;
                        case KeyEvent.VK_S: noteOff(beginningNoteNum + 1);break;
                        case KeyEvent.VK_X: noteOff(beginningNoteNum + 2);break;
                        case KeyEvent.VK_D: noteOff(beginningNoteNum + 3);break;
                        case KeyEvent.VK_C: noteOff(beginningNoteNum + 4);break;
                        case KeyEvent.VK_V: noteOff(beginningNoteNum + 5);break;
                        case KeyEvent.VK_G: noteOff(beginningNoteNum + 6);break;
                        case KeyEvent.VK_B: noteOff(beginningNoteNum + 7);break;
                        case KeyEvent.VK_H: noteOff(beginningNoteNum + 8);break;
                        case KeyEvent.VK_N: noteOff(beginningNoteNum + 9);break;
                        case KeyEvent.VK_J: noteOff(beginningNoteNum + 10);break;
                        case KeyEvent.VK_M: noteOff(beginningNoteNum + 11);break;
                        case KeyEvent.VK_COMMA: noteOff(beginningNoteNum + 12);break;
                    }
                }
            });

        JButton upOctave = new JButton("Up Octave");
        upOctave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setBeginningNoteNum(beginningNoteNum + 12);
                    textField1.setText(Integer.toString(beginningNoteNum));
                }
            });
        upOctave.setMnemonic(KeyEvent.VK_RIGHT);

        JButton downOctave = new JButton("Down Octave");
        downOctave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setBeginningNoteNum(beginningNoteNum - 12);
                    textField1.setText(Integer.toString(beginningNoteNum));
                }
            });
        downOctave.setMnemonic(KeyEvent.VK_LEFT);

        GroupLayout layout = new GroupLayout(setupPanel);
        setupPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        makeComboBox();

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(textField1) 
                    .addComponent(instrBox))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(upOctave)
                    .addComponent(downOctave)))
            .addComponent(keyButton));

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(textField1)
                .addComponent(upOctave))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2)
                .addComponent(instrBox)
                .addComponent(downOctave))
            .addComponent(keyButton));

        layout.linkSize(upOctave, downOctave);

        frame.getContentPane().add(notePanel, BorderLayout.NORTH);
        frame.getContentPane().add(setupPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void playNote(int note)  {
        try
        {
            channels[0].noteOn(note, volume);

        }
        catch(Exception e)
        {
            System.out.println("PlayNote " + (note));
        }
    }

    public void noteOff(int note)
    {
        try 
        {
            channels[0].noteOff(note);
        }
        catch(Exception e) {
            System.out.println("NoteOff " + note);
        }
    }

    public void test()
    {
        System.out.println("test");
    }

    public void setBeginningNoteNum(int n)
    {
        this.beginningNoteNum = n;
        makeButtons();
    }

    public void setInstrument(int n)
    {
        for(MidiChannel ch : channels)
        {
            ch.programChange(instr[n].getPatch().getProgram());
        }
    }

    public void makeButtons()
    {
        buttons = new JButton[numKeys];
        for(int i = 0; i < numKeys; i++) {
            final int buttonIndex = i;
            buttons[i] = new JButton("Note " + (buttonIndex + 1));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try 
                        {
                            playNote(beginningNoteNum + buttonIndex);
                            Thread.sleep(1000);
                            noteOff(beginningNoteNum + buttonIndex);
                        }
                        catch (Exception x)
                        {
                            System.out.println("PlayNote " + (beginningNoteNum+buttonIndex));
                        }

                    }
                });
        }
    }

    public void makeComboBox()
    {
        for(Instrument i : instr){
            instrBox.addItem(i);
        }
        instrBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox cb = (JComboBox)e.getSource();
                    setInstrument(cb.getSelectedIndex());
                }

            });
        }
    }
