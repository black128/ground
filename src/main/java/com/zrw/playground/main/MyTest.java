package com.zrw.playground.main;


import org.junit.Test;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author: zrw
 * @date: 2020/11/19 0019-16:01
 */
public class MyTest {

    @Test
    public void test123() {
        Random random = new Random();
        int i = random.nextInt(10);
        System.out.println(i);

    }


    ThreadLocal<String> tl = new ThreadLocal<>();

    public boolean containsDuplicate(int[] nums) {

//        JSONArray jsonArray = new JSONArray();
//        int length = jsonArray.toArray().length;
//        Set<Integer> set = new HashSet<>();
//        for (int num : nums) {
//            if (set.contains(num)) {
//                return true;
//            } else {
//                set.add(num);
//            }
////            for (int j = i+1; j < nums.length; j++) {
////                if(nums[i] == nums[j]){
////                    return true;
////                }
////            }
//        }
        return false;
    }


    @Test
    public void test() throws ParseException {
//        String s = "abcabcdeabdcssdkldkfsdfksdjf";
//        int i = lengthOfLongestSubstring(s);
//        int j = lengthOfLongestSubstring2(s);
//        System.out.println(i+"---"+j);
//        System.out.println(myAtoi("21474836460"));
//        System.out.println(integerReplacement(8));
//        System.out.println(sortString("aaaabbbbcccc"));
//        String str = "2020-11-25 00:00:00";
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String format = df.format(df.parse(str));
//        System.out.println(format);

        String str1 = new String("123");
        str1 = str1.intern();
        String str2 = new String("123");
        str2 = str2.intern();
        System.out.println(str2 == str1);
    }

    public String sortString(String s) {

        int[] arr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < s.length()) {
            for (int i = 0; i < 26; i++) {
                if (arr[i] > 0) {
                    sb.append((char) (i + 'a'));
                    arr[i]--;
                }
            }
            for (int i = 25; i >= 0; i--) {
                if (arr[i] > 0) {
                    sb.append((char) (i + 'a'));
                    arr[i]--;
                }
            }
        }
        return sb.toString();
    }


    int count = 0;

    public int integerReplacement(int n) {
        count++;
//        JSONObject.parseObject();
        if (n == 1) {
            return 0;
        }
        if (n % 2 == 0) {
            integerReplacement(n / 2);
        } else {
            integerReplacement(n - 1);
        }
        return count;
    }

    public int myAtoi(String s) {
        int res = 0;
        s = s.trim();
        if (s.length() == 0) {
            return 0;
        }
        if (s.startsWith("+") || s.startsWith("-") || Pattern.matches("[0-9]", s.charAt(0) + "")) {
            StringBuilder sb = new StringBuilder();
            sb.append(s.charAt(0));
            for (int i = 1; i < s.length(); i++) {
                if ((s.charAt(i) + "").matches("[0-9]")) {
                    sb.append(s.charAt(i));
                } else if (" ".equals(s.charAt(i) + "")) {
                } else {
                    break;
                }
            }
            try {
                res = Integer.parseInt(sb.toString());
            } catch (NumberFormatException e) {
                if ((sb.toString().startsWith("+") || (s.charAt(0) + "").matches("[0-9]")) && sb.length() > 1) {
                    res = Integer.MAX_VALUE;
                } else if (sb.toString().startsWith("-") && sb.length() > 1) {
                    res = Integer.MIN_VALUE;
                } else {
                    res = 0;
                }
            }
        }
        return res;
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                left = Math.max(left, map.get(s.charAt(i)));
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left);
        }
        return max;
    }

    public int lengthOfLongestSubstring2(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int left = 0;
        int max = 0;
//        Map<Character,Integer> map = new HashMap<>();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                left = Math.max(left, set.size());
//                set.remove(s.charAt(i));
            }
//            if(map.containsKey(s.charAt(i))){
//                left = Math.max(left,map.get(s.charAt(i))+1);
//            }
//            map.put(s.charAt(i),i);
//            max = Math.max(max,i-left+1);
            set.add(s.charAt(i));
            max = Math.max(left, i - left);
        }
        return max;
    }

    public int a(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

}
