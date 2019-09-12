//递归全排列
import java.util.Arrays;
import java.util.Scanner; 

public class Lab2{
    public static void permute(int[] array,int start){  
        if(start==array.length){  // 输出
            System.out.println(Arrays.toString(array));
        }
        else
            for(int i=start;i<array.length;++i){
                swap(array,start,i);  //  交换元素
                permute(array,start+1);  //交换后，再进行全排列算法
                swap(array,start,i);  //还原成原来的数组，便于下一次的全排列
            }
    }
     
    private static void swap(int[] array,int s,int i){
        int t=array[s];
        array[s]=array[i];
        array[i]=t;
    }
    public static void main(String[] args){
        int n = 0;
        System.out.println("请输入将进行全排序的数组长度：");
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int[] array=new int[n];
        for(int i = 0;i<n;i++){
            array[i] = i+1;
        }
        permute(array,0);
    }
    
}