package com.hubu.sensitiveFilter;

import org.junit.Before;
import java.util.HashMap;
import java.util.Map;

public class SensitiveFilter {

    private class TrieNode{

      //标记这个词是否是这个前缀树的结尾
      private boolean end = false;
      //用于存储这个字符节点下的字符
      private Map<Character,TrieNode> subNodes = new HashMap<Character,TrieNode>();
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

    @Before
    public void initTree(){


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
}
