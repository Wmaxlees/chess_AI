package org.ucdenver.leesw.ai;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ucdenver.leesw.ai.pieces.Piece;

public class Controller {
    @FXML
    private TextField tile11;
    @FXML
    private TextField tile12;
    @FXML
    private TextField tile13;
    @FXML
    private TextField tile14;
    @FXML
    private TextField tile15;
    @FXML
    private TextField tile16;
    @FXML
    private TextField tile17;
    @FXML
    private TextField tile18;

    @FXML
    private TextField tile21;
    @FXML
    private TextField tile22;
    @FXML
    private TextField tile23;
    @FXML
    private TextField tile24;
    @FXML
    private TextField tile25;
    @FXML
    private TextField tile26;
    @FXML
    private TextField tile27;
    @FXML
    private TextField tile28;

    @FXML
    private TextField tile31;
    @FXML
    private TextField tile32;
    @FXML
    private TextField tile33;
    @FXML
    private TextField tile34;
    @FXML
    private TextField tile35;
    @FXML
    private TextField tile36;
    @FXML
    private TextField tile37;
    @FXML
    private TextField tile38;

    @FXML
    private TextField tile41;
    @FXML
    private TextField tile42;
    @FXML
    private TextField tile43;
    @FXML
    private TextField tile44;
    @FXML
    private TextField tile45;
    @FXML
    private TextField tile46;
    @FXML
    private TextField tile47;
    @FXML
    private TextField tile48;

    @FXML
    private TextField tile51;
    @FXML
    private TextField tile52;
    @FXML
    private TextField tile53;
    @FXML
    private TextField tile54;
    @FXML
    private TextField tile55;
    @FXML
    private TextField tile56;
    @FXML
    private TextField tile57;
    @FXML
    private TextField tile58;

    @FXML
    private TextField tile61;
    @FXML
    private TextField tile62;
    @FXML
    private TextField tile63;
    @FXML
    private TextField tile64;
    @FXML
    private TextField tile65;
    @FXML
    private TextField tile66;
    @FXML
    private TextField tile67;
    @FXML
    private TextField tile68;

    @FXML
    private TextField tile71;
    @FXML
    private TextField tile72;
    @FXML
    private TextField tile73;
    @FXML
    private TextField tile74;
    @FXML
    private TextField tile75;
    @FXML
    private TextField tile76;
    @FXML
    private TextField tile77;
    @FXML
    private TextField tile78;

    @FXML
    private TextField tile81;
    @FXML
    private TextField tile82;
    @FXML
    private TextField tile83;
    @FXML
    private TextField tile84;
    @FXML
    private TextField tile85;
    @FXML
    private TextField tile86;
    @FXML
    private TextField tile87;
    @FXML
    private TextField tile88;

    @FXML
    private Button nextButton;

    public void nextPressed(ActionEvent ae) {
        this.tile11.setText(generateToken(1, 1));
        this.tile12.setText(generateToken(1, 2));
        this.tile13.setText(generateToken(1, 3));
        this.tile14.setText(generateToken(1, 4));
        this.tile15.setText(generateToken(1, 5));
        this.tile16.setText(generateToken(1, 6));
        this.tile17.setText(generateToken(1, 7));
        this.tile18.setText(generateToken(1, 8));

        this.tile21.setText(generateToken(2, 1));
        this.tile22.setText(generateToken(2, 2));
        this.tile23.setText(generateToken(2, 3));
        this.tile24.setText(generateToken(2, 4));
        this.tile25.setText(generateToken(2, 5));
        this.tile26.setText(generateToken(2, 6));
        this.tile27.setText(generateToken(2, 7));
        this.tile28.setText(generateToken(2, 8));

        this.tile31.setText(generateToken(3, 1));
        this.tile32.setText(generateToken(3, 2));
        this.tile33.setText(generateToken(3, 3));
        this.tile34.setText(generateToken(3, 4));
        this.tile35.setText(generateToken(3, 5));
        this.tile36.setText(generateToken(3, 6));
        this.tile37.setText(generateToken(3, 7));
        this.tile38.setText(generateToken(3, 8));

        this.tile41.setText(generateToken(4, 1));
        this.tile42.setText(generateToken(4, 2));
        this.tile43.setText(generateToken(4, 3));
        this.tile44.setText(generateToken(4, 4));
        this.tile45.setText(generateToken(4, 5));
        this.tile46.setText(generateToken(4, 6));
        this.tile47.setText(generateToken(4, 7));
        this.tile48.setText(generateToken(4, 8));

        this.tile51.setText(generateToken(5, 1));
        this.tile52.setText(generateToken(5, 2));
        this.tile53.setText(generateToken(5, 3));
        this.tile54.setText(generateToken(5, 4));
        this.tile55.setText(generateToken(5, 5));
        this.tile56.setText(generateToken(5, 6));
        this.tile57.setText(generateToken(5, 7));
        this.tile58.setText(generateToken(5, 8));

        this.tile61.setText(generateToken(6, 1));
        this.tile62.setText(generateToken(6, 2));
        this.tile63.setText(generateToken(6, 3));
        this.tile64.setText(generateToken(6, 4));
        this.tile65.setText(generateToken(6, 5));
        this.tile66.setText(generateToken(6, 6));
        this.tile67.setText(generateToken(6, 7));
        this.tile68.setText(generateToken(6, 8));

        this.tile71.setText(generateToken(7, 1));
        this.tile72.setText(generateToken(7, 2));
        this.tile73.setText(generateToken(7, 3));
        this.tile74.setText(generateToken(7, 4));
        this.tile75.setText(generateToken(7, 5));
        this.tile76.setText(generateToken(7, 6));
        this.tile77.setText(generateToken(7, 7));
        this.tile78.setText(generateToken(7, 8));

        this.tile81.setText(generateToken(8, 1));
        this.tile82.setText(generateToken(8, 2));
        this.tile83.setText(generateToken(8, 3));
        this.tile84.setText(generateToken(8, 4));
        this.tile85.setText(generateToken(8, 5));
        this.tile86.setText(generateToken(8, 6));
        this.tile87.setText(generateToken(8, 7));
        this.tile88.setText(generateToken(8, 8));

        //(new Thread(GameLogic.getGame())).run();
    }

    private String generateToken(int x, int y) {
        byte piece = GameLogic.getGame().getBoard().getPieceType(x, y);
        return Piece.getSymbol(piece);
    }
}
