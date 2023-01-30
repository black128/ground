package com.zrw.playground;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
class PlaygroundApplicationTests {

    @Test
    public void test3(){
        BigDecimal obj1 = new BigDecimal("1.0");
        BigDecimal obj2 = new BigDecimal("3.0");
//        ComparableUtils.le(BigDecimal.class, obj2);
        System.out.println(CompareToBuilder.reflectionCompare(obj1,obj2));
    }

    /**
     * 合并有序数组
     */
    @Test
    public void test11(){
        int[] num1 = {1,5,7,8,11,44};
        int[] num2 = {2,4,6,7,9,55,88};
        merge(num1,num1.length,num2,num2.length);


    }
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 确定指针位置
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;

        while ((p1 >= 0) && (p2 >= 0)) {
            nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--] : nums1[p1--];
        }
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }
    @Test
    public void test2() {
        String[] arr = new String[]{"010", "011", "012", "013", "014"};
        Random random = new Random();
        // 获取值：arr[random.nextInt(5)]
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

    }

    @Test
    public void test() {
//        int[] nums = new int[]{2, 7, 11, 15};
//        int target = 9;
//        System.out.println(Arrays.toString(twoSum(nums, target)));
//        ListNode l1 = new ListNode(2);
//        ListNode l2 = new ListNode(4);
//        ListNode l3 = new ListNode(3);
//        ListNode l4 = new ListNode(5);
//        ListNode l5 = new ListNode(6);
//        ListNode l6 = new ListNode(4);
//
//        l1.next = l2;
//        l2.next = l3;
//        l4.next = l5;
//        l5.next = l6;
//
//        System.out.println(addTwoNumbers(l1, l4));

//        System.out.println(lengthOfLongestSubstring(" "));
    }
    public String countAndSay(int n) {

        String finalString = "1";

        if (n == 1) {
            return finalString;
        }

        int characterPointer = 0;
        int countPointer = 0;
        StringBuilder stringInProgress = new StringBuilder();

        while (n > 1) {
            while (countPointer < finalString.length()) {
                while (finalString.charAt(characterPointer) == finalString.charAt(countPointer)) {
                    countPointer++;
                }

                stringInProgress.append((countPointer - characterPointer));
                stringInProgress.append(finalString.charAt(characterPointer));
                characterPointer = countPointer;
            }

            finalString = stringInProgress.toString();
            stringInProgress = new StringBuilder();
            n--;
            characterPointer = 0;
            countPointer = 0;
        }

        return finalString;
    }


    //滑动窗口
    public int lengthOfLongestSubstring(String s) {

//        int count = 0;
//        for (int i = 0; i < s.length(); i++) {
//            int tmp = 1;
//            for (int j = i + 1; j < s.length(); j++) {
//                if (s.substring(i, j).contains(s.substring(j,j+1))) {
//                    break;
//                } else {
//                    tmp++;
//                }
//            }
//            count = Math.max(tmp, count);
//        }
//        return count;
        int left = 0;
        int right = 0;
        Set<Character> set = new HashSet<>();
        int maxSubstringLength = 0;

        while (right < s.length()) {
            if (!set.add(s.charAt(right))) {
                maxSubstringLength = Math.max(maxSubstringLength, set.size());
                right++;
            } else {
                set.remove(s.charAt(left));
                left++;
            }
        }
        return maxSubstringLength;
    }

    public int[] twoSum(int[] nums, int target) {
//        for (int i = 0; i < nums.length-1; i++) {
//            for (int j = i+1; j < nums.length; j++) {
//                if(nums[i]+nums[j] == target){
//                    return new int[]{i,j};
//                }
//            }
//        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return null;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(-1);
        ListNode l = dummyHead;
        int carry = 0;

        while (l1 != null || l2 != null) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            l.next = new ListNode(sum % 10);
            l = l.next;
            carry = sum / 10;
        }

        if (carry != 0)
            l.next = new ListNode(carry);

        return dummyHead.next;

    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }
}
