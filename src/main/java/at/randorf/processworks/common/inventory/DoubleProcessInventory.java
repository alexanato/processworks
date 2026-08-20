package at.randorf.processworks.common.inventory;

public class DoubleProcessInventory extends MultiProcessInventory {

    public DoubleProcessInventory(int capacity, int buffer,Runnable onChanged) {
        super(Math.max(2, (capacity + 63) / 64),capacity,2,buffer, onChanged);
    }

    public DoubleProcessInventory(int capacity,int buffer) {
        super( Math.max(2, (capacity + 63) / 64),capacity,2,buffer );
    }
}