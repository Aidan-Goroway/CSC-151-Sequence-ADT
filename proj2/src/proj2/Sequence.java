package proj2;

/*
 *  @author Aidan Goroway 1/21/2023
 * @version 1.61 (Increments by 0.1)

 *  I affirm that I have carried out the attached academic endeavors with full academic honesty, in
 *  accordance with the Union College Honor Code and the course syllabus.
 */

/**
 * Class description:
 * The Sequence class models a sequence of Strings. the sequence has a set capacity that will modify itself when
 * needed, until a hard limit (2^20) is reached. This sequence can be added to and removed from, centered
 * around a specific element marked the "Current Element". A sequence can also be cloned, combined with another
 * sequence, emptied, compared to another sequence for equality, and trimmed.

 * Invariants:
 * size and capacity must be >= 0
 * size must <= capacity
 * CurrentElement must be >= -1. When = -1, there is not currentElement.
 * Capacity and size must < 2^20 (2^31 is the int limit)
 */
public class Sequence {

    // Constants
    public static final int INITIAL_CAPACITY_10 = 10;
    public static final int START_OF_SEQUENCE = 0;
    public static final int NO_CURRENT_ELEMENT = -1;
    public static final int CAPACITY_SIZE_LIMIT = (int) Math.pow(2,20);

    //Instance Variables
    private int size;
    private int capacity;
    private int currentElement;

    private String[] stringHolder;


    /**
     * Creates a new sequence with initial capacity 10.
     */
    public Sequence() { // Default Constructor
        this.stringHolder = new String[INITIAL_CAPACITY_10];
        this.capacity = INITIAL_CAPACITY_10;
        this.size = START_OF_SEQUENCE;
        this.currentElement = NO_CURRENT_ELEMENT;
    }
    

    /**
     * Creates a new sequence.
     * 
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity){ // Capacity Constructor
        this.stringHolder = new String[initialCapacity];
        this.capacity = initialCapacity;
        this.size = START_OF_SEQUENCE;
        this.currentElement = NO_CURRENT_ELEMENT;
    }
    

    /**
     * Adds a string to the sequence in the location before the
     * current element. If the sequence has no current element, the
     * string is added to the beginning of the sequence.

     * The added element becomes the current element.

     * If the sequence's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addBefore(String value){
        if (capacity == this.size()){
            doubleCapacity();
        }
        if (isCurrent()){ // if there is a current element
            for(int index = size(); index != currentElement; index--){
                if (index != START_OF_SEQUENCE){
                    stringHolder[index] = stringHolder[index - 1];
                }
            }
            stringHolder[currentElement] = value;
        }
        else { // if there is no current element
            for (int index = size(); index > START_OF_SEQUENCE; index--){ // from array's end to start
                stringHolder[index] = stringHolder[index - 1];
            }
            stringHolder[START_OF_SEQUENCE] = value;
            currentElement = START_OF_SEQUENCE;
        }
        size += 1;
        this.updateSizeAndCapacity();
    }
    
    
    /**
     * Adds a string to the sequence in the location after the current
     * element. If the sequence has no current element, the string is
     * added to the end of the sequence.

     * The added element becomes the current element.

     * If the sequence's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addAfter(String value){
        if (capacity == size()){
            doubleCapacity();
        }
        if (isCurrent()){
            for (int index = size(); index > currentElement; index--){ // from array's end to CE
                stringHolder[index] = stringHolder[index - 1];
            }
            if (currentElement != size()){ // as long as CE is not last
                currentElement += 1;
            }
            stringHolder[currentElement] = value;
        }
        else { // if there is no current element...
            if (size() == START_OF_SEQUENCE){ // because this is the first addition to an empty sequence
                stringHolder[START_OF_SEQUENCE] = value;
                currentElement = START_OF_SEQUENCE;
            }
            else { // because the previous CE is currently N/A, but there are elements in the sequence
                stringHolder[size()] = value;
                currentElement = size();
            }
        }
        size += 1;
        this.updateSizeAndCapacity();
    }

    /**
     * Doubles the capacity and adds 1 to it.
     */
    private void doubleCapacity(){
        capacity = ((getCapacity()*2) + 1);
        if (capacity > CAPACITY_SIZE_LIMIT){
            capacity = CAPACITY_SIZE_LIMIT;
        }
        this.updateSizeAndCapacity();
    }

    
    /**
     * @return true if and only if the sequence has a current element.
     */
    public boolean isCurrent(){
        if (currentElement > -1){
            return true;
    }
        else {
            return false;
        }
    }
    
    
    /**
     * @return the capacity of the sequence.
     */
    public int getCapacity(){
        return capacity;
    }

    
    /**
     * @return the element at the current location in the sequence, or
     * null if there is no current element.
     */
    public String getCurrent(){
        if (isCurrent()){
            return stringHolder[currentElement];
        }
        else {
            return null;
        }
    }


    /**
     * Returns the integer index position of the CE.
     * If there is no CE, return -1, the position of an absent CE.
     * @return The int index of the CE.
     */
    private int getCurrentPos(){
        if (isCurrent()){
            for (int index = START_OF_SEQUENCE; index < size(); index++){ // from start to end (to avoid nulls)
                if (stringHolder[index].equals(getCurrent())){
                    return index;
                }
            }
        }
        return NO_CURRENT_ELEMENT;
    }

    /**
     * Returns a copy of an array's stringHolder.
     * @param newSequence the copy that does not yet have any elements in its stringHolder.
     * @return a copy with all the elements a Sequences stringHolder.
     */
    private String[] copyStringHolder(Sequence newSequence){
        for(int index = START_OF_SEQUENCE; index < this.size(); index++){
            newSequence.stringHolder[index] = independentStringGetter(index);
        }
        return newSequence.stringHolder;
    }

    /**
     * A getter for an element of a stringHolder.
     * @param index the index of the element you are getting.
     * @return the element at the specified index.
     */
    private String independentStringGetter(int index){
        return stringHolder[index];
    }


    /**
     * Updates the size and capacity of the Sequence by creating a new array
     */
    private void updateSizeAndCapacity(){
        this.capacity = this.getCapacity();
        this.size = this.size();

        String[] newHolder = new String[this.getCapacity()];

        System.arraycopy(this.stringHolder,START_OF_SEQUENCE,newHolder,START_OF_SEQUENCE,this.size());
        this.stringHolder = newHolder;

    }
    
    
    /**
     * Increase the sequence's capacity to be
     * at least minCapacity.  Does nothing
     * if current capacity is already >= minCapacity.
     *
     * @param minCapacity the minimum capacity that the sequence
     * should now have.
     */
    public void ensureCapacity(int minCapacity){
        if (capacity < minCapacity){
            capacity = minCapacity;
        }

        this.updateSizeAndCapacity();
    }

    
    /**
     * Places the contents of another sequence at the end of this sequence.

     * If adding all elements of the other sequence would exceed the
     * capacity of this sequence, the capacity is changed to make room for
     * all of the elements to be added.

     * Postcondition: NO SIDE EFFECTS!  the other sequence should be left
     * unchanged.  The current element of both sequences should remain
     * where they are. (When this method ends, the current element
     * should refer to the same element that it did at the time this method
     * started.)
     *
     * @param another the sequence whose contents should be added.
     */
    public void addAll(Sequence another){
        while ((this.size() + another.size()) > this.getCapacity()){ //while is used in case one double is not enough
            this.doubleCapacity();
        }

        System.arraycopy(another.stringHolder,START_OF_SEQUENCE,this.stringHolder,this.size(),another.size());
        this.size += another.size();
        this.updateSizeAndCapacity();
    }

    
    /**
     * Move forward in the sequence so that the current element is now
     * the next element in the sequence.
     *
     * If the current element was already the end of the sequence,
     * then advancing causes there to be no current element.
     *
     * If there is no current element to begin with, do nothing.
     */
    public void advance(){
        if (isCurrent()){
            if (currentElement != (size() - 1)){
                currentElement += 1;
            }
            else {
                currentElement = NO_CURRENT_ELEMENT;
            }
        }
    }

    
    /**
     * Make a copy of this sequence.  Subsequence changes to the copy
     * do not affect the current sequence, and vice versa.
     * 
     * Postcondition: NO SIDE EFFECTS!  This sequence's current
     * element should remain unchanged.  The clone's current
     * element will correspond to the same place as in the original.
     *
     * @return the copy of this sequence.
     */
    public Sequence clone(){

        Sequence newSequence = new Sequence(getCapacity());

        newSequence.stringHolder = copyStringHolder(newSequence);
        newSequence.currentElement = this.getCurrentPos();
        newSequence.capacity = this.getCapacity();
        newSequence.size = this.size();

        return newSequence;
    }
   
    
    /**
     * Remove the current element from this sequence.  The following
     * element, if there was one, becomes the current element.  If
     * there was no following element (current was at the end of the
     * sequence), the sequence now has no current element.
     *
     * If there is no current element, does nothing.
     */
    public void removeCurrent(){
        if (isCurrent()) {
            if (currentElement != (size() - 1)){ // if CE is not last
                for (int index = currentElement; index < (size() - 1); index++){ // from CE to end
                    stringHolder[index] = stringHolder[index + 1];
                }
            }

            else { // if the last element is removed
                currentElement = NO_CURRENT_ELEMENT;
            }

            size -= 1;
            this.updateSizeAndCapacity();
        }
    }

    
    /**
     * @return the number of elements stored in the sequence.
     */
    public int size(){
        return size;
    }

    
    /**
     * Sets the current element to the start of the sequence.  If the
     * sequence is empty, the sequence has no current element.
     */
    public void start(){
        if (!isEmpty()){ // if not empty
            currentElement = START_OF_SEQUENCE;
        }
        else { // if empty
            currentElement = NO_CURRENT_ELEMENT;
        }
    }

    
    /**
     * Reduce the current capacity to its actual size, so that it has
     * capacity to store only the elements currently stored.
     */
    public void trimToSize(){
        capacity = size();
        this.updateSizeAndCapacity();
    }
    
    
    /**
     * Produce a string representation of this sequence.  The current
     * location is indicated by a >.  For example, a sequence with "A"
     * followed by "B", where "B" is the current element, and the
     * capacity is 5, would print as:
     * 
     *    {A, >B} (capacity = 5)
     * 
     * The string you create should be formatted like the above example,
     * with a comma following each element, no comma following the
     * last element, and all on a single line.  An empty sequence
     * should give back "{}" followed by its capacity.
     * 
     * @return a string representation of this sequence.
     */
    public String toString(){
        String sequenceString = "{";
        for (int index = START_OF_SEQUENCE; index <= (size() - 1); index++){
            if (index == currentElement){
                sequenceString += ">";
            }

            if (stringHolder[index] != null){ // if an element is not null
                sequenceString += stringHolder[index];
            }

            if ((index != (size() - 1)) && (stringHolder[index] != null)){ //if index is not last
                sequenceString += ", ";
            }
        }

        sequenceString += "} (capacity = ";
        sequenceString += this.getCapacity();
        sequenceString += ")";

        return sequenceString;
    }
    
    /**
     * Checks whether another sequence is equal to this one.  To be
     * considered equal, the other sequence must have the same size
     * as this sequence, have the same elements, in the same
     * order, and with the same element marked
     * current.  The capacity can differ.
     * 
     * Postcondition: NO SIDE EFFECTS!  this sequence and the
     * other sequence should remain unchanged, including the
     * current element.
     * 
     * @param other the other Sequence with which to compare
     * @return true iff the other sequence is equal to this one.
     */
    public boolean equals(Sequence other){
        if ( (this.size() == other.size()) && // size equal AND
                ( (this.getCurrent() == null || other.getCurrent() == null) // either CE are null OR...
                        || this.getCurrent().equals(other.getCurrent())) ){ //both CE are equal
            //IF size matches AND (either CE are null OR both CE match)

            if ((this.getCurrent() == null && other.getCurrent() != null) ||
                    (this.getCurrent() != null && other.getCurrent() == null)){
                // if CE1 is null but CE2 is "real", or vise versa.
                return false;
            }

            for (int index = START_OF_SEQUENCE; index < (size() - 1); index++){
                //goes from beginning to end if CE and Size are equal
                if (!this.stringHolder[index].equals(other.stringHolder[index])){ //if an element does not match
                    return false;
                }
            }

            return true; // if all elements match
        }
        else { // if size or CE do not match
            return false;
        }
    }
    
    
    /**
     * 
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty(){
        if ((size() - 1) < 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     *  empty the sequence.  There should be no current element.
     */
    public void clear(){
        removeCurrent(); // also sets CE to NONE

        for(int index = size() - 1; index >= START_OF_SEQUENCE; index--){
            //removes from end to start
            size -= 1;
        }

        updateSizeAndCapacity();
    }

}
