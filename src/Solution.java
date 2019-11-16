import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;

public class Solution {
    public static int[] swap(int a,int b){
        return new int[]{b,a};
    }

    public static void numbersAdd(int[] numbers,int n){
        int pointerOfNumbers=n-1;//指向数组末位
        while(pointerOfNumbers>=0){
            numbers[pointerOfNumbers]++;
            if(numbers[pointerOfNumbers]>=10){
                numbers[pointerOfNumbers]=0;
                pointerOfNumbers--;//进一
            }
            else{
                break;
            }
        }
    }

    public static void print1toN(int n){
        if(n<=0) return;
        String[] baseNum={"0","1","2","3","4","5","6","7","8","9"};//对应表
        int[] numbers=new int[n];//n位数字
        numbers[n-1]=1;
        boolean doneFlag=false;//判断输出完毕
        while(!doneFlag){
            String currentNumber="";
            for(int i=0;i<n;i++){//根据numbers拼接得到输出字符串
                if(!(numbers[i]==0&&currentNumber.equals(""))){
                    currentNumber=currentNumber+baseNum[numbers[i]];
                }
            }
            if(currentNumber.equals("")){
                doneFlag=true;//数字归零，输出完毕
            }
            else{
                System.out.println(currentNumber);
            }
            numbersAdd(numbers,n);//字符串数字自增
        }
    }

    public static int partition(int[] array,int left,int right){
        int firstPointer=left,secondPointer=left-1;
        Random ra=new Random();
        int i=ra.nextInt(right-left)+left;
        int[] swap=swap(array[i],array[right]);
        array[i]=swap[0];array[right]=swap[1];
        while(firstPointer<right){
            if(array[firstPointer]<array[right]){
                secondPointer++;
                if(firstPointer!=secondPointer){
                    swap=swap(array[firstPointer],array[secondPointer]);
                    array[firstPointer]=swap[0];array[secondPointer]=swap[1];
                }
            }
            firstPointer++;
        }
        array[++secondPointer]=array[right];
        return secondPointer;
    }

    public static void quicksort(int[] array,int left,int right){
        if(left>=right) return;
        int partitionIndex=partition(array,left,right);
        if(partitionIndex>left) quicksort(array,left,partitionIndex-1);
        if(partitionIndex<right) quicksort(array,partitionIndex+1,right);
    }

//    public static void Print1toN(StringBuilder str,int N){
//        if(N==0){
//            if(!(str.length()==0)) System.out.println(str);
//        }
//        else{
//            for(int i=0;i<10;i++){
//                if(!(str.length()==0&&i==0)) {
//                    str.append(i);
//                }
//                Print1toN(str,N-1);
//                if(!(str.length()==0)){
//                    str.deleteCharAt(str.length()-1);
//                }
//            }
//        }
//    }
//    public static void printNumber1toNBit(int N){
//        if(N<=0) {
//            return;
//        }
//        else{
//            StringBuilder str=new StringBuilder("");
//            Print1toN(str,N);
//        }
//    }

    public static void permu(char[] str,int pstr){
        if(str[pstr]=='z'){
            System.out.println(str);
        }
        else{
            for(int pcur=pstr;str[pcur]!='z';pcur++){
                char tmp=str[pcur];
                str[pcur]=str[pstr];
                str[pstr]=tmp;
                permu(str,pstr+1);
                tmp=str[pcur];
                str[pcur]=str[pstr];
                str[pstr]=tmp;
            }
        }
    }
    public static void permutation(char[] str){
        int pstr=0;
        if(str[pstr]=='z'){
            return;
        }
        permu(str,pstr);
    }

    public static int getAward(int n,int[] a){
        int[] aReality=new int[n];
        int sum=0;
        for(int i=0;i<n;i++){
            if(aReality[i]==0){
                aReality[i]=award(n,a,i,aReality);
                sum+=aReality[i];
            }
        }
        return sum;
    }

    public static int award(int n,int[] a,int i,int[] aReality){
        int before,next;
        if(i==0){
            before=n-1;
            next=1;
        }
        else if(i==n-1){
            before=i-1;
            next=0;
        }
        else{
            before=i-1;
            next=i+1;
        }

        if(a[i]<=a[before]&&a[i]<=a[next]){
            return 1;
        }
        else if(a[i]<=a[before]&&a[i]>a[next]){
            if(aReality[next]!=0) return aReality[next]+1;
            return award(n,a,next,aReality)+1;
        }
        else if(a[i]>a[before]&&a[i]<=a[next]){
            if(aReality[before]!=0) return aReality[before]+1;
            return award(n,a,before,aReality)+1;
        }
        else if(a[i]>a[before]&&a[i]>a[next]){
            int awBefore=0,awNext=0;
            if(aReality[before]!=0){
                awBefore=aReality[before];
            }
            else{
                awBefore=award(n,a,before,aReality);
            }
            if(aReality[next]!=0){
                awNext=aReality[next];
            }
            else{
                awNext=award(n,a,next,aReality);
            }
            if(awBefore>awNext){
                return awBefore+1;
            }
            else{
                return awNext+1;
            }
        }
        return 0;
    }

    public static boolean judgeMent(int n,int m,int[] a,double lestR){
        int sum=0;
        for(int i=0;i<n;i++){
            sum=sum+(int)(a[i]/lestR);
        }
        if(sum>=m){
            return true;
        }
        else {
            return false;
        }
    }

    public static String longestRope(int n,int m,int[] a){
        if(n<=0||m<=0||a.length==0){
            return "0.00";
        }
        double left=0,right=0,middle=0;
        boolean couldFlag=false;
        for(int i=0;i<n;i++){
            if(a[i]>right){
                right=a[i];
            }
        }

        while((left-right)>0.001||(left-right)<-0.001){
            middle=(left+right)/2;
            couldFlag=judgeMent(n,m,a,middle);
            if(couldFlag) {
                left=middle;
            }
            else{
                right=middle;
            }
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(middle);
    }

    public static String correct(String str){
        int length=str.length();
        int i=1,j=i+1;
        if(length<3){
            return str;
        }
        while(i<length){
            while(i<length&&(str.charAt(i)!=str.charAt(i-1))){i++;}
            if(i+1<length&&str.charAt(i)==str.charAt(i+1)){
                if(i+2>=length) str=str.substring(0,i+1);
                else str=str.substring(0,i+1)+str.substring(i+2,length);
            }
            else if(i+2<length&&i+1<length&&str.charAt(i+1)==str.charAt(i+2)){
                if(i+3>=length) str=str.substring(0,i+2);
                else str=str.substring(0,i+2)+str.substring(i+3,length);
            }
            else {
                i++;
            }
            length=str.length();
        }
        return str;
    }

    public static double Power(double base, int exponent) {
        //需要考虑底数与指数分别为正零负九种情况。容易忽略的点：1.整数次幂，不是正数次幂；2.底数为零，指数为负；底数为零，指数为零；3.比较base与零相等，要注意double类型的特殊性；4.可以通过递归降低时间复杂度；5.除2操作可以通过移位提高效率
        boolean isNegtive=false;
        double anwser=-1.0;
        if(exponent<0) {
            isNegtive=true;
            exponent=-1*exponent;
        }
        if(exponent==0) anwser=1.0;
        else if(exponent==1) anwser=base;
        else if(exponent==2) anwser=base*base;
        else if((exponent%2)!=0) anwser=Power(Power(base,exponent/2),2)*base;
        else anwser=Power(Power(base,exponent/2),2);
        if(!isNegtive) return anwser;
        else {
            if(anwser==0) return -1;
            return 1/anwser;
        }
    }

    public static void reOrderArray(int [] array) {
        int tmp;
        int i=0,j=array.length-1;
        while(i<j){
            while(array[i]%2!=0&&i<j) i++;
            while(array[j]%2==0&&i<j) j--;
            tmp=array[i];
            array[i]=array[j];
            array[j]=tmp;
            i++;j--;
        }
    }

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
        ListNode(){}
    }

    public ListNode FindKthToTail(ListNode head,int k) {
        if(head==null || k<=0) return null;
        int count=0;
        ListNode firster,seconder;
        firster=head;
        seconder=head;
        while(firster!=null){
            firster=firster.next;
            count++;
            if(count>k){
                seconder=seconder.next;
            }
        }
        if(count<k){
            return null;
        }
        return seconder;
    }

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    public boolean treeContained(TreeNode root1,TreeNode root2){
        if(root2==null)return true;
        else if((root1==null&&root2!=null)) return false;
        else{
            if(root1.val!=root2.val) return false;
            else{
                boolean flag=false;
                flag=treeContained(root1.left,root2.left);
                if(!flag) return false;
                flag=treeContained(root1.right,root2.right);
                return flag;
            }
        }
    }
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if(root2==null)return false;
        else if(root1==null&&root2!=null) return false;
        else{
            boolean flag=false;
            flag=treeContained(root1,root2);
            if(flag) return flag;
            flag=treeContained(root1.left,root2);
            if(flag) return flag;
            flag=treeContained(root1.right,root2);
            return flag;
        }
    }

    public static ArrayList<Integer> printMatrix(int [][] matrix) {
        if(matrix==null) return new ArrayList<>();
        int rowLength=matrix.length;
        if(rowLength==0) return new ArrayList<>();
        int colLength=matrix[0].length;
        if(colLength==0) return new ArrayList<>();
        int top=0,bottom=rowLength-1,left=0,right=colLength-1;
        ArrayList<Integer> answer=new ArrayList<>();
        while(top<=bottom&&left<=right){
            if(top<=bottom&&left<=right){
                //打印上边界
                for(int i=left;i<=right;i++){
                    answer.add(matrix[top][i]);
                }
                top++;
            }
            if(top<=bottom&&left<=right){
                //打印右边界
                for(int i=top;i<=bottom;i++){
                    answer.add(matrix[i][right]);
                }
                right--;
            }

            if(top<=bottom&&left<=right){
                //打印下边界
                for(int i=right;i>=left;i--){
                    answer.add(matrix[bottom][i]);
                }
                bottom--;
            }

            if(top<=bottom&&left<=right){
                //打印左边界
                for(int i=bottom;i>=top;i--){
                    answer.add(matrix[i][left]);
                }
                left++;
            }
        }
        return answer;
    }

//    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
//        if(root==null) return new ArrayList<>();
//        ArrayList<ArrayList<Integer>> answer=new ArrayList<>();
//        ArrayList<Integer> curPath=new ArrayList<>();
//        TreeNode[] mystack=new TreeNode[100000];
//        int top=-1;
//        int curSum=0;
//        TreeNode curNode=null;
//        boolean flag=true;//true代表向下,false代表向上
//        mystack[++top]=root;
//        while(top!=-1){
//
//            if(flag){
//                curNode=mystack[top];
//                curSum+=curNode.val;
//                curPath.add(curNode.val);
//                if(curSum>target) {
//                    top--;
//                    curPath.remove(curPath.size()-1);
//                    curSum-=curNode.val;
//                    continue;
//                }
//                if(curSum==target&&curNode.left==null&&curNode.right==null){
//                    answer.add(curPath);
//                    top--;
//                    curPath.remove(curPath.size()-1);
//                    curSum-=curNode.val;
//                    continue;
//                }
//                if(curNode.right!=null){
//                    mystack[++top]=curNode.right;
//                }
//                if(curNode.left!=null){
//                    mystack[++top]=curNode.left;
//                }
//                if(curNode.left==null&&curNode.right==null&&curSum<target){
//                    top--;
//                    curPath.remove(curPath.size()-1);
//                    curSum-=curNode.val;
//                }
//            }
//        }
//        return answer;
//    }

    public static void dfs(TreeNode root,int target,ArrayList<Integer> Path,ArrayList<ArrayList<Integer>> answer){
        if(root==null) return;
        target-=root.val;
        Path.add(root.val);
        if(target==0&&root.left==null&&root.right==null){
            answer.add(new ArrayList<>(Path));
        }
        if(root.left!=null) dfs(root.left,target,Path,answer);
        if(root.right!=null) dfs(root.right,target,Path,answer);
        Path.remove(Path.size()-1);
    }
    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if(root==null) return new ArrayList<>();
        ArrayList<ArrayList<Integer>> answer=new ArrayList<>();
        ArrayList<Integer> curAns=new ArrayList<>();
        dfs(root,target,curAns,answer);
        return answer;
    }

    public static class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    public static RandomListNode Clone(RandomListNode pHead)
    {
        if(pHead==null) return null;
        RandomListNode newNode=null;
        RandomListNode curNode=pHead;
        while(curNode!=null){
            newNode=new RandomListNode(curNode.label);
            newNode.next=curNode.next;
            curNode.next=newNode;
            curNode=newNode.next;
        }

        RandomListNode pFirst=pHead;
        RandomListNode pSecond=null;
        RandomListNode pNewHead=pHead.next;
        while(pFirst!=null){
            pSecond=pFirst.next;
            if(pFirst.random!=null){
                pSecond.random=pFirst.random.next;
            }
            pFirst.next=pSecond.next;
            if(pSecond.next!=null) pSecond.next=pFirst.next.next;
            pFirst=pFirst.next;
        }
        return pNewHead;
    }

    public static void getallString(char[] symbols,int length,int begin,ArrayList<String> answer){
        if(begin>=length) return;
        if(begin==length-1){
            StringBuilder ans=new StringBuilder();
            boolean existsFlag=false;
            for(char c:symbols) ans.append(c);
            String ansString=ans.toString();
            for(String s:answer) {
                if(s.equals(ansString)) {
                    existsFlag=true;break;
                }
            }
            if(!existsFlag) answer.add(ansString);
        }
        for(int i=begin;i<length;i++){
            char tmp=symbols[begin];
            symbols[begin]=symbols[i];
            symbols[i]=tmp;
            getallString(symbols,length,begin+1,answer);
            tmp=symbols[begin];
            symbols[begin]=symbols[i];
            symbols[i]=tmp;
        }
    }
    public static ArrayList<String> Permutation(String str) {
        ArrayList<String> answer=new ArrayList<>();
        if(str==null||str.length()==0) return answer;
        char[] symbols=str.toCharArray();
        getallString(symbols,symbols.length,0,answer);
        Collections.sort(answer);
        return answer;
    }

    public static int partition(int[] array,int begin,int end,int k){
        if(begin==end) return array[begin];
        int length=end-begin+1;
        Random ra=new Random();
        int position=Math.abs(ra.nextInt()%length)+begin;
        int key=array[position];
        int tmp=array[end];
        array[end]=array[position];
        array[position]=tmp;
        int i=begin,j=end;
        while(i<j){
            while(array[i]<=key&&i<j) i++;
            if(i<j) {
                array[j--]=array[i];
            }
            while(array[j]>key&&i<j) j--;
            if(i<j) {
                array[i++]=array[j];
            }
        }
        array[i]=key;
        if((i-begin+1)==k){
            return array[i];
        }
        else if((i-begin+1)>k){
            return partition(array,begin,i-1,k);
        }
        else{
            return partition(array,i+1,end,k-(i-begin+1));
        }
    }
    public static int MoreThanHalfNum_Solution(int [] array) {
        if(array==null||array.length==0) return -1;
        return partition(array,0,array.length-1,(array.length)/2);
    }

    public static void getLeastNumbers(int[] input,int begin,int end,int k){
        if(begin>=end||(end-begin+1)==k) return;
        Random ra=new Random();
        int keyPosition=Math.abs(ra.nextInt())%(end-begin+1)+begin;
        int key=input[keyPosition];
        input[keyPosition]=input[end];
        input[end]=key;
        int i=begin,j=end;
        int tmp=0;
        while(i<j){
            while(input[i]<=key&&i<j) i++;
            if(i<j){
                input[j--]=input[i];
            }
            while(input[j]>key&&i<j) j--;
            if(i<j){
                input[i++]=input[j];
            }
        }
        input[i]=key;
        if((i-begin+1)==k) return;
        if((i-begin+1)>k) getLeastNumbers(input,begin,i-1,k);
        if((i-begin+1)<k) getLeastNumbers(input,i+1,end,k-(i-begin+1));
    }
    public static ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> answer=new ArrayList<>();
        if(input==null||input.length==0||k>input.length) return answer;
        getLeastNumbers(input,0,input.length-1,k);
        for(int i=0;i<k;i++){
            answer.add(input[i]);
        }
        Collections.sort(answer);
        return answer;
    }

    public static int NumberOf1Between1AndN_Solution(int n){
        //异常输入单独处理
        if(n<=0) return 0;
        //临时变量
        int nTmp=n;//n的替身
        int numberLength=0;//n的位数
        int highestBit=0;//n的最高位
        //三个count，分别代表最高位、除最高位的其它位、第一段分别出现1的个数
        int highestBitCount=0,otherBitCount=0,firstPhaseCount=0;
        //计算临时变量
        while(nTmp!=0){
            highestBit=nTmp%10;//记录最高位
            nTmp/=10;
            numberLength++;//计算n的位数
        }
        //只有一位的数字单独处理
        if(numberLength==1) return 1;
        //最高位1个数计算
        if(highestBit==1){
            highestBitCount=n-highestBit*(int)(Math.pow(10,numberLength-1)+0.5)+1;
        }
        else if(highestBit>1){
            highestBitCount=(int)(Math.pow(10,numberLength-1)+0.5);
        }

        //其它位1个数计算
        if(numberLength>1){
            otherBitCount=highestBit*(numberLength-1)*(int)(Math.pow(10,numberLength-2)+0.5);
        }
        //第一段迭代
        firstPhaseCount=NumberOf1Between1AndN_Solution(n-highestBit*(int)(Math.pow(10,numberLength-1)+0.5));
        //最终结果
        return highestBitCount+otherBitCount+firstPhaseCount;
    }

    public static boolean connectCompare(String str1,String str2){
        if(str1==""||str2==""||str1==null||str2==null) return false;
        String con1=str1+str2;
        String con2=str2+str1;
        return con1.compareTo(con2)<=0?true:false;
    }

    public static String PrintMinNumber(int[] numbers) {
        int length=numbers.length;
        String answer="";
        String[] numbersString=new String[length];

        String strTmp="";
        for(int i=0;i<length;i++) {
            numbersString[i]=String.valueOf(numbers[i]);
        }
        for(int i=0;i<length;i++){
            for(int j=i+1;j<length;j++){
                if(!connectCompare(numbersString[i],numbersString[j])){
                    strTmp=numbersString[i];
                    numbersString[i]=numbersString[j];
                    numbersString[j]=strTmp;
                }
            }
        }
        for(int i=0;i<length;i++){
            answer+=numbersString[i];
        }
        return answer;
    }

    public static int minNumber(int a,int b){
        return a<=b?a:b;
    }
    public static int GetUglyNumber_Solution(int index) {
        if(index<=0) return 0;
        //初始化丑数存储list
        index-=1;
        ArrayList<Integer> UglyNumbers=new ArrayList<>();
        UglyNumbers.add(1);
        UglyNumbers.add(2);
        UglyNumbers.add(3);
        UglyNumbers.add(4);
        UglyNumbers.add(5);
        int length=UglyNumbers.size();
        //初始化几个参数
        int M2index=0,M3index=0,M5index=0;
        int M2=0,M3=0,M5=0,maxUglyNum=5,nextUglyNum=0;
        //begin
        if(index<5) return UglyNumbers.get(index);

        while(length-1<index){
            //乘2的丑数候选处理
            while(M2index<length&&M2<=maxUglyNum){
                M2=2*UglyNumbers.get(M2index++);
            }
            if(M2index<length) M2index--;
            else return -1;//异常返回
            //乘3的丑数候选处理
            while(M3index<length&&M3<=maxUglyNum){
                M3=3*UglyNumbers.get(M3index++);
            }
            if(M3index<length) M3index--;
            else return -1;//异常返回
            //乘5的丑数候选处理
            while(M5index<length&&M5<=maxUglyNum){
                M5=5*UglyNumbers.get(M5index++);
            }
            if(M5index<length) M5index--;
            else return -1;//异常返回
            //获得下一个丑数
            nextUglyNum=minNumber(minNumber(M2,M3),M5);
            //三个指针分别移动
            if(nextUglyNum==M2){
                M2index++;
            }
            if(nextUglyNum==M3){
                M3index++;
            }
            if(nextUglyNum==M5){
                M5index++;
            }
            //更新list
            UglyNumbers.add(nextUglyNum);
            length=UglyNumbers.size();
            maxUglyNum=nextUglyNum;
            M2=0;M3=0;M5=0;
        }

        return nextUglyNum;
    }

    public static int FirstNotRepeatingChar(String str) {
        if(str==null) return -1;

        boolean[] count=new boolean[58];
        int[] position=new int[58];
        char[] characters=str.toCharArray();

        int index=0;//用于指向字符在数组中的位置
        int length=characters.length;
        for(int i=0;i<length;i++){
            index=characters[i]-65;
            //若字符首次出现，记录其位置
            if(!count[index]&&position[index]>=0) {
                count[index]=true;
                position[index]=i;
            }
            //不是首次出现，标记
            else  if(count[index]) {
                count[index]=false;
                position[index]=-1;
            }
        }

        //从记录中寻找答案
        int answer=length;
        for(int i=0;i<58;i++){
            if(count[i]) answer=answer<position[i]?answer:position[i];
        }
        if(answer!=length) return answer;
        else return -1;
    }

    /**
     * start1小于start2, end1小于end2, 两段没有交集
     * @param array
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static int mergeCount(int[] array,int start1,int end1,int start2,int end2){
        if(array==null||array.length==0||end1<start1||end2<start2) return 0;
        //变量区
        int totalLength=(end1-start1+1)+(end2-start2+1);
        int[] bufStore=new int[totalLength];
        int pointer1=end1,pointer2=end2,pointer3=totalLength-1;
        int count=0;

        //归并排序
        while(pointer1>=start1&&pointer2>=start2){
            while(pointer1>=start1&&array[pointer1]>array[pointer2]){
                bufStore[pointer3--]=array[pointer1--];
                //array[pointer1]>array[pointer2]，统计逆序数量
                count+=(pointer2-start2+1);
            }
            if(pointer1<start1) break;
            while(pointer2>=start2&&array[pointer1]<=array[pointer2]){
                bufStore[pointer3--]=array[pointer2--];
            }
            if(pointer2<start2) break;
        }

        if(pointer1>=start1) {
            for(int i=pointer1;i>=start1;i--){
                bufStore[pointer3--]=array[i];
            }
        }
        if(pointer2>=start2) {
            for(int i=pointer2;i>=start2;i--){
                bufStore[pointer3--]=array[i];
            }
        }

        pointer1=start1;pointer2=start2;
        for(int i=0;i<totalLength;i++){
            if(i<(end1-start1+1)) array[pointer1++]=bufStore[i];
            if(i>=(end1-start1+1)) array[pointer2++]=bufStore[i];
        }
        return count;
    }
    public static int mergeSortCount(int [] array,int start,int end) {
        if(array==null||array.length==0||start>=end) return 0;
        int middle=(start+end)/2;
        int leftPartCount=mergeSortCount(array,start,middle)%1000000007;
        int rightPartCount=mergeSortCount(array,middle+1,end)%1000000007;
        int combineCount=mergeCount(array,start,middle,middle+1,end)%1000000007;
        return (leftPartCount+rightPartCount+combineCount)%1000000007;
    }

    public static int InversePairs(int [] array) {
        if(array==null||array.length==0) return 0;
        return mergeSortCount(array,0,array.length-1)%1000000007;
    }

    public static ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if(pHead1==null||pHead2==null) return null;
        //变量区
        int length1=0,length2=0,deltaLength=0;
        ListNode pointer1=pHead1,pointer2=pHead2;

        while(pointer1!=null) {
            length1++;
            pointer1=pointer1.next;
        }
        while(pointer2!=null) {
            length2++;
            pointer2=pointer2.next;
        }
        deltaLength=Math.abs(length1-length2);

        pointer1=pHead1;
        pointer2=pHead2;
        if(length1>length2){
            while(deltaLength-->0){
                pointer1=pointer1.next;
            }
        }
        if(length2>length1){
            while(deltaLength-->0){
                pointer2=pointer2.next;
            }
        }

        while(pointer1!=null&&pointer2!=null){
            if(pointer1==pointer2){
                return pointer1;
            }
            pointer1=pointer1.next;
            pointer2=pointer2.next;
        }

        return null;
    }

    public static int getIndexLessThanK(int[] array,int k){
        if(array==null||array.length==0||k<array[0]||k>array[array.length-1]) return -1;
        //变量定义
        int length=array.length;
        int left=0,right=length-1,middle=0;

        while(left<=right){
            middle=(left+right)/2;
            if(array[middle]<k) left=middle+1;
            if(array[middle]>=k) right=middle-1;
        }
        return right;
    }

    public static int getIndexlargerThanK(int[] array,int k){
        if(array==null||array.length==0||k<array[0]||k>array[array.length-1]) return -1;
        //变量定义
        int length=array.length;
        int left=0,right=length-1,middle=0;

        while(left<=right){
            middle=(left+right)/2;
            if(array[middle]<=k) left=middle+1;
            if(array[middle]>k) right=middle-1;
        }
        return left;
    }

    public static int GetNumberOfK(int [] array , int k) {
        int leftBound=getIndexLessThanK(array,k);
        int rightBound=getIndexlargerThanK(array,k);
        if((rightBound-leftBound)<2) return 0;
        return (rightBound-leftBound)-1;
    }

    /**
     * 关键变量命名为key，通过key将array分为两个数组
     * flag记录key中某一个不为零的位置
     * @param array
     * @param num1
     * @param num2
     */
    public static void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        if(array==null||array.length%2!=0) return;
        int key=0;
        int flag=1;

        for(int elem: array){
            key^=elem;
        }

        while((key&flag)==0){
            flag=flag<<1;
        }

        for(int elem:array){
            if ((elem&flag)!=0) num1[0]^=elem;
            else num2[0]^=elem;
        }
    }

    public static ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> answer=new ArrayList<>();
        ArrayList<Integer> currentList=new ArrayList<>();

        if(sum<=1) return answer;

        int left=1,right=2;
        int currentSum=0;
        while(left<=sum/2){
            currentList=new ArrayList<>();
            currentSum=(right*(right+1)/2)-((left-1)*left/2);
            if(currentSum<sum) {
                right++;
            }
            else if(currentSum==sum){
                for(int i=left;i<=right;i++){
                    currentList.add(i);
                }
                answer.add(currentList);
                right++;
            }
            else {
                left++;
            }
        }
        return answer;
    }

    public static ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> answer=new ArrayList<>();
        if(array==null||array.length<=1) return answer;

        int length=array.length;
        int left=0,right=length-1;
        int currentSum=0;
        while(left<right){
            currentSum=array[left]+array[right];
            if(currentSum==sum){
                answer.add(array[left]);
                answer.add(array[right]);
                break;
            }
            else if(currentSum<sum){
                left++;
            }
            else{
                right--;
            }
        }
        return answer;
    }

    public static String LeftRotateString(String str,int n) {
        if(str==null||str.length()==0||n<=0||n>=str.length()) return str;
        return str.substring(n,str.length())+str.substring(0,n);
    }

    public static String ReverseSentence(String str) {
        if(str==null||str.length()==0) return str;
        String answer="";
        StringBuffer sentence=new StringBuffer();
        String[] words=str.split(" ");

        if(words==null||words.length==0) {
            return str;
        }
        for(String word:words){
            sentence.append(new StringBuffer(word).reverse());
            sentence.append(" ");
        }
        sentence.deleteCharAt(sentence.length()-1);
        answer=sentence.reverse().toString();
        return answer;
    }

    public static boolean isContinuous(int [] numbers) {
        if(numbers==null||numbers.length==0) return false;
        //变量定义
        boolean answer=false;
        boolean[] arrayForSort=new boolean[14];
        int zeroCount=0;

        //借助哈希表排序
        Arrays.fill(arrayForSort,false);
        for(int elem:numbers){
            if(elem<0||elem>13) {
                answer=false;
                return answer;
            }
            else if(elem==0){
                zeroCount++;
            }
            else {
                if(arrayForSort[elem]){
                    answer=false;
                    return answer;
                }
                else {
                    arrayForSort[elem]=true;
                }
            }
        }

        //检查是否为顺子
        int index=0;
        //定位到顺子
        while(index<14&&!arrayForSort[index++]);
        //填充空白
        for(int i=index;i<14&&(zeroCount>0);i++){
            if(!arrayForSort[i]){
                arrayForSort[i]=true;
                zeroCount--;
            }
        }
        //后面填满，向前填充
        for(int i=index-2;i>=0&&(zeroCount>0);i--){
            arrayForSort[i]=true;
            zeroCount--;
        }
        //还有0剩余，说明出现牌数量大于13
        if(zeroCount>0) {
            answer=false;
            return answer;
        }
        //检查顺子之后是否还有不连续的牌
        while(index<14&&arrayForSort[index++]);
        while(index<14&&!arrayForSort[index++]);
        if(index>=14) answer=true;
        return answer;
    }

    public static int LastRemaining_Solution(int n, int m) {
        if(n<1||m<1) return 0;

        int answer=0;
        for(int i=2;i<=n;i++){
            answer=(answer+m)%i;
        }
        return answer;
    }

    public static int Sum_Solution(int n) {
        if(n<1) return -1;
        return ((int)Math.pow(n,2)+n)>>1;
    }

    public static int Add(int num1,int num2) {
        int num1Tmp=num1,num2Tmp=num2;
        int valueAfterXOR=num1Tmp ^ num2Tmp;
        int valueAfterAND=(num1Tmp & num2Tmp) << 1;
        while(valueAfterAND!=0){
            num1Tmp=valueAfterXOR;
            num2Tmp=valueAfterAND;
            valueAfterXOR=num1Tmp ^ num2Tmp;
            valueAfterAND=(num1Tmp & num2Tmp) << 1;
        }
        return valueAfterXOR;
    }

    public static int StrToInt(String str) {
        if(str==null||str.equals("")) return 0;

        //变量定义
        long answer=0;
        boolean answerFlag=true;
        char[] numbers=str.toCharArray();
        int length=numbers.length;
        long power=1;

        if(numbers[0]=='-') answerFlag=false;
        if(numbers[0]=='+'||numbers[0]=='-') numbers[0]='0';

        for(int i=length-1;i>=0;i--){
            if(numbers[i]<48||numbers[i]>57) return 0;
            answer+=(numbers[i]-48)*power;
            power*=10;
        }

        answer=answerFlag?answer:answer*(-1);
        if(answer>0x7fffffff||answer<0x80000000) return 0;
        return (int)answer;
    }

    public static boolean duplicate(int numbers[],int length,int [] duplication) {
        if(numbers==null||numbers.length==0||length<=0) return false;

        //临时变量
        int valForExchange=0;

        for(int i=0;i<length;i++){
            if(numbers[i]!=i){
                if(numbers[numbers[i]]==numbers[i]) {
                    duplication[0]=numbers[i];
                    return true;
                }
                valForExchange=numbers[i];
                numbers[i]=numbers[valForExchange];
                numbers[valForExchange]=valForExchange;
                i--;
            }
        }
        return false;
    }

    public static int[] multiply(int[] A) {
        if(A==null||A.length==0) return A;

        //变量定义
        int length=A.length;
        int[] B=new int[length];
        int[] firstPart=new int[length];
        int[] secondPart=new int[length];

        firstPart[0]=1;
        secondPart[length-1]=1;
        for(int i=1;i<length;i++){
            firstPart[i]=firstPart[i-1]*A[i-1];
        }
        for(int i=length-2;i>=0;i--){
            secondPart[i]=secondPart[i+1]*A[i+1];
        }
        for(int i=0;i<length;i++){
            B[i]=firstPart[i]*secondPart[i];
        }
        return B;
    }

    public static boolean matchCore(char[] str,int strStart, char[] pattern, int patternStart){
        //字符串与模式的剩余未匹配长度
        int strNotMatchLength=str.length-strStart;
        int patternNotMatchLength=pattern.length-patternStart;

        //字符串与模式都遍历结束
        if(strNotMatchLength<=0&&patternNotMatchLength<=0) return true;
        //字符串结束，模式有剩余，仅当模式剩余的是若干个"c*"才可能匹配
        if(strNotMatchLength<=0&&patternNotMatchLength>0){
            if(patternNotMatchLength>1&&pattern[patternStart+1]=='*'){
                return  matchCore(str,strStart,pattern,patternStart+2);
            }
            return false;
        }
        //字符串有剩余，模式无剩余
        if(strNotMatchLength>0&&patternNotMatchLength<=0) return false;
        //字符串与模式都有剩余，
        //当前字符与模式当前字符匹配，后面是'*'分三种情况，不是'*'一种情况
        if(str[strStart]==pattern[patternStart]||pattern[patternStart]=='.'){
            if(patternStart+1<pattern.length&&pattern[patternStart+1]=='*'){
                return matchCore(str,strStart,pattern,patternStart+2)||matchCore(str,strStart+1,pattern,patternStart)|| matchCore(str,strStart+1,pattern,patternStart+2);
            }
            return  matchCore(str,strStart+1,pattern,patternStart+1);
        }
        //当前字符与模式当前字符不匹配，后面是'*'一种情况
        else if(patternStart+1<pattern.length&&pattern[patternStart+1]=='*'){
            return  matchCore(str,strStart,pattern,patternStart+2);
        }
        return false;
    }

    public static boolean match(char[] str, char[] pattern){
        if(str==null||pattern==null) return false;
        return matchCore(str,0,pattern,0);
    }

    public static int max(int a,int b){
        return a>b?a:b;
    }

    /**
     * 基本思路：根据四个符号将字符串划分为若干段，然后在每一段判断是否只有数字
     * @param str
     * @return
     */
    public static boolean isNumeric(char[] str) {
        if(str==null||str.length==0) return false;

        //变量定义
        int positionOfBaseFlag=-1,positionOfExponentFlag=-1,positionOfPoint=-1,positionOfEore=-1;
        int strLength=str.length;
        //找到四个特殊符号的位置
        for(int i=0;i<strLength;i++){
            if(str[i]=='+'||str[i]=='-'){
                if(positionOfBaseFlag==-1&&positionOfEore==-1) {
                    positionOfBaseFlag = i;
                }
                else if(positionOfExponentFlag==-1){
                    positionOfExponentFlag=i;
                }
                else {
                    return false;
                }
            }
            if(str[i]=='.'){
                if(positionOfPoint==-1) {
                    positionOfPoint = i;
                }
                else {
                    return false;
                }
            }
            if(str[i]=='e'||str[i]=='E'){
                if(positionOfEore==-1){
                    positionOfEore=i;
                }
                else{
                    return false;
                }
            }
        }
        //判断四个符号的位置是否有误，如果符号存在，那么：
        //基数的符号位在0的位置
        if(positionOfBaseFlag!=-1&&positionOfBaseFlag!=0) return false;
        //任意一个符号都不应该在字符串末尾
        if(positionOfBaseFlag==strLength-1||positionOfPoint==strLength-1||positionOfEore==strLength-1||positionOfExponentFlag==strLength-1) return false;
        //小数点的位置比e的位置小，并且小数点与e之间至少包含一个数字符号
        if(positionOfPoint!=-1&&positionOfEore!=-1&&positionOfPoint>positionOfEore) return false;
        if(positionOfPoint!=-1&&positionOfEore!=-1&&positionOfEore-positionOfPoint<1) return false;
        //e在指数的符号位之前,并且二者之间不应该有符号
        if(positionOfExponentFlag!=-1&&positionOfEore==-1) return false;
        if(positionOfExponentFlag!=-1&&positionOfEore!=-1&&positionOfEore>positionOfExponentFlag) return false;
        if(positionOfExponentFlag!=-1&&positionOfEore!=-1&&(positionOfExponentFlag-positionOfEore)>1) return false;

        //分段检查数字
        positionOfBaseFlag=0;
        for(int i=positionOfBaseFlag+1;i<positionOfPoint;i++){
            if(str[i]<47||str[i]>57) return false;
        }
        for(int i=positionOfPoint>positionOfBaseFlag?positionOfPoint+1:positionOfBaseFlag+1;i<positionOfEore;i++){
            if(str[i]<47||str[i]>57) return false;
        }
        for(int i=max(max(positionOfBaseFlag,positionOfPoint),max(positionOfEore,positionOfExponentFlag))+1;i<strLength;i++){
            if(str[i]<47||str[i]>57) return false;
        }
        return true;
    }

    public static HashMap<Character,Integer> mapForUnrepeatableChar=new HashMap<>();
    public static int charCounter=0;

    //Insert one char from stringstream
    public static void Insert(char ch)
    {
        if(mapForUnrepeatableChar.containsKey(ch)){
            mapForUnrepeatableChar.put(ch,-1);
        }
        else{
            mapForUnrepeatableChar.put(ch,charCounter);
        }
        charCounter++;
    }
    //return the first appearence once char in current stringstream
    public static char FirstAppearingOnce()
    {
        char answer='#';
        int minIndex=charCounter+1,index=-1;
        if(mapForUnrepeatableChar.isEmpty()) return answer;
        Iterator iterator=mapForUnrepeatableChar.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry elem=(Map.Entry)iterator.next();
            index=(int)elem.getValue();
            if(index!=-1){
                if(index<minIndex){
                    minIndex=index;
                    answer=(char)elem.getKey();
                }
            }
        }
        return answer;
    }

    public static ListNode EntryNodeOfLoop(ListNode pHead)
    {
        if(pHead==null) return pHead;

        ListNode p1=pHead,p2=pHead.next;
        int loopLength=1;//环中至少有一个节点

        while(p1!=null&&p2!=null&&p1!=p2){
            if(p1.next!=null) p1=p1.next;
            if(p2.next!=null&&p2.next.next!=null) p2=p2.next.next;
        }
        if(p1==p2&&p1!=null) {
            //计算环的长度
            p2=p2.next;
            while(p2!=p1) {
                loopLength++;
                p2=p2.next;
            }
            //追逐
            p1=pHead;p2=pHead;
            for(int i=0;i<loopLength;i++){
                p1=p1.next;
            }
            while(p1!=p2){
                p1=p1.next;
                p2=p2.next;
            }
            //相遇
            if(p1==p2){
                return p1;
            }
        }
        return null;
    }

    public ListNode deleteDuplication(ListNode pHead)
    {
        if(pHead==null) return pHead;

        ListNode pNewHead=new ListNode(-1);
        pNewHead.next=pHead;
        ListNode p1=pNewHead,p2=pHead,p3=pHead.next;

        while(p3!=null){
            while(p2.val==p3.val&&p3.next!=null){
                p3=p3.next;
            }
            if(p3.next==null){
                if(p2.val==p3.val){
                    p1.next=null;
                    p3=p3.next;
                }
            }
            if(p3==null) break;
            if(p2.val!=p3.val){
                if(p3!=p2.next){
                    p1.next=p3;
                    p2=p3;
                    p3=p3.next;
                }
                else{
                    p1=p1.next;
                    p2=p2.next;
                    p3=p3.next;
                }
            }
        }
        return pNewHead.next;
    }

    public class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    public static TreeLinkNode findNextOfRightChild(TreeLinkNode root){
        if(root==null) return null;
        while(root.left!=null){
            root=root.left;
        }
        return root;
    }

    public static TreeLinkNode GetNext(TreeLinkNode pNode)
    {
        if(pNode==null) return null;
        if(pNode.right!=null){
            pNode=pNode.right;
            while(pNode.left!=null){
                pNode=pNode.left;
            }
            return pNode;
        }
        if(pNode.next==null) return null;
        if(pNode.next.left==pNode) return pNode.next;
        if(pNode.next.right==pNode){
            pNode=pNode.next;
            while(pNode.next!=null&&pNode.next.right==pNode){
                pNode=pNode.next;
            }
            return pNode.next;
        }
        return null;
    }

    public static ArrayList<Integer> midTrace(TreeNode root){
        ArrayList<Integer> sequence=new ArrayList<>();
        if(root==null) return sequence;
        sequence.addAll(midTrace(root.left));
        sequence.add(root.val);
        sequence.addAll(midTrace(root.right));
        return sequence;
    }

    public static boolean treeEqual(TreeNode left,TreeNode right){
        if(left==null&&right==null) return true;
        if(left==null||right==null) return false;
        if(left.val==right.val) return treeEqual(left.left,right.right)&treeEqual(left.right,right.left);
        return false;
    }

    public static boolean isSymmetrical(TreeNode pRoot)
    {
        if(pRoot==null) return true;
        return treeEqual(pRoot.left,pRoot.right);
    }

    public static ArrayList<ArrayList<Integer> > stackPrint(TreeNode pRoot) {
        if(pRoot==null) return new ArrayList<>();
        ArrayList<ArrayList<Integer>> answer=new ArrayList<>();
        Stack<TreeNode> stack1=new Stack<>();
        Stack<TreeNode> stack2=new Stack<>();

        Stack<TreeNode> stackOut=null;
        Stack<TreeNode> stackIn=null;
        boolean dirctionFlag=false;//false代表下一轮从右向左打印
        //tmp val
        TreeNode node=null;

        stack1.push(pRoot);
        while(!(stack1.empty()&&stack2.empty())){
            ArrayList<Integer> currentFloor=new ArrayList<>();
            stackOut=stack1.empty()?stack2:stack1;
            stackIn=stack1.empty()?stack1:stack2;
            while(!stackOut.empty()){
                node=stackOut.pop();
                currentFloor.add(node.val);
                if(dirctionFlag) {
                    if(node.right!=null) stackIn.push(node.right);
                    if(node.left!=null) stackIn.push(node.left);
                }
                else{
                    if(node.left!=null) stackIn.push(node.left);
                    if(node.right!=null) stackIn.push(node.right);
                }
            }
            answer.add(currentFloor);
            dirctionFlag=!dirctionFlag;
        }
        return answer;
    }

    public static ArrayList<ArrayList<Integer> > queuePrint(TreeNode pRoot) {
        if(pRoot==null) return new ArrayList<>();
        ArrayList<ArrayList<Integer>> answer=new ArrayList<>();
        LinkedList<TreeNode> queue=new LinkedList<>();

        //临时变量
        TreeNode node=null;
        ArrayList<Integer> currentFloor=null;

        queue.add(null);
        queue.add(pRoot);
        while(!queue.isEmpty()){
            node=queue.poll();
            if(node!=null){
                if(node.left!=null) queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
                currentFloor.add(node.val);
            }
            else{
                if(currentFloor!=null) {
                    if(currentFloor.size()!=0) {
                        answer.add(currentFloor);
                    }
                    else break;
                }
                queue.add(null);
                currentFloor=new ArrayList<>();
            }
        }
        return answer;
    }

    public static String Serialize(TreeNode root) {
        if(root==null) return "#";
        String answerList=""+root.val;
        String leftList=Serialize(root.left);
        String rightList=Serialize(root.right);
        if(answerList.charAt(answerList.length()-1)!='!'){
            answerList=answerList+"!";
        }
        answerList=answerList+leftList;
        if(answerList.charAt(answerList.length()-1)!='!'){
            answerList=answerList+"!";
        }
        answerList=answerList+rightList;
        if(answerList.charAt(answerList.length()-1)!='!'){
            answerList=answerList+"!";
        }
        return answerList;
    }

    public static TreeNode DeserializeCore(StringBuffer tmpStr) {
        if(tmpStr==null||tmpStr.length()==0) {
            return null;
        }

        String node=tmpStr.substring(0,tmpStr.indexOf("!"));

        TreeNode root=null;
        if(!node.equals("#")) {
            root=new TreeNode(Integer.parseInt(node));
        }

        tmpStr.delete(0,tmpStr.indexOf("!")+1);
        if(root!=null) {
            root.left=DeserializeCore(tmpStr);
            root.right=DeserializeCore(tmpStr);
        }

        return root;
    }

    public static TreeNode Deserialize(String str) {
        if(str==null||str.length()==0||str.equals("#")) return null;
        StringBuffer tmpStr=new StringBuffer(str);
        return DeserializeCore(tmpStr);
    }

    public static ArrayList<TreeNode> midTraceForKthNode(TreeNode pRoot,int k){
        ArrayList<TreeNode> sortedList=new ArrayList<>();
        if(pRoot==null||sortedList.size()==k) return sortedList;
        sortedList.addAll(midTraceForKthNode(pRoot.left,k));
        if(sortedList.size()==k) return sortedList;
        sortedList.add(pRoot);
        if(sortedList.size()==k) return sortedList;
        sortedList.addAll(midTraceForKthNode(pRoot.right,k));
        return sortedList;
    }

    public static TreeNode KthNode(TreeNode pRoot, int k)
    {
        if(pRoot==null||k<=0) return null;
        ArrayList<TreeNode> sortedList=midTraceForKthNode(pRoot,k);
        if(sortedList.size()<k) return null;
        else return sortedList.get(k-1);
    }

    public static PriorityQueue<Integer> minHeap=new PriorityQueue<>();
    public static PriorityQueue<Integer> maxHeap=new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1;
        }
    });
    public static void Insert(Integer num) {
        if(minHeap.size()==0&&maxHeap.size()==0){
            minHeap.add(num);
        }
        else if(minHeap.size()!=0&&maxHeap.size()==0){
            if(num>minHeap.peek()){
                maxHeap.add(minHeap.poll());
                minHeap.add(num);
            }
            else{
                maxHeap.add(num);
            }
        }
        else{
            if(minHeap.size()>maxHeap.size()){
                maxHeap.add(minHeap.poll());
            }
            if(num>=maxHeap.peek()){
                minHeap.add(num);
            }
            else if(num<maxHeap.peek()){
                minHeap.add(maxHeap.poll());
                maxHeap.add(num);
            }
        }
    }

    public static Double GetMedian() {
        if(minHeap.size()==0&&maxHeap.size()==0){
            return -1.0;
        }
        else if(minHeap.size()!=0&&maxHeap.size()==0){
            return (double)minHeap.peek();
        }
        else{
            if(minHeap.size()==maxHeap.size()){
                return (double) (minHeap.peek()+maxHeap.peek())/2;
            }
            return (double) minHeap.peek();
        }
    }

    public static ArrayList<Integer> maxInWindows(int [] num, int size)
    {
        ArrayList<Integer> answer=new ArrayList<>();
        if(num==null||num.length==0||size>num.length||size<=0) return answer;

        LinkedList<Integer> queue=new LinkedList<>();
        for(int i=0,length=num.length;i<length;i++){
            if(queue.isEmpty()) {
                queue.addFirst(0);
            }
            else{
                while(!queue.isEmpty()&&(i-queue.peek())>=size){
                    queue.removeFirst();
                }
                while(!queue.isEmpty()&&num[i]>=num[queue.getLast()]){
                    queue.removeLast();
                }
                queue.addLast(i);
            }

            if(i+1>=size){
                answer.add(num[queue.peek()]);
            }
        }
        return answer;
    }

    public static boolean hasPathCore(char[] matrix, int rows, int cols, int row, int col, char[] str, int index, boolean[][] visited){
        if(index>=str.length) return true;
        if(row<0||row>=rows||col<0||col>=cols) return false;

        boolean answer=false;
        if(!visited[row][col]&&str[index]==matrix[row*cols+col]) {
            visited[row][col]=true;
            answer=hasPathCore(matrix,rows,cols,row+1,col,str,index+1,visited)||
                   hasPathCore(matrix,rows,cols,row-1,col,str,index+1,visited)||
                   hasPathCore(matrix,rows,cols,row,col-1,str,index+1,visited)||
                   hasPathCore(matrix,rows,cols,row,col+1,str,index+1,visited);
            visited[row][col]=false;
        }
        return answer;
    }

    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        boolean answer=false;
        if(matrix==null||matrix.length==0||str==null||str.length==0) return answer;

        boolean[][] visited=new boolean[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                answer=hasPathCore(matrix,rows,cols,i,j,str,0,visited);
                if(answer) return answer;
            }
        }
        return answer;
    }


    public static boolean checkPosition(int threshold, int row,int col){
        int sum=0;
        int tmpRow=row,tmpCol=col;
        while(tmpRow!=0){
            sum+=tmpRow%10;
            tmpRow/=10;
        }
        while(tmpCol!=0){
            sum+=tmpCol%10;
            tmpCol/=10;
        }
        return sum<=threshold?true:false;
    }

    public static int movingCountCore(int threshold, int rows, int cols, int row, int col, boolean[][] visited)
    {
        if(row<0||row>=rows||col<0||col>=cols) return 0;

        if(!visited[row][col]&&checkPosition(threshold,row,col)){
            visited[row][col]=true;
            return 1+movingCountCore(threshold,rows,cols,row-1,col,visited)+
                     movingCountCore(threshold,rows,cols,row+1,col,visited)+
                     movingCountCore(threshold,rows,cols,row,col-1,visited)+
                     movingCountCore(threshold,rows,cols,row,col+1,visited);
        }
        return 0;
    }

    public static int movingCount(int threshold, int rows, int cols)
    {
        if(threshold<0||rows<=0||cols<=0) return 0;

        boolean[][] visited=new boolean[rows][cols];
        return movingCountCore(threshold,rows,cols,0,0,visited);
    }

    private static void change(String s){
        s=null;
    }

    private static String getAaa(){
        return "aaa";
    }

    public static int fun(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        if (n == 0 || m == 0)
            return 0;
        int[][] c = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i] == b[j])
                    c[i + 1][j + 1] = c[i][j] + 1;
                else
                    c[i + 1][j + 1] = c[i][j + 1] > c[i + 1][j] ? c[i][j + 1] : c[i + 1][j];
            }
        }
        return c[n][m];
    }

    class MiNode{
        char val=0;
        MiNode left=null,right=null;

        MiNode(char val){
            this.val=val;
        }
    }

    public StringBuffer midTrace(MiNode root){
        StringBuffer answer=new StringBuffer();
        if(root==null) return answer;

        answer.append(midTrace(root.left));
        answer.append(root.val);
        answer.append(midTrace(root.right));

        return answer;
    }

    public String midTraceByStack(String str){
        String answer="";
        if(str==null||str.length()==0) return answer;

        char[] chars=str.toCharArray();
        Stack<MiNode> stack=new Stack<>();

        //临时变量
        int length=chars.length;
        char currentValue=chars[0];
        MiNode node=new MiNode(currentValue);
        MiNode root=node;
        boolean dircFlag=true;

        for(int i=1;i<length;i++){
            currentValue=chars[i];
            if(currentValue=='('){
                stack.push(node);
                dircFlag=true;
            }
            else if(currentValue==')'){
                dircFlag=false;
                stack.pop();
            }
            else if(currentValue==','){
                dircFlag=false;
            }
            else{
                node=new MiNode(currentValue);
                if(dircFlag){
                    stack.peek().left=node;
                }
                else{
                    stack.peek().right=node;
                }
            }
        }
        answer=midTrace(root).toString();
        return answer;
    }

    public static String reverse(String str){
        StringBuffer answer=new StringBuffer();
        if(str==null||str.length()==0) return "";

        char[] chars=str.toCharArray();
        int length=chars.length;
        Stack<Character> stack=new Stack<>();
        Queue<Character> queue=new LinkedList<>();

        //临时变量
        char currentValue;

        for(int i=0;i<length;i++){
            if(chars[i]==')'){
                while(!stack.empty()&&stack.peek()!='('){
                    currentValue=stack.pop();
                    ((LinkedList<Character>) queue).addLast(currentValue);
                }
                if(!stack.empty()){
                    stack.pop();
                }
                while(!queue.isEmpty()){
                    stack.push(((LinkedList<Character>) queue).pollFirst());
                }
            }
            else{
                stack.push(chars[i]);
            }
        }

        for(char elem:stack){
            answer.append(elem);
        }

        return answer.toString();
    }

    public static void main(String[] args){
        print1toN(3);
    }
}