import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class StackManager {
    private Map<String, Stack<Car>> stacks;

    public StackManager() {
        stacks = new HashMap<>();
    }

    public void createStack(String name, int capacity) {
        stacks.put(name, new Stack<>(capacity));
    }

    @SuppressWarnings("unchecked")
    public Stack<Car> getStack(String name) {
        return (Stack<Car>) stacks.get(name);
    }

    public boolean hasStack(String name) {
        return stacks.containsKey(name);
    }

    public void saveStack(String name, String filename) throws IOException {
        Stack<Car> stack = getStack(name);
        if (stack == null) {
            throw new NoSuchElementException("Stack not found: " + name);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(stack);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadStack(String name, String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Stack<Car> loadedStack = (Stack<Car>) ois.readObject();
            stacks.put(name, loadedStack);
        }
    }

    public String[] getStackNames() {
        return stacks.keySet().toArray(new String[0]);
    }
}