import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by schult on 10/26/16.
 */
public class GameTest {
    @Test
    void something() {
        Game game = new Game();
        List<String> questions = new ArrayList<>();

        game.addCategory("Foo", questions);

        // Private, also no querying API: game.askQuestion();
        //assertTrue(???)
    }
}