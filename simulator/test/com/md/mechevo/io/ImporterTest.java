package com.md.mechevo.io;

import com.md.mechevo.game.Map;
import com.md.mechevo.game.State;
import org.junit.Assert;
import org.junit.Test;

public class ImporterTest {

    /**
     * Uses all required JSON params.
     */
	@Test
	public void testStateCreation() {
        final String JSON = "{" +
                            "  \"map\": {" +
                            "    \"width\": 800," +
                            "    \"height\": 800" +
                            "  }," +
                            "  \"obstacles\": [" +
                            "    {" +
                            "      \"x\": 50," +
                            "      \"y\": 50," +
                            "      \"radius\": 10" +
                            "    }" +
                            "  ]," +
                            "  \"players\": [" +
                            "    {" +
                            "      \"teamId\": 0," +
                            "      \"weapon\": [" +
                            "        \"\"," +
                            "        \"\"," +
                            "        \"\"" +
                            "      ]," +
                            "      \"x\": 100," +
                            "      \"y\": 200," +
                            "      \"angle\": 50," +
                            "      \"algorithm\": [" +
                            "        {" +
                            "          \"conditions\": [" +
                            "            {" +
                            "              \"name\": \"TrueCondition\"," +
                            "              \"param\": \"\"" +
                            "            }" +
                            "          ]," +
                            "          \"actions\": [" +
                            "            {" +
                            "              \"name\": \"Attack\"," +
                            "              \"param\": \"\"" +
                            "            }" +
                            "          ]" +
                            "        }" +
                            "      ]" +
                            "    }" +
                            "  ]" +
                            "}";
        State state = Importer.createInitialState(JSON);

        // Projectiles don't exist at this moment
        Assert.assertTrue(state.getProjectiles().isEmpty());

        /*
            Asserts related to Map
         */
        Map map = state.getMap();
        Assert.assertTrue(map.getWidth() == 800);
        Assert.assertTrue(map.getHeight() == 800);

        // 1 Player, 1 Obstacle
        final int NUM_ELEMENTS = 2;
        Assert.assertTrue(map.getElements().size() == NUM_ELEMENTS);

        // TODO to finish test
	}

    /**
     * Verifies error handling.
     */
    @Test
    public void testErrorHandling() {
        // TODO
    }

    /**
     * Verifies that all solids are calling Solid#begin().
     */
    @Test
    public void testEventReports() {
        // TODO
    }
}
