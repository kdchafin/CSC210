//the Flower subclass for plant. creates the plant and defines
//the growth pattern for the flower

import javafx.scene.paint.Color;

public class Flower extends Plant{
        
	public Flower(String name) {
		super(name.toLowerCase());
		setSymbol(name.toLowerCase().charAt(0));
		setFamily("flower");
        generateColorFromName(name);
		updatePlot(2,2,this.getSymbol());
	}

    private void generateColorFromName(String name) {
        // convert the name to lower case for consistency
        name = name.toLowerCase();
        
        // generate RGB components based on the sum of ASCII values of the characters in the name
        int red = 50 + (name.hashCode() % 50); //flowers will be more blue colored
        int green = 50 + (name.hashCode() % 50);
        int blue = 100 + (name.hashCode() % 100);
        
        // clamp values between 0 and 255 to ensure valid RGB
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

		this.setColor(Color.rgb(red,green,blue));
    }

    //flowers grow out from their center.
	@Override
    public void grow(int amount) {
        int newSize = this.getSize() + amount;
        this.setSize(newSize);

        // start filling the outermost layer based on the current size
        for (int xPos = 0; xPos < 5; xPos++) {
            for (int yPos = 0; yPos < 5; yPos++) {
                if (!this.isSymbol(xPos, yPos)) {

                    // calculate the Manhattan distance from the center (2, 2)
                    int dist = Math.abs(xPos - 2) + Math.abs(yPos - 2);

                    // Only fill cells on the boundary (where the current distance is equal to the new size)
                    if (dist <= this.getSize() - 1) {
                        // ff the cell is not already a symbol, we place one
                        this.updatePlot(xPos, yPos, this.getSymbol());}
    }}}}
}