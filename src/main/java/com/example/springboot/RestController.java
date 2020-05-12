package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/v1")

public class RestController {

    private final StackStruct stack;

    @Autowired
    public RestController(StackStruct stack) {
        this.stack = stack;
    }

    /**
     * GET endpoint: returns the size
     * of the stack formatted in a JSON structure.
     */
    @GetMapping("/stack/size")
    public Map<String, Integer> getStackSize() {
        int size = stack.getSize();

        Map<String, Integer> response = new HashMap<>();
        response.put("size", size);
        return response;
    }

    /**
     * DELETE endpoint: pops a string from the stack
     * and returns it if stack is non-empty; otherwise
     * return 404 not found status code
     */
    @DeleteMapping("/stack")
    public ResponseEntity<String> pop() {
        String s = stack.pop();
        if (s != null) {
            return ResponseEntity.ok(s);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT endpoint: pushes the given string parameter
     * into the stack
     */
    @PutMapping("/stack/{item}")
    public ResponseEntity<String> push(@PathVariable(value="item") String s) {
        stack.push(s);
        return ResponseEntity.ok(s);
    }

    /**
     * DELETE endpoint: clears the entire stack
     */
    @DeleteMapping("/stack/clear")
    public void clear() {
        stack.clear();
    }
}
