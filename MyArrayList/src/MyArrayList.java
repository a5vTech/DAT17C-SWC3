
@SuppressWarnings("unchecked")
public class MyArrayList<T> implements List {
    private int min;
    private T[] gg = ((T[]) new Object[min]);


    /**
     * This method adds an object to th e last index of the arraylist
     *
     * @param object object
     */
    @Override
    public void add(Object object) {
        int length = gg.length;
        int emptySpaces = 0;

        //Count emptyspaces

        for (Object o : gg) {
            if (o == null) {
                emptySpaces++;
            }
        }

        //If array is full. Make new array with ten more spaces
        if (emptySpaces == 0) {
            // min = (min*3)/2+1; THe official way!
            min += 10;
            T[] tempArray = ((T[]) new Object[min]);

            for (int i = 0; i < gg.length; i++) {
                tempArray[i] = gg[i];
            }

            gg = tempArray;

        }

        //add item to last index
        if (gg[length - emptySpaces] == null) {
            gg[length - emptySpaces] = (T) object;
        } else {
            System.out.println("ERROR NOT EMPTY");
        }


    }


    /**
     * This method removes and object in the ArrayList and moves all elements to the right of it
     * to the left
     *
     * @param index index
     */
    @Override
    public void remove(int index) {

//Remove object at index
        gg[index] = null;

        //Move all objects to the left
        for (int i = index + 1; i < gg.length; i++) {
            gg[i - 1] = gg[i];
        }


    }

    /**
     * This method returns the size of the ArrayList
     *
     * @return int
     */
    @Override
    public int size() {
        int length = 0;
        for (Object o : gg) {
            if (o != null) {
                length++;
            }
        }
        return length;
    }

    /**
     * This method retrieves an object from the ArrayList by index
     *
     * @param index index
     * @return object
     */
    @Override
    public Object get(int index) {
        if (index >= 0 && index < gg.length) {
            if (gg[index] != null) {
                return gg[index];
            } else {
                return "Empty spot here!";
            }
        } else
            throw new IndexOutOfBoundsException("Index out of bounds!");
    }


    /**
     * This method replaces an element in the ArrayList by index and object to replace
     *
     * @param index index
     * @param object object
     */
    @Override
    public void set(int index, Object object) {
        if (index >= 0 && index < gg.length) {
            gg[index] = (T) object;
        } else {
            System.out.println("Index out of bounds!");
        }


    }

    @Override
    public String toString() {

        for (Object o : gg) {
            if (o != null) {
                System.out.println(o);
                System.out.println(" ");
            }
        }
        return "";
    }
}

