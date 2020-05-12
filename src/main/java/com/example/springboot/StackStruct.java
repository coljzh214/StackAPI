package com.example.springboot;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Repository
public class StackStruct {
    private Stack<String> stack;

    public StackStruct() {
        stack = new Stack();
    }

    // check if the stack is empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int getSize() {
        return stack.size();
    }

    public String peek() {
        return stack.peek();
    }

    public String pop() {
        if (isEmpty()) {
            return null;
        }

        String s = stack.pop();
        return s;
    }

    public void push(String s) {
        stack.push(s);
    }

    public void clear() {
        stack.clear();
    }
}
