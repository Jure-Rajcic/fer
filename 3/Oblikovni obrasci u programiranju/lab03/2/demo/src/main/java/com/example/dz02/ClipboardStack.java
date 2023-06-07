package com.example.dz02;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.example.dz02.interfaces.ClipboardObserver;

public class ClipboardStack {

    private Stack<String> texts;
    private List<ClipboardObserver> observers;

    public ClipboardStack() {
        texts = new Stack<>();
        this.observers = new LinkedList<>();
    }

    public void push(String text) {
        texts.push(text);
        notifyObservers();
    }

    public String pop() {
        String text = texts.pop();
        notifyObservers();
        return text;
    }

    public String peek() {
        return texts.peek();
    }

    public boolean isEmpty() {
        return texts.isEmpty();
    }

    public void clear() {
        texts.clear();
    }

    public void addObserver(ClipboardObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ClipboardObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ClipboardObserver observer : observers) {
            observer.updateClipboard();
        }
    }

    @Override
    public String toString() {
        return texts.toString();
    }


    
}
