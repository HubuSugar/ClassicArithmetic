package com.hubu.commonExercise.dynamincProgramming;

/**
 * 有ABCDE5个人分别喜欢0,1,2,3,4本中的几本，怎么才能得到皆大欢喜的分书方案
 * */
public class Main_分书问题 {
    int n;    //用来记录方案的总数
    private int[]  take = {-1,-1,-1,-1,-1};   //take矩阵表示这本书分给了那个人，比如take[3] = 5表示第三本书分给了第5个人
    boolean[][] like = {
            {false,false,true,true,false},
            {true,true,false,false,true},
            {false,true,true,false,true},
            {false,false,false,true,false},
            {false,true,false,false,true}
          };                 //like矩阵表示每个人的喜好 ,like[i][j] = true表示第i个人喜欢第j本书

    //动态规划尝试为第i个人分书
     void tryer(int i){
         int j,k;
         for(j = 0;j < 5;j++){
             if(like[i][j]&&take[j] == -1){
                 //如果第i个人喜欢第j本书，并且第j本书刚好
                 take[j] = i;
                 if(i == 5 - 1){
                     //如果已经分配到了第5个人那么就会产生一种分配方案
                     n++;
                     System.out.println("第"+n+"种方案:");
                     System.out.println("书"+"   "+"人");
                     for(k = 0;k < 5;k++){
                         System.out.println(k+"   "+(char)('A'+ take[k]));
                     }
                 }else{
                     //否则如果不是最后一个人那么接着为下一个人找寻分书方案
                     tryer(i + 1);
                 }
                     take[j] = -1;     //同时要换掉第j本书
             }
         }
     }

    public static void main(String[] args) {
       Main_分书问题 main_分书问题 = new   Main_分书问题();
       main_分书问题.tryer(0);
    }
    
}
