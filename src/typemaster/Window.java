/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typemaster;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.border.LineBorder;


public class Window extends javax.swing.JFrame {

    /**
     * Creates new form Window
     */
    private String paragraph;
    private Stack<String> paragraphs;
    private int currentParagraph, checkPosition;
    private long startTime, endTime;
    private int count=60;
    private Timer t;
    
    
    
    public Window() {
        initComponents();
        setLocation();
        setSize(1500,850);
        checkPosition = 0;
        jEdittext2.setEnabled(false);
        initializeParagraphs();
        initializeTextAreas();
        setStyle();
    }
    private void setStyle(){
        jParaText1.setBackground(Color.BLACK);
        Style style = jParaText1.addStyle("Bell MT", null);
        StyleConstants.setForeground(style, Color.WHITE.brighter());
        StyledDocument doc = jParaText1.getStyledDocument();
        doc.setCharacterAttributes(0, paragraph.length()-1, style, false);
    }
    private void checkLetter(char letter,int position)
    {
        System.out.println("My Character: "+letter+" Expected Character: "+paragraph.charAt(position)+" P: "+position);
        if(paragraph.charAt(position)==letter){
            correctLetter(position);
            checkPosition++;
        }
        else
            wrongLetter(letter);
    }
    private void resetEverything()
    {
        jEdittext2.setText("");
        checkPosition = -1;
        startTime = System.currentTimeMillis();
        
    }
    private void initializeTextAreas()
    {
        jParaText1.setEditable(false);
        jParaText1.setToolTipText("This is your paragraph. Type it in below box");
        jEdittext2.setToolTipText("Type the above paragraph in this box");
    }
    private void playError(){
        File file = new File("error.wav");
        if(file.exists()) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceLine.open(audioFormat);
                sourceLine.start();
                int nBytesRead = 0;
                byte[] abData = new byte[128000];
                while (nBytesRead != -1) {
                    try {
                         nBytesRead = audioInputStream.read(abData, 0, abData.length);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (nBytesRead >= 0) {
                        sourceLine.write(abData, 0, nBytesRead);
                    }
                }
                sourceLine.drain();
                sourceLine.close();
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("The selected file doesn't exist!");
        }
    }
    private void changeParagraph(){
        if(currentParagraph==5){
            currentParagraph=0;
            
        JOptionPane.showMessageDialog(null,"CONGRATULATIONS YOU HAVE SUCCESSFULLY COMPLETED ALL ROUNDS. THANKS FOR PARTICIPATION");
        System.exit(0);
        }
        else{
            currentParagraph++;
        paragraph = paragraphs.get(currentParagraph);
        jParaText1.setText(paragraphs.get(currentParagraph));
        changeColorOfSelectedText(0,paragraph.length()-1,Color.WHITE);
        }
    }
    private void setLocation()
    {
        int screenHeight, screenWidth, frameHeight, frameWidth, screenHeightHalf, screenWidthHalf, frameHeightHalf, frameWidthHalf;
        
        screenHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
        screenWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
        screenHeightHalf=screenHeight/2;
        screenWidthHalf=screenWidth/2;
        
        frameHeight=getHeight();
        frameWidth=getWidth();
        frameHeightHalf=frameHeight/2;
        frameWidthHalf=frameWidth/2;
        
        int x, y;
        x=screenWidthHalf-frameWidthHalf;
        y=screenHeightHalf-frameHeightHalf;
        
        this.setLocation(x, y);
    }
    private void initializeParagraphs(){
        paragraphs = new Stack<String>();
        paragraphs.add("NO PRESSURE AND TYPE FAST");
        /*paragraphs.add("Scolding is something common in student life. Being a naughty boy, I am always scolded by my parents. But one day I was severely scolded by my English teacher. She infect teaches well. But that day, I could not resist the temptation that an adventure of Nancy Drew offered. While she was teaching, I was completely engrossed in reading that book. Nancy Drew was caught in the trap laid by some smugglers and it was then when I felt a light tap on my bent head. The teacher had caught me red handed. She scolded me then and there and insulted me in front of the whole class. I was embarrassed. My cheeks burned being guilty conscious. When the class was over, I went to the teacher to apologize. When she saw that I had realized my mistake, she cooled down and then told me in a very kind manner how disheartening it was when she found any student not paying attention. I was genuinely sorry and promised to myself never to commit such a mistake again.");
        paragraphs.add("Days are not of equal value in ones life. Some bring happiness while others bring sadness. Sadness and happiness both are equally important to mans life, since they are the two sides of a coin. As we cannot forget the happiest day, we are unable to forget the saddest day of our life too. The saddest day of my life was the Diwali Day. Diwali is considered to be a happy festival and till last Diwali, it was my favorite festival. On last Diwali, my sister, my brother and I were busy lighting the fireworks. I was holding a fuljhari in my hand and unfortunately my younger brother, who was standing just beside me, had a cracker in his hand. This cracker caught fire and a very loud explosion was heard which shook my sister and me. After that, we all could think of nothing else than blood stained cotton, bandage, dettol, etc. My cousin took my brother to the doctor where he got 14 stitches in his forefinger and thumb. But at home, everybody kept cursing and blaming me for the mishap. That night, I could not sleep and I cried a lot. For next few days, I bore the burden of this blame for being responsible for this unfortunate incident. I felt deeply guilty conscious which I was able to overcome after a long time.");
        paragraphs.add("Studying is the main source of knowledge. Books are indeed never failing friends of man. For a mature mind, reading is the greatest source of pleasure and solace to distressed minds. The study of good books ennobles us and broadens our outlook. Therefore, the habit of reading should be cultivated. A student should never confine himself to his schoolbooks only. He should not miss the pleasure locked in the classics, poetry, drama, history, philosophy etc. We can derive benefit from other’s experiences with the help of books. The various sufferings, endurance and joy described in books enable us to have a closer look at human life. They also inspire us to face the hardships of life courageously. Nowadays there are innumerable books and time is scarce. So we should read only the best and the greatest among them. With the help of books we shall be able to make our thinking mature and our life more meaningful and worthwhile.");
        paragraphs.add("Recently, an exhibition Building A New India was held in the capital. It was organized by the Ministry of Information and Broadcasting, Government of India. The exhibition was set up in the Triveni Kala Sangam. The chief exhibits were photographs, novels, some sculptures by Indian modern artists presenting Indian cultural inheritance. First of all, I visited the general section of the exhibition where different charts and photographs depicting Indias development in various fields were set. Most impressive photographs among these were those showing Indias nuclear development. The second section dealt with Indias magnificent historical background. I was fascinated by the pictures of Mohanjodaro excavation. Then I saw the most beautiful and colorful section of the exhibition i.e. the cultural section. It consisted of paintings, sculptures, photographs etc. The Rajasthani and Gujarati paintings were very colourful and attractive. This exhibition, inaugurated by the Prime Minister, lasted for a week. It proved to be of great educational value. It brushed up my knowledge about India as my motherland. It enhanced my respect for my great country, India. I would very much appreciate if the Indian government organized some more such exhibitions.");
        paragraphs.add("A teacher is called builder of the nation. The profession of teaching needs men and women with qualities of head and heart. There are many teachers in our school and a large number of teachers among them are highly qualified. I have great respect for all of them. Yet I have a special liking for Miss Y. Miss Y is a woman of great principles. She is jewel among all the teachers. Almost all the students respect her. She teaches us English. She is quite at home in this subject. She takes keen interest in teaching students. Simple living and high thinking is her motto. She is a woman of sweet temper and is always ready to help in difficulties. She treats us like her own brothers and sisters. She is an ideal teacher. It is these qualities of head and heart that have endeared Miss Y to the students and teachers alike. She is an ideal teacher in real sense of the word. She is the real model to emulate. May she live as long as there is sweet fragrance in the flowers?");
        */
       /* paragraphs.add("Do you want to learn how to type fast. Key Hero is a typing test but it's also really good if you want to learn touch typing or other techniques to type faster. I personnally touch type using the colemak keyboard layout. I find this layout very comfortable. It helps reducing finger movements when you type and tries to improve certain problem found with the Dvorak keyboard layout.\n" +
"\n" +
"There are a couple of layout you can choose from if you are not happy with the default qwerty keyboard layout. You can find out about this topic on the wikipedia page.\n" +
"\n" +
"A lot of apps have been written to help people improve their typing skills. The difference between Key Hero and the other applications is that it's an online free solution. You won't have to install a thing. You just need any web browser and flash player to start improving your typing skills. Another important feature of the site is all the performance stats. When you are a registered user, you can view a chart of your typing tests and see how you are improving. You can also see your accuracy and how you have progressed.\n" +
"\n" +
"You can get rid of typing software and half baked typing games. Key Hero has everything you need to make you into an A-class typing master.\n" +
"\n" +
"You will soon speed up your typing if you use the site regularly. I believe that if you practice 5 minutes per day during a month you will greatly improve your speed.");
        paragraphs.add("After I joined the company, whom I found sitting in CLEANTHES's library, DEMEA paid CLEANTHES some compliments on the great care which he took of my education, and on his unwearied perseverance and constancy in all his friendships. The father of PAMPHILUS, said he, was your intimate friend: The son is your pupil; and may indeed be regarded as your adopted son, were we to judge by the pains which you bestow in conveying to him every useful branch of literature and science. You are no more wanting, I am persuaded, in prudence, than in industry.");
        paragraphs.add("Once you have made it to the box office and gotten your tickets, you are confronted with the problems of the theater itself. If you are in one of the run-down older theaters, you must adjust to the musty smell of seldom-cleaned carpets. Escaped springs lurk in the faded plush or cracked leather seats, and half the seats you sit in seem loose or tilted so that you sit at a strange angle. The newer twin and quad theaters offer their own problems. Sitting in an area only one-quarter the size of a regular theater, moviegoers often have to put up with the sound of the movie next door. This is especially jarring when the other movie involves racing cars or a karate war and you are trying to enjoy a quiet love story. And whether the theater is old or new, it will have floors that seem to be coated with rubber cement. By the end of a movie, shoes almost have to be pried off the floor because they have become sealed to a deadly compound of spilled soda, hardening bubble gum, and crushed Ju-Jubes.");
        paragraphs.add("Some of the patrons are even more of a problem than the theater itself. Little kids race up and down the aisles, usually in giggling packs. Teenagers try to impress their friends by talking back to the screen, whistling, and making what they consider to be hilarious noises. Adults act as if they were at home in their own living rooms and comment loudly on the ages of the stars or why movies aren't as good anymore. And people of all ages crinkle candy wrappers, stick gum on their seats, and drop popcorn tubs or cups of crushed ice and soda on the floor. They also cough and burp, squirm endlessly in their seats, file out for repeated trips to the rest rooms or concession stand, and elbow you out of the armrest on either side of your seat.");
        paragraphs.add("First of all, just getting to the theater presents difficulties. Leaving a home equipped with a TV and a video recorder isn't an attractive idea on a humid, cold, or rainy night. Even if the weather cooperates, there is still a thirty-minute drive to the theater down a congested highway, followed by the hassle of looking for a parking space. And then there are the lines. After hooking yourself to the end of a human chain, you worry about whether there will be enough tickets, whether you will get seats together, and whether many people will sneak into the line ahead of you.");
        */
        paragraphs.add("hsjnf jdoanf jdd ;ang afjgpwn g iag ijdgank ponagkn pngepna daggg is a galag student's faogm mkljgd xyzzeog jgaj klgl ldsgln ldkglfjfa ldsgkln lknag Snlsgnknld lkadlllg kgk lad,a,g dgklkang sdgognoad lhagiio kiksdhn  helhah turrr lagknda buraa jsndhn ldfd;m lnfondsgn lsfnhkn knaglnh 13cftdjj zhnk a ngek giasgsj gadlgn lkdglknag lkshkka kgna i gk shpna lgnla kngan kingan kadhl akgn kg akdga agknagnka and students 'fankg'  agjgkla kdglnadg ig kia gia l agik ladhkn ealgkl.");
        paragraphs.add("Hola! Yo empece aprendo Espanol hace dos mes en la escuela. Yo voy la universidad. Yo tratar estudioso Espanol tres hora todos los dlas para que yo saco mejor rapido. Cosa algun yo debo hacer ademas construir ml vocabulario? Muchas veces yo estudioso la palabras solo para que yo construir ml voabulario rapido. Yo quiero empiezo leo el periodico Espanol la proxima semana. Por favor correcto algun la equivocaciónes yo hisciste. Gracias!");
        paragraphs.add("kfd kfal laegnj jf jgg ja fk fm f s g ws g s g a g ag g awgljafjb ajegbj ladgb jeag k agkae  kjdhbag kaeagb kag AND gsjkb  GAGgsd aGAEG aedggja dg gkaf gag g gl grwh algna ag gkkag aglnag lknd agbl gg ag g algga nfg,nag g ag g nejgeg  lag lagggnaeklg lkgkadgag lg ga gj gag alg dgk gjsa o g asg  gwa5 ak 315 asfk al gka  akg aagk kh ag ga gla g g t tloah dsg gagj kjagl kladg j jga gjladgllg onahnkah laglkahn lkjgad gakjbadg l jbaga gslgsglsjg slga.");
        paragraphs.add("nfak jfa  ifa algb k aggj AGff MGAHDFJ JDA FAJ ga loaGAJG mksnf'g GJAg kg agdgbn dgnd g fka gk gka gldfsdg  GJDAGA jgj dajgd agDJG  g gl grwh algna ag gkkag aglnag lknd agbl gg ag g algga nfg,nag g ag g nejgeg  lag lagggnaeklg fjbjafknk jf KF IW RSFNBN FIB W Fkif kgdamgi agBMGSB kn  fkhnkgbg kia ggjg 3i3 fgnj eag gjkgnsh ikg gng g  f r g gmam kdg  aelag lag kd kahnln oahnogn gf agg alg lg agnk gkag kggkig gkg aegl ajpr gp9arw gjnag kdnal lsgmlmg lng.");
        /*paragraphs.add("When it comes to animals, dogs are great pets. Dogs can help both elderly people and children with their daily life. Because dogs are so active, they can be great buddies for exercise. There are many different dogs from the really big to the small ones. You can train your dog yourself or send it to school in order to make it obedient. Dogs are loyal and reliable animals that people have had as pets for hundreds of years. When it comes to animals, dogs are great pets. Dogs can help both elderly people and children with their daily life. Because dogs are so active, they can be great buddies for exercise. There are many different dogs from the really big to the small ones. You can train your dog yourself or send it to school in order to make it obedient. Dogs are loyal and reliable animals that people have had as pets for hundreds of years.");
        paragraphs.add("In recent years many people have become increasingly aware of the need for physical fitness.Almost everywhere people turn, whether it is to a newsstand, television or billboard ,advice for guarding and improving health bombards them.  Although much of this advice is commercially motivated by those eager to sell vitamins, natural foods and reducing gimmicks ,some of it, especially that advocating a regular exercise program, merits serious attention. Such a program, if it consists of at least thirty minutes three times a week and if a person's physician approves it,provides numerous benefits. Almost everywhere people turn, whether it is to a newsstand, television or billboard ,advice for guarding and improving health bombards them.  Although much of this advice is commercially motivated by those eager to sell vitamins, natural foods and reducing gimmicks ,some of it, especially that advocating a regular exercise program, merits serious attention. Such a program, if it consists of at least thirty minutes three times a week and if a person's physician approves it,provides numerous benefits. Regular exercise releases tension, improves appearance, and increases stamina");
        paragraphs.add("'Why both Sartre and Camus should have been so keenly aware of the absurd about the same time is one of those mysteries of the zeitgeist time spirit: that we are still far from solving. Camus always had a strongly lyrical feeling for the material world-sunlight, sea, saw- so that his consciousness of the absurd was an intermittent feeling, occurring only in moments of metaphysical interrogation. Also, as he came from the working classs, he did not look upon it so much as a social stratum but rather as a highly differentiated reality, made up of irreducible individuals. In other words, his intellectualism, which was genuine and had moreover been developed by philosophical studies at the university level, was tempered by pragmatism and a sense of inarticualate values. Poverty, tuberculosis, and a feeling for human solidarity made him much more of an ordinary man than Sartre, the ferociously dedicated intellectual, whose professed aim is to translate everything into words, so that formulated truths can react on society. '");
        paragraphs.add("Dearest creature in creation,\n" +
"Study English pronunciation.\n" +
"I will teach you in my verse\n" +
"Sounds like corpse, corps, horse, and worse.\n" +
"I will keep you, Suzy, busy,\n" +
"Make your head with heat grow dizzy.\n" +
"Tear in eye, your dress will tear.\n" +
"So shall I! Oh hear my prayer.\n" +
"\n" +
"Just compare heart, beard, and heard,\n" +
"Dies and diet, lord and word,\n" +
"Sword and sward, retain and Britain.\n" +
"(Mind the latter, how it’s written.)\n" +
"Now I surely will not plague you\n" +
"With such words as plaque and ague.\n" +
"But be careful how you speak:\n" +
"Say break and steak, but bleak and streak;\n" +
"Cloven, oven, how and low,\n" +
"Script, receipt, show, poem, and toe.\n" +
"\n" +
"Hear me say, devoid of trickery,\n" +
"Daughter, laughter, and Terpsichore,\n" +
"Typhoid, measles, topsails, aisles,\n" +
"Exiles, similes, and reviles;\n" +
"Scholar, vicar, and cigar,\n" +
"Solar, mica, war and far;\n" +
"One, anemone, Balmoral,\n" +
"Kitchen, lichen, laundry, laurel;\n" +
"Gertrude, German, wind and mind,\n" +
"Scene, Melpomene, mankind.\n" +
"\n" +
"Billet does not rhyme with ballet,\n" +
"Bouquet, wallet, mallet, chalet.\n" +
"Blood and flood are not like food,\n" +
"Nor is mould like should and would.\n" +
"Viscous, viscount, load and broad,\n" +
"Toward, to forward, to reward.\n" +
"And your pronunciation’s OK\n" +
"When you correctly say croquet,\n" +
"Rounded, wounded, grieve and sieve,\n" +
"Friend and fiend, alive and live.\n" +
"\n");*/
        paragraphs.add("urr urraa urrra urararr blahh mahhh\n" + 
                " supp fupp cupp 'and this was some tricky' words! to type in " +
                "you final round of typing competition. ONCE i Was " + 
                "VeRY scary so zzzz fkamf kd HaF!GG AF 10FF and the suddenly "+ 
                "i broke my fingers because of typing. Days are not of " +
                "equal value in ones life. Some bring happiness while others bring sadness. " +
                "This is A Gr8 OpportuNity to shoW your typing Skills. " + 
                "'All THE Best' Haahahahaha lolzz 12Hsgg42HAge in the water ther was a fish. " +
                "Thank You AND HAve a GReat Day Ahead.");
        
        jParaText1.setFont(new Font("Bell MT",Font.PLAIN,35));
       
        jParaText1.setText(paragraphs.elementAt(0));
        currentParagraph = 0;
        paragraph = paragraphs.get(currentParagraph);
    }
    private void startTest()
    {
        startTime = System.currentTimeMillis();
        jEdittext2.setEnabled(true);        
        jEdittext2.requestFocus();
        
        /*Timer t = new Timer(1000, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                count++;
                number.setText(count + "");
                if(count==60000/1000)
                {
                    
                    JOptionPane.showMessageDialog(jEdittext2,"TimeUp!! Please hit ok to show your result");
                    String stats = "Correct Letters: "+(checkPosition+1) + " Time Taken: "+(60000/1000);
                    jLabel1.setText(stats);
                    
                      
                }
                //endTime = System.currentTimeMillis();
                //jEdittext2.setEnabled(false);
            }
        });
        t.start();*/
        
    }
    private void endTest(){
        endTime = System.currentTimeMillis();
        jEdittext2.setEnabled(false);
        String stats = "Correct Letters: "+(checkPosition+1);
        jLabel1.setText(stats);
    }
    
    private void changeColorOfSelectedText(int start,int end,Color c)
    {
        jParaText1.setSelectionStart(start);
        jParaText1.setSelectionEnd(end+1);
        StyledDocument doc = jParaText1.getStyledDocument();
        if (start == end) { // No selection, cursor position.
            return;
        }
        if (start > end) { // Backwards selection?
            int life = start;
            start = end;
            end = life;
        }
        Style style = jParaText1.addStyle("Bell MT", null);
        StyleConstants.setForeground(style, c.brighter());
        style = jParaText1.getStyle("Bell MT");
        doc.setCharacterAttributes(start, end - start, style, false);
    }
    private void correctLetter(int position){
        changeColorOfSelectedText(0,position+1,Color.GREEN);
    }
    private void wrongLetter(char c){
        String text = jEdittext2.getText();
        jEdittext2.setText(text.substring(0,checkPosition));
        System.out.println("Wrong letter call");
        Toolkit.getDefaultToolkit().beep();
        playError();
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
        startest = new javax.swing.JButton();
        cpara = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jParaText1 = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEdittext2 = new javax.swing.JTextPane();
        jPanel5 = new javax.swing.JPanel();
        timerlabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TypeMaster");

        jPanel1.setLayout(new java.awt.GridLayout(1, 5));

        startest.setForeground(new java.awt.Color(204, 102, 0));
        startest.setText("Start Test");
        startest.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 0), 2, true));
        startest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startestActionPerformed(evt);
            }
        });
        jPanel1.add(startest);

        cpara.setForeground(new java.awt.Color(204, 102, 0));
        cpara.setText("Change Paragraph");
        cpara.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 0), 2, true));
        cpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cparaActionPerformed(evt);
            }
        });
        jPanel1.add(cpara);

        jButton3.setForeground(new java.awt.Color(204, 102, 0));
        jButton3.setText("Exit");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 0), 2, true));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 102, 0));
        jLabel1.setText("Status:");
        jPanel2.add(jLabel1, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(204, 102, 0));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 0), 5, true));
        jPanel3.setLayout(new java.awt.GridLayout(2, 0, 0, 10));

        jParaText1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jScrollPane1.setViewportView(jParaText1);

        jPanel3.add(jScrollPane1);

        jPanel4.setBackground(new java.awt.Color(204, 102, 0));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 204, 0));
        jLabel2.setText("TYPE-O-MANIA");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel4.add(jLabel2, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel4);

        jEdittext2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jEdittext2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jEdittext2.setToolTipText("");
        jEdittext2.getCaret();
        jEdittext2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jEdittext2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jEdittext2KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jEdittext2);

        jPanel3.add(jScrollPane2);

        jPanel5.setBackground(new java.awt.Color(204, 102, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        timerlabel1.setFont(new java.awt.Font("Courier New", 1, 23)); // NOI18N
        timerlabel1.setForeground(new java.awt.Color(255, 204, 0));
        timerlabel1.setText("60");

        jLabel3.setFont(new java.awt.Font("Courier New", 1, 23)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 204, 0));
        jLabel3.setText("TIME:");

        jTextArea1.setBackground(new java.awt.Color(204, 102, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Courier New", 1, 15)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 204, 0));
        jTextArea1.setRows(5);
        jTextArea1.setText("\tSCORING RULES:\n\n\t• Typing speed will be calculated depending \n  \ton correct letters typed per minute(LPM).\n\n\t• Typing speed will be displayed after \n  \teach attempt.\n\n\t• Candidate with highest LPM will be the \n  \twinner.\n\n\t• Five attempts per participants are allowed.\n\n\t• In case of tie, final decision will be \n  \ttaken by the organizers.\n\n");
        jTextArea1.setEditable(false);
        jScrollPane3.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timerlabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timerlabel1)
                    .addComponent(jLabel3))
                .addContainerGap(181, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );

        jPanel3.add(jPanel5);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("Typing Tool");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cparaActionPerformed
        // TODO add your handling code here:
        changeParagraph();
    }//GEN-LAST:event_cparaActionPerformed
/**/
    private void jEdittext2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEdittext2KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(evt.getKeyCode()==KeyEvent.VK_BACKSPACE){
            checkPosition--;
            System.out.println("CP: "+checkPosition);
            return;
        }
        else if(evt.isActionKey())
            return;
        else{
            checkLetter(c,checkPosition+1);
        }
    }//GEN-LAST:event_jEdittext2KeyTyped

    private void jEdittext2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEdittext2KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_BACKSPACE){
            checkPosition--;
            changeColorOfSelectedText(checkPosition+1,checkPosition+2,Color.GREEN);
        }
    }//GEN-LAST:event_jEdittext2KeyReleased

    
    private void startestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startestActionPerformed
        // TODO add your handling code here:
        startTest();
        starttimer();
        count=60;
        t.start();
        resetEverything();
       // jLabel1.setText("Status:");
        timerlabel1.setText("60");
        changeParagraph();
        changeColorOfSelectedText(ABORT, WIDTH, Color.white);
        
        
        
    }//GEN-LAST:event_startestActionPerformed
/**/
    /*     */
    private void starttimer(){
        t = new Timer(1000,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count--;
                timerlabel1.setText("" + count);
                startest.setEnabled(false);
                cpara.setEnabled(false);
//                resetbtn.setEnabled(false);
//                endtest.setEnabled(false);
                jEdittext2.setEditable(true);
                
                if(count <=0 ){
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,"TIME UP! PLEASE HIT OK TO SHOW YOUR RESULT");
                    String stats = "Correct Letters: "+(checkPosition+1) + " Time Taken: "+(60000/1000);
                    jLabel1.setText(stats);
                    count=0;
                    t.stop();
                    jEdittext2.setEditable(false);
                    startest.setEnabled(true);
                    cpara.setEnabled(true);
//                    resetbtn.setEnabled(true);
//                    endtest.setEnabled(true);
                }

            }
        });
        
    }
    
    
   /*   */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cpara;
    private javax.swing.JButton jButton3;
    private javax.swing.JTextPane jEdittext2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextPane jParaText1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton startest;
    private javax.swing.JLabel timerlabel1;
    // End of variables declaration//GEN-END:variables

}
