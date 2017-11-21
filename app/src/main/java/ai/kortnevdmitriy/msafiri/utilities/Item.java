package ai.kortnevdmitriy.msafiri.utilities;

import android.graphics.Bitmap;

/**
 * Created by kortn on 11/21/2017.
 */

public class Item {
    public Bitmap image;
    public String title;
    public boolean isSelected;


    public Item(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
