package Thematic37;

import java.awt.Color;					//繪製顏色及形狀
import java.awt.Font;					
import java.awt.Graphics;				
import java.awt.Graphics2D;				
import java.awt.geom.Rectangle2D;		//-----------------------
import java.awt.event.ActionEvent;		//事件傾聽者(ActionListener)
import java.awt.event.ActionListener;	//-----------------------
import javax.swing.JButton;				//程式介面物件()
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;				//計時器物件

public class chapter37 extends JFrame {
	private static final long serialVersionUID = 1L;
	
	final static int quest=25;							//quest:題目數(固定常數)
	private static int[] ans = new int[quest];			//ans[]:正確答案紀錄陣列
	private static JTextArea Manual;					//Manual:說明書
	private static JLabel lblWrong,lblRight;			//lblWorng:錯誤分數(顯示)	lblRight:正確分數(顯示)
	private static JLabel lblTime,lbltrue_ans;			//lblTime:時間倒數(顯示)		lbltrue_ans:答題錯誤時的真正答案(顯示)
	private static JButton btnOK;						//btnOK:開始or暫停or繼續or重置
	private static JButton[] btn = new JButton[3];		//btn[]:答案按鈕陣列
	private static Canvas pan;							//pan:畫筆
	private static Color[][] Q = new Color[quest][3];	//Q[]:顏色陣列
	private static boolean is_pause=true;				//is_pause:暫停or繼續(判斷)
	private static boolean is_initial=true;				//is_initial:初始化(判斷)
	private static boolean is_clock=false;				//is_clock:時間計數(判斷)
	private static long tot_time,con_time;				//tot_time:累計經過時間(計時)	con_time:系統當前時間(計時)
	private static int wrong=0,right=0;					//wrong:錯誤分數(計數)	right:正確分數(計數)
	private static int R,G,B,choas;						//R,G,b:紅,綠,藍	choas:-25~25隨機數
	private static int set_time=60,remain_time=99,now=0;//set_time:設定秒數	remain_time:剩餘秒數	now:現在題目
	
	chapter37() {
		setTitle("顏色敏感度測試"); setSize(800,600); setLocationRelativeTo(null);
		setVisible(true); getContentPane().setLayout(null); setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pan = new Canvas();
		pan.setLayout(null);
		pan.setBounds(0,0,800,600); getContentPane().add(pan);
		
		Manual = new JTextArea("歡迎來到顏色敏感度測試\n測試規則如下:\n需完成 20 題正確答題\n\n並且錯誤少於 5 題\n\n"
				+ "另有 60 秒時間限制\n\n途中可以使用暫停離開考場\n\n為保障其他考生權益，暫停時會隱藏題目\n\n祝福順利通過測試");
		Manual.setBounds(250,50,300,300); Manual.setEditable(false);
		Manual.setFont(new Font("",Font.BOLD,15)); Manual.setLayout(null);
		Manual.setLineWrap(true); Manual.setWrapStyleWord(true); pan.add(Manual);
		
		btn[0] = new JButton(); btn[0].setBounds(100,410,100,40); btn[0].setVisible(false);
		btn[0].setText("選我~~"); btn[0].addActionListener(new mouseAction()); pan.add(btn[0]);
		btn[1] = new JButton(); btn[1].setBounds(350,410,100,40); btn[1].setVisible(false);
		btn[1].setText("選我~~"); btn[1].addActionListener(new mouseAction()); pan.add(btn[1]);
		btn[2] = new JButton(); btn[2].setBounds(600,410,100,40); btn[2].setVisible(false);
		btn[2].setText("選我~~"); btn[2].addActionListener(new mouseAction()); pan.add(btn[2]);
		btnOK = new JButton(); btnOK.setBounds(325,475,150,60);
		btnOK.setFont(new Font("",Font.BOLD,20));
		btnOK.setText("開始"); btnOK.addActionListener(new mouseAction()); pan.add(btnOK);
		
		lblRight = new JLabel(); lblRight.setBounds(610,70,100,60); lblRight.setVisible(false);
		lblRight.setText("0分"); lblRight.setFont(new Font("",Font.BOLD,45)); pan.add(lblRight);
		lblWrong = new JLabel(); lblWrong.setBounds(610,170,100,60); lblWrong.setVisible(false);
		lblWrong.setText("答錯0題"); lblWrong.setFont(new Font("",Font.BOLD,22)); pan.add(lblWrong);
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
						lblRight.setText(++right+"分");
						lbltrue_ans.setText("");
					}
					else {
						lblWrong.setText("答錯"+(++wrong)+"次");
						lbltrue_ans.setText("上題答案是 "+(ans[now]+1));

					}
					now++;
				}
				
				if(e.getSource()==btn[1] && !is_pause) {
					if(ans[now]==1) {
						lblRight.setText(++right+"分");
						lbltrue_ans.setText("");
					}
					else {
						lblWrong.setText("答錯"+(++wrong)+"次");
						lbltrue_ans.setText("上題答案是 "+(ans[now]+1));
					}
					now++;
				}
				
				if(e.getSource()==btn[2] && !is_pause) {
					if(ans[now]==2) {
						lblRight.setText(++right+"分");
						lbltrue_ans.setText("");
					}
					else {
						lblWrong.setText("答錯"+(++wrong)+"次");
						lbltrue_ans.setText("上題答案是 "+(ans[now]+1));
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
						btnOK.setText("繼續");
						is_clock=false;
					}
					else {
						btnOK.setText("暫停");
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
		
		btnOK.setText("暫停");
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
			JOptionPane.showMessageDialog(this,"您的成績如下:\n答對題數: "+right+"題\n答錯題數: "+wrong+
					"題\n剩餘時間: "+remain_time+"秒","時間已到",JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(this,"也許是判斷不夠果斷，反應力也是很重要的，要相信自己啊\n下次再來過吧 年輕人",
					"未通過測試",JOptionPane.ERROR_MESSAGE);
		}
		else if(wrong>=5) {
			is_clock=false;
			JOptionPane.showMessageDialog(this,"您的成績如下:\n答對題數: "+right+"題\n答錯題數: "+wrong+
					"題\n剩餘時間: "+remain_time+"秒","成績未達標準",JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(this,"也許是天分不夠，也可能準備不足\n下次再來過吧 年輕人"
					,"未通過測試",JOptionPane.ERROR_MESSAGE);
		}
		else if(right>=20) {
			is_clock=false;
			JOptionPane.showMessageDialog(this,"您的成績如下:\n答對題數: "+right+"題\n答錯題數: "+wrong+
					"題\n剩餘時間: "+remain_time+"秒","成績已達標準",JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(this,"看來您是可造之材啊，一定有雙銳利的眼睛\n\n再次恭喜您通過本測試!!!"
					,"已通過測試",JOptionPane.PLAIN_MESSAGE);
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
					lblTime.setText("剩餘"+String.valueOf(remain_time)+"s");
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