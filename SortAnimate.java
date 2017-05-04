import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SortAnimate extends JPanel {

    private static int NUM_OF_ITEMS = 200;
    private static int TIMER_VALUE = 50;
    private static final int DIM_W = 1000;
    private static final int DIM_H = 650;
    private static final int HORIZON = 600;
    private static final int VERT_INC = 2;

    private JButton startButton;
    private JButton pauseButton;
    private JSlider arraySize;
    private JSlider time;
    private JLabel delay;
    private JLabel arrayLabel;
    private Timer timer = null;
    private JButton resetButton;
    private JComboBox options;
    public int radix=0;
    Integer[] list;
    Integer[] sorted;
    int currentIndex = NUM_OF_ITEMS - 1;
    String[] sorts = {"Bubble Sort","Selection Sort","Insertion Sort","Quick Sort","Merge Sort","Radix Sort"};

    public SortAnimate() {
        list = initList(NUM_OF_ITEMS);
        sorted = initSorted(NUM_OF_ITEMS);

        timer = new Timer(TIMER_VALUE, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    switch(options.getSelectedIndex()){

                    case 0:
                        bubbleSort();
                        break;
                    case 1:
                        selectionSort();
                        break;
                    case 2:
                        insertionSort();
                        break;
                    case 3:
                        quicksort(0,currentIndex);
                        break;
                    case 4:
                        mergeSort(0,currentIndex);
                        break;
                    case 5:
                        radixSort();
                        radix++;
                        break;
                    }
                }
                repaint();
            }
        });



        arrayLabel = new JLabel("Array Size: 200");
        arraySize = new JSlider(JSlider.HORIZONTAL, 50, 250, 200);
        arraySize.addChangeListener(new ChangeListener() {
              public void stateChanged(ChangeEvent event) {
                int value = arraySize.getValue();
                String data = "Array Size: "+ value;
                arrayLabel.setText(data);
              }
            });






        delay = new JLabel("Delay: 50ms");
        time = new JSlider(JSlider.HORIZONTAL, 1, 200, 50);
        time.addChangeListener(new ChangeListener() {
              public void stateChanged(ChangeEvent event) {
                timer.stop();
                int value = time.getValue();
                TIMER_VALUE = value;
                timer = new Timer(TIMER_VALUE, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (isSortingDone()) {
                            ((Timer) e.getSource()).stop();
                            startButton.setEnabled(false);
                        } else {
                            switch(options.getSelectedIndex()){

                            case 0:
                                bubbleSort();
                                break;
                            case 1:
                                selectionSort();
                                break;
                            case 2:
                                insertionSort();
                                break;
                            case 3:
                                quicksort(0,currentIndex);
                                break;
                            case 4:
                                mergeSort(0,currentIndex);
                                break;
                            case 5:
                                radixSort();
                                radix++;
                                break;
                        }
                        }
                        repaint();
                    }
                });
                timer.start();
                String data = "Delay: "+ value+"ms";
                delay.setText(data);

              }
            });



        options = new JComboBox(sorts);


        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timer.start();
            }
        });



        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                }
            });



        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();

                list = initList(arraySize.getValue());
                sorted = initSorted(arraySize.getValue());
                radix=0;
                NUM_OF_ITEMS = arraySize.getValue();
                currentIndex = NUM_OF_ITEMS - 1;
                repaint();
                startButton.setEnabled(true);
            }
        });

        add(arrayLabel);
        add(arraySize);
        add(delay);
        add(time);
        add(options);
        add(startButton);
        add(pauseButton);
        add(resetButton);
    }

    public boolean isSortingDone() {
        return Arrays.equals(list,sorted);
    }

    public Integer[] initList(int NUM_OF_ITEMS) {
        Integer[] nums = new Integer[NUM_OF_ITEMS];
        for (int i = 1; i <= nums.length; i++) {
            nums[i - 1] = i;
        }
        Collections.shuffle(Arrays.asList(nums));
        return nums;
    }

    public Integer[] initSorted(int NUM_OF_ITEMS) 
    {
        Integer[] nums = new Integer[NUM_OF_ITEMS];
        for (int i = 1; i <= nums.length; i++) {
            nums[i - 1] = i;
        }
        return nums;
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


    public void drawItem(Graphics g, int item, int index) {
        int HOR_INC = DIM_W / NUM_OF_ITEMS;
        int height = item * VERT_INC;
        int y = HORIZON - height;
        int x = index * HOR_INC;
        g.fillRect(x, y, HOR_INC-1, height);
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