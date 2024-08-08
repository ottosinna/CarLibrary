import java.util.ArrayList;
import java.io.Serializable;
import java.util.EmptyStackException;

class Stack<T> implements StackInterface<T>, java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private java.util.ArrayList<T> stack;
    private int capacity;

    public Stack(int capacity) {
        this.capacity = capacity;
        this.stack = new java.util.ArrayList<>(capacity);
    }

    @Override
    public void push(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot push null item");
        }
        if (size() == capacity) {
            throw new IllegalStateException("Stack is full");
        }
        stack.add(item);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(stack.size() - 1);
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.get(stack.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean contains(T item) {
        return stack.contains(item);
    }

    public int getCapacity() {
        return capacity;
    }
}
