package com.peaceful.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component  // 容器管理
public class SensitiveFilter {
    /**
     *  1、定义前缀树
     *  2、根据敏感词，初始化前缀树
     *  3、编写过滤敏感词的方法
     */

    // 定义前缀树 (内部类)
    private class TrieNode{
        // 关键词结束标识(是否是叶子节点，一个字符串的结尾)
        private boolean isKeyWordEnd = false;

        // 子节点(key是下级字符，value是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        // 添加子节点
        public void setSubNodes(Character c, TrieNode node){
            subNodes.put(c, node);
        }
        // 获取子节点
        public TrieNode getSubNodes(Character c){
            return subNodes.get(c);
        }
        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
    }

    // 实例化一个log打印日志
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换常量
    private static final String REPLACEMENT = "***";

    // 初始化
    // 根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct  // 实例化SensitiveFilter的bean后，自动调用
    public void init(){
        // 获取类加载器，从类路径下去加载资源classes目录下
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                // 字节流转换成字符流
                // 缓存流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword; // 读取出来的每一行
            while((keyword = reader.readLine())!=null){
                // 添加到前缀树
                this.addKeyWord(keyword);
            }
        }catch (IOException e){
            logger.error("加载敏感词文件失败："+e.getMessage());
        }
    }

    // 将敏感词字符串添加到前缀树中方法
    private void addKeyWord(String keyword){
        // 指针
        TrieNode tempNode = rootNode;
        for(int i=0; i<keyword.length(); i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNodes(c);
            if(subNode == null){
                // 初始化子节点
                subNode = new TrieNode();
                tempNode.setSubNodes(c, subNode);
            }

            // 指针，指向子节点，进入下一轮循环
            tempNode = subNode;
            // 设置结束标识, i游走在该字符串的最后一个节点
            if(i==keyword.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    /**
     *     过滤敏感词的方法
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        if(StringUtils.isBlank(text))
            return null;
        // 指针1
        TrieNode tempNode = rootNode;
        // 指针2
        int begin = 0;
        // 指针3
        int end = 0;
        // 记录结果
        StringBuilder sb = new StringBuilder();

        while(end < text.length()){
            char c = text.charAt(end);

            // 跳过符号（敏感词中间穿插符号，避免干扰）
            if(isSymbol(c)){
                // 若指针1处于根节点，将此符号计入结果，让指针2向下走一步
                // 满足两种情况：一个是根节点，一个是开※票，2指向开，3指向※
                if(tempNode == rootNode){
                    sb.append(c);  // 保留特殊符号
                    begin++;
                }
                end++;  // 无论符号在开头或中间，3都向下走一步
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNodes(c);
            if (tempNode == null){
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                end = ++begin;
                // 重新指向根节点
                tempNode = rootNode;
            }else if(tempNode.isKeyWordEnd()){
                // 发现敏感词，将begin~end字符串给替换掉
                sb.append(REPLACEMENT);
                // 进入下一个位置
                begin = ++end;
                // 重新指向根节点
                tempNode = rootNode;
            }else {
                // 检查下一个字符
                end++;
            }
        }
        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }
    // 判断是否为符号
    private boolean isSymbol(Character c){
        // 普通字符123xyz返回true，特殊字符返回false
        // 0x2E80~0x9FFF 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }
}
