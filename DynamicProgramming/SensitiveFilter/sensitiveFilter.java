package com.hubu.sensitiveFilter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SensitiveFilter{

    //定义前缀树的数据结构
    private class TrieNode{
        //标记这个词是否是这个前缀树的结尾
        private boolean end = false;
       //用于存储这个字符节点下的字符
       private Map<Character,TrieNode>  subNodes = new HashMap<Character,TrieNode>();
       public void addSubNodes(Character key,TrieNode node){
             subNodes.put(key,node);
       }
      //获取下一个结点
        TrieNode getSubNode(Character key){
          return  subNodes.get(key);
        }
       //返回是否是关键字
        boolean isKeyWordEnd(){
          return  end;
        }
       //设置关键字
        void setKeyWordEnd(boolean end){
          this.end = true;
        }
    }

    private TrieNode rootNode = new TrieNode();

    public void init(){
        //读取敏感词的文件
        try{
            InputStream in =  Thread.currentThread().getContextClassLoader().getResourceAsStream("keyWords.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null){
                addWord(lineText.trim());
            }
            in.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void addWord(String lineText){
           TrieNode tempNode = rootNode;
           for(int i = 0;i < lineText.length();++i){
               Character c = lineText.charAt(i);
               TrieNode node = tempNode.getSubNode(c);
               //如果根下没有子节点
               if(node == null){
                   node = new TrieNode();
                   //如果没有，就构造一个子节点加入到tempNode下
                   tempNode.addSubNodes(c,node);
               }
               //如果有子节点
               tempNode = node;
               if(i == lineText.length() - 1){
                   tempNode.setKeyWordEnd(true);
               }
           }
    }

    public String filter(String text){

        if(text == null){
            return "";
        }
        StringBuilder result = new StringBuilder();
        String replacement = "***";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

       while(position < text.length()){
           char c = text.charAt(position);
           //position位置字符下的子字符集合
           tempNode =  tempNode.getSubNode(c);
           if(tempNode == null){
               result.append(text.charAt(begin));
               position = begin + 1;
               begin = position;
               tempNode = rootNode;
           }else if(tempNode.isKeyWordEnd()){
                  //发现敏感词
               result.append(replacement);
               position = position + 1;
               begin = position;
               tempNode = rootNode;
           }else {
                ++position;
           }

       }

        //如果遍历完后最后一串没有敏感词将剩余的字符串拼接起来
        result.append(text.substring(begin));
         return result.toString();
    }


    public static void main(String[] args) {

        SensitiveFilter sensitiveFilter = new SensitiveFilter();
        //初始化前缀树
        sensitiveFilter.init();
        System.out.println(sensitiveFilter.filter("你好色情赌博"));
    }

}
