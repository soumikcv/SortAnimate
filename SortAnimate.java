import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SortAnimate extends JPanel {

    private static final int NUM_OF_ITEMS = 200;
    private static final int DIM_W = 1000;
    private static final int DIM_H = 650;
    private static final int HORIZON = 600;
    private static final int VERT_INC = 2;
    private static final int HOR_INC = DIM_W / NUM_OF_ITEMS;

    private JButton startButton;
    private Timer timer1 = null;
    private Timer timer2 = null;
    private Timer timer3 = null;
    private Timer timer4 = null;
    private Timer timer5 = null;
    private Timer timer6 = null;
    private JButton resetButton;
    public int radix=0;
    Integer[] list;
    Integer[] sorted;
    int currentIndex = NUM_OF_ITEMS - 1;

    public SortAnimate() {
        list = initList();
        sorted = initSorted();

        timer1 = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    bubbleSort();
                }
                repaint();
            }
        });

        timer2 = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    selectionSort();
                }
                repaint();
            }
        });
        
        timer3 = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    insertionSort();
                }
                repaint();
            }
        });

        timer4 = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    quicksort(0,currentIndex);
                }
                repaint();
            }
        });
        timer5 = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    mergeSort(0,currentIndex);
                }
                repaint();
            }
        });
        timer6 = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    radixSort();
                    radix++;
                }
                repaint();
            }
        });
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer6.start();
            }
        });
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list = initList();
                currentIndex = NUM_OF_ITEMS - 1;
                radix = 0;
                repaint();
                startButton.setEnabled(true);
            }
        });
        add(startButton);
        add(resetButton);
    }

    public boolean isSortingDone() {
        return Arrays.equals(list,sorted);
    }

    public Integer[] initList() {
        Integer[] nums = new Integer[NUM_OF_ITEMS];
        for (int i = 1; i <= nums.length; i++) {
            nums[i - 1] = i;
        }
        Collections.shuffle(Arrays.asList(nums));
        return nums;
    }

    public Integer[] initSorted() 
    {
        Integer[] nums = new Integer[NUM_OF_ITEMS];
        for (int i = 1; i <= nums.length; i++) {
            nums[i - 1] = i;
        }
        return nums;
    }

    public void drawItem(Graphics g, int item, int index) {
        int height = item * VERT_INC;
        int y = HORIZON - height;
        int x = index * HOR_INC;
        g.fillRect(x, y, HOR_INC-1, height);
    }

    public void bubbleSort() {  
        int n = list.length;  
        int temp = 0;  
         for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(list[j-1] > list[j])
                          {    
                             temp = list[j-1];  
                             list[j-1] = list[j];  
                             list[j] = temp;
                             return;  
                         }  
                          
                 }  
         }
     }

    public void selectionSort() {
        int currentMax = list[0];
        int currentMaxIndex = 0;

        for (int j = 1; j <= currentIndex; j++) {
            if (currentMax < list[j]) {
                currentMax = list[j];
                currentMaxIndex = j;
            }
        }

        if (currentMaxIndex != currentIndex) {
            list[currentMaxIndex] = list[currentIndex];
            list[currentIndex] = currentMax;
        }
        currentIndex--;
    }

   public void insertionSort() {
    for (int i = 1; i < list.length; i++) {
      int j = i - 1;
      int numberToInsert = list[i];
      while (j >= 0 && numberToInsert < list[j]) {
        list[j + 1] = list[j];
        list[j] = numberToInsert;
        j--;
            return;
      }
    }
  }

	public void quicksort(int first,int last)
        {
            int pivot,j,temp,i;

            if(first<last)
            {
                pivot=first;
                i=first;
                j=last;

                while(i<j)
                {
                    while(list[i]<=list[pivot]&&i<last)
                        i++;
                    while(list[j]>list[pivot])
                        j--;
                    if(i<j)
                    {
                        temp=list[i];
                        list[i]=list[j];
                        list[j]=temp;
                        return;
                    }
                }

                temp=list[pivot];
                list[pivot]=list[j];
                list[j]=temp;
                quicksort(first,j-1);
                quicksort(j+1,last);

            }
        }


    public void mergeSort(int min,int max){
        if(max-min==0){
        }
        else if(max-min==1){
            if(list[min]>list[max])
                swap(min,max);
        }
        else{
            int mid=((int) Math.floor((min+max)/2));
            
            mergeSort(min,mid);
            mergeSort(mid+1,max);
            
            int i=min;
            while(i<=mid){
            if(list[i]>list[mid+1]){
                swap(i,mid+1);
                push(mid+1,max);
                return;
            }           
            i++;

        }  
        }
    }


    public void swap(int loc1,int loc2){

        list[loc1]=list[loc1]^list[loc2];
        list[loc2]=list[loc1]^list[loc2];
        list[loc1]=list[loc1]^list[loc2];
    }

    public void push(int s,int e){
        for(int i=s;i<e;i++){
            if(list[i]>list[i+1])
                swap(i,i+1);
        }
    }

public void radixSort()
    {
        int i, m = list[0], exp = 1, n = list.length;
        int[] b = new int[n];
        int c = 0;
        
        for (i = 1; i < n; i++)
            if (list[i] > m)
                m = list[i];
        
         while (m / exp > 0)
        {   
            int[] bucket = new int[10];
 
            for (i = 0; i < n; i++)
                bucket[(list[i] / exp) % 10]++;
              for (i = 1; i < 10; i++)
                bucket[i] += bucket[i - 1];
            for (i = n - 1; i >= 0; i--){
                b[--bucket[(list[i] / exp)%10]] = list[i];
            }
            for (i = 0; i < n; i++)
                {list[i] = b[i];

                }
            if(c==radix)
                return;
            c++;
            exp *= 10;
             
        }
    }   





    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < list.length; i++) {
            drawItem(g, list[i], i);
        }
    }

    
    public Dimension getPreferredSize() {
        return new Dimension(DIM_W, DIM_H);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Sort");
                frame.add(new SortAnimate());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}