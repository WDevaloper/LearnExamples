package com.github.jvmdemo.algorithm.stack;

import java.util.Arrays;
import java.util.LinkedList;

public class StackTest {
    public Boolean validCharacters(String origin) {
        LinkedList<Character> stack = new LinkedList<>();
        for (char c : origin.toCharArray()) {
            if (c == '[') stack.push(']');
            else if (c == '(') stack.push(')');
            else if (c == '{') stack.push('}');
            else if (stack.isEmpty() || c != stack.pop()) return false;
        }
        return stack.isEmpty();
    }


    //当天最大的温度在栈中  [23, 25, 21, 19, 22, 26, 23]

    /*

     [23, 25, 21, 19, 22, 26, 23]
      0   1   2   3   4   5   6
      当 i=0 时，单调栈为空，因此将0进栈。
      stack=[0(23)]
      ans=[0,0,0,0,0,0,0]

      当 i=1 时 top = 0(23)， 25 大于 23，因此移除栈顶元素 0，赋值 ans[0]=1-0，将 1 进栈。
      stack=[1(25)]
      ans=[1,0,0,0,0,0,0]

      当 i=2 时 top = 1(25)， 21 小于 25，因此将 2 进栈
      stack=[1(25)，2(21)]
      ans=[1,0,0,0,0,0,0]

      当 i=3 时 top = 2(21)， 19 小于 21.因此将 3 进栈
      stack=[1(25)，2(21)，3(19)]
      ans=[1,0,0,0,0,0,0]

       当 i=4 时 top = 3(19)， 22 大于 19.因此移除栈顶元素 3，赋值 ans[3]=4-3。
       stack=[1(25)，2(21)]
       ans=[1,0,0,1,0,0,0]

       当 i=4 时 top = 2(21)， 22 大于 21.因此移除栈顶元素 2，赋值 ans[2]=4-2，将 4 进栈。
       stack=[1(25)]
       ans=[1,0,2,1,0,0,0]

       当 i=4 时,top = 1(25)， 22 小于 25.因此将 4 进栈
       stack=[1(25),4(22)]
       ans=[1,0,2,1,0,0,0]

       当 i=5 时， 26 大于 22.因此移除栈顶元素 4，赋值 ans[4]=5-4，将 5 进栈。
       stack=[1(25)]
       ans=[1,0,2,1,1,0,0]

       当 i=5 时， 26 大于 25.因此移除栈顶元素 1，赋值 ans[1]=5-1，将 5 进栈。
       stack=[5(26)]
       ans=[1,4,2,1,1,0,0]

       当 i=6 时， 23 小于 26.因此将 6 进栈
       stack=[5(26),6(23)]
       ans=[1,4,2,1,1,0,0]

     */
    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < temperatures.length; i++) {
            System.out.println("当 i = " + i);
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                res[stack.peek()] = i - stack.pop();
            }

            stack.push(i);
            System.out.println("stack = " + stack.toString() + " , top = " + temperatures[stack.peek()]);
        }
        return res;
    }
}
