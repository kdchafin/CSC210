//the tree subclass for plant. creates the plant and defines
//the growth pattern for the tree

import javafx.scene.paint.Color;

public class Tree extends Plant{

	public Tree(String name) {
		super(name.toLowerCase());
		setSymbol(name.toLowerCase().charAt(0));
		setFamily("tree");
		updatePlot(4,2,super.getSymbol());
		generateColorFromName(name);
	}

	private void generateColorFromName(String name) {
        // convert the name to lower case for consistency
        name = name.toLowerCase();
        
        // generate RGB components based on the sum of ASCII values of the characters in the name
        int red = 100 + (name.hashCode() % 100); //plants will have a more reddish color
        int green = 50 + (name.hashCode() % 50);
        int blue = 50 + (name.hashCode() % 50);
        
        // clamp values between 0 and 255 to ensure valid RGB
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

		this.setColor(Color.rgb(red,green,blue));
    }

	//trees go straight up based on size.
	@Override
	public void grow(int amount) {
		int newSize = this.getSize() + amount;
		this.setSize(newSize);

		//grow up from 4(bottom) to 0(top)
		for (int xPos = 0; xPos < this.getSize(); xPos++) {
			updatePlot(4 - xPos, 2, this.getSymbol());}
	}
}