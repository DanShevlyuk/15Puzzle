package gui;

import model.FPCell;
import model.FPPuzzle;

/**
 * Created by reqwy on 29.01.2015.
 */
public class CheatedPuzzleForTest extends FPPuzzle{

    public CheatedPuzzleForTest() {
        super();

        for (int i = 0; i < 16; i++) {
            if (i!=14 )
            {
                if (i != 15) {
                    cells.set(i, new FPCell(i, i));
                }

                else {
                    cells.set(i, new FPCell(14, i));
                }
            } else {
                cells.set(i, new FPCell(-1, i));
            }
        }
    }
}
