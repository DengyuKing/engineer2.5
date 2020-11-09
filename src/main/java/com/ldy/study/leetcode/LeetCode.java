package com.ldy.study.leetcode;


import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.*;

public class LeetCode {

    public static void main(String[] args) {

        int [] nums ={-1, 0, 1, 2, -1, -4};
      System.out.println(threeSum(nums));


    }



    public static List<List<Integer>> threeSum(int[] nums) {

        if (nums == null || nums.length<3){
            return new ArrayList<List<Integer>>();
        }
        List<List<Integer>> list = new ArrayList();

        Arrays.sort(nums);
        Map<String,List<Integer>> map = new HashMap();
        for (int i =0;i<nums.length-2;i++){
            int l = i+1,r=nums.length-1;

            while (l<r){
                int res = nums[i] + nums[r] + nums[l];
                if (res == 0){

                    List<Integer> li = new ArrayList();
                    li.add(nums[i]);
                    li.add(nums[l]);
                    li.add(nums[r]);
                    String key = String.format("%d_%d_%d",nums[i],nums[l],nums[r]);
                    if (!map.containsKey(key)){
                        map.put(key,li);
                    }
                    l++;

                    //去重
                }else if (res<0){
                    l++;
                }else {
                    r--;
                }
            }
        }
        for (List<Integer> li :map.values()){
            list.add(li);
        }
        return list;
    }


    public static int threeSumClosest(int[] nums, int target) {
        /**
         * 思路梳理
         * 本题的要求是找三个与目标值最接近的数，并输出其加和
         *
         * 根据之前的经验可以选择把数组排序和不排序两种算法
         *
         * 不排序算法：
         *      不排序算法需要首先确定数组的左右边界，这样就确定了用到了两个数，然后在左右边界确定的区间内寻找第三个数
         *      这样，对于数组的每一个元素都可能是左边界或者右边界，从而枚举了所有的可能性，当然有些情况不满足题目的要求。
         * 排序算法：
         *      首先对原数组进行排序，这样，整个数组就都是有序的了。考虑一件事情，如果确定了左边界，那么一定会找到右边界
         *      和第三个数，使得这三个数构成的结果与目标值最小。在寻找右边界和第三个数的时候，问题变成了 在一个排序数组中，
         *      找到与给定值最接近的两个数。这个问题是可以从O(N)时间完成的。
         *
         *      此算法的正确性：对于数组中的元素，每一个元素都会充当左边界，而针对每一个给定的左边界，总能在O(N)的时间内
         *      找到最优的解，这样就覆盖了整个解集
         *
         * 不正确算法：
         *      首先对数组排序，然后确定左右边界，在左右边界的区间里寻找第三个数，找到后根据这三个数与目标值的大小，选择
         *      左指针后移或者右指针前移。这种算法不会找到最优解。因为不能保证组合的完整性。
         *
         */
        //排序，找数，做差
        Arrays.sort(nums);
        if (nums == null || nums.length<2){
            return 0;
        }
        if (nums.length == 3){
            return nums[0]+nums[1] +nums[2];
        }

        int i = 0,l =0,r=0 ;
        int close = 0x7fffffff;
        int result = 0;
        //第一次循环时，固定左边界
        for (;i<nums.length-2;i++){
            l = i+1;
            r=nums.length-1;
            while (l<r){
                int res = nums[i]+nums[l]+nums[r];
               if (Math.abs(target-res)<close){
                   close = Math.abs(target-res);
                   result = res;
               }
               if (target == res){
                   return res;
               }else if (target<res){
                   r--;
               }else{
                   l++;
               }
            }
        }
        return result;
    }


    static int  findMatch(int [] nums ,int target,int l,int r){
        if (target <= nums[l]){
            return l;
        }
        if (target >= nums[r]){
            return r;
        }
        while (r-l>1){
            int mid = l+(r-l)/2;
            if (nums[l]<= target && target<=nums[mid]){
                r = mid;
            }else if (nums[r]>=target && target>=nums[mid]) {
                l =mid;
            }
        }
        if (Math.abs(target-nums[r])>=Math.abs(target-nums[l])){
            return l;
        }else {
            return r;
        }

    }


    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() ==0){
            return 0 ;
        }
        //记录重复的位置,每次重复后更新
        Map<Character,Integer> map = new HashMap();
        int i =0,j=0,sum = 0,max =-1;
        while (j<s.length()){
            if (map.containsKey(s.charAt(j))){
                int index = map.get(s.charAt(j));
                max =Math.max(max,sum);

                if (index>=i) {
                    i = index + 1;
                    sum = j - i + 1;
                }else {
                    sum++;
                }
               map.put(s.charAt(j),j);

            }else {
                map.put(s.charAt(j),j);
                sum++;
            }

            j++;
        }
        max =Math.max(max,sum);
        return max;

    }

    public static List<Integer>  findClosestElements(int[] arr, int k, int x) {
        // 首先二分查到和x差值最小的数，然后从这个最小的数开始左右查找

        List<Integer> list = new ArrayList();

        if (arr == null || arr.length ==0 || arr.length <=k){
            return arrToList(arr);
        }

        if (arr[arr.length-1]<=x){
            int [] res = Arrays.copyOfRange(arr,arr.length-k,arr.length-1);
            return arrToList(res);
        }
        if (arr[0]>=x){
            int [] res = Arrays.copyOfRange(arr,0,k);
            return arrToList(res);
        }

        // 二分查找

        int i = 0,j = arr.length-1;

        int mid=0;

        while (i<j-1){//退出时，得到一个区间
            mid = i + (j-i)/2;

            if (arr[mid] ==x){
                break;
            } else if (arr[mid]<x){
                i = mid;
            }else{
                j = mid;
            }
        }
        //退出时有两种情况  ;双指针扩
        if (i!=j-1){
            i = mid;
            j = mid+1;
        }



        while (k>0){
            if (i>=0 && j<arr.length){
                if (Math.abs(arr[i]-x)<=Math.abs(arr[j]-x)){
                    i--;
                    k--;
                }else {
                    j++;
                    k--;
                }
            }else {
                if (j<arr.length){
                    j++;
                    k--;
                }
                if (i>=0) {
                    i--;
                    k--;
                }
            }
        }
        int [] res = Arrays.copyOfRange(arr,i+1,j);

        return arrToList(res);

    }

    private static  List<Integer> arrToList(int [] arr){
        List<Integer> list = new ArrayList();
        for (int i =0;i<arr.length;i++){
            list.add(arr[i]);
        }
        return list;

    }


    public static  boolean judgeSquareSum(int c) {
        //夹逼法

        if (c<0){
            return false;
        }

        // 从中位数开始，扫描

        long i =0,j=(long)Math.sqrt(c);

        while (i<=j){
            long d = i*i +j*j ;
            if (d == c){
                return true;
            }else if (d>c){
                j--;
            }else {
                i++;
            }
            if (j ==101){
            System.out.println(100);
            }

        }
        return false;


    }


    public static  boolean canPlaceFlowers(int[] flowerbed, int n) {
        //插空
        if (flowerbed == null || flowerbed.length == 0){
            return false;
        }

        // 统计两个1之间有多少个空可以插入
        int i = 0,j = 0,count = 0,sum = n;

        while (j<flowerbed.length){
            // 先数0
            if (flowerbed[j] == 0){
                ++count;
            }
            //全是0
            if (flowerbed[i] ==0 && flowerbed[j] == 0 && j==flowerbed.length-1){
                sum -= (count+1)/2;
            }

            if ((flowerbed[i] == 0 && flowerbed[j] == 1) || (flowerbed[i] == 1 && flowerbed[j] == 0 && j == flowerbed.length-1) ){//                开头为0 或者结尾为0
                sum -= count/2;
                count = 0;

            }
            if (flowerbed[i] == 1 && flowerbed[j] == 1){//统计之间的0的个数
                if (count>=3){
                    sum -= (count-1)/2;
                }
                i = j;
                count = 0;
            }


            j++;

        }
        return sum<=0?true:false;


}



    public static String reverseStr(String s, int k) {
        if (s == null || s.length() == 0 || k <=0){
            return s;
        }
        char [] chs = s.toCharArray();
        int cur = 0;

        while ((cur+2*k)<s.length()){

            reverse(chs,cur,cur+k-1);
            cur += (2*k);
        }
        if (cur+k-1>=s.length()){
            reverse(chs,cur,s.length()-1);
        }else {
            reverse(chs,cur,cur+k-1);
        }
        return new String(chs);


    }
    static void  reverse(char [] chs ,int i,int j){

        while (i<j){
            char tmp = chs[i];
            chs[i] = chs[j];
            chs[j] = tmp;
            i++;
            j--;
        }

    }


    public static int findPairs(int[] nums, int k) {
        //排序，去重，双指针
        //对于重复的来说，每个重复的数字只能用一次，所以
        //还需要检查是否重复
        if  (nums== null || nums.length ==0 ){
            return 0;
        }
        Arrays.sort(nums);
        int i =0,j =0,sum = 0;

        while(i< nums.length && j<nums.length){
            if (j<=i){//保证i<j
                j++;
                continue;
            }
            if (nums[j] - nums[i] == k){//去重
                sum++;
                int tmpi = nums[i],tmpj =nums[j];
                while (i<nums.length && nums[i] == tmpi){
                    i++;
                }
                // 去重j
                while (j<nums.length && nums[j] == tmpj){
                    j++;
                }

            }
            while (i<nums.length && j<nums.length && nums[j] - nums[i] < k){
              j++;
            }
            while (i<nums.length && j<nums.length && nums[j]-nums[i]>k){
                i++;
            }





        }
        return sum;


    }

    public static  boolean checkPerfectNumber(int num) {

        if (num ==0)
            return false;
        int sum =0;

        int i=1,j = num;

        while (i<=j){
            if (num%i==0){
                sum+=i;
                j = num/i;
                if (j != num){
                    sum+=j;
                    j--;
                }
            }
            i++;
        }

        return sum == num;

    }

   static Map<Character,Integer> map = new HashMap();
   static {
        map.put('a',2);
        map.put('s',2);
        map.put('d',2);
        map.put('f',2);
        map.put('g',2);
        map.put('h',2);
        map.put('j',2);
        map.put('k',2);
        map.put('l',2);
        map.put('q',1);
        map.put('w',1);
        map.put('e',1);
        map.put('r',1);
        map.put('t',1);
        map.put('y',1);
        map.put('u',1);
        map.put('i',1);
        map.put('o',1);
        map.put('p',1);
        map.put('z',3);
        map.put('x',3);
        map.put('c',3);
        map.put('v',3);
        map.put('b',3);
        map.put('n',3);
        map.put('m',3);
    }
    public static String[] findWords(String[] words) {
        //核心问题是识别出跨行,快速识别出跨行。
        int row=0;
        boolean flag = false;
        List<String> list = new ArrayList();
        for (String str :words){
             flag = true;
            for (int i = 0;i<str.length();i++){
                char ch = str.charAt(i);
                if (ch>='A' && ch <='Z'){
                    ch+=32;
                }
                if (i ==0){
                    row = map.get(ch);
                }
                if (row != map.get(ch)){
                    flag = false;
                    break;
                }
            }
            if (flag){
                list.add(str);
            }
        }
        return list.toArray(new String[0]);

    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 简单题快刷,简单暴力

        int res [] = new int[nums1.length];
        for (int i =0;i<res.length;i++){
            res[i] = -1;
        }

        //预处理nums2
        Map<Integer,Integer> map = new HashMap();
        for (int i = 0;i<nums2.length;i++){
            for (int j = i+1;j<nums2.length;j++){
                if (nums2[j]>nums2[i]){
                    map.put(nums2[i],nums2[j]);
                    break;
                }
            }
            if (!map.containsKey(nums2[i])){
                map.put(nums2[i],-1);
            }
        }



        for (int i =0;i<nums1.length;i++){
           res[i] = map.get(nums1[i]);
        }
        return res;


    }


    public static  int[] constructRectangle(int area) {
        //找约数，然后看是否满足条件、
        // 既然是最合适的，那么可能有多个都符合条件
        int length = 1;
        int suit =0x7fffffff;
        for (int i = area;i>0;i--){
            if (area%i ==0 && area/i<=i){
                if (suit>(i-area/i)){
                    suit = (i-area/i);
                    length = i;
                }

            }
            if (i<area/i){
                break;
            }
        }
        return new int [] {length,area/length};

    }

    public String licenseKeyFormatting(String S, int K) {
        // 首先去破折号，然后加破折号
        if (S == null || S.length() ==0){
            return null;
        }


        StringBuffer sb = new StringBuffer();
        int j = 0;
        for (int i = S.length()-1;i>=0;i--){
            if (S.charAt(i) == '-'){
                continue;
            }
            char ch = S.charAt(i);
            if (ch>='a' && ch<='z'){
                ch -=32;
            }
            sb.append(ch);
            j++;
            if (j == K){
                sb.append("-");
                j=0;
            }

        }
        if (sb.charAt(sb.length()-1)=='-'){
            sb.setLength(sb.length()-1);
        }
            return sb.reverse().toString();


    }

    public static boolean repeatedSubstringPattern(String s) {
        // 从小到大遍历循环，如果有以N为周期的字符串，则返回true;

        StringBuffer sb = new StringBuffer(s);
        Set<String>set = new HashSet();
        for (int i = 1;i<=s.length()/2;i++){
            if (s.length()%i !=0){
                continue;
            }
            set.add(sb.substring(0,i));//左闭右开区间
            for (int j = 0;j<s.length();j+=i){
                if (!set.contains(sb.substring(j,j+i))){
                    break;
                }
                if (j+i == sb.length()){
                    return true;
                }
            }
        }
        return false;

    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        // 完全没有思路
        // 如果是用加和，只能统计目标和与实际和之间的差别，能确定被替换的元素是小替大或者大替小，如果同时
        // 有小替大和大替小的情况，则这种情况是不能确定的。

        // 第三个思路，元素的位置和元素的值进行匹配，没有匹配上的元素就是要找的元素。

        // a[i] = i || a[i] = j && a[j] = j  当前元素已经是正确位置或者 当前元素所指向的元素已经是正确位置，则遍历下一个

        if (nums ==  null || nums.length == 0){
            return null;
        }
        List<Integer> list = new ArrayList();

        int i =0,j=0;

        while (i<nums.length){
            j = nums[i];
            if ((i<nums.length && j<nums.length && (nums[i] == i+1 || nums[j] == j+1)) || j>=nums.length){
                i++;
                continue;
            }
            nums[i] = nums[j-1];
            nums[j-1] = j;
            System.out.println(Arrays.toString(nums));
        }

        for (i = 0;i<nums.length;i++){
            if (nums[i] != i+1){
                list.add(i+1);
            }
        }
        return list;


    }

    public  static List<String> readBinaryWatch(int num) {
            if (num == 0 || num > 10){
                return null;
            }
            int [] leds = new int[10];
            List<String> list = new ArrayList<>();
            getTime(leds,num,0,list);
            return list;
    }

    static  void  getTime(int [] leds,int num,int i,List<String> list){
        //可分配的灯数比剩余的灯数少
        if (num >leds.length -i){
            return;
        }
        //最后一个节点，num == 1 或 0  否则到不了这
        if (i == leds.length-1 || num ==0){
            if (i == leds.length -1 && num != 0){//最后一个灯算上
                leds[i] = 1;
            }
            String time =null;
            if ((time = parseTime(leds)) != null){
                list.add(time);
            }
//            System.out.println(Arrays.toString(leds));
            if (i == leds.length -1 && num !=0){//最后一个灯清零返回
                leds[i] = 0;
            }
            return ;
        }
        //当前灯不算
        getTime(leds,num,i+1,list);
        //当前灯算
        leds [i] =1;
        getTime(leds,num-1,i+1,list);
        leds [i] =0; //退出当前灯时，清除
    }

   static String parseTime(int [] leds){

        int hours = 0;
        int minus = 0;
        int bi = 8;
        for (int i =0;i<4;i++){
            hours += bi*leds[i];
            bi/=2;
        }
        bi = 32;
        for (int i =4;i<leds.length;i++){
             minus+= bi*leds[i];
            bi/=2;
        }
        if (hours >11 || minus>59){
            return null;
        }
        return String.format("%d:%02d",hours,minus);
    }
























    public static int numberOfBoomerangs(int[][] points) {
        //每个点都要和其他的点进行比较，并按距离记录节点的个数
        //如果给定的点的个数少于三个，当然要返回0

        if (points.length <3){
            return 0;
        }

        // 计算每一个节点的距离为n的节点对，放入map

        List<LinkedHashMap <Integer,Integer>> list = new ArrayList<>();
        for (int i = 0;i<points.length;i++){
            LinkedHashMap <Integer,Integer> map = new LinkedHashMap<>();
            for (int j = 0;j<points.length;j++){//条件不满足不会进循环的
                if (i ==j){
                    continue;
                }
                int dis = distance(points,i,j);
                if (map.containsKey(dis)){
                    int sum = map.get(dis);
                    map.put(dis,++sum);
                }else {
                    map.put(dis,1);
                }
            }
            list.add(map);
        }
        //双重循环计算
        int sum = 0;

        for (LinkedHashMap <Integer,Integer> map : list){
             Set<Map.Entry<Integer,Integer>> entrys = map.entrySet();
             for (Map.Entry<Integer,Integer> entry :entrys){
                 sum += entry.getValue()*(entry.getValue()-1);
             }
        }
        return sum;

}

    private static int distance(int [][] points, int x,int  y ){

        return (points[x][0]-points[y][0])*(points[x][0]-points[y][0])+(points[x][1]-points[y][1])*(points[x][1]-points[y][1]);
    }

    public static int countSegments(String s) {
        s = s.trim();
        if (s==null || s.length()==0){
            return 0;
        }
        //当String串按特定字符拆分时，如果左右都有字母 则需统计可能不准
        // s = aaaaaa,,,aaaaa  s.split(",") = 4
        String [] strs = s.split("\\s++");
        return s.split("\\s++").length;

    }


    public static int compress(char[] chars) {
        // 数字时有序的

        int p = 0,q =0,sum = 0;
        char tmp = chars[0];

        while (q < chars.length){
            if (tmp == chars[q]){
                sum++;
            }

            if (tmp!=chars[q] || q == chars.length-1){//最后一次的
                chars[p++] = tmp;
                if (sum !=1) {
                    String s = String.valueOf(sum);
                    for (int i = 0; i < s.length(); i++)
                        chars[p++] = s.charAt(i);
                }
                tmp = chars[q];
                sum = 1;
            }
            q++;
        }
        return p;
    }

    public static String addStrings(String num1, String num2) {
        //字符串相加问题
        //循环加末尾处理
        int flag = 0;

        int i = num1.length()-1,j = num2.length()-1;
        int tmp =0;
        StringBuffer sb = new StringBuffer();
        while (i>=0 && j>=0){
            tmp = (int)(num1.charAt(i)-'0') + (int)(num2.charAt(j) -'0') + flag;
            if (tmp >=10){
                tmp%=10;
                flag =1;
            }else {
                flag = 0;
            }
            sb.append(tmp);
            i--;
            j--;
        }

        while (i>=0){
            tmp = flag + (int)(num1.charAt(i)-'0');
            if (tmp >=10){
                tmp%=10;
                flag =1;
            }else {
                flag = 0;
            }
            sb.append(tmp);
            i--;
        }
        while (j>=0){
            tmp = flag + (int)(num2.charAt(j)-'0');
            if (tmp >=10){
                tmp%=10;
                flag =1;
            }else {
                flag = 0;
            }
            sb.append(tmp);
            j--;
        }
        if (flag == 1){
            sb.append(1);
        }
        return sb.reverse().toString();

    }






}
