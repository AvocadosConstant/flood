package codes.timhung.flood;

import android.widget.ToggleButton;

public class ColorButton {

    ToggleButton button;
    CellColor cellColor;

    public ColorButton(ToggleButton button, CellColor cellColor) {
        this.button = button;
        this.cellColor = cellColor;
    }

    public void setChecked(boolean check) {
        button.setChecked(check);
    }

    public boolean isChecked() {
        return button.isChecked();
    }
}
