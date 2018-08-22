
package com.hubu.commonExercise.dynamincProgramming;

public class Main_01背包问题 {

     /**
      * @param key  对象的一个属性
      * @param value   对象的另一个矛盾的属性
      *  @param threshold  问题的阈值
      * */
     static void  pkgProblem(int[] key,int[] value,int threshold){
         if(key == null || value == null) return;
         int length = key.length;
         //将传入的物品的对象赋值一份
         int[] key_copy  = new int[length + 1];
         key_copy[0] = 0;
         int[] value_copy = new int[length + 1];
         value_copy[0] = 0;
         for(int i = 0;i < length;i++){
             key_copy[i + 1] = key[i];
             value_copy[i + 1] = value[i];
         }
         //创建一个关于对象数和阈值的一个数组
         //math[i][j]表示对于第i件物品，容量为j时能获得的最大价值
         int[][] math = new int[length + 1][threshold + 1];
         for(int i = 1;i <= length;i++){
             for(int j = 1;j <= threshold;j++){
                 //
                 if(j >= key_copy[i]){
                     math[i][j] = Math.max(math[i - 1][j],math[i - 1][j - key_copy[i]] + value_copy[i]);
                 }else{
                     math[i][j] = math[i - 1][j];
                 }
             }
         }
         //输出选择
          int[]  isChosed = new int[length+1];
          for(int i = length;i > 1;i--){
              //比较每件物品
              if(math[i][threshold] == math[i - 1][threshold]){
                  isChosed[i] = 0;
              }else{
                  isChosed[i] = 1;
                  threshold -= key_copy[i];
              }
          }
          isChosed[1] = math[1][threshold] > 0? 1:0;

         for (int k:isChosed
              ) {
             System.out.print(k+"  ");
         }
     }


    public static void main(String[] args) {

        int[] weight = {4, 6, 2, 2, 5, 1};   //每件物品的重量
        int[] value = {8, 10, 6, 3, 7, 2};    //每件物品对应的价值
        int c = 12;          //背包的最大容量
        pkgProblem(weight,value,c);

    }


}
