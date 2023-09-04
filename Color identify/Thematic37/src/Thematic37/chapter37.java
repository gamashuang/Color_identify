package Thematic37;

import java.awt.Color;					//ø�s�C��ΧΪ�
import java.awt.Font;					
import java.awt.Graphics;				
import java.awt.Graphics2D;				
import java.awt.geom.Rectangle2D;		//-----------------------
import java.awt.event.ActionEvent;		//�ƥ��ť��(ActionListener)
import java.awt.event.ActionListener;	//-----------------------
import javax.swing.JButton;				//�{����������()
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;				//�p�ɾ�����

public class chapter37 extends JFrame {
	private static final long serialVersionUID = 1L;
	
	final static int quest=25;							//quest:�D�ؼ�(�T�w�`��)
	private static int[] ans = new int[quest];			//ans[]:���T���׬����}�C
	private static JTextArea Manual;					//Manual:������
	private static JLabel lblWrong,lblRight;			//lblWorng:���~����(���)	lblRight:���T����(���)
	private static JLabel lblTime,lbltrue_ans;			//lblTime:�ɶ��˼�(���)		lbltrue_ans:���D���~�ɪ��u������(���)
	private static JButton btnOK;						//btnOK:�}�lor�Ȱ�or�~��or���m
	private static JButton[] btn = new JButton[3];		//btn[]:���׫��s�}�C
	private static Canvas pan;							//pan:�e��
	private static Color[][] Q = new Color[quest][3];	//Q[]:�C��}�C
	private static boolean is_pause=true;				//is_pause:�Ȱ�or�~��(�P�_)
	private static boolean is_initial=true;				//is_initial:��l��(�P�_)
	private static boolean is_clock=false;				//is_clock:�ɶ��p��(�P�_)
	private static long tot_time,con_time;				//tot_time:�֭p�g�L�ɶ�(�p��)	con_time:�t�η�e�ɶ�(�p��)
	private static int wrong=0,right=0;					//wrong:���~����(�p��)	right:���T����(�p��)
	private static int R,G,B,choas;						//R,G,b:��,��,��	choas:-25~25�H����
	private static int set_time=60,remain_time=99,now=0;//set_time:�]�w���	remain_time:�Ѿl���	now:�{�b�D��
	
	chapter37() {
		setTitle("�C��ӷP�״���"); setSize(800,600); setLocationRelativeTo(null);
		setVisible(true); getContentPane().setLayout(null); setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pan = new Canvas();
		pan.setLayout(null);
		pan.setBounds(0,0,800,600); getContentPane().add(pan);
		
		Manual = new JTextArea("�w��Ө��C��ӷP�״���\n���ճW�h�p�U:\n�ݧ��� 20 �D���T���D\n\n�åB���~�֩� 5 �D\n\n"
				+ "�t�� 60 ��ɶ�����\n\n�~���i�H�ϥμȰ����}�ҳ�\n\n���O�٨�L�ҥ��v�q�A�Ȱ��ɷ|�����D��\n\n���ֶ��Q�q�L����");
		Manual.setBounds(250,50,300,300); Manual.setEditable(false);
		Manual.setFont(new Font("",Font.BOLD,15)); Manual.setLayout(null);
		Manual.setLineWrap(true); Manual.setWrapStyleWord(true); pan.add(Manual);
		
		btn[0] = new JButton(); btn[0].setBounds(100,410,100,40); btn[0].setVisible(false);
		btn[0].setText("���~~"); btn[0].addActionListener(new mouseAction()); pan.add(btn[0]);
		btn[1] = new JButton(); btn[1].setBounds(350,410,100,40); btn[1].setVisible(false);
		btn[1].setText("���~~"); btn[1].addActionListener(new mouseAction()); pan.add(btn[1]);
		btn[2] = new JButton(); btn[2].setBounds(600,410,100,40); btn[2].setVisible(false);
		btn[2].setText("���~~"); btn[2].addActionListener(new mouseAction()); pan.add(btn[2]);
		btnOK = new JButton(); btnOK.setBounds(325,475,150,60);
		btnOK.setFont(new Font("",Font.BOLD,20));
		btnOK.setText("�}�l"); btnOK.addActionListener(new mouseAction()); pan.add(btnOK);
		
		lblRight = new JLabel(); lblRight.setBounds(610,70,100,60); lblRight.setVisible(false);
		lblRight.setText("0��"); lblRight.setFont(new Font("",Font.BOLD,45)); pan.add(lblRight);
		lblWrong = new JLabel(); lblWrong.setBounds(610,170,100,60); lblWrong.setVisible(false);
		lblWrong.setText("����0�D"); lblWrong.setFont(new Font("",Font.BOLD,22)); pan.add(lblWrong);
		lbltrue_ans = new JLabel(); lbltrue_ans.setBounds(75,150,100,60); lbltrue_ans.setVisible(false);
		lbltrue_ans.setFont(new Font("",Font.BOLD,16)); lbltrue_ans.setForeground(Color.RED); pan.add(lbltrue_ans);
		
		lblTime = new JLabel(); lblTime.setBounds(75,50,150,60); lblTime.setVisible(false);
		lblTime.setText(""); lblTime.setFont(new Font("",Font.BOLD,30)); pan.add(lblTime);
		pan.repaint();
		
		Timer();
	}
	
	class Canvas extends JPanel{
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g1 = (Graphics2D)g;
			
			if(!is_pause) {
				for(int i=0;i<3;i++) {
					g1.setColor(Q[now][i]);
						if(i==ans[now]) {
							g1.fill(new Rectangle2D.Double(100+(250*i),300,100,100));
							g1.fill(new Rectangle2D.Double(300,50,200,200));
						}
						else {
							g1.fill(new Rectangle2D.Double(100+(250*i),300,100,100));
						}
				}
			}
			else {
				g1.setColor(null);
			}
		}
	}
	
	class mouseAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {		

				if(e.getSource()==btn[0] && !is_pause) {
					if(ans[now]==0) {
						lblRight.setText(++right+"��");
						lbltrue_ans.setText("");
					}
					else {
						lblWrong.setText("����"+(++wrong)+"��");
						lbltrue_ans.setText("�W�D���׬O "+(ans[now]+1));

					}
					now++;
				}
				
				if(e.getSource()==btn[1] && !is_pause) {
					if(ans[now]==1) {
						lblRight.setText(++right+"��");
						lbltrue_ans.setText("");
					}
					else {
						lblWrong.setText("����"+(++wrong)+"��");
						lbltrue_ans.setText("�W�D���׬O "+(ans[now]+1));
					}
					now++;
				}
				
				if(e.getSource()==btn[2] && !is_pause) {
					if(ans[now]==2) {
						lblRight.setText(++right+"��");
						lbltrue_ans.setText("");
					}
					else {
						lblWrong.setText("����"+(++wrong)+"��");
						lbltrue_ans.setText("�W�D���׬O "+(ans[now]+1));
					}
					now++;
				}
	
				if(e.getSource()==btnOK && wrong<5 && right<20 && remain_time>0) {
					
					is_pause=!is_pause;
					
					if(is_initial) {
						begin();
						is_clock=true;
						con_time = System.currentTimeMillis();
					}
					else if(is_pause){
						btnOK.setText("�~��");
						is_clock=false;
					}
					else {
						btnOK.setText("�Ȱ�");
						is_clock=true;
						con_time = System.currentTimeMillis();
					}
				}
				end();
				pan.repaint();
		}
	}
	
	public void begin() {
		
		is_initial=false;
		
		btnOK.setText("�Ȱ�");
		lblRight.setVisible(true);
		lblWrong.setVisible(true);
		lbltrue_ans.setVisible(true);
		lblTime.setVisible(true);
		btn[0].setVisible(true);
		btn[1].setVisible(true);
		btn[2].setVisible(true);
		Manual.setVisible(false);
			
		for(int i=0;i<quest;i++) {
				
			ans[i]=(int)(Math.random()*3);
			R=(int)(Math.random()*206)+25;
			G=(int)(Math.random()*206)+25;
			B=(int)(Math.random()*206)+25;
				
			for(int j=0;j<3;j++) {
					
				choas=(int)(Math.random()*51)-25;
				if(choas==0) {j--; continue;}
					
				if(ans[i]==j) {
					Q[i][j] = new Color(R,G,B);
				}
				else {
					Q[i][j] = new Color(R+choas,G+choas,B+choas);
				}
			}
		}
	}
	
	public void end() {
		
		if(remain_time<=0) {
			JOptionPane.showMessageDialog(this,"�z�����Z�p�U:\n�����D��: "+right+"�D\n�����D��: "+wrong+
					"�D\n�Ѿl�ɶ�: "+remain_time+"��","�ɶ��w��",JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(this,"�]�\�O�P�_�����G�_�A�����O�]�O�ܭ��n���A�n�۫H�ۤv��\n�U���A�ӹL�a �~���H",
					"���q�L����",JOptionPane.ERROR_MESSAGE);
		}
		else if(wrong>=5) {
			is_clock=false;
			JOptionPane.showMessageDialog(this,"�z�����Z�p�U:\n�����D��: "+right+"�D\n�����D��: "+wrong+
					"�D\n�Ѿl�ɶ�: "+remain_time+"��","���Z���F�з�",JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(this,"�]�\�O�Ѥ������A�]�i��ǳƤ���\n�U���A�ӹL�a �~���H"
					,"���q�L����",JOptionPane.ERROR_MESSAGE);
		}
		else if(right>=20) {
			is_clock=false;
			JOptionPane.showMessageDialog(this,"�z�����Z�p�U:\n�����D��: "+right+"�D\n�����D��: "+wrong+
					"�D\n�Ѿl�ɶ�: "+remain_time+"��","���Z�w�F�з�",JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(this,"�ݨӱz�O�i�y�����ڡA�@�w�����U�Q������\n\n�A�����߱z�q�L������!!!"
					,"�w�q�L����",JOptionPane.PLAIN_MESSAGE);
		}
		else {
			return;
		}
		
		System.exit(0);
	}
		
	public void Timer()	{
		ActionListener Aray = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(is_clock) {
					tot_time += System.currentTimeMillis() - con_time;
					remain_time = set_time - (int)(tot_time/1000);
					if(remain_time<=0) {remain_time=0; is_clock=false; is_pause=true; end();}
					lblTime.setText("�Ѿl"+String.valueOf(remain_time)+"s");
					con_time = System.currentTimeMillis();	
				}
			}
		};
		Timer timer = new Timer(1000,Aray);
		if(is_initial) {
			timer.start();
		}
		else if(is_pause){		
			timer.stop();
		}
		else {
			timer.restart();
		}
	}
	
	public static void main(String[] args) {
		new chapter37();
	}
}