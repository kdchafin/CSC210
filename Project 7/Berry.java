//the berry subclass for plant. creates the plant and defines
//the growth pattern for the berry

import javafx.scene.paint.Color;

public class Berry extends Plant {
    public Berry(String name) {
		super(name.toLowerCase());
		setSymbol(name.toLowerCase().charAt(0));
		setFamily("berry");
		generateColorFromName(name);
		updatePlot(2,2,super.getSymbol());
	}
    
    private void generateColorFromName(String name) {
        // convert the name to lower case for consistency
        name = name.toLowerCase();
        
        // generate RGB components based on the sum of ASCII values of the characters in the name
        int red = 100 + (name.hashCode() % 100); //berries will be more purplish
        int green = 50 + (name.hashCode() % 20);
        int blue = 100 + (name.hashCode() % 100);
        
        // clamp values between 0 and 255 to ensure valid RGB
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

		this.setColor(Color.rgb(red,green,blue));
    }

    //berrys grow out in the x axis
	@Override
	public void grow(int amount) {
		int newSize = this.getSize() + amount;
        this.setSize(newSize);

        // Start filling the outermost layer based on the current size
        for (int xPos = 0; xPos < 5; xPos++) {
            if (!this.isSymbol(2, xPos)) {

                // calculate the Manhattan distance from the center (2, 2)
                int dist = Math.abs(xPos - 2);

                // Only fill cells on the boundary (where the current distance is equal to the new size)
                if (dist <= this.getSize() - 1) {
                    // If the cell is not already a symbol, we place one
                    this.updatePlot(2, xPos, this.getSymbol());}
    }}}
}
